package me.cxj.j3dtiles.utils;

import me.cxj.j3dtiles.impl.v1.ComponentType;
import me.cxj.j3dtiles.impl.v1.ContainerType;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.cxj.j3dtiles.utils.CommonUtils.addReference;
import static me.cxj.j3dtiles.utils.CommonUtils.calcPadding;

/**
 * Created by vipcxj on 2018/11/13.
 */
public class BinaryBodyHelper implements Closeable {

    private final Map<String, Object> jsonHeader;
    private final ByteArrayOutputStream bos;
    private final LittleEndianDataOutputStream dos;
    private int size = 0;

    public BinaryBodyHelper() {
        this.jsonHeader = new HashMap<>();
        this.bos = new ByteArrayOutputStream();
        this.dos = new LittleEndianDataOutputStream(this.bos);
    }

    public BinaryBodyHelper(Map<String, Object> jsonHeader) {
        this.jsonHeader = jsonHeader;
        this.bos = new ByteArrayOutputStream();
        this.dos = new LittleEndianDataOutputStream(this.bos);
    }

    public void writeData(String property, List data, ComponentType componentType, ContainerType containerType, boolean feature) throws IOException {
        writeData(property, data, componentType, containerType, feature, true);
    }

    public void writeData(String property, List data, ComponentType componentType, ContainerType containerType, boolean feature, boolean writeHeader) throws IOException {
        int width = componentType.getSize();
        padding(width);
        if (writeHeader) {
            addReference(jsonHeader, property, size, componentType, containerType, feature);
        }
        int writes = 0;
        int unit = containerType.getSize();
        if (unit == 1) {
            switch (componentType) {
                case BYTE:
                    for (Object el : data) {
                        dos.write(TypeUtils.toByte(el));
                        writes += width;
                    }
                    break;
                case UNSIGNED_BYTE:
                    for (Object el : data) {
                        dos.write(TypeUtils.toUnsignedByte(el));
                        writes += width;
                    }
                    break;
                case SHORT:
                    for (Object el : data) {
                        dos.writeShort(TypeUtils.toShort(el));
                        writes += width;
                    }
                    break;
                case UNSIGNED_SHORT:
                    for (Object el : data) {
                        dos.writeShort(TypeUtils.toUnsignedShort(el));
                        writes += width;
                    }
                    break;
                case INT:
                    for (Object el : data) {
                        dos.writeInt(TypeUtils.toInteger(el));
                        writes += width;
                    }
                    break;
                case UNSIGNED_INT:
                    for (Object el : data) {
                        dos.writeInt(TypeUtils.toUnsignedInteger(el));
                        writes += width;
                    }
                    break;
                case FLOAT:
                    for (Object el : data) {
                        dos.writeFloat(TypeUtils.toFloat(el));
                        writes += width;
                    }
                    break;
                case DOUBLE:
                    for (Object el : data) {
                        dos.writeDouble(TypeUtils.toDouble(el));
                        writes += width;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("This is impossible.");
            }
        } else {
            switch (componentType) {
                case BYTE:
                    for (Object el : data) {
                        byte[] v = TypeUtils.toByteArray(el, unit);
                        dos.write(v, 0, unit);
                        writes += width * unit;
                    }
                    break;
                case UNSIGNED_BYTE:
                    for (Object el : data) {
                        byte[] v = TypeUtils.toUnsignedByteArray(el, unit);
                        dos.write(v, 0, unit);
                        writes += width * unit;
                    }
                    break;
                case SHORT:
                    for (Object el : data) {
                        short[] array = TypeUtils.toShortArray(el, unit);
                        for (short v : array) {
                            dos.writeShort(v);
                        }
                        writes += width * unit;
                    }
                    break;
                case UNSIGNED_SHORT:
                    for (Object el : data) {
                        short[] array = TypeUtils.toUnsignedShortArray(el, unit);
                        for (short v : array) {
                            dos.writeShort(v);
                        }
                        writes += width * unit;
                    }
                    break;
                case INT:
                    for (Object el : data) {
                        int[] array = TypeUtils.toIntegerArray(el, unit);
                        for (int v : array) {
                            dos.writeInt(v);
                        }
                        writes += width * unit;
                    }
                    break;
                case UNSIGNED_INT:
                    for (Object el : data) {
                        int[] array = TypeUtils.toUnsignedIntegerArray(el, unit);
                        for (int v : array) {
                            dos.writeInt(v);
                        }
                        writes += width * unit;
                    }
                    break;
                case FLOAT:
                    for (Object el : data) {
                        float[] array = TypeUtils.toFloatArray(el, unit);
                        for (float v : array) {
                            dos.writeFloat(v);
                        }
                        writes += width * unit;
                    }
                    break;
                case DOUBLE:
                    for (Object el : data) {
                        double[] array = TypeUtils.toDoubleArray(el, unit);
                        for (double v : array) {
                            dos.writeDouble(v);
                        }
                        writes += width * unit;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("This is impossible.");
            }
        }
        size += writes;
    }

    public void writeBatchId(List<Long> batchIdList) throws IOException {
        writeBatchId(batchIdList, true);
    }

    public void writeBatchId(List<Long> batchIdList, boolean writeHeader) throws IOException {
        Long max = batchIdList.stream().max(Long::compareTo).orElse(null);
        if (max == null) {
            throw new IllegalArgumentException("There null value in the batch id list.");
        }
        if (max > 0xFFFF) {
            writeData("BATCH_ID", batchIdList, ComponentType.UNSIGNED_INT, ContainerType.SCALAR, true, writeHeader);
        } else if (max > 0xFF) {
            writeData("BATCH_ID", batchIdList, ComponentType.UNSIGNED_SHORT, ContainerType.SCALAR, true, writeHeader);
        } else {
            writeData("BATCH_ID", batchIdList, ComponentType.UNSIGNED_BYTE, ContainerType.SCALAR, true, writeHeader);
        }
    }

    public int getSize() {
        return size;
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }

    private void padding(int paddingUnit) throws IOException {
        int padding = calcPadding(size, paddingUnit);
        fillPadding(dos, padding);
        size += padding;
    }

    public void finished() throws IOException {
        padding(8);
    }

    private static void fillPadding(LittleEndianDataOutputStream dos, int padding) throws IOException {
        for (int i = 0; i < padding; ++i) {
            dos.write(0);
        }
    }

    @Override
    public void close() throws IOException {
        dos.close();
        bos.close();
    }
}
