package com.mysynergis.soe.config.impl;

import com.esri.arcgis.systemUI.IOperation;
import com.mysynergis.soe.config.IConstant;

/**
 * known keys used in the configuration<br>
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public enum EKey implements IConstant {

    /**
     * list of one or more implementations (which must implement the {@link IOperation} interface to be valid)
     */
    OPERATION_LIST("operation_list"),

    LAUNCHERS_PATH("launchers_path"),

    TYPE("type"),

    DEFS("defs");

    private final String constant;

    EKey(String constant) {
        this.constant = constant;
    }

    /**
     * gets the string value of this key as in the JSON configuration
     *
     * @return
     */
    @Override
    public String getConstant() {
        return this.constant;
    }

}
