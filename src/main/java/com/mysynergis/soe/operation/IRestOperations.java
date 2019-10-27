package com.mysynergis.soe.operation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.mysynergis.soe.lifecycle.ILifecycle;

/**
 * definition for types that hold {@link IRestOperation} instances and can provide them either as list or by name<br>
 *
 * @author h.fleischer
 * @since 02.09.2019
 *
 */
public interface IRestOperations extends ILifecycle {

    /**
     * get a list of configured operations contained in this instance<br>
     * this list shall not contain default operations, ...<br>
     * if the implementation needs to provide a default implementation, it shall use the {@link IRestOperations#optOperation(String, String)} method
     * to add such default operations
     *
     * @return
     */
    List<IRestOperation> getOperations();

    /**
     * get an operation for the given resourceName and operationName, if any
     *
     * @param resourceName
     * @param operationName
     * @return
     */
    Optional<IRestOperation> optOperation(String resourceName, String operationName);

    /**
     * get the rest operation schema as suitable for the type (SOE|SOI) that this instance is implemented for
     * 
     * @return
     * @throws IOException
     */
    String getSchema() throws IOException;

}
