package me.cxj.j3dtiles.model.v1;

import me.cxj.j3dtiles.impl.v1.BinaryBodyReference;
import me.cxj.j3dtiles.impl.v1.ComponentType;
import me.cxj.j3dtiles.impl.v1.ContainerType;
import me.cxj.j3dtiles.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.cxj.j3dtiles.utils.CommonUtils.checkListElements;
import static me.cxj.j3dtiles.utils.CommonUtils.mismatchedLength;

/**
 * Created by vipcxj on 2018/11/9.
 */
public class PntsFeatureTable {

    //Instance semantics
    /**
     * A 3-component array of numbers containing x, y, and z Cartesian coordinates for the position of the point.
     * float32[3],
     * Required, unless POSITION_QUANTIZED is defined.
     */
    private List<float[]> POSITION;
    /**
     * A 3-component array of numbers containing x, y, and z in quantized Cartesian coordinates for the position of the point.
     * uint16[3],
     * Required, unless POSITION is defined.
     */
    private List<int[]> POSITION_QUANTIZED;
    /**
     * uint8[4] for RGBA, uint8[3] for RGB, uint16 for RGB565
     */
    private List<Integer> color;
    private ColorFormat colorFormat;
    /**
     * A unit vector defining the normal of the point.
     * float32[3],
     * Not Required.
     */
    private List<float[]> NORMAL;
    /**
     * An oct-encoded unit vector with 16 bits of precision defining the normal of the point.
     * uint8[2],
     * Not Required.
     */
    private List<byte[]> NORMAL_OCT16P;
    /**
     * The batchId of the point that can be used to retrieve metadata from the Batch Table.
     * uint8, uint16 (default), or uint32,
     * Not Required
     */
    private List<Long> BATCH_ID;

    //Global semantics
    /**
     * The number of points to render. The length of each array value for a point semantic should be equal to this.
     * uint32,
     * Required
     */
    private int POINTS_LENGTH;
    /**
     * A 3-component array of numbers defining the center position when position positions are defined relative-to-center.
     * float32[3],
     * Not Required
     */
    private float[] RTC_CENTER;
    /**
     * A 3-component array of numbers defining the offset for the quantized volume.
     * float32[3],
     * Not Required, unless POSITION_QUANTIZED is defined.
     */
    private float[] QUANTIZED_VOLUME_OFFSET;
    /**
     * A 3-component array of numbers defining the scale for the quantized volume.
     * float32[3],
     * Not Required, unless POSITION_QUANTIZED is defined.
     */
    private float[] QUANTIZED_VOLUME_SCALE;
    /**
     * A 4-component array of values defining a constant RGBA color for all points in the tile.
     * uint8[4],
     * Not Required
     */
    private Integer CONSTANT_RGBA;
    /**
     * The number of unique BATCH_ID values.
     * uint32,
     * Not Required, unless BATCH_ID is defined.
     */
    private Integer BATCH_LENGTH;


    public List<float[]> getPositionList() {
        return POSITION;
    }

    public void setPositionList(List<float[]> value) {
        POSITION = value;
    }

    public float[] getPosition(int featureId) {
        return POSITION != null ? POSITION.get(featureId) : null;
    }

    public float[] setPosition(int featureId, float[] value) {
        return POSITION.set(featureId, value);
    }

    public int addPosition(float[] value) {
        if (POSITION == null) {
            POSITION = new ArrayList<>();
        }
        POSITION.add(value);
        return POSITION.size() - 1;
    }

    public float[] removePosition(int featureId) {
        if (POSITION != null) {
            return POSITION.remove(featureId);
        }
        return null;
    }

    public List<int[]> getPositionQuantizedList() {
        return POSITION_QUANTIZED;
    }

    public void setPositionQuantizedList(List<int[]> value) {
        POSITION_QUANTIZED = value;
    }

    public int[] getPositionQuantized(int featureId) {
        return POSITION_QUANTIZED != null ? POSITION_QUANTIZED.get(featureId) : null;
    }

