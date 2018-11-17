package me.cxj.j3dtiles.impl.v1;

import java.io.Serializable;

/**
 * An object defining the reference to a section of the binary body
 * of the batch table where the property values are stored if not
 * defined directly in the JSON.
 */
public class BinaryBodyReference implements Serializable {

    /**
     * The offset into the buffer in bytes.
     * (required)
     */
    private int byteOffset;

    /**
     * The data type of components in the property.
     * (required)
     */
    private ComponentType componentType;

    /**
     * Specifies if the property is a scalar or vector.
     * (required)
     */
    private ContainerType type;

    public BinaryBodyReference() {
    }

    public BinaryBodyReference(int byteOffset, ComponentType componentType, ContainerType type) {
        this.byteOffset = byteOffset;
        this.componentType = componentType;
        this.type = type;
    }

    /**
     * The offset into the buffer in bytes.
     * (required)
     * @return the offset
     */
    public int getByteOffset() {
        return byteOffset;
    }

    /**
     * The offset into the buffer in bytes.
     * (required)
     * @param byteOffset the offset
     */
    public void setByteOffset(int byteOffset) {
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
    public ContainerType getType() {
        return type;
    }

    /**
     * Specifies if the property is a scalar or vector.
     * (required)
     * @param type the container type
     */
    public void setType(ContainerType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryBodyReference)) return false;

        BinaryBodyReference that = (BinaryBodyReference) o;

        if (getByteOffset() != that.getByteOffset()) return false;
        if (getComponentType() != that.getComponentType()) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = getByteOffset();
        result = 31 * result + (getComponentType() != null ? getComponentType().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BatchTableBinaryBodyReference{" +
                "byteOffset=" + byteOffset +
                ", componentType=" + componentType +
                ", type=" + type +
                '}';
    }
}
