package com.mysynergis.soe.config.impl;

import java.util.Optional;

import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.config.IOperationConfig;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.util.VoidUtil;

/**
 * accessor util to {@link IOperationConfig} instances
 *
 * @author h.fleischer
 * @since 15.10.2019
 *
 */
public class OperationConfig {

    private OperationConfig() {
        //no public instance
    }

    /**
     * create an instance of {@link IOperationConfig} from a single {@link JSONObject}<br>
     * json must follow the convention:<br>
     * {<br>
     * &nbsp;"type": "com.mysynergis.interceptor.RestOperationImplAnchorEdits",<br>
     * &nbsp;"defs": {<br>
     * &nbsp;&nbsp;[definition as suitable for the specific operation type, may also be an empty object in certain cases]<br>
     * &nbsp;}<br>
     * }<br>
     *
     *
     * @param operationConfigJo
     * @return
     */
    public static Optional<IOperationConfig> optOpConfig(JSONObject operationConfigJo) {

        String type = operationConfigJo.optString(EKey.TYPE.getConstant());
        JSONObject defsJo = operationConfigJo.optJSONObject(EKey.DEFS.getConstant());
        if (VoidUtil.isNotVoid(type) && VoidUtil.isNotVoid(defsJo)) {
            return Optional.of(new OperationConfigImpl(type, defsJo));
        } else {
            Log.getInstance().warn(new ParseFailure("failed to build operation config due to invalid configuration params (type: " + type + ", defs: " + defsJo + ")", null));
            return Optional.empty();
        }

    }

}