    public int[] setPositionQuantized(int featureId, int[] value) {
        return POSITION_QUANTIZED.set(featureId, value);
    }

    public int addPositionQuantized(int[] value) {
        if (POSITION_QUANTIZED == null) {
            POSITION_QUANTIZED = new ArrayList<>();
        }
        POSITION_QUANTIZED.add(value);
        return POSITION_QUANTIZED.size() - 1;
    }

    public int[] removePositionQuantized(int featureId) {
        if (POSITION_QUANTIZED != null) {
            return POSITION_QUANTIZED.remove(featureId);
        }
        return null;
    }

    public List<Integer> getColorList() {
        return color;
    }

    public void setColorList(List<Integer> value) {
        color = value;
    }

    public Integer getColor(int featureId) {
        return color != null ? color.get(featureId) : null;
    }

    public Integer setColor(int featureId, int color) {
        return this.color.set(featureId, color);
    }

    public int addColor(int value) {
        if (color == null) {
            color = new ArrayList<>();
        }
        color.add(value);
        return color.size() - 1;
    }

    public Integer removeColor(int featureId) {
        return color != null ? color.remove(featureId) : null;
    }

    public ColorFormat getColorFormat() {
        return colorFormat;
    }

    public void setColorFormat(ColorFormat colorFormat) {
        this.colorFormat = colorFormat;
    }

    public List<float[]> getNormalList() {
        return NORMAL;
    }

    public void setNormalList(List<float[]> value) {
        NORMAL = value;
    }

    public float[] getNormal(int featureId) {
        return NORMAL != null ? NORMAL.get(featureId) : null;
    }

    public float[] setNormal(int featureId, float[] value) {
        return NORMAL.set(featureId, value);
    }

    public int addNormal(float[] value) {
        if (NORMAL == null) {
            NORMAL = new ArrayList<>();
        }
        NORMAL.add(value);
        return NORMAL.size() - 1;
    }

    public float[] removeNormal(int featureId) {
        return NORMAL != null ? NORMAL.remove(featureId) : null;
    }

    public List<byte[]> getNormalOct16pList() {
        return NORMAL_OCT16P;
    }

    public void setNormalOct16pList(List<byte[]> value) {
        NORMAL_OCT16P = value;
    }

    public byte[] getNormalOct16p(int featureId) {
        return NORMAL_OCT16P != null ? NORMAL_OCT16P.get(featureId) : null;
    }

    public byte[] setNormalOct16p(int featureId, byte[] value) {
        return NORMAL_OCT16P.set(featureId, value);
    }

    public int addNormalOct16p(byte[] value) {
        if (NORMAL_OCT16P == null) {
            NORMAL_OCT16P = new ArrayList<>();
        }
        NORMAL_OCT16P.add(value);
        return NORMAL_OCT16P.size() - 1;
    }

    public byte[] removeNormalOct16p(int featureId) {
        return NORMAL_OCT16P != null ? NORMAL_OCT16P.remove(featureId) : null;
    }

    public List<Long> getBatchIdList() {
        return BATCH_ID;
    }

    public void setBatchIdList(List<Long> value) {
        BATCH_ID = value;
    }

    public Long getBatchId(int featureId) {
        if (BATCH_ID == null) {
            return null;
        }
        return BATCH_ID.get(featureId);
    }

    public Long setBatchId(int featureId, long value) {
        return BATCH_ID.set(featureId, value);
    }

    public int addBatchId(long value) {
        if (BATCH_ID == null) {
            BATCH_ID = new ArrayList<>();
        }
        BATCH_ID.add(value);
        return BATCH_ID.size() - 1;
    }

    public Long removeBatchId(int featureId) {
        return BATCH_ID != null ? BATCH_ID.remove(featureId) : null;
    }

    public int getPointsLength() {
        return POINTS_LENGTH;
    }

    public void setPointsLength(int value) {
        POINTS_LENGTH = value;
    }

    public float[] getRtcCenter() {
        return RTC_CENTER;
    }

    public void setRtcCenter(float[] value) {
        RTC_CENTER = value;
    }

