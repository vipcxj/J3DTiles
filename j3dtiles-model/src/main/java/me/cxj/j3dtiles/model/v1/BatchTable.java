package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.impl.v1.BatchTableBinaryBodyReference;
import me.cxj.j3dtiles.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class BatchTable {

    private Map<String, List<?>> data;
    private Map<String, BatchTableBinaryBodyReference> references;

    private static Map<String, BatchTableBinaryBodyReference> collectReferences(Map<String, Object> jsonHeader) {
        Map<String, BatchTableBinaryBodyReference> references = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : jsonHeader.entrySet()) {
            if (entry.getValue() instanceof Map) {
                references.put(entry.getKey(), FeatureUtils.toReference(entry.getValue()));
            }
        }
        return references;
    }

    public static BatchTable read(InputStream is, BatchHeader header, JsonParser parser, int batchLength) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
        BatchTable table = new BatchTable();
        byte[] jsonByteBuff = new byte[header.getBatchTableJSONByteLength()];
        dis.readFully(jsonByteBuff);
        Object jsonHeaderObject = parser.parse(new String(jsonByteBuff, "UTF-8"));
        if (!(jsonHeaderObject instanceof Map)) {
            throw new IllegalArgumentException("The json parser must parse the object to a map. And the batch table json header must be an object.");
        }
        //noinspection unchecked
        Map<String, Object> jsonHeader = (Map<String, Object>) jsonHeaderObject;
        byte[] binaryByteBuff = new byte[header.getBatchTableBinaryByteLength()];
        dis.readFully(binaryByteBuff);
        table.references = collectReferences(jsonHeader);
        table.data = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : jsonHeader.entrySet()) {
            BatchTableBinaryBodyReference reference = table.references.get(entry.getKey());
            if (reference != null) {
                table.data.put(entry.getKey(), FeatureUtils.getBatchTableValues(reference, binaryByteBuff, entry.getKey(), batchLength));
            } else if (entry.getValue() instanceof List) {
                table.data.put(entry.getKey(), (List) entry.getValue());
            } else {
                throw new IllegalArgumentException("Invalid batch table property: " + entry + ".");
            }
        }
        return table;
    }

    public byte[] createBuffer(BatchHeader header, JsonParser parser, int batchLength) {
        Map<String, Object> outJsonHeader = new HashMap<>();
        int binarySize = 0;
        int offset = 0;
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            BatchTableBinaryBodyReference reference = references.get(entry.getKey());
            if (reference == null) {
                outJsonHeader.put(entry.getKey(), entry.getValue());
            } else {
                reference.setByteOffset(offset);
                int dataLen = reference.getComponentType().getSize() * reference.getType().getSize() * batchLength;
                int padding = (offset + dataLen) % reference.getComponentType().getSize();
                if (padding != 0) {
                    padding = reference.getComponentType().getSize() - padding;
                }
                binarySize = offset + dataLen;
                offset += dataLen + padding;
                outJsonHeader.put(entry.getKey(), reference);
            }
        }
        int binaryPadding = binarySize % 8;
        if (binaryPadding != 0) {
            binaryPadding = 8 - binaryPadding;
        }
        String jsonHeader = parser.toJsonString(outJsonHeader);
        byte[] jsonHeaderBytes = jsonHeader.getBytes(StandardCharsets.UTF_8);
        int jsonSize = jsonHeaderBytes.length;
        int jsonPadding = jsonSize % 8;
        if (jsonPadding != 0) {
            jsonPadding = 8 - jsonPadding;
        }
        int buffSize = jsonSize + jsonPadding + binarySize + binaryPadding;
        byte[] out = new byte[buffSize];
        System.arraycopy(jsonHeaderBytes, 0, out, 0, jsonSize);
        for (int i = 0; i < jsonPadding; ++i) {
            out[jsonSize + i] = 0x20;
        }
        for (Map.Entry<String, Object> entry : outJsonHeader.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof BatchTableBinaryBodyReference) {
                BatchTableBinaryBodyReference reference = (BatchTableBinaryBodyReference) value;
                List realData = data.get(entry.getKey());
                int containerSize = reference.getType().getSize();
                int unitSize = reference.getComponentType().getSize() * containerSize;
                int baseOffset = jsonSize + jsonPadding + reference.getByteOffset();
                for (int i = 0; i < realData.size(); ++i) {
                    Object el = realData.get(i);
                    switch (reference.getComponentType()) {
                        case BYTE:
                            if (containerSize == 1) {
                                out[baseOffset + i * unitSize] = TypeUtils.toByte(el);
                            } else {
                                StreamUtils.byteArrayCopyToByteArrayLE(out, baseOffset + i * unitSize, (byte[]) el);
                            }
                            break;
                        case UNSIGNED_BYTE:
                            if (containerSize == 1) {
                                out[baseOffset + i * unitSize] = (byte) (int) el;
                            } else {
                                StreamUtils.unsignedByteArrayCopyToByteArrayLE(out, baseOffset + i * unitSize, (int[]) el);
                            }
                            break;
                        case SHORT:
                            if (containerSize == 1) {
                                short v = (short) el;
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                            } else {
                                StreamUtils.shortArrayCopyToByteArrayLE(out, baseOffset, (short[]) el);
                            }
                            break;
                        case UNSIGNED_SHORT:
                            if (containerSize == 1) {
                                int v = (int) el;
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                            } else {
                                StreamUtils.unsignedShortArrayCopyToByteArrayLE(out, baseOffset, (int[]) el);
                            }
                            break;
                        case INT:
                            if (containerSize == 1) {
                                int v = (int) el;
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                                out[baseOffset + i * unitSize + 2] = (byte) (0xFF & (v >> 16));
                                out[baseOffset + i * unitSize + 3] = (byte) (0xFF & (v >> 24));
                            } else {
                                StreamUtils.intArrayCopyToByteArrayLE(out, baseOffset, (int[]) el);
                            }
                            break;
                        case UNSIGNED_INT:
                            if (containerSize == 1) {
                                long v = (long) el;
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                                out[baseOffset + i * unitSize + 2] = (byte) (0xFF & (v >> 16));
                                out[baseOffset + i * unitSize + 3] = (byte) (0xFF & (v >> 24));
                            } else {
                                StreamUtils.unsignedIntArrayCopyToByteArrayLE(out, baseOffset, (long[]) el);
                            }
                            break;
                        case FLOAT:
                            if (containerSize == 1) {
                                int v = Float.floatToIntBits((float) el);
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                                out[baseOffset + i * unitSize + 2] = (byte) (0xFF & (v >> 16));
                                out[baseOffset + i * unitSize + 3] = (byte) (0xFF & (v >> 24));
                            } else {
                                StreamUtils.floatArrayCopyToByteArrayLE(out, baseOffset, (float[]) el);
                            }
                            break;
                        case DOUBLE:
                            if (containerSize == 1) {
                                long v = Double.doubleToLongBits((double) el);
                                out[baseOffset + i * unitSize] = (byte) (0xFF & v);
                                out[baseOffset + i * unitSize + 1] = (byte) (0xFF & (v >> 8));
                                out[baseOffset + i * unitSize + 2] = (byte) (0xFF & (v >> 16));
                                out[baseOffset + i * unitSize + 3] = (byte) (0xFF & (v >> 24));
                                out[baseOffset + i * unitSize + 4] = (byte) (0xFF & (v >> 32));
                                out[baseOffset + i * unitSize + 5] = (byte) (0xFF & (v >> 40));
                                out[baseOffset + i * unitSize + 6] = (byte) (0xFF & (v >> 48));
                                out[baseOffset + i * unitSize + 7] = (byte) (0xFF & (v >> 56));
                            } else {
                                StreamUtils.doubleArrayCopyToByteArrayLE(out, baseOffset, (double[]) el);
                            }
                            break;

                    }
                }
            }
        }
        header.setBatchTableJSONByteLength(jsonSize + jsonPadding);
        header.setBatchTableBinaryByteLength(binarySize + binaryPadding);
        return out;
    }

    public Object getProperty(String property, int batchId) {
        return data.get(property).get(batchId);
    }
}
