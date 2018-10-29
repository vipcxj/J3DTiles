/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

/**
 * A bounding volume that encloses a tile or its content. Exactly one 
 * `box`, `region`, or `sphere` property is required. 
 * 
 * Auto-generated for boundingVolume.schema.json 
 * 
 */
public class BoundingVolume extends Extensible {

    /**
     * An array of 12 numbers that define an oriented bounding box. The first 
     * three elements define the x, y, and z values for the center of the 
     * box. The next three elements (with indices 3, 4, and 5) define the x 
     * axis direction and half-length. The next three elements (indices 6, 7, 
     * and 8) define the y axis direction and half-length. The last three 
     * elements (indices 9, 10, and 11) define the z axis direction and 
     * half-length. (optional)<br> 
     * Number of items: 12<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private float[] box;
    /**
     * An array of six numbers that define a bounding geographic region in 
     * EPSG:4979 coordinates with the order [west, south, east, north, 
     * minimum height, maximum height]. Longitudes and latitudes are in 
     * radians, and heights are in meters above (or below) the WGS84 
     * ellipsoid. (optional)<br> 
     * Number of items: 6<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private float[] region;
    /**
     * An array of four numbers that define a bounding sphere. The first 
     * three elements define the x, y, and z values for the center of the 
     * sphere. The last element (with index 3) defines the radius in meters. 
     * (optional)<br> 
     * Number of items: 4<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private float[] sphere;

    /**
     * An array of 12 numbers that define an oriented bounding box. The first 
     * three elements define the x, y, and z values for the center of the 
     * box. The next three elements (with indices 3, 4, and 5) define the x 
     * axis direction and half-length. The next three elements (indices 6, 7, 
     * and 8) define the y axis direction and half-length. The last three 
     * elements (indices 9, 10, and 11) define the z axis direction and 
     * half-length. (optional)<br> 
     * Number of items: 12<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param box The box to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setBox(float[] box) {
        if (box == null) {
            this.box = null;
            return ;
        }
        if (box.length< 12) {
            throw new IllegalArgumentException("Number of box elements is < 12");
        }
        if (box.length > 12) {
            throw new IllegalArgumentException("Number of box elements is > 12");
        }
        this.box = box;
    }

    /**
     * An array of 12 numbers that define an oriented bounding box. The first 
     * three elements define the x, y, and z values for the center of the 
     * box. The next three elements (with indices 3, 4, and 5) define the x 
     * axis direction and half-length. The next three elements (indices 6, 7, 
     * and 8) define the y axis direction and half-length. The last three 
     * elements (indices 9, 10, and 11) define the z axis direction and 
     * half-length. (optional)<br> 
     * Number of items: 12<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The box
     * 
     */
    public float[] getBox() {
        return this.box;
    }

    /**
     * An array of six numbers that define a bounding geographic region in 
     * EPSG:4979 coordinates with the order [west, south, east, north, 
     * minimum height, maximum height]. Longitudes and latitudes are in 
     * radians, and heights are in meters above (or below) the WGS84 
     * ellipsoid. (optional)<br> 
     * Number of items: 6<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param region The region to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setRegion(float[] region) {
        if (region == null) {
            this.region = null;
            return ;
        }
        if (region.length< 6) {
            throw new IllegalArgumentException("Number of region elements is < 6");
        }
        if (region.length > 6) {
            throw new IllegalArgumentException("Number of region elements is > 6");
        }
        this.region = region;
    }

    /**
     * An array of six numbers that define a bounding geographic region in 
     * EPSG:4979 coordinates with the order [west, south, east, north, 
     * minimum height, maximum height]. Longitudes and latitudes are in 
     * radians, and heights are in meters above (or below) the WGS84 
     * ellipsoid. (optional)<br> 
     * Number of items: 6<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The region
     * 
     */
    public float[] getRegion() {
        return this.region;
    }

    /**
     * An array of four numbers that define a bounding sphere. The first 
     * three elements define the x, y, and z values for the center of the 
     * sphere. The last element (with index 3) defines the radius in meters. 
     * (optional)<br> 
     * Number of items: 4<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param sphere The sphere to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setSphere(float[] sphere) {
        if (sphere == null) {
            this.sphere = null;
            return ;
        }
        if (sphere.length< 4) {
            throw new IllegalArgumentException("Number of sphere elements is < 4");
        }
        if (sphere.length > 4) {
            throw new IllegalArgumentException("Number of sphere elements is > 4");
        }
        this.sphere = sphere;
    }

    /**
     * An array of four numbers that define a bounding sphere. The first 
     * three elements define the x, y, and z values for the center of the 
     * sphere. The last element (with index 3) defines the radius in meters. 
     * (optional)<br> 
     * Number of items: 4<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The sphere
     * 
     */
    public float[] getSphere() {
        return this.sphere;
    }

}
