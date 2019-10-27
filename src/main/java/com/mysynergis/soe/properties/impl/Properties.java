package com.mysynergis.soe.properties.impl;

import java.io.IOException;
import java.util.List;

import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.properties.IProperties;
import com.mysynergis.soe.properties.IProperty;

/**
 * accessor util to {@link IProperty} instances<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class Properties {

    private static IProperties instance;

    private Properties() {
        // no public instance
    }

    /**
     * create an instance of {@link IProperties}
     *
     * @return
     */
    public static IProperties getInstance() {
        if (instance == null) {
            instance = new PropertiesImpl();
        }
        return instance;
    }

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
    public static List<IProperty> parseInstanceProperties(List<IProperty> defaultProperties, IPropertySet propertySet) throws ParseFailure {
        return new PropertiesParserImpl().parseInstanceProperties(defaultProperties, propertySet);
    }

    /**
     * parses the default properties from the {@link ServerObjectExtProperties}
     * annotation
     *
     * @param extensionClass
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static List<IProperty> parseDefaultProperties(Class<? extends IServerObjectExtension> extensionClass) {
        return new PropertiesParserImpl().parseDefaultProperties(extensionClass);
    }

}
