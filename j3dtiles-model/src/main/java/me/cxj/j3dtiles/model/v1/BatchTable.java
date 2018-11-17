package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.impl.v1.BinaryBodyReference;
import me.cxj.j3dtiles.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class BatchTable {

    private Map<String, List<?>> data;
    private Map<String, BinaryBodyReference> references;

    private static Map<String, BinaryBodyReference> collectReferences(Map<String, Object> jsonHeader) {
        Map<String, BinaryBodyReference> references = new LinkedHashMap<>();
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
            BinaryBodyReference reference = table.references.get(entry.getKey());
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

    public byte[] createBuffer(BatchHeader header, JsonParser parser, int batchLength) throws IOException {
        Map<String, Object> outJsonHeader = new HashMap<>();
        try (BinaryBodyHelper helper = new BinaryBodyHelper(outJsonHeader)){
            for (Map.Entry<String, List<?>> entry : data.entrySet()) {
                BinaryBodyReference reference = references.get(entry.getKey());
                if (reference == null) {
                    outJsonHeader.put(entry.getKey(), entry.getValue());
                } else {
                    helper.writeData(entry.getKey(), entry.getValue(), reference.getComponentType(), reference.getType(), false, true);
                }
            }
            helper.finished();
            String jsonHeader = parser.toJsonString(outJsonHeader);
            byte[] jsonHeaderBytes = jsonHeader.getBytes(StandardCharsets.UTF_8);
            int jsonSize = jsonHeaderBytes.length;
            int jsonPadding = CommonUtils.calcPadding(jsonSize, 8);
            byte[] binaryBody = helper.toByteArray();
            int buffSize = jsonSize + jsonPadding + binaryBody.length;
            byte[] out = new byte[buffSize];
            System.arraycopy(jsonHeaderBytes, 0, out, 0, jsonSize);
            Arrays.fill(out, jsonSize, jsonSize + jsonPadding, (byte) 0x20);
            System.arraycopy(binaryBody, 0, out, jsonSize + jsonPadding, binaryBody.length);
            header.setBatchTableJSONByteLength(jsonSize + jsonPadding);
            header.setBatchTableBinaryByteLength(binaryBody.length);
            return out;
        }
    }

    public int calcSize(BatchHeader header, JsonParser parser, int batchLength) {
        Map<String, Object> outJsonHeader = new HashMap<>();
        BinaryBodySizeHelper helper = new BinaryBodySizeHelper(outJsonHeader);
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            BinaryBodyReference reference = references.get(entry.getKey());
            if (reference == null) {
                outJsonHeader.put(entry.getKey(), entry.getValue());
            } else {
                helper.addProperty(entry.getKey(), entry.getValue(), reference.getComponentType(), reference.getType(), false);
            }
        }
        helper.finished();
        return helper.calcHeaderSize(0, parser) + helper.getSize();
    }

    public Object getProperty(String property, int batchId) {
        return data.get(property).get(batchId);
    }
}
