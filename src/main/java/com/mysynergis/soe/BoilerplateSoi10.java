package com.mysynergis.soe;

import com.esri.arcgis.interop.extn.ArcGISExtension;
import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.system.IObjectConstruct;
import com.esri.arcgis.system.IRESTRequestHandler;
import com.esri.arcgis.system.IRequestHandler;
import com.esri.arcgis.system.IRequestHandler2;
import com.esri.arcgis.system.IWebRequestHandler;
import com.mysynergis.soe.request.ESoType;

/**
 * boilerplace ServerObjectInterceptor (acrobjects)
 *
 * @author h.fleischer
 * @since 08.01.2019
 *
 */
@SuppressWarnings("unused") //IRequestHandler must be included, or SOEPackager will complain about missing interface
@ArcGISExtension
@ServerObjectExtProperties(displayName = ABoilerplateExtension.SOI_NAME + " (arcobjects)", description = ABoilerplateExtension.SOI_DESC + " (arcobjects)", properties = {
        "config=DEFAULT" }, interceptor = true, servicetype = "MapService")
public class BoilerplateSoi10 extends ABoilerplateExtension implements IServerObjectExtension, IRESTRequestHandler, IWebRequestHandler, IRequestHandler, IRequestHandler2, IObjectConstruct {

    private static final long serialVersionUID = 8744787013856323774L;

    @Override
    public ESoType getSoType() {
        return ESoType.SOI;
    }

}