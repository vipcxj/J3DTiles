package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.utils.CommonUtils;
import me.cxj.j3dtiles.utils.FeatureUtils;
import me.cxj.j3dtiles.utils.JsonParser;
import me.cxj.j3dtiles.utils.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class B3dmFeatureTable {

    private static final String PROP_BATCH_LENGTH = "BATCH_LENGTH";
    private static final String PROP_RTC_CENTER = "RTC_CENTER";
    private static final String PROP_BYTE_OFFSET = "byteOffset";

    private int BATCH_LENGTH;
    private float[] RTC_CENTER;

    public static B3dmFeatureTable read(InputStream is, B3dmHeader header, JsonParser parser) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
        B3dmFeatureTable table = new B3dmFeatureTable();
        int jsonByteLength = header.getFeatureTableJSONByteLength();
        byte[] jsonByteBuff = new byte[jsonByteLength];
        dis.readFully(jsonByteBuff);
        String jsonByteString = new String(jsonByteBuff, "UTF-8");
        Object jsonHeaderObject = parser.parse(jsonByteString);
        if (!(jsonHeaderObject instanceof Map)) {
            throw new IllegalArgumentException("The json parser must parse the object to a map. And the feature table json header must be an object.");
        }
        //noinspection unchecked
        Map<String, Object> jsonHeaderMap = (Map<String, Object>) jsonHeaderObject;
        byte[] binaryByteBuff = new byte[header.getFeatureTableBinaryByteLength()];
        dis.readFully(binaryByteBuff);
        Integer batchLength = FeatureUtils.getIntegerScalarFeatureValue(jsonHeaderMap, binaryByteBuff, PROP_BATCH_LENGTH);
        if (batchLength == null) {
            throw new IllegalArgumentException("The property BATCH_LENGTH is required in the b3dm feature table json header");
        }
        table.BATCH_LENGTH = batchLength;
        table.RTC_CENTER = FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, PROP_RTC_CENTER);
        return table;
    }

    private Map<String, Object> createData() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(PROP_BATCH_LENGTH, BATCH_LENGTH);
        if (RTC_CENTER != null) {
            data.put(PROP_RTC_CENTER, RTC_CENTER);
        }
        return data;
    }

    public byte[] createBuffer(B3dmHeader header, JsonParser parser) {
        byte[] bytes = parser.toJsonString(createData()).getBytes(StandardCharsets.UTF_8);
        bytes = CommonUtils.createPaddingBytes(bytes, header.getHeaderLength() + bytes.length, 8, (byte) 0x20);
        header.setFeatureTableJSONByteLength(bytes.length);
        header.setFeatureTableBinaryByteLength(0);
        return bytes;
    }

    public long calcSize(B3dmHeader header, JsonParser parser) {
        byte[] bytes = parser.toJsonString(createData()).getBytes(StandardCharsets.UTF_8);
        return bytes.length + CommonUtils.calcPadding(header.getHeaderLength() + bytes.length, 8);
    }

    public int getBatchLength() {
        return BATCH_LENGTH;
    }

    public float[] getRtcCenter() {
        return RTC_CENTER;
    }
}
