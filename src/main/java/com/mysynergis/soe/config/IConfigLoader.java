package com.mysynergis.soe.config;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.failure.ParseFailure;

/**
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
@FunctionalInterface
public interface IConfigLoader {

    /**
     * load a {@link JSONObject}
     *
     * @param mappingJo
     * @return
     * @throws ParseFailure
     */
    JSONObject load() throws ParseFailure;

}
