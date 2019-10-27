package com.mysynergis.soe.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;
import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.config.IConfiguration;
import com.mysynergis.soe.config.IOperationConfig;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.util.VoidUtil;

/**
 * implementation of {@link IConfiguration}<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class ConfigurationImpl implements IConfiguration {

    private JSONObject rootJo;

    ConfigurationImpl() {

    }

    protected JSONObject getRootJo() {
        return this.rootJo;
    }

    @Override
    public Optional<String> optLaunchersPath() {
        String launchersPath = getRootJo().optString(EKey.LAUNCHERS_PATH.getConstant());
        if (VoidUtil.isNotVoid(launchersPath)) {
            return Optional.of(launchersPath);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<IOperationConfig> getOperationConfigList() {
        JSONArray operationsListJa = getRootJo().optJSONArray(EKey.OPERATION_LIST.getConstant());
        List<IOperationConfig> operationConfigList = new ArrayList<>();
        if (VoidUtil.isNotVoid(operationsListJa)) {
            for (int i = 0; i < operationsListJa.length(); i++) {
                JSONObject operationConfigJo = operationsListJa.getJSONObject(i);
                OperationConfig.optOpConfig(operationConfigJo).ifPresent(operationConfigList::add);
            }
        }
        return operationConfigList;
    }

    @Override
    public void shutdown() throws ShutdownFailure {
        Log.getInstance().info("shutting down SOI/SOE log ...");
        this.rootJo = null;
    }

    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        Log.getInstance().info("constructing SOI/SOE log (no-op) ...");
        try {
            this.rootJo = Configuration.load();
        } catch (Exception ex) {
            throw new ConstructFailure("failed to constructo SOI/SOE properties", ex);
        }
    }

}
