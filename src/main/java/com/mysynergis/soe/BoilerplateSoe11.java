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
 * boilerplace ServerObjectExtension (enterprise)
 *
 * @author h.fleischer
 * @since 13.10.2019
 *
 */
@SuppressWarnings("unused") //IRequestHandler must be included, or SOEPackager will complain about missing interface
@ArcGISExtension
@ServerObjectExtProperties(displayName = ABoilerplateExtension.SOE_NAME + " (enterprise)", description = ABoilerplateExtension.SOE_DESC + " (enterprise)", properties = { "config=DEFAULT" })
public class BoilerplateSoe11 extends ABoilerplateExtension implements IServerObjectExtension, IRESTRequestHandler, IWebRequestHandler, IRequestHandler, IRequestHandler2, IObjectConstruct {

    private static final long serialVersionUID = 4918326454957139135L;

    @Override
    public ESoType getSoType() {
        return ESoType.SOE;
    }

}