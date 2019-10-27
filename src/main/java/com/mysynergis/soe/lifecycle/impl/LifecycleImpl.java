package com.mysynergis.soe.lifecycle.impl;

import com.esri.arcgis.system.IPropertySet;
import com.mysynergis.soe.ABoilerplateExtension;
import com.mysynergis.soe.config.impl.Configuration;
import com.mysynergis.soe.debug.impl.Debug;
import com.mysynergis.soe.failure.ConstructFailure;
import com.mysynergis.soe.failure.ShutdownFailure;
import com.mysynergis.soe.lifecycle.ILifecycle;
import com.mysynergis.soe.lifecycle.ILifecycleEx;
import com.mysynergis.soe.log.impl.Log;
import com.mysynergis.soe.properties.impl.Properties;
import com.mysynergis.soe.request.ESoType;

/**
 * central implementation of {@link ILifecycle} managing all other {@link ILifecycle} instances<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
class LifecycleImpl implements ILifecycleEx {

    private ESoType soType;

    public LifecycleImpl() {

    }

    @Override
    public void init(ABoilerplateExtension extension) {
        this.soType = extension.getSoType();
        Properties.getInstance().init(extension);
    }

    @Override
    public void construct(IPropertySet propertySet) throws ConstructFailure {

        Log.getInstance().construct(propertySet);
        Log.getInstance().info("constructing SOI/SOE lifecycle instance (properties) ...");

        // read instance properties
        Properties.getInstance().construct(propertySet);

        Log.getInstance().info("constructing SOI/SOE lifecycle instance (configuration) ...");

        // validate the configuration
        Configuration.getInstance().construct(propertySet);

        Log.getInstance().info("constructing SOI/SOE lifecycle instance (debug) ...");

        //configuration must be ready before debug gets set up
        Debug.getInstance().construct(propertySet);

        Log.getInstance().info("constructing SOI/SOE lifecycle instance (operation) ...");

        //TODO get the appropriate instance from current soe
        this.soType.getRestOperations().construct(propertySet);

    }

    @Override
    public void shutdown() throws ShutdownFailure {

        Log.getInstance().info("shutting down SOI/SOE lifecycle instances (operations -> configuration -> properties -> debug -> log) ...");

        this.soType.getRestOperations().shutdown();

        // release configuration
        Configuration.getInstance().shutdown();

        // forget properties
        Properties.getInstance().shutdown();

        // deletes debug config if possible
        Debug.getInstance().shutdown();

        // shutdown logging
        Log.getInstance().shutdown();

    }

}
