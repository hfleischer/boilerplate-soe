package com.mysynergis.soe.operation;

/**
 * definition for types that hold responses and can provide byte[] output from that information
 *
 * @author h.fleischer
 * @since 13.10.2019
 *
 */
@FunctionalInterface
public interface IRestOperationOutput {

    byte[] getResponseBytes();

}
