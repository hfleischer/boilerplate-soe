package com.mysynergis.soe.lifecycle;

import com.mysynergis.soe.ABoilerplateExtension;

/**
 * to be implemented by classes "living" with the SOI creation, activation, ... cycle<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public interface ILifecycleEx extends ILifecycle {

    void init(ABoilerplateExtension extension);

}
