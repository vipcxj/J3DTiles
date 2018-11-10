package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.impl.v1.BatchTableComponentType;
import me.cxj.j3dtiles.impl.v1.BatchTableContainerType;
import me.cxj.j3dtiles.utils.TypeUtils;

import java.util.List;

/**
 * Created by vipcxj on 2018/11/1.
 */
public class BatchTableValue {

    private boolean ref;
    private List<Object> values;
    private BatchTableComponentType componentType;
    private BatchTableContainerType containerType;
    private int byteOffset;
    private byte[] binaryData;

    public Object get(int index) {

    }

    private void assertType(BatchTableContainerType containerType, BatchTableComponentType componentType) {
        if (componentType != this.componentType || containerType != this.containerType) {
            throw new UnsupportedOperationException("Batch value type mismatched! Expected " + componentType + "[" + containerType.getSize() + "], actually " + this.componentType + "[" + this.containerType.getSize() + "].");
        }
    }

    public byte getByteValue(int index) {
        if (ref) {
            assertType(BatchTableContainerType.SCALAR, BatchTableComponentType.BYTE);
            return binaryData[byteOffset];
        } else {
            Object value = values.get(index);
            return TypeUtils.t
        }
    }
}
