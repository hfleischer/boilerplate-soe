package com.mysynergis.soe.config.impl;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.config.IConfiguration;
import com.mysynergis.soe.failure.ParseFailure;

/**
 * accessor util to {@link IConfiguration} instance<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Configuration {

    private static IConfiguration instance;

    private Configuration() {
        // no public instance
    }

    /**
     * get the {@link IConfiguration} instance
     *
     * @return
     */
    public static IConfiguration getInstance() {
        if (instance == null) {
            instance = new ConfigurationImpl();
        }
        return instance;
    }

    /**
     * load the json config file residing at the path specified with the "CONFIG" property (SOI property page)
     *
     * @return
     * @throws ParseFailure
     */
    public static JSONObject load() throws ParseFailure {
        return new ConfigLoaderImpl().load();
    }

}
