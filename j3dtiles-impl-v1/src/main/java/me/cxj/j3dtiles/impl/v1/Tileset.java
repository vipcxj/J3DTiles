/*
 * 3D-Tiles JSON model
 * 
 * Do not modify this class. It is automatically generated
 * with JsonModelGen (https://github.com/javagl/JsonModelGen)
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */

package me.cxj.j3dtiles.impl.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A 3D Tiles tileset. 
 * 
 * Auto-generated for tileset.schema.json 
 * 
 */
public class Tileset extends Extensible {

    /**
     * Metadata about the entire tileset. (required) 
     * 
     */
    private Asset asset;
    /**
     * A dictionary object of metadata about per-feature properties. 
     * (optional) 
     * 
     */
    private Map<String, Schema> properties;
    /**
     * The error, in meters, introduced if this tileset is not rendered. At 
     * runtime, the geometric error is used to compute screen space error 
     * (SSE), i.e., the error measured in pixels. (required)<br> 
     * Minimum: 0 (inclusive) 
     * 
     */
    private Float geometricError;
    /**
     * A tile in a 3D Tiles tileset. (required) 
     * 
     */
    private Tile root;
    /**
     * Names of 3D Tiles extensions used somewhere in this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private List<String> extensionsUsed;
    /**
     * Names of 3D Tiles extensions required to properly load this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     */
    private List<String> extensionsRequired;

    /**
     * Metadata about the entire tileset. (required) 
     * 
     * @param asset The asset to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setAsset(Asset asset) {
        if (asset == null) {
            throw new NullPointerException("Invalid value for asset, may not be null");
        }
        this.asset = asset;
    }

    /**
     * Metadata about the entire tileset. (required) 
     * 
     * @return The asset
     * 
     */
    public Asset getAsset() {
        return this.asset;
    }

    /**
     * A dictionary object of metadata about per-feature properties. 
     * (optional) 
     * 
     * @param properties The properties to set
     * 
     */
    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    /**
     * A dictionary object of metadata about per-feature properties. 
     * (optional) 
     * 
     * @return The properties
     * 
     */
    public Map<String, Schema> getProperties() {
        return this.properties;
    }

    /**
     * Add the given properties. The properties of this instance will be 
     * replaced with a map that contains all previous mappings, and 
     * additionally the new mapping. 
     * 
     * @param key The key
     * @param value The value
     * @throws NullPointerException If the given key or value is <code>null</code>
     * 
     */
    public void addProperties(String key, Schema value) {
        if (key == null) {
            throw new NullPointerException("The key may not be null");
        }
        if (value == null) {
            throw new NullPointerException("The value may not be null");
        }
        Map<String, Schema> oldMap = this.properties;
        Map<String, Schema> newMap = new LinkedHashMap<String, Schema>();
        if (oldMap!= null) {
            newMap.putAll(oldMap);
        }
        newMap.put(key, value);
        this.properties = newMap;
    }

    /**
     * Remove the given properties. The properties of this instance will be 
     * replaced with a map that contains all previous mappings, except for 
     * the one with the given key.<br> 
     * If this new map would be empty, then it will be set to 
     * <code>null</code>. 
     * 
     * @param key The key
     * @throws NullPointerException If the given key is <code>null</code>
     * 
     */
    public void removeProperties(String key) {
        if (key == null) {
            throw new NullPointerException("The key may not be null");
        }
        Map<String, Schema> oldMap = this.properties;
        Map<String, Schema> newMap = new LinkedHashMap<String, Schema>();
        if (oldMap!= null) {
            newMap.putAll(oldMap);
        }
        newMap.remove(key);
        if (newMap.isEmpty()) {
            this.properties = null;
        } else {
            this.properties = newMap;
        }
    }

    /**
     * The error, in meters, introduced if this tileset is not rendered. At 
     * runtime, the geometric error is used to compute screen space error 
     * (SSE), i.e., the error measured in pixels. (required)<br> 
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
     * The error, in meters, introduced if this tileset is not rendered. At 
     * runtime, the geometric error is used to compute screen space error 
     * (SSE), i.e., the error measured in pixels. (required)<br> 
     * Minimum: 0 (inclusive) 
     * 
     * @return The geometricError
     * 
     */
    public Float getGeometricError() {
        return this.geometricError;
    }

