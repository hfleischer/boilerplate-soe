package com.mysynergis.interceptor;

/**
 * helper type for offset calculation<br>
 *
 * @author h.fleischer
 * @since 16.10.2019
 *
 */
public class AnnotationOffsetHelper {

    public static final double MILLIMETERS_PER_INCH = 25.4;
    public static final double MILLIMETERS_PER_METER = 1000;
    public static final double DOTS_PER_INCH = 72; //seems to be the appropriate value when calculating arcojects symbology

    double metersPerPixel;

    AnnotationOffsetHelper(double referenceScale) {
        this.metersPerPixel = MILLIMETERS_PER_INCH / DOTS_PER_INCH / MILLIMETERS_PER_METER * referenceScale;
    }

    public double getMetersPerPixel() {
        return this.metersPerPixel;
    }

    public double calculateOffset(double xLabelled, double xAnchor) {
        return (xAnchor - xLabelled) / this.metersPerPixel;
    }

}
