package me.cxj.j3dtiles.utils;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.GltfUtils;
import de.javagl.jgltf.model.io.GltfModelWriter;
import de.javagl.jgltf.model.io.VersionUtils;
import de.javagl.jgltf.model.v2.GltfModelV2;
import me.cxj.j3dtiles.impl.v1.ComponentType;
import me.cxj.j3dtiles.impl.v1.ContainerType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.util.*;

/**
 * Created by vipcxj on 2018/11/9.
 */
public class CommonUtils {

    public static int calcPadding(int offset, int paddingUnit) {
        int padding = offset % paddingUnit;
        if (padding != 0) {
            padding = paddingUnit - padding;
        }
        return padding;
    }

    public static void paddingBytes(byte[] bytes, int srcLen, int paddingUnit, byte paddingCode) {
        int padding = calcPadding(srcLen, paddingUnit);
        for (int i = 0; i < padding; ++i) {
            bytes[srcLen + i] = paddingCode;
        }
    }

    public static byte[] createPaddingBytes(byte[] bytes, int offset, int paddingUnit, byte paddingCode) {
        int padding = calcPadding(offset, paddingUnit);
        if (padding == 0) {
            return bytes;
        }
        byte[] out = Arrays.copyOf(bytes, bytes.length + padding);
        Arrays.fill(out, bytes.length, bytes.length + padding, paddingCode);
        return out;
    }

    public static byte[] createPaddingBytes(byte[] bytes, int paddingUnit, byte paddingCode) {
        return createPaddingBytes(bytes, bytes.length, paddingUnit, paddingCode);
    }

    public static void fillPadding(OutputStream os, int padding) throws IOException {
        fillPadding(os, padding, null);
    }

    public static void fillPadding(OutputStream os, int padding, Byte v) throws IOException {
        byte f = 0;
        if (v != null) {
            f = v;
        }
        for (int i = 0; i < padding; ++i) {
            os.write(f);
        }
    }

    public static Map<String, Object> createReference(int offset, String componentType) {
        Map<String, Object> reference = new HashMap<>();
        reference.put("byteOffset", offset);
        if (componentType != null) {
            reference.put("componentType", componentType);
        }
        return reference;
    }

    public static void mismatchedLength(String property) {
        throw new IllegalArgumentException("Mismatched length of " + property + " array.");
    }

    public static void checkListElements(String property, List list, int componentLength) {
        int pos = 0;
        for (Object el : list) {
            if (componentLength == 1) {
                if (el == null) {
                    throw new IllegalArgumentException("Invalid " + property + " at pos: " + pos + ": " + null + ".");
                }
            } else {
                if (el == null || Array.getLength(el) != componentLength) {
                    throw new IllegalArgumentException("Invalid " + property + " at pos: " + pos + ": " + el + ".");
                }
            }
        }
    }

