package com.mysynergis.soe.properties;

import java.io.IOException;
import java.util.List;

import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.failure.ParseFailure;

/**
 * definition for a helper type for SOE/SOI property parsing<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface IPropertiesParser {

    /**
     * with the set of properties already defined in default properties, try to
     * resolve appropriate instacne properties from the given {@link IPropertySet}
     * instance
     *
     * @param defaultProperties
     * @param propertySet
     * @return
     */
    @SuppressWarnings("rawtypes")
    List<IProperty> parseInstanceProperties(List<IProperty> defaultProperties, IPropertySet propertySet) throws ParseFailure;

    /**
     * parses the default properties from the {@link ServerObjectExtProperties}
     * annotation
     *
     * @param extensionClass
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    List<IProperty> parseDefaultProperties(Class<? extends IServerObjectExtension> extensionClass);

}