    /**
     * A tile in a 3D Tiles tileset. (required) 
     * 
     * @param root The root to set
     * @throws NullPointerException If the given value is <code>null</code>
     * 
     */
    public void setRoot(Tile root) {
        if (root == null) {
            throw new NullPointerException("Invalid value for root, may not be null");
        }
        this.root = root;
    }

    /**
     * A tile in a 3D Tiles tileset. (required) 
     * 
     * @return The root
     * 
     */
    public Tile getRoot() {
        return this.root;
    }

    /**
     * Names of 3D Tiles extensions used somewhere in this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param extensionsUsed The extensionsUsed to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setExtensionsUsed(List<String> extensionsUsed) {
        if (extensionsUsed == null) {
            this.extensionsUsed = null;
            return ;
        }
        if (extensionsUsed.size()< 1) {
            throw new IllegalArgumentException("Number of extensionsUsed elements is < 1");
        }
        this.extensionsUsed = extensionsUsed;
    }

    /**
     * Names of 3D Tiles extensions used somewhere in this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The extensionsUsed
     * 
     */
    public List<String> getExtensionsUsed() {
        return this.extensionsUsed;
    }

    /**
     * Add the given extensionsUsed. The extensionsUsed of this instance will 
     * be replaced with a list that contains all previous elements, and 
     * additionally the new element. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void addExtensionsUsed(String element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<String> oldList = this.extensionsUsed;
        List<String> newList = new ArrayList<String>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.add(element);
        this.extensionsUsed = newList;
    }

    /**
     * Remove the given extensionsUsed. The extensionsUsed of this instance 
     * will be replaced with a list that contains all previous elements, 
     * except for the removed one.<br> 
     * If this new list would be empty, then it will be set to 
     * <code>null</code>. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void removeExtensionsUsed(String element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<String> oldList = this.extensionsUsed;
        List<String> newList = new ArrayList<String>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.remove(element);
        if (newList.isEmpty()) {
            this.extensionsUsed = null;
        } else {
            this.extensionsUsed = newList;
        }
    }

    /**
     * Names of 3D Tiles extensions required to properly load this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @param extensionsRequired The extensionsRequired to set
     * @throws IllegalArgumentException If the given value does not meet
     * the given constraints
     * 
     */
    public void setExtensionsRequired(List<String> extensionsRequired) {
        if (extensionsRequired == null) {
            this.extensionsRequired = null;
            return ;
        }
        if (extensionsRequired.size()< 1) {
            throw new IllegalArgumentException("Number of extensionsRequired elements is < 1");
        }
        this.extensionsRequired = extensionsRequired;
    }

    /**
     * Names of 3D Tiles extensions required to properly load this tileset. 
     * (optional)<br> 
     * Minimum number of items: 1<br> 
     * Array elements:<br> 
     * &nbsp;&nbsp;The elements of this array (optional) 
     * 
     * @return The extensionsRequired
     * 
     */
    public List<String> getExtensionsRequired() {
        return this.extensionsRequired;
    }

    /**
     * Add the given extensionsRequired. The extensionsRequired of this 
     * instance will be replaced with a list that contains all previous 
     * elements, and additionally the new element. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void addExtensionsRequired(String element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<String> oldList = this.extensionsRequired;
        List<String> newList = new ArrayList<String>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.add(element);
        this.extensionsRequired = newList;
    }

    /**
     * Remove the given extensionsRequired. The extensionsRequired of this 
     * instance will be replaced with a list that contains all previous 
     * elements, except for the removed one.<br> 
     * If this new list would be empty, then it will be set to 
     * <code>null</code>. 
     * 
     * @param element The element
     * @throws NullPointerException If the given element is <code>null</code>
     * 
     */
    public void removeExtensionsRequired(String element) {
        if (element == null) {
            throw new NullPointerException("The element may not be null");
        }
        List<String> oldList = this.extensionsRequired;
        List<String> newList = new ArrayList<String>();
        if (oldList!= null) {
            newList.addAll(oldList);
        }
        newList.remove(element);
        if (newList.isEmpty()) {
            this.extensionsRequired = null;
        } else {
            this.extensionsRequired = newList;
        }
    }

}
