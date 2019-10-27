package com.mysynergis.soe.debug.impl;

// ------------------------------------------------------
//
// IMPORTS
//
// ------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.config.impl.Configuration;
import com.mysynergis.soe.debug.IDebug;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.util.FileUtil;

/**
 * This class creates the launch configurations (files). Using a launch template
 *
 * @author h.fleischer
 * @since 28.08.2019
 */
public class DebugImpl implements IDebug {

    /**
     * File with a launch template
     */
    private static final String LAUNCH_TEMPLATE_FILE = "template._launch";

    /**
     * Port
     */
    private static final String PORT_TOKEN = "@PORT@";

    /**
     * When deleting the old launch configuration files, then using this "purge" time span (in millseconds)
     *
     * Default: 2 minutes
     */
    private static final long LAUNCH_FILES_PURGE_TIME_SPAN = 2 * 60 * 1000L;

    /**
     * Temporarily remember path to the launcher files. Used when deleting them.<br>
     */
    protected String launchersPath = "src\\main\\resources\\soidebug";

    /**
     * Temporarily remember name of the service, which launcher files should be created for. Used when deleting them.
     */
    private String launchersService;

    DebugImpl() {

    }

    /**
     * Try to delete old launchers configurations
     */
    @Override
    public void shutdown() {
        Log.getInstance().info("shutting down SOI/SOE debug ...");
        deleteLaunchConfigurations(this.launchersPath, this.launchersService);
    }

    /**
     * Calling when "constructing" (starting) the SOE
     *
     * @param soeConfig Configuration of the SOE
     */
    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {

        Log.getInstance().info("constructing SOI/SOE debug ...");

        String service = System.getProperty("service");
        String port = System.getProperty("debugPort");

        Log.getInstance().info(String.format("service: %s, port: %s", service, port));

        this.launchersPath = Configuration.getInstance().optLaunchersPath().orElseGet(() -> null);
        this.launchersService = service; // remember the name of the service

        if (this.launchersPath != null && service != null && port != null) {

            //cleanup and re-create launch configurations
            deleteLaunchConfigurations(this.launchersPath, service);
            createLaunchConfiguration(this.launchersPath, service, port);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }

    /**
     * Delete (old) launch configurations in the path, before creating new one. Only the launch configurations, which are older than LAUNCH_FILES_PURGE_TIME_SPAN.
     *
     * @param launchersPath Path to the folder with configurations
     * @param service The service, which configurations should be deleted of
     */
    public static void deleteLaunchConfigurations(String launchersPath, final String service) {

        try {

            File dir = new File(launchersPath);

            // -----------------------------------------------
            // Filter out the files, which should be deleted, pattern <service>*.launch
            // -----------------------------------------------

            if (dir.exists()) {

                File[] toBeDeleted = dir.listFiles(new LaunchFileFilter(service));

                // -----------------------------------------------
                // Delete only files older than this purge time
                // -----------------------------------------------

                long purgeTime = System.currentTimeMillis() - LAUNCH_FILES_PURGE_TIME_SPAN;
                for (File file : toBeDeleted) {
                    if (file.lastModified() < purgeTime) {
                        Files.delete(file.toPath());
                    }
                }

            }

        } catch (IOException ioFailure) {
            ShutdownFailure shutdownFailure = new ShutdownFailure("failed to delete launch configurations", ioFailure);
            Log.getInstance().warn(shutdownFailure);
        }

    }

    /**
     * Creating new launch configuration
     *
     * @param launchersPath Path to the folder, where a new configuration should be created
     * @param service Service
     * @param port Port
     */
    public static void createLaunchConfiguration(String launchersPath, String service, String port) throws ConstructFailure {

        File templateFile = new File(launchersPath, LAUNCH_TEMPLATE_FILE);
        File launchFile = new File(launchersPath, String.format("%s_%s.launch", service, port));
        try (BufferedReader templateReader = FileUtil.createReader(templateFile); BufferedWriter launchWriter = FileUtil.createWriter(launchFile)) {

            String line;
            while ((line = templateReader.readLine()) != null) {
                launchWriter.write(String.format("%s%n", line.replaceAll(PORT_TOKEN, port)));
            }

        } catch (IOException ex) {
            throw new ConstructFailure(
                    String.format("failed to create launch configuration (launchersPath: %s, template: %s, service: %s, port: %s)", launchersPath, LAUNCH_TEMPLATE_FILE, service, port), ex);
        }

    }

}
