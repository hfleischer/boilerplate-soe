package com.mysynergis.soe.operation.impl;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.stream.Collectors;

import com.esri.arcgis.server.json.JSONObject;
import com.esri.arcgis.system.ServerUtilities;
import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.config.IOperationConfig;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.operation.IRestOperation;

public class Operation {

    private Operation() {
        //no public instance
    }

    /**
     * create a new soi-delegate rest-operation
     *
     * @param resourceName
     * @param operationName
     * @return
     */
    public static IRestOperation delegate(String resourceName, String operationName) {
        return new RestOperationImplSoiDelegate(resourceName, operationName);
    }

    /**
     * create a new error-operation
     *
     * @param message
     * @param failure
     * @return
     */
    public static IRestOperation error(String message, Exception failure) {
        return new OperationImplFailure(message, failure);
    }

    public static IRestOperation noOpError() {
        return Operation.error("failed to find suitable operation", null);
    }

    public static Optional<IRestOperation> optRestOperation(IOperationConfig operationConfig) {

        String opTypeName = operationConfig.getOperationType();
        JSONObject defsJo = operationConfig.getOperationDefs();
        return optOpType(opTypeName).flatMap(opType -> optRestOperation(opType, defsJo));

    }

    @SuppressWarnings("unchecked")
    protected static Optional<Class<IRestOperation>> optOpType(String opTypeName) {

        Log.getInstance().info("attempting to load op type from name (" + opTypeName + ")");
        try {
            Class<?> opType = Class.forName(opTypeName);
            Log.getInstance().info("checking operation op type for IRestOperation type (" + opType + ")");
            if (IRestOperation.class.isAssignableFrom(opType)) {
                return Optional.of((Class<IRestOperation>) opType);
            } else {
                throw new ParseFailure("invalid op type, must be assignable from " + IOperation.class.getName() + ")", null);
            }
        } catch (Exception ex) {
            Log.getInstance().warn(new ParseFailure("failed to resolve op type", ex));
            return Optional.empty();
        }

    }

    protected static Optional<IRestOperation> optRestOperation(Class<IRestOperation> opType, JSONObject defsJo) {

        try {
            Log.getInstance().info("searching for single json-object constructor on op-type");
            Constructor<IRestOperation> typeConstructor = opType.getConstructor(JSONObject.class);
            if (typeConstructor != null) {
                Log.getInstance().info("found constructor, creating type instance with definition");
                return Optional.of(typeConstructor.newInstance(defsJo));
            } else {
                Log.getInstance().warn(new ParseFailure("failed to find constructor, please ensure that the type " + opType + " has a public constructor taking a single JSONObject instance", null));
                return Optional.empty();
            }
        } catch (Exception ex) {
            Log.getInstance().warn(new ParseFailure("failed to create operation instance", ex));
            return Optional.empty();
        }

    }

    public static JSONObject getOperationJo(IRestOperation operation) {

        String name = operation.getOperationName();
        String parameterList = operation.getParameterNames().stream().collect(Collectors.joining(","));
        String supportedOutputFormatsList = "json";
        return ServerUtilities.createOperation(name, parameterList, supportedOutputFormatsList, false);

    }

}
