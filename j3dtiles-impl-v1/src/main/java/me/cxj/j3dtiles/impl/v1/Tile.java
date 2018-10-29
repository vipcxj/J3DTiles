/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

import java.util.ArrayList;
import java.util.List;


/**
 * A tile in a 3D Tiles tileset. 
 * 
 * Auto-generated for tile.schema.json 
 * 
 */
public class Tile extends Extensible {

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (required) 
     * 
     */
    private BoundingVolume boundingVolume;
    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     */
    private BoundingVolume viewerRequestVolume;
    /**
     * The error, in meters, introduced if this tile is rendered and its 
     * children are not. At runtime, the geometric error is used to compute 
     * screen space error (SSE), i.e., the error measured in pixels. 
     * (required)<br> 
     * Minimum: 0 (inclusive) 
     * 
     */
    private Float geometricError;
    /**
     * Specifies if additive or replacement refinement is used when 
     * traversing the tileset for rendering. This property is required for 
     * the root tile of a tileset; it is optional for all other tiles. The 
     * default is to inherit from the parent tile. (optional)<br> 
     * Valid values: ["ADD", "REPLACE"] 
     * 
     */
    private String refine;
    /**
     * A floating-point 4x4 affine transformation matrix, stored in 
     * column-major order, that transforms the tile's content--i.e., its 
     * features as well as content.boundingVolume, boundingVolume, and 
     * viewerRequestVolume--from the tile's local coordinate system to the 
     * parent tile's coordinate system, or, in the case of a root tile, from 
     * the tile's local coordinate system to the tileset's coordinate system. 
     * transform does not apply to geometricError, nor does it apply any 
     * volume property when the volume is a region, defined in EPSG:4979 
     * coordinates. (optional)<br> 
     * Default: 
     * [1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]<br> 
     * Number of items: 16<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private float[] transform;
    /**
     * Metadata about the tile's content and a link to the content. 
     * (optional) 
     * 
     */
    private TileContent content;
    /**
     * An array of objects that define child tiles. Each child tile content 
     * is fully enclosed by its parent tile's bounding volume and, generally, 
     * has a geometricError less than its parent tile's geometricError. For 
     * leaf tiles, the length of this array is zero, and children may not be 
     * defined. (optional)<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;A tile in a 3D Tiles tileset. (optional) 
     * 
     */
    private List<Tile> children;

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (required) 
     * 
     * @param boundingVolume The boundingVolume to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setBoundingVolume(BoundingVolume boundingVolume) {
        if (boundingVolume == null) {
            throw new NullPointerException("Invalid value for boundingVolume, may not be null");
        }
        this.boundingVolume = boundingVolume;
    }

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (required) 
     * 
     * @return The boundingVolume
     * 
     */
    public BoundingVolume getBoundingVolume() {
        return this.boundingVolume;
    }

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     * @param viewerRequestVolume The viewerRequestVolume to set
     * 
     */
    public void setViewerRequestVolume(BoundingVolume viewerRequestVolume) {
        this.viewerRequestVolume = viewerRequestVolume;
    }

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     * @return The viewerRequestVolume
     * 
     */
    public BoundingVolume getViewerRequestVolume() {
        return this.viewerRequestVolume;
    }

    /**
     * The error, in meters, introduced if this tile is rendered and its 
     * children are not. At runtime, the geometric error is used to compute 
     * screen space error (SSE), i.e., the error measured in pixels. 
     * (required)<br> 
     * Minimum: 0 (inclusive) 
     * 
     * @param geometricError The geometricError to set
     * @throws NullPointerException If the given value is <code>null</code>
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setGeometricError(Float geometricError) {
        if (geometricError == null) {
            throw new NullPointerException("Invalid value for geometricError, may not be null");
        }
        if (geometricError< 0.0D) {
            throw new IllegalArgumentException("geometricError < 0.0");
        }
        this.geometricError = geometricError;
    }

    /**
     * The error, in meters, introduced if this tile is rendered and its 
     * children are not. At runtime, the geometric error is used to compute 
     * screen space error (SSE), i.e., the error measured in pixels. 
     * (required)<br> 
     * Minimum: 0 (inclusive) 
     * 
     * @return The geometricError
     * 
     */
    public Float getGeometricError() {
        return this.geometricError;
    }

