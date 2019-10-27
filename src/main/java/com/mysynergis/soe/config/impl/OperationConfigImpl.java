package com.mysynergis.soe.config.impl;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.config.IOperationConfig;

class OperationConfigImpl implements IOperationConfig {

    private final String type;
    private final JSONObject defs;

    OperationConfigImpl(String type, JSONObject defs) {
        this.type = type;
        this.defs = defs;
    }

    @Override
    public String getOperationType() {
        return this.type;
    }

    @Override
    public JSONObject getOperationDefs() {
        return this.defs;
    }

}
