package com.mysynergis.soe.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.esri.arcgis.server.SOIHelper;
import com.mysynergis.soe.log.impl.Log;

/**
 * SOE/SOI specific helper util
 *
 * @author h.fleischer
 * @since 08.01.2019
 *
 */
public class ServerUtil {

    public static final String UTF_8 = "UTF-8";

    protected static final String ARCGISHOME_ENV = "AGSSERVER";

    private ServerUtil() {
        // no public instance
    }

    /**
     * read the ArcGIS home directory path from the system's environment variables
     *
     * @return
     */
    protected static String getArcGisHomeDir() {

        String arcgisHome = null;
        /* Not found in env, check system property */
        if (System.getProperty(ARCGISHOME_ENV) != null) {
            arcgisHome = System.getProperty(ARCGISHOME_ENV);
        }
        if (arcgisHome == null) {
            /* To make env lookup case insensitive */
            Map<String, String> envs = System.getenv();
            for (Entry<String, String> envEntry : envs.entrySet()) {
                if (envEntry.getKey().equalsIgnoreCase(ARCGISHOME_ENV)) {
                    arcgisHome = envEntry.getValue();
                }
            }
        }
        if (arcgisHome != null && !arcgisHome.endsWith(File.separator)) {
            arcgisHome += File.separator;
        }
        return arcgisHome;

    }

    protected static String getMapServerWsdl(String arcgisHome) {
        return String.format("%sXmlSchema%sMapServer.wsdl", arcgisHome, File.separator);
    }

    /**
     * extracted from the ESRI stub implementation of the SOI,
     * resolves arcgis home dire and build an instance of {@link SOIHelper}
     *
     * @return
     * @throws IOException
     */
    public static SOIHelper createSoiHelper() {

        String arcgisHome = getArcGisHomeDir();
        if (arcgisHome != null) {
            return new SOIHelper(getMapServerWsdl(arcgisHome));
        } else {
            IllegalStateException environmentMissingException = new IllegalStateException("failed to get ArcGIS home directory. please check if environment variable " + ARCGISHOME_ENV + " exists",
                    null);
            Log.getInstance().severe(environmentMissingException);
            throw environmentMissingException;
        }

    }

}