    public static List<byte[]> createUnsignedByteVecList(byte[] data, int listSize, int componentLength) {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            byte[] el = new byte[componentLength];
            System.arraycopy(data, componentLength * i, el, 0, componentLength);
            list.add(el);
        }
        return list;
    }

    public static List<Float> createFloatList(float[] data, int listSize) {
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    public static List<float[]> createFloat3List(float[] data, int listSize) {
        List<float[]> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            float[] el = new float[3];
            el[0] = data[3 * i];
            el[1] = data[3 * i + 1];
            el[2] = data[3 * i + 2];
            list.add(el);
        }
        return list;
    }

    public static List<Integer> createUnsignedShortList(int[] data, int listSize) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    public static List<Long> createUnsignedIntList(long[] data, int listSize) {
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    public static List<int[]> createUnsignedShortVecList(int[] data, int listSize, int componentLength) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            int[] el = new int[componentLength];
            System.arraycopy(data, componentLength * i, el, 0, componentLength);
            list.add(el);
        }
        return list;
    }

    public static int writeFloatList(LittleEndianDataOutputStream dos, List<Float> list) throws IOException {
        int writes = 0;
        for (float el : list) {
            dos.writeFloat(el);
            writes += 4;
        }
        return writes;
    }

    public static int writeFloatArrayList(LittleEndianDataOutputStream dos, List<float[]> list) throws IOException {
        int writes = 0;
        for (float[] el : list) {
            for (float v : el) {
                dos.writeFloat(v);
                writes += 4;
            }
        }
        return writes;
    }

    public static int writeUnsignedShortArrayList(LittleEndianDataOutputStream dos, List<int[]> list) throws IOException {
        int writes = 0;
        for (int[] el : list) {
            for (int i : el) {
                dos.writeShort(i);
                writes += 2;
            }
        }
        return writes;
    }

    public static void addReference(Map<String, Object> jsonHeader, String property, int offset, ComponentType componentType, ContainerType containerType, boolean feature) {
        Map<String, Object> reference;
        if (feature) {
            if ("BATCH_ID".equals(property)) {
                if (containerType != ContainerType.SCALAR) {
                    throw new IllegalArgumentException("Invalid container type for BATCH_ID: " + containerType + ".");
                }
                if (componentType != ComponentType.UNSIGNED_BYTE && componentType != ComponentType.UNSIGNED_SHORT && componentType != ComponentType.UNSIGNED_INT) {
                    throw new IllegalArgumentException("Invalid component type for BATCH_ID: " + componentType + ".");
                }
                if (componentType != ComponentType.UNSIGNED_SHORT) {
                    reference = createReference(offset, componentType, null);
                } else {
                    reference = createReference(offset, null, null);
                }
            } else {
                reference = createReference(offset, null, null);
            }
        } else {
            reference = createReference(offset, componentType, containerType);
        }
        jsonHeader.put(property, reference);
    }

    public static Map<String, Object> createReference(int offset, ComponentType componentType, ContainerType containerType) {
        Map<String, Object> reference = new HashMap<>();
        reference.put("byteOffset", offset);
        if (componentType != null) {
            reference.put("componentType", componentType);
        }
        if (containerType != null) {
            reference.put("type", containerType);
        }
        return reference;
    }

    public static byte[] getPadGltf(GltfModel model, int paddingUnit) {
        if (!(model instanceof GltfModelV2)) {
            throw new IllegalArgumentException("Only support gltf v2.");
        }
        if (paddingUnit % 4 != 0) {
            throw new IllegalArgumentException("The input padding unit should be the multiple of 4 which now is " + paddingUnit + ".");
        }
        byte[] bytes;
        try {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
                new GltfModelWriter().writeBinary(model, bos);
                bytes = bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer intBuffer = buffer.asIntBuffer();
        int gltfLength = intBuffer.get(2);
        if (gltfLength < 0) {
            throw new IllegalArgumentException("Too big gltf length: " + (gltfLength & 0xFFFFFFFFL) + ".");
        }
        if (gltfLength > buffer.capacity()) {
            throw new IllegalArgumentException("Invalid gltf length: " + gltfLength + ", bigger the whole gltf buffer length: " + buffer.capacity());
        }
        if (gltfLength % 4 != 0) {
            throw new IllegalArgumentException("Invalid gltf length: " + gltfLength + ", it should 4-bytes aligned.");
        }
        int padding = calcPadding(gltfLength, paddingUnit);
        if (padding == 0) {
            return bytes;
        }
        int jsonChunkLength = intBuffer.get(3);
        int jsonChunkEndOffset = 12 + jsonChunkLength;
        byte[] out = new byte[gltfLength + padding];
        System.arraycopy(bytes, 0, out, 0, jsonChunkEndOffset);
        Arrays.fill(out, jsonChunkEndOffset, jsonChunkEndOffset + padding, (byte) 0x20);
        System.arraycopy(bytes, jsonChunkEndOffset, out, jsonChunkEndOffset + padding, gltfLength - jsonChunkEndOffset);
        ByteBuffer newBuffer = ByteBuffer.wrap(out).order(ByteOrder.LITTLE_ENDIAN);
        intBuffer = newBuffer.asIntBuffer();
        intBuffer.put(2, gltfLength + padding);
        intBuffer.put(3, jsonChunkLength + padding);
        return out;
    }

    public static int calcGltfSize(GltfModel model, int paddingUnit) {
        return getPadGltf(model, paddingUnit).length;
    }
}
