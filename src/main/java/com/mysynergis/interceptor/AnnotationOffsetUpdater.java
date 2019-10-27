package com.mysynergis.interceptor;

import java.io.IOException;

import com.esri.arcgis.carto.IMapServerDataAccess;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureClassProxy;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.server.IServerObject;
import com.esri.arcgis.server.json.JSONObject;
import com.mysynergis.soe.arcobjects.impl.ArcObjects;
import com.mysynergis.soe.arcobjects.impl.Releaser;
import com.mysynergis.soe.helper.impl.ServerHelper;
import com.mysynergis.soe.log.impl.Log;

/**
 * helper class that gets initialized with some values common to a set of updateable anchor features, then can accept multiple editable features<br>
 *
 * @author h.fleischer
 * @since 17.10.2019
 *
 */
public class AnnotationOffsetUpdater {

    public static final String FIELD_NAME_X_OFFSET = "XOffset";
    public static final String FIELD_NAME_Y_OFFSET = "YOffset";
    public static final String FIELD_NAME_FEATUREID = "FeatureID";

    private final IFeatureClass fcAnchor;
    private final IFeatureClass fcAnnotation;
    private final IFeatureClass fcCity;

    private final int fieldIndexFeatureIdAnchor;
    private final int fieldIndexXOffsetAnnotation;
    private final int fieldIndexYOffsetAnnotation;

    private final String mapName;
    private final int layerIdAnchor;
    private final int layerIdAnnotation;
    private final int layerIdLabelled;

    private final AnnotationOffsetHelper annotationOffsetHelper;

    AnnotationOffsetUpdater(String mapName, int layerIdAnchor, int layerIdAnnotation, int layerIdLabelled) throws IOException {

        this.mapName = mapName;
        this.layerIdAnchor = layerIdAnchor;
        this.layerIdAnnotation = layerIdAnnotation;
        this.layerIdLabelled = layerIdLabelled;

        IServerObject serverObject = ServerHelper.getServerObject();
        IMapServerDataAccess mapServerDataAccess = (IMapServerDataAccess) serverObject;

        this.fcAnchor = new IFeatureClassProxy(mapServerDataAccess.getDataSource(this.mapName, this.layerIdAnchor));
        this.fcAnnotation = new IFeatureClassProxy(mapServerDataAccess.getDataSource(this.mapName, this.layerIdAnnotation));
        this.fcCity = new IFeatureClassProxy(mapServerDataAccess.getDataSource(this.mapName, this.layerIdLabelled));

        this.fieldIndexFeatureIdAnchor = this.fcAnchor.findField(FIELD_NAME_FEATUREID);
        this.fieldIndexXOffsetAnnotation = this.fcAnnotation.findField(FIELD_NAME_X_OFFSET);
        this.fieldIndexYOffsetAnnotation = this.fcAnnotation.findField(FIELD_NAME_Y_OFFSET);

        this.annotationOffsetHelper = new AnnotationOffsetHelper(5000000);

    }

    /**
     * <li>fetches the feature about to be updated and get the associated featureId<br>
     * <li>get the labelled feature (that feature's OBJECTID is the FeauterID of the anchor feature being edited)<br>
     * <li>calculate x- and y-distances between labelled feature and anchor feature and scale to offsets by reference scale<br>
     * <li>find and edit annotation feature<br>
     * <li>rewrite the update-json of the anchor feature being edited<br>
     *
     * @param updateJo a json object describing the updates to be applied to the anchor feature
     * @return
     * @throws IOException
     */
    protected JSONObject calculateAndApplyOffset(JSONObject updateJo) throws IOException {

        //find objectid and coordinates of anchor feature
        JSONObject attributesJo = updateJo.optJSONObject("attributes");
        int objectId = attributesJo.getInt("OBJECTID");
        JSONObject geometryJoAnchor = updateJo.optJSONObject("geometry");

        //get the anchor feature and fetch featureid -> needed to find city and annotation
        IFeature featureAnchor = Releaser.register(this.fcAnchor.getFeature(objectId));
        int featureId = Integer.valueOf(String.valueOf(featureAnchor.getValue(this.fieldIndexFeatureIdAnchor)));

        //the featureId points to the city's objectId
        IFeature featureCity = Releaser.register(this.fcCity.getFeature(featureId));
        IPoint geometryCity = Releaser.register((IPoint) featureCity.getShape());

        double xOffset = this.annotationOffsetHelper.calculateOffset(geometryCity.getX(), geometryJoAnchor.getDouble("x"));
        double yOffset = this.annotationOffsetHelper.calculateOffset(geometryCity.getY(), geometryJoAnchor.getDouble("y"));

        //apply (edit) respective annotation feature
        applyOffsetToAnnotation(featureId, xOffset, yOffset);

        //add some attributes to requesting jeson
        applyOffsetToJson(updateJo, featureId, xOffset, yOffset);

        return updateJo;

    }

    /**
     * apply the given values to the update-json object<br>
     * when done during request-rewriting this will apply these values during the actual edit request
     *
     * @param updateJo
     * @param featureId
     * @param xOffset
     * @param yOffset
     */
    protected static void applyOffsetToJson(JSONObject updateJo, int featureId, double xOffset, double yOffset) {
        JSONObject attributesJo = updateJo.optJSONObject("attributes");
        attributesJo.put(FIELD_NAME_FEATUREID, featureId);
        attributesJo.put(FIELD_NAME_X_OFFSET, xOffset);
        attributesJo.put(FIELD_NAME_Y_OFFSET, yOffset);
    }

    /**
     * <li>searches the annotation feauture having the given featureID<br>
     * <li>applies the given offset to the XOffset and YOffset fields<br>
     * <li>stores the annotation feature (which will automatically update it's internal represenation<br>
     *
     * @param featureId
     * @param xOffset
     * @param yOffset
     * @throws IOException
     */
    protected void applyOffsetToAnnotation(int featureId, double xOffset, double yOffset) throws IOException {

        //setup query-filter
        IQueryFilter queryFilter = ArcObjects.createAndRegister(QueryFilter.class);
        queryFilter.setWhereClause(String.format("FeatureID = %s", featureId)); //improve: find feature-id field and its name instead of hardcoding
        queryFilter.setSubFields("*");

        IFeatureCursor featureCursor = Releaser.register(this.fcAnnotation.IFeatureClass_update(queryFilter, false));
        IFeature featureAnnotation;
        while ((featureAnnotation = Releaser.register(featureCursor.nextFeature())) != null) {
            featureAnnotation.setValue(this.fieldIndexXOffsetAnnotation, xOffset);
            featureAnnotation.setValue(this.fieldIndexYOffsetAnnotation, yOffset);
            featureAnnotation.store();
            Log.getInstance().info("featureAnnotation: " + featureAnnotation);
        }

    }

}