    /**
     * Specifies if additive or replacement refinement is used when 
     * traversing the tileset for rendering. This property is required for 
     * the root tile of a tileset; it is optional for all other tiles. The 
     * default is to inherit from the parent tile. (optional)<br> 
     * Valid values: ["ADD", "REPLACE"] 
     * 
     * @param refine The refine to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setRefine(String refine) {
        if (refine == null) {
            this.refine = null;
            return ;
        }
        if ((!"ADD".equals(refine))&&(!"REPLACE".equals(refine))) {
            throw new IllegalArgumentException((("Invalid value for refine: "+ refine)+", valid: [\"ADD\", \"REPLACE\"]"));
        }
        this.refine = refine;
    }

    /**
     * Specifies if additive or replacement refinement is used when 
     * traversing the tileset for rendering. This property is required for 
     * the root tile of a tileset; it is optional for all other tiles. The 
     * default is to inherit from the parent tile. (optional)<br> 
     * Valid values: ["ADD", "REPLACE"] 
     * 
     * @return The refine
     * 
     */
    public String getRefine() {
        return this.refine;
    }

    /**
     * A floating-point 4x4 affine transformation matrix, stored in 
     * column-major order, that transforms the tile's content--i.e., its 
     * features as well as content.boundingVolume, boundingVolume, and 
     * viewerRequestVolume--from the tile's local coordinate system to the 
     * parent tile's coordinate system, or, in the case of a root tile, from 
     * the tile's local coordinate system to the tileset's coordinate system. 
     * transform does not apply to geometricError, nor does it apply any 
     * volume property when the volume is a region, defined in EPSG:4979 
     * coordinates. (optional)<br> 
     * Default: 
     * [1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]<br> 
     * Number of items: 16<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param transform The transform to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setTransform(float[] transform) {
        if (transform == null) {
            this.transform = null;
            return ;
        }
        if (transform.length< 16) {
            throw new IllegalArgumentException("Number of transform elements is < 16");
        }
        if (transform.length > 16) {
            throw new IllegalArgumentException("Number of transform elements is > 16");
        }
        this.transform = transform;
    }

    /**
     * A floating-point 4x4 affine transformation matrix, stored in 
     * column-major order, that transforms the tile's content--i.e., its 
     * features as well as content.boundingVolume, boundingVolume, and 
     * viewerRequestVolume--from the tile's local coordinate system to the 
     * parent tile's coordinate system, or, in the case of a root tile, from 
     * the tile's local coordinate system to the tileset's coordinate system. 
     * transform does not apply to geometricError, nor does it apply any 
     * volume property when the volume is a region, defined in EPSG:4979 
     * coordinates. (optional)<br> 
     * Default: 
     * [1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]<br> 
     * Number of items: 16<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The transform
     * 
     */
    public float[] getTransform() {
        return this.transform;
    }

    /**
     * Returns the default value of the transform<br> 
     * @see #getTransform 
     * 
     * @return The default transform
     * 
     */
    public float[] defaultTransform() {
        return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
    }

    /**
     * Metadata about the tile's content and a link to the content. 
     * (optional) 
     * 
     * @param content The content to set
     * 
     */
    public void setContent(TileContent content) {
        this.content = content;
    }

    /**
     * Metadata about the tile's content and a link to the content. 
     * (optional) 
     * 
     * @return The content
     * 
     */
    public TileContent getContent() {
        return this.content;
    }

    /**
     * An array of objects that define child tiles. Each child tile content 
     * is fully enclosed by its parent tile's bounding volume and, generally, 
     * has a geometricError less than its parent tile's geometricError. For 
     * leaf tiles, the length of this array is zero, and children may not be 
     * defined. (optional)<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;A tile in a 3D Tiles tileset. (optional) 
     * 
     * @param children The children to set
     * 
     */
    public void setChildren(List<Tile> children) {
        this.children = children;
    }

    /**
     * An array of objects that define child tiles. Each child tile content 
     * is fully enclosed by its parent tile's bounding volume and, generally, 
     * has a geometricError less than its parent tile's geometricError. For 
     * leaf tiles, the length of this array is zero, and children may not be 
     * defined. (optional)<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;A tile in a 3D Tiles tileset. (optional) 
     * 
     * @return The children
     * 
     */
    public List<Tile> getChildren() {
        return this.children;
    }

    /**
     * Add the given children. The children of this instance will be replaced 
     * with a list that contains all previous elements, and additionally the 
     * new element. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void addChildren(Tile element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<Tile> oldList = this.children;
        List<Tile> newList = new ArrayList<Tile>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.add(element);
        this.children = newList;
    }

    /**
     * Remove the given children. The children of this instance will be 
     * replaced with a list that contains all previous elements, except for 
     * the removed one.<br> 
     * If this new list would be empty, then it will be set to 
     * <code>null</code>. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void removeChildren(Tile element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<Tile> oldList = this.children;
        List<Tile> newList = new ArrayList<Tile>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.remove(element);
        if (newList.isEmpty()) {
            this.children = null;
        } else {
            this.children = newList;
        }
    }

}
