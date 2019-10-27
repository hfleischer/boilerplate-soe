package com.mysynergis.soe.config;

import java.util.List;
import java.util.Optional;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.lifecycle.ILifecycle;

/**
 * provides access to the loaded configuration {@link JSONObject} root object
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface IConfiguration extends ILifecycle {

    /**
     * get a configured launchers path
     *
     * @return
     */
    Optional<String> optLaunchersPath();

    /**
     * get a list of configured operation types
     *
     * @return
     */
    List<IOperationConfig> getOperationConfigList();

}
