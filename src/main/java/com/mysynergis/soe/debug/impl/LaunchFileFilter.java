package com.mysynergis.soe.debug.impl;

import java.io.File;
import java.io.FileFilter;

/**
 * filtering for ".launch" files
 *
 * @author h.fleischer
 * @since 28.08.2019
 *
 */
public class LaunchFileFilter implements FileFilter {

    private final String service;

    LaunchFileFilter(String service) {
        this.service = service;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().startsWith(this.service) && pathname.getName().endsWith(".launch");
    }

}
