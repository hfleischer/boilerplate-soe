package com.mysynergis.soe.config.impl;

import java.io.IOException;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.config.IConfigLoader;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.properties.IProperty;
import com.mysynergis.soe.properties.impl.Properties;
import com.mysynergis.soe.properties.impl.PropertyKey;
import com.mysynergis.soe.util.FileUtil;

/**
 * helper class used for loading
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class ConfigLoaderImpl implements IConfigLoader {

    @Override
    public JSONObject load() throws ParseFailure {

        try {

            // assume the properties are initialized now
            IProperty<?> configFileProperty = Properties.getInstance().getProperty(PropertyKey.CONFIG);
            if (!configFileProperty.hasValue()) {
                throw new ParseFailure(String.format("the config property (%s) has an invalid/empty value", PropertyKey.CONFIG), null);
            }

            JSONObject configJo = new JSONObject();
            if (!configFileProperty.isDefaultValue()) {

                Log.getInstance().info(String.format("loading SOI/SOE configuration file (path: %s) ...", configFileProperty));

                String configFilePath = String.valueOf(configFileProperty.getValue());
                String configFileContent = FileUtil.readFileContent(configFilePath);

                // read the configuration to JSON (no validation yet)
                configJo = new JSONObject(configFileContent);

            } else {

                Log.getInstance().info(String.format("skipping SOI/SOE configuration file (path: %s) ...", configFileProperty));

            }

            return configJo;

        } catch (IOException ex) {
            throw new ParseFailure("failed to parse the SOE configuration!", ex);
        }

    }

}