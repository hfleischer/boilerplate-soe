package com.mysynergis.soe.request;

import com.esri.arcgis.system.IRESTRequestHandler;
import com.esri.arcgis.system.IRequestHandler2;
import com.esri.arcgis.system.IWebRequestHandler;
import com.mysynergis.soe.operation.IRestOperations;
import com.mysynergis.soe.operation.impl.RestOperationsImplSoe;
import com.mysynergis.soe.operation.impl.RestOperationsImplSoi;

/**
 * enumeration of known server-object types (SOE / SOI)
 *
 * @author h.fleischer
 * @since 13.10.2019
 *
 */
public enum ESoType {

    /**
     * server-object-extension
     */
    SOE {

        private final IRestOperations soeOperations = new RestOperationsImplSoe();

        @Override
        public IRestOperations getRestOperations() {
            return this.soeOperations;
        }

    },

    /**
     * server-object-interceptor
     */
    SOI {

        private final IRestOperations soeOperations = new RestOperationsImplSoi();

        @Override
        public IRestOperations getRestOperations() {
            return this.soeOperations;
        }

    };

    /**
     * get an instance of {@link IRestOperations} suitable for this so-type
     * 
     * @return
     */
    public abstract IRestOperations getRestOperations();

    /**
     * create a new instance of {@link IRESTRequestHandler} for this so-type
     *
     * @return
     */
    public IRESTRequestHandler restRequestHandler() {
        return new RestRequestHandler(this);
    }

    /**
     * get an {@link IWebRequestHandler} delegate instance
     *
     * @return
     */
    @SuppressWarnings("static-method")
    public IWebRequestHandler webRequestHandler() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * get an {@link IRequestHandler2} delegate instance
     *
     * @return
     */
    @SuppressWarnings("static-method")
    public IRequestHandler2 requestHandler2() {
        throw new IllegalStateException("not implemented");
    }

}
