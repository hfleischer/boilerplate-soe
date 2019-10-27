package com.mysynergis.soe.operation;

import com.esri.arcgis.system.IRESTRequestHandler;

/**
 * wrapper type for the parameters coming with a reest request<br>
 * this type is used to reduce method signature in various place and also to make clear which parameters of
 * {@link IRESTRequestHandler} actually describe input
 *
 * @author h.fleischer
 * @since 13.10.2019
 *
 */
public interface IRestOperationInput {

    String getCapabilities();

    String getOperationInput();

    String getRequestProperties();

    String getOutputFormat();

}
