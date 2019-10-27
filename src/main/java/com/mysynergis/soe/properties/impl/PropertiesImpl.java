package com.mysynergis.soe.properties.impl;

import java.util.ArrayList;
import java.util.List;

import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.ABoilerplateExtension;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ParseFailure;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.properties.IProperties;
import com.mysynergis.soe.properties.IProperty;
import com.mysynergis.soe.properties.IPropertyKey;

/**
 * implementation of {@link IProperties}<br>
 * this class servers as a container for the properties<br>
 * the actual work is done in the {@link Properties} utility<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class PropertiesImpl implements IProperties {

    private Class<? extends ABoilerplateExtension> extensionType;
    @SuppressWarnings("rawtypes")
    private final List<IProperty> defaultProperties;
    @SuppressWarnings("rawtypes")
    private final List<IProperty> instanceProperties;

    @Override
    public void init(ABoilerplateExtension extension) {
        this.extensionType = extension.getClass();
    }

    /**
     * create instance and parse the SOE/SOI default properties
     */
    PropertiesImpl() {
        this.defaultProperties = new ArrayList<>();
        this.instanceProperties = new ArrayList<>();
    }

    /**
     * get the property identified by the given key
     *
     * @return the property or null if no such property is defined
     */
    @Override
    @SuppressWarnings("rawtypes")
    public IProperty getProperty(IPropertyKey key) {
        for (IProperty<?> property : this.instanceProperties) {
            if (property.getKey().equals(key)) {
                return property;
            }
        }
        return null;
    }

    @Override
    public void shutdown() {
        Log.getInstance().info("shutting down SOI/SOE properties ...");
        this.instanceProperties.clear();
    }

    /**
     * build instance properties
     */
    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {
        Log.getInstance().info("constructing SOI/SOE default properties ...");
        this.defaultProperties.addAll(Properties.parseDefaultProperties(this.extensionType));
        try {
            this.instanceProperties.addAll(Properties.parseInstanceProperties(this.defaultProperties, propertySet));
        } catch (ParseFailure ex) {
            throw new ConstructFailure("failed to construct SOI/SOE properties", ex);
        }
    }

}
