package me.cxj.j3dtiles.impl.v1;

/**
 * An object defining the reference to a section of the binary body
 * of the batch table where the property values are stored if not
 * defined directly in the JSON.
 */
public class BatchTableBinaryBodyReference {

    /**
     * The offset into the buffer in bytes.
     * (required)
     */
    private long byteOffset;

    /**
     * The data type of components in the property.
     * (required)
     */
    private ComponentType componentType;

    /**
     * Specifies if the property is a scalar or vector.
     * (required)
     */
    private Type type;

    /**
     * The offset into the buffer in bytes.
     * (required)
     * @return the offset
     */
    public long getByteOffset() {
        return byteOffset;
    }

    /**
     * The offset into the buffer in bytes.
     * (required)
     * @param byteOffset the offset
     */
    public void setByteOffset(long byteOffset) {
        this.byteOffset = byteOffset;
    }

    /**
     * The data type of components in the property.
     * (required)
     * @return The data type
     */
    public ComponentType getComponentType() {
        return componentType;
    }

    /**
     * The data type of components in the property.
     * (required)
     * @param componentType The data type
     */
    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    /**
     * Specifies if the property is a scalar or vector.
     * (required)
     * @return the container type
     */
    public Type getType() {
        return type;
    }

    /**
     * Specifies if the property is a scalar or vector.
     * (required)
     * @param type the container type
     */
    public void setType(Type type) {
        this.type = type;
    }

    public enum ComponentType {
        BYTE, UNSIGNED_BYTE, SHORT, UNSIGNED_SHORT, INT, UNSIGNED_INT, FLOAT, DOUBLE
    }

    public enum Type {
        SCALAR, VEC2, VEC3, VEC4
    }
}
