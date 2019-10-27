package com.mysynergis.soe.properties.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.properties.IPropertiesParser;
import com.mysynergis.soe.properties.IProperty;
import com.mysynergis.soe.properties.IPropertyKey;
import com.mysynergis.soe.util.VoidUtil;

/**
 * implementation of {@link IPropertiesParser}<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class PropertiesParserImpl implements IPropertiesParser {

    private static final int KEY_VALUE_COUNT = 2;

    /**
     * with the set of properties already defined in default properties, try to
     * resolve appropriate instacne properties from the given {@link IPropertySet}
     * instance
     *
     * @param defaultProperties
     * @param propertySet
     * @return
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<IProperty> parseInstanceProperties(List<IProperty> defaultProperties, IPropertySet propertySet) throws ParseFailure {

        List<IProperty> instanceProperties = new ArrayList<>();

        Log.getInstance().info("parsing SOI/SOE instance properties ...");

        Object valueRaw;
        boolean isDefaultValue;

        // iterate all properties defined on the class for actually configured value
        for (IProperty<?> defaultProperty : defaultProperties) {
            try {
                valueRaw = propertySet.getProperty(defaultProperty.getKey().getKey());
                if (valueRaw != null) {
                    isDefaultValue = valueRaw.equals(defaultProperty.getValue());
                    Log.getInstance().info(String.format("got SOI/SOE property (%s, value: %s)", defaultProperty.getKey(), String.valueOf(valueRaw)));
                    instanceProperties.add(defaultProperty.getKey().createProperty(String.valueOf(valueRaw), isDefaultValue));
                } else {
                    throw new ParseFailure("failed to get value from property set (property: " + defaultProperty + ")", null);
                }
            } catch (Exception ex) {
                throw new ParseFailure("failed to parse SOI/SOE instance property " + defaultProperty, ex);
            }

        }

        return instanceProperties;

    }

    /**
     * parses the default properties from the {@link ServerObjectExtProperties}
     * annotation
     *
     * @param extensionClass
     * @return
     * @throws IOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<IProperty> parseDefaultProperties(Class<? extends IServerObjectExtension> extensionClass) {

        List<IProperty> defaultProperties = new ArrayList<>();

        Log.getInstance().info("parsing SOI/SOE default properties ...");

        ServerObjectExtProperties extensionProps = extensionClass.getAnnotation(ServerObjectExtProperties.class);
        if (extensionProps != null && extensionProps.properties() != null) {
            defaultProperties.addAll(parseDefaultProperties(extensionProps.properties()));
        }

        return defaultProperties;

    }

    @SuppressWarnings("rawtypes")
    protected List<IProperty> parseDefaultProperties(String[] propertiesRaw) {
        List<IProperty> defaultProperties = new ArrayList<>();
        IProperty<?> property;
        for (String propertyRaw : propertiesRaw) {
            try {
                property = parseDefault(propertyRaw);
                if (property != null) {
                    defaultProperties.add(property);
                }
            } catch (ParseFailure ex) {
                ParseFailure failedToParsePropertyException = new ParseFailure("failed to parse SOI/SOE default property " + propertyRaw, ex);
                Log.getInstance().severe(failedToParsePropertyException);
            }
        }
        return defaultProperties;
    }

    /**
     * parse a single property from "key=value" to an {@link IProperty} instance
     *
     * @param raw
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "static-method" })
    protected IProperty parseDefault(String raw) throws ParseFailure {

        Log.getInstance().fine(String.format("parsing property, raw value: %s", raw));

        if (VoidUtil.isVoid(raw)) {
            return null;
        }

        // split at the "=" character
        String[] split = raw.split("=");
        if (split.length == KEY_VALUE_COUNT) {
            IPropertyKey<?> propertyKey = PropertyKey.fromId(split[0]);
            if (propertyKey != null) {
                return propertyKey.createProperty(split[1], true);
            } else {
                throw new ParseFailure("failed to resolve a property key for the given raw property name (" + split[0] + ")", null);
            }
        } else {
            throw new ParseFailure("invalid raw property format (" + raw + "), a separated key=value pair was expected instead", null);
        }

    }

}