    public float[] getQuantizedVolumeOffset() {
        return QUANTIZED_VOLUME_OFFSET;
    }

    public void setQuantizedVolumeOffset(float[] QUANTIZED_VOLUME_OFFSET) {
        this.QUANTIZED_VOLUME_OFFSET = QUANTIZED_VOLUME_OFFSET;
    }

    public float[] getQuantizedVolumeScale() {
        return QUANTIZED_VOLUME_SCALE;
    }

    public void setQuantizedVolumeScale(float[] value) {
        QUANTIZED_VOLUME_SCALE = value;
    }

    public Integer getConstantRgba() {
        return CONSTANT_RGBA;
    }

    public void setConstantRgba(Integer value) {
        CONSTANT_RGBA = value;
    }

    public Integer getBatchLength() {
        return BATCH_LENGTH;
    }

    public void setBatchLength(Integer value) {
        BATCH_LENGTH = value;
    }

    @SuppressWarnings("Duplicates")
    public void validate() {
        if (POSITION == null && POSITION_QUANTIZED == null) {
            throw new IllegalArgumentException("Either POSITION or POSITION_QUANTIZED is needed.");
        }
        if (POSITION != null) {
            if (POSITION.size() != POINTS_LENGTH) {
                mismatchedLength("POSITION");
            }
            checkListElements("POSITION", POSITION, 3);
        }
        if (POSITION_QUANTIZED != null) {
            if (POSITION_QUANTIZED.size() != POINTS_LENGTH) {
                mismatchedLength("POSITION_QUANTIZED");
            }
            checkListElements("POSITION_QUANTIZED", POSITION_QUANTIZED, 3);
            if (QUANTIZED_VOLUME_OFFSET == null) {
                throw new IllegalArgumentException("QUANTIZED_VOLUME_OFFSET is needed when POSITION_QUANTIZED is defined.");
            }
            if (QUANTIZED_VOLUME_OFFSET.length != 3) {
                mismatchedLength("QUANTIZED_VOLUME_OFFSET");
            }
            if (QUANTIZED_VOLUME_SCALE == null) {
                throw new IllegalArgumentException("QUANTIZED_VOLUME_SCALE is needed when POSITION_QUANTIZED is defined.");
            }
            if (QUANTIZED_VOLUME_SCALE.length != 3) {
                mismatchedLength("QUANTIZED_VOLUME_SCALE");
            }
        }
        if (color != null) {
            if (color.size() != POINTS_LENGTH) {
                mismatchedLength("color");
            }
            if (colorFormat == null || colorFormat == ColorFormat.NONE) {
                throw new IllegalArgumentException("Invalid color format: " + colorFormat + ".");
            }
        } else {
            if (colorFormat != null && colorFormat != ColorFormat.NONE) {
                throw new IllegalArgumentException("Invalid color data with color format: " + colorFormat + ".");
            }
        }
        if (NORMAL != null) {
            if (NORMAL.size() != POINTS_LENGTH) {
                mismatchedLength("NORMAL");
            }
            checkListElements("NORMAL", NORMAL, 3);
        }
        if (NORMAL_OCT16P != null) {
            if (NORMAL_OCT16P.size() != POINTS_LENGTH) {
                mismatchedLength("NORMAL_OCT16P");
            }
            checkListElements("NORMAL_OCT16P", NORMAL_OCT16P, 2);
        }
        if ((BATCH_LENGTH != null && BATCH_ID == null) || (BATCH_LENGTH == null && BATCH_ID != null)) {
            throw new IllegalArgumentException("BATCH_ID and BATCH_LENGTH are depend on each other.");
        }
        if (BATCH_ID != null) {
            if (BATCH_ID.size() != POINTS_LENGTH) {
                mismatchedLength("BATCH_ID");
            }
            checkListElements("BATCH_ID", BATCH_ID, 1);
        }
        if (RTC_CENTER != null) {
            if (RTC_CENTER.length != 3) {
                mismatchedLength("RTC_CENTER");
            }
        }
    }

