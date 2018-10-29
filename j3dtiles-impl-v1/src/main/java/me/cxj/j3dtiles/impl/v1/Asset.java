/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

/**
 * Metadata about the entire tileset. 
 * 
 * Auto-generated for asset.schema.json 
 * 
 */
public class Asset extends Extensible {

    /**
     * The 3D Tiles version. The version defines the JSON schema for the 
     * tileset JSON and the base set of tile formats. (required) 
     * 
     */
    private String version;
    /**
     * Application-specific version of this tileset, e.g., for when an 
     * existing tileset is updated. (optional) 
     * 
     */
    private String tilesetVersion;

    /**
     * The 3D Tiles version. The version defines the JSON schema for the 
     * tileset JSON and the base set of tile formats. (required) 
     * 
     * @param version The version to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setVersion(String version) {
        if (version == null) {
            throw new NullPointerException("Invalid value for version, may not be null");
        }
        this.version = version;
    }

    /**
     * The 3D Tiles version. The version defines the JSON schema for the 
     * tileset JSON and the base set of tile formats. (required) 
     * 
     * @return The version
     * 
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Application-specific version of this tileset, e.g., for when an 
     * existing tileset is updated. (optional) 
     * 
     * @param tilesetVersion The tilesetVersion to set
     * 
     */
    public void setTilesetVersion(String tilesetVersion) {
        this.tilesetVersion = tilesetVersion;
    }

    /**
     * Application-specific version of this tileset, e.g., for when an 
     * existing tileset is updated. (optional) 
     * 
     * @return The tilesetVersion
     * 
     */
    public String getTilesetVersion() {
        return this.tilesetVersion;
    }

}
