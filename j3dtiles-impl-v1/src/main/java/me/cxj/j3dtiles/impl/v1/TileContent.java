/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

/**
 * Metadata about the tile's content and a link to the content. 
 * 
 * Auto-generated for tile.content.schema.json 
 * 
 */
public class TileContent extends Extensible {

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     */
    private BoundingVolume boundingVolume;
    /**
     * A uri that points to the tile's content. When the uri is relative, it 
     * is relative to the referring tileset JSON file. (required) 
     * 
     */
    private String uri;

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     * @param boundingVolume The boundingVolume to set
     * 
     */
    public void setBoundingVolume(BoundingVolume boundingVolume) {
        this.boundingVolume = boundingVolume;
    }

    /**
     * A bounding volume that encloses a tile or its content. Exactly one 
     * `box`, `region`, or `sphere` property is required. (optional) 
     * 
     * @return The boundingVolume
     * 
     */
    public BoundingVolume getBoundingVolume() {
        return this.boundingVolume;
    }

    /**
     * A uri that points to the tile's content. When the uri is relative, it 
     * is relative to the referring tileset JSON file. (required) 
     * 
     * @param uri The uri to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setUri(String uri) {
        if (uri == null) {
            throw new NullPointerException("Invalid value for uri, may not be null");
        }
        this.uri = uri;
    }

    /**
     * A uri that points to the tile's content. When the uri is relative, it 
     * is relative to the referring tileset JSON file. (required) 
     * 
     * @return The uri
     * 
     */
    public String getUri() {
        return this.uri;
    }

}