    public static PntsFeatureTable read(InputStream is, PointCloudHeader header, JsonParser parser) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
        PntsFeatureTable table = new PntsFeatureTable();
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
        Integer intValue = FeatureUtils.getIntegerScalarFeatureValue(jsonHeaderMap, binaryByteBuff, "POINTS_LENGTH");
        if (intValue == null) {
            throw new IllegalArgumentException("POINTS_LENGTH is required for pnts file.");
        }
        int pointsLength = intValue;
        table.setPointsLength(pointsLength);
        table.setRtcCenter(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "RTC_CENTER"));
        table.setQuantizedVolumeOffset(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "QUANTIZED_VOLUME_OFFSET"));
        table.setQuantizedVolumeScale(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "QUANTIZED_VOLUME_SCALE"));
        byte[] constant_rgba = FeatureUtils.getUnsignedByteArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "CONSTANT_RGBA", 4);
        if (constant_rgba != null) {
            table.setConstantRgba(constant_rgba[0] << 16 | constant_rgba[1] << 8 | constant_rgba[2] | constant_rgba[3] << 24);
        }
        table.setBatchLength(FeatureUtils.getIntegerScalarFeatureValue(jsonHeaderMap, binaryByteBuff, "BATCH_LENGTH"));

        float[] floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "POSITION", pointsLength * 3);
        if (floatArrayValue != null) {
            table.setPositionList(CommonUtils.createFloat3List(floatArrayValue, pointsLength));
        }
        int[] unsignedShortArrayValue = FeatureUtils.getUnsignedShortArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "POSITION_QUANTIZED", pointsLength * 3);
        if (unsignedShortArrayValue != null) {
            table.setPositionQuantizedList(CommonUtils.createUnsignedShortVecList(unsignedShortArrayValue, pointsLength, 3));
        }

        BinaryBodyReference reference = FeatureUtils.getBinaryBodyReference(jsonHeaderMap, "RGBA");
        table.setColorFormat(ColorFormat.RGBA);
        if (reference == null) {
            reference = FeatureUtils.getBinaryBodyReference(jsonHeaderMap, "RGB");
            table.setColorFormat(ColorFormat.RGB);
        }
        if (reference == null) {
            reference = FeatureUtils.getBinaryBodyReference(jsonHeaderMap, "RGB565");
            table.setColorFormat(ColorFormat.RGB565);
        }
        if (reference == null) {
            table.setColorFormat(ColorFormat.NONE);
        }
        switch (table.getColorFormat()) {
            case RGBA: {
                List<Integer> color = new ArrayList<>();
                for (int i = 0; i < pointsLength; ++i) {
                    //noinspection ConstantConditions
                    int r = binaryByteBuff[reference.getByteOffset() + i * 4];
                    int g = binaryByteBuff[reference.getByteOffset() + i * 4 + 1];
                    int b = binaryByteBuff[reference.getByteOffset() + i * 4 + 2];
                    int a = binaryByteBuff[reference.getByteOffset() + i * 4 + 3];
                    color.add(a << 24 | r << 16 | g << 8 | b);
                }
                table.setColorList(color);
                break;
            }
            case RGB: {
                List<Integer> color = new ArrayList<>();
                for (int i = 0; i < pointsLength; ++i) {
                    //noinspection ConstantConditions
                    int r = binaryByteBuff[reference.getByteOffset() + i * 3];
                    int g = binaryByteBuff[reference.getByteOffset() + i * 3 + 1];
                    int b = binaryByteBuff[reference.getByteOffset() + i * 3 + 2];
                    //noinspection NumericOverflow
                    color.add(0xFF << 24 | r << 16 | g << 8 | b);
                }
                table.setColorList(color);
                break;
            }
            case RGB565: {
                List<Integer> color = new ArrayList<>();
                for (int i = 0; i < pointsLength; ++i) {
                    //noinspection ConstantConditions
                    int shortValue = binaryByteBuff[reference.getByteOffset() + i * 2 + 1] | binaryByteBuff[reference.getByteOffset() + i * 2] << 8;
                    int r = shortValue >>> 11 << 3;
                    int g = ((shortValue >>> 5) & 0b111111) << 2;
                    int b = (shortValue & 0b11111) << 3;
                    //noinspection NumericOverflow
                    color.add(0xFF << 24 | r << 16 | g << 8 | b);
                }
                table.setColorList(color);
                break;
            }
            case NONE:
            default:
        }
        floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL", pointsLength * 3);
        if (floatArrayValue != null) {
            table.setNormalList(CommonUtils.createFloat3List(floatArrayValue, pointsLength));
        }
        byte[] byteArrayValue = FeatureUtils.getUnsignedByteArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL_OCT16P", pointsLength * 2);
        if (byteArrayValue != null) {
            table.setNormalOct16pList(CommonUtils.createUnsignedByteVecList(byteArrayValue, pointsLength, 2));
        }
        long[] unsignedIntArrayValue = FeatureUtils.getBatchId(jsonHeaderMap, binaryByteBuff, pointsLength);
        if (unsignedIntArrayValue != null) {
            table.setBatchIdList(CommonUtils.createUnsignedIntList(unsignedIntArrayValue, pointsLength));
        }
        table.validate();
        return table;
    }

    private Map<String, Object> createSimpleHeader() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("POINTS_LENGTH", getPointsLength());
        float[] rtcCenter = getRtcCenter();
        if (rtcCenter != null) {
            data.put("RTC_CENTER", rtcCenter);
        }
        float[] quantizedVolumeOffset = getQuantizedVolumeOffset();
        if (quantizedVolumeOffset != null) {
            data.put("QUANTIZED_VOLUME_OFFSET", quantizedVolumeOffset);
        }
        float[] quantizedVolumeScale = getQuantizedVolumeScale();
        if (quantizedVolumeScale != null) {
            data.put("QUANTIZED_VOLUME_SCALE", quantizedVolumeScale);
        }
        Integer constantRgba = getConstantRgba();
        if (constantRgba != null) {
            data.put("CONSTANT_RGBA", ColorUtils.toRGBA(constantRgba));
        }
        Integer batchLength = getBatchLength();
        if (batchLength != null) {
            data.put("BATCH_LENGTH", batchLength);
        }
        return data;
    }

    public byte[] createBuffer(PointCloudHeader header, JsonParser parser) throws IOException {
        validate();
        Map<String, Object> data = createSimpleHeader();
        try (BinaryBodyHelper helper = new BinaryBodyHelper(data)){
            List<float[]> positionList = getPositionList();
            if (positionList != null && !positionList.isEmpty()) {
                helper.writeData("POSITION", positionList, ComponentType.FLOAT, ContainerType.VEC3, true);
            }
            List<int[]> positionQuantizedList = getPositionQuantizedList();
            if (positionQuantizedList != null && !positionQuantizedList.isEmpty()) {
                helper.writeData("POSITION_QUANTIZED", positionQuantizedList, ComponentType.UNSIGNED_SHORT, ContainerType.VEC3, true);
            }
            List<Integer> colorList = getColorList();
            if (colorList != null && !colorList.isEmpty()) {
                switch (getColorFormat()) {
                    case RGBA: {
                        List<byte[]> bytesList = colorList.stream().map(ColorUtils::toRGBA).collect(Collectors.toList());
                        helper.writeData("RGBA", bytesList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC4, true);
                        break;
                    }
                    case RGB: {
                        List<byte[]> bytesList = colorList.stream().map(ColorUtils::toRGB).collect(Collectors.toList());
                        helper.writeData("RGB", bytesList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC3, true);
                        break;
                    }
                    case RGB565: {
                        List<Short> shortList = colorList.stream().map(ColorUtils::toRGB565).collect(Collectors.toList());
                        helper.writeData("RGB565", shortList, ComponentType.UNSIGNED_SHORT, ContainerType.SCALAR, true);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("This is impossible.");
                }
            }
            List<float[]> normalList = getNormalList();
            if (normalList != null && !normalList.isEmpty()) {
                helper.writeData("NORMAL", normalList, ComponentType.FLOAT, ContainerType.VEC3, true);
            }
            List<byte[]> normalOct16pList = getNormalOct16pList();
            if (normalOct16pList != null && !normalOct16pList.isEmpty()) {
                helper.writeData("NORMAL_OCT16P", normalOct16pList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC2, true);
            }
            List<Long> batchIdList = getBatchIdList();
            if (batchIdList != null && !batchIdList.isEmpty()) {
                helper.writeBatchId(batchIdList);
            }
            helper.finished();
            byte[] jsonBytes = parser.toJsonString(data).getBytes(StandardCharsets.UTF_8);
            jsonBytes = CommonUtils.createPaddingBytes(jsonBytes, header.getHeaderLength() + jsonBytes.length, 8, (byte) 0x20);
            header.setFeatureTableJSONByteLength(jsonBytes.length);
            byte[] binaryBytes = helper.toByteArray();
            header.setFeatureTableBinaryByteLength(binaryBytes.length);
            byte[] out = new byte[jsonBytes.length + binaryBytes.length];
            System.arraycopy(jsonBytes, 0, out, 0, jsonBytes.length);
            System.arraycopy(binaryBytes, 0, out, jsonBytes.length, binaryBytes.length);
            return out;
        }
    }

    public int calcSize(PointCloudHeader header, JsonParser parser) {
        Map<String, Object> data = createSimpleHeader();
        BinaryBodySizeHelper helper = new BinaryBodySizeHelper(data);
        List<float[]> positionList = getPositionList();
        if (positionList != null && !positionList.isEmpty()) {
            helper.addProperty("POSITION", positionList, ComponentType.FLOAT, ContainerType.VEC3, true);
        }
        List<int[]> positionQuantizedList = getPositionQuantizedList();
        if (positionQuantizedList != null && !positionQuantizedList.isEmpty()) {
            helper.addProperty("POSITION_QUANTIZED", positionQuantizedList, ComponentType.UNSIGNED_SHORT, ContainerType.VEC3, true);
        }
        List<Integer> colorList = getColorList();
        if (colorList != null && !colorList.isEmpty()) {
            switch (getColorFormat()) {
                case RGBA: {
                    List<byte[]> bytesList = colorList.stream().map(ColorUtils::toRGBA).collect(Collectors.toList());
                    helper.addProperty("RGBA", bytesList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC4, true);
                    break;
                }
                case RGB: {
                    List<byte[]> bytesList = colorList.stream().map(ColorUtils::toRGB).collect(Collectors.toList());
                    helper.addProperty("RGB", bytesList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC3, true);
                    break;
                }
                case RGB565: {
                    List<Short> shortList = colorList.stream().map(ColorUtils::toRGB565).collect(Collectors.toList());
                    helper.addProperty("RGB565", shortList, ComponentType.UNSIGNED_SHORT, ContainerType.SCALAR, true);
                    break;
                }
                default:
                    throw new IllegalArgumentException("This is impossible.");
            }
        }
        List<float[]> normalList = getNormalList();
        if (normalList != null && !normalList.isEmpty()) {
            helper.addProperty("NORMAL", normalList, ComponentType.FLOAT, ContainerType.VEC3, true);
        }
        List<byte[]> normalOct16pList = getNormalOct16pList();
        if (normalOct16pList != null && !normalOct16pList.isEmpty()) {
            helper.addProperty("NORMAL_OCT16P", normalOct16pList, ComponentType.UNSIGNED_BYTE, ContainerType.VEC2, true);
        }
        List<Long> batchIdList = getBatchIdList();
        if (batchIdList != null && !batchIdList.isEmpty()) {
            helper.addBatchId(batchIdList);
        }
        helper.finished();
        return helper.calcHeaderSize(header.getHeaderLength(), parser) + helper.getSize();
    }

    enum ColorFormat {
        RGB, RGBA, RGB565, NONE
    }
}
