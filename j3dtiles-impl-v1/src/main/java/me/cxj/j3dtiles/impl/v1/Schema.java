/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

/**
 * A dictionary object of metadata about per-feature properties. 
 * 
 * Auto-generated for properties.schema.json 
 * 
 */
public class Schema extends Extensible {

    /**
     * The maximum value of this property of all the features in the tileset. 
     * (required) 
     * 
     */
    private Float maximum;
    /**
     * The minimum value of this property of all the features in the tileset. 
     * (required) 
     * 
     */
    private Float minimum;

    /**
     * The maximum value of this property of all the features in the tileset. 
     * (required) 
     * 
     * @param maximum The maximum to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setMaximum(Float maximum) {
        if (maximum == null) {
            throw new NullPointerException("Invalid value for maximum, may not be null");
        }
        this.maximum = maximum;
    }

    /**
     * The maximum value of this property of all the features in the tileset. 
     * (required) 
     * 
     * @return The maximum
     * 
     */
    public Float getMaximum() {
        return this.maximum;
    }

    /**
     * The minimum value of this property of all the features in the tileset. 
     * (required) 
     * 
     * @param minimum The minimum to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setMinimum(Float minimum) {
        if (minimum == null) {
            throw new NullPointerException("Invalid value for minimum, may not be null");
        }
        this.minimum = minimum;
    }

    /**
     * The minimum value of this property of all the features in the tileset. 
     * (required) 
     * 
     * @return The minimum
     * 
     */
    public Float getMinimum() {
        return this.minimum;
    }

}
