package com.mysynergis.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.operation.IRestOperation;
import com.mysynergis.soe.operation.IRestOperationInput;
import com.mysynergis.soe.operation.IRestOperationOutput;
import com.mysynergis.soe.operation.impl.Operation;
import com.mysynergis.soe.operation.impl.OperationInput;

/**
 * accepts a configuration of:<br>
 *
 * <pre>
 * {
 *   "path": "[anchor layer id, ie layers/0]",
 *   "name": "applyEdits",
 *   "map_name": "[map name]",
 *   "layer_id_anchor": [anchor layer id],
 *   "layer_id_annotation": [annotation layer id],
 *   "layer_id_labelled": [feature linked annotation source layer id]
 * }
 * </pre>
 *
 * @author h.fleischer
 * @since 15.10.2019
 *
 */
public class RestOperationImplAnchorEdits implements IRestOperation {

    private final String resourceName;
    private final String operationName;

    private final String mapName;
    private final int layerIdAnchor;
    private final int layerIdAnnotation;
    private final int layerIdLabelled;

    public RestOperationImplAnchorEdits(JSONObject defsJo) {
        this.resourceName = defsJo.getString("path");
        this.operationName = defsJo.getString("name");
        this.mapName = defsJo.getString("map_name");
        this.layerIdAnchor = defsJo.getInt("layer_id_anchor");
        this.layerIdAnnotation = defsJo.getInt("layer_id_annotation");
        this.layerIdLabelled = defsJo.getInt("layer_id_labelled");
    }

    @Override
    public String getResourceName() {
        return this.resourceName;
    }

    @Override
    public String getOperationName() {
        return this.operationName;
    }

    @Override
    public List<String> getParameterNames() {
        return Collections.emptyList();
    }

    @Override
    public IRestOperationOutput handleRequest(IRestOperationInput requestedInput) throws IOException {

        IRestOperationInput input = optRewrittenInput(requestedInput).orElseGet(() -> requestedInput);
        IRestOperationOutput operationOutput = Operation.delegate(getResourceName(), getOperationName()).handleRequest(input);
        return () -> operationOutput.getResponseBytes();

    }

    protected Optional<IRestOperationInput> optRewrittenInput(IRestOperationInput requestedInput) {

        JSONObject requestJo = new JSONObject(requestedInput.getOperationInput());
        List<JSONObject> jsonUpdates = findUpdates(requestJo);
        List<JSONObject> jsonUpdatesHavingGeometry = jsonUpdates.stream().filter(jo -> jo.has("geometry")).collect(Collectors.toList());
        if (!jsonUpdatesHavingGeometry.isEmpty()) {

            try {
                AnnotationOffsetUpdater annotationOffsetUpdater = new AnnotationOffsetUpdater(this.mapName, this.layerIdAnchor, this.layerIdAnnotation, this.layerIdLabelled);
                for (JSONObject updateJo : jsonUpdatesHavingGeometry) {
                    annotationOffsetUpdater.calculateAndApplyOffset(updateJo);
                }
            } catch (Exception ex) {
                Log.getInstance().warn(ex);
            }

            //create a new set up updates and rewrite the input
            JSONArray updatesJa = new JSONArray();
            jsonUpdates.forEach(updatesJa::put);
            requestJo.put("updates", updatesJa);

            IRestOperationInput rewrittenInput = OperationInput.create(requestedInput.getCapabilities(), requestJo.toString(), requestedInput.getRequestProperties(), requestedInput.getOutputFormat());
            return Optional.of(rewrittenInput);

        }
        return Optional.empty();

    }

    /**
     * finds all updates contained in the request and collects them in a {@link List} of {@link JSONObject}s
     *
     * @param requestJo
     * @return
     */
    protected static List<JSONObject> findUpdates(JSONObject requestJo) {

        List<JSONObject> jsonUpdates = new ArrayList<>();
        if (requestJo.has("updates")) {
            JSONArray updatesJa = requestJo.getJSONArray("updates");
            for (int i = 0; i < updatesJa.length(); i++) {
                jsonUpdates.add(updatesJa.getJSONObject(i));
            }
        }
        return jsonUpdates;

    }

}
