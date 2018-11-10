package me.cxj.j3dtiles.model.v1;

import com.sun.istack.internal.Nullable;
import me.cxj.j3dtiles.utils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static me.cxj.j3dtiles.utils.CommonUtils.calcPadding;

/**
 * Created by vipcxj on 2018/11/8.
 */
public class I3dmFeatureTable {

    //Instance semantics
    /**
     * A 3-component array of numbers containing x, y, and z Cartesian coordinates for the position of the instance.
     * float32[3],
     * Required, unless POSITION_QUANTIZED is defined.
     */
    private List<float[]> POSITION;
    /**
     * A 3-component array of numbers containing x, y, and z in quantized Cartesian coordinates for the position of the instance.
     * uint16[3],
     * Required, unless POSITION is defined.
     */
    private List<int[]> POSITION_QUANTIZED;
    /**
     * A unit vector defining the up direction for the orientation of the instance.
     * 	float32[3],
     * 	Not Required, unless NORMAL_RIGHT is defined.
     */
    private List<float[]> NORMAL_UP;
    /**
     * A unit vector defining the right direction for the orientation of the instance. Must be orthogonal to up.
     * float32[3],
     * Not Required, unless NORMAL_UP is defined.
     */
    private List<float[]> NORMAL_RIGHT;
    /**
     * An oct-encoded unit vector with 32-bits of precision defining the up direction for the orientation of the instance.
     * uint16[2],
     * Not Required, unless NORMAL_RIGHT_OCT32P is defined.
     */
    private List<int[]> NORMAL_UP_OCT32P;
    /**
     * An oct-encoded unit vector with 32-bits of precision defining the right direction for the orientation of the instance. Must be orthogonal to up.
     * uint16[2],
     * Not Required, unless NORMAL_UP_OCT32P is defined.
     */
    private List<int[]> NORMAL_RIGHT_OCT32P;
    /**
     * A number defining a scale to apply to all axes of the instance.
     * float32,
     * Not Required
     */
    private List<Float> SCALE;
    /**
     * A 3-component array of numbers defining the scale to apply to the x, y, and z axes of the instance.
     * float32[3],
     * Not Required
     */
    private List<float[]> SCALE_NON_UNIFORM;
    /**
     * The batchId of the instance that can be used to retrieve metadata from the Batch Table.
     * uint8, uint16 (default), or uint32,
     * Not Required
     */
    private List<Long> BATCH_ID;

    //Global semantics
    /**
     * The number of instances to generate. The length of each array value for an instance semantic should be equal to this.
     * uint32,
     * Required
     */
    private int INSTANCES_LENGTH;
    /**
     * A 3-component array of numbers defining the center position when instance positions are defined relative-to-center.
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
     * When true and per-instance orientation is not defined, each instance will default to the east/north/up reference frame's orientation on the WGS84 ellipsoid.
     * boolean,
     * Not Required
     */
    private Boolean EAST_NORTH_UP;

    private void mismatchedLength(String property) {
        throw new IllegalArgumentException("Mismatched length of " + property + " array.");
    }

    private void checkListElements(String property, List list, int componentLength) {
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

    public void validate() {
        if (POSITION == null && POSITION_QUANTIZED == null) {
            throw new IllegalArgumentException("Either POSITION or POSITION_QUANTIZED is needed.");
        }
        if (POSITION != null) {
            if (POSITION.size() != INSTANCES_LENGTH) {
                mismatchedLength("POSITION");
            }
            checkListElements("POSITION", POSITION, 3);
        }
        if (POSITION_QUANTIZED != null) {
            if (POSITION_QUANTIZED.size() != INSTANCES_LENGTH) {
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
        if ((NORMAL_UP != null && NORMAL_RIGHT == null) || (NORMAL_UP == null && NORMAL_RIGHT != null)) {
            throw new IllegalArgumentException("NORMAL_UP and NORMAL_RIGHT are depend on each other.");
        }
        if (NORMAL_UP != null) {
            if (NORMAL_UP.size() != INSTANCES_LENGTH) {
                mismatchedLength("NORMAL_UP");
            }
            checkListElements("NORMAL_UP", NORMAL_UP, 3);
            if (NORMAL_RIGHT.size() != INSTANCES_LENGTH) {
                mismatchedLength("NORMAL_RIGHT");
            }
            checkListElements("NORMAL_RIGHT", NORMAL_RIGHT, 3);
        }
        if ((NORMAL_UP_OCT32P != null && NORMAL_RIGHT_OCT32P == null) || (NORMAL_UP_OCT32P == null && NORMAL_RIGHT_OCT32P != null)) {
            throw new IllegalArgumentException("NORMAL_UP_OCT32P and NORMAL_RIGHT_OCT32P are depend on each other.");
        }
        if (NORMAL_UP_OCT32P != null) {
            if (NORMAL_UP_OCT32P.size() != INSTANCES_LENGTH) {
                mismatchedLength("NORMAL_UP_OCT32P");
            }
            checkListElements("NORMAL_UP_OCT32P", NORMAL_UP_OCT32P, 2);
            if (NORMAL_RIGHT_OCT32P.size() != INSTANCES_LENGTH) {
                mismatchedLength("NORMAL_RIGHT_OCT32P");
            }
            checkListElements("NORMAL_RIGHT_OCT32P", NORMAL_RIGHT_OCT32P, 2);
        }
        if (SCALE != null) {
            if (SCALE.size() != INSTANCES_LENGTH) {
                mismatchedLength("SCALE");
            }
            checkListElements("SCALE", SCALE, 1);
        }
        if (SCALE_NON_UNIFORM != null) {
            if (SCALE_NON_UNIFORM.size() != INSTANCES_LENGTH) {
                mismatchedLength("SCALE_NON_UNIFORM");
            }
            checkListElements("SCALE_NON_UNIFORM", SCALE_NON_UNIFORM, 3);
        }
        if (BATCH_ID != null) {
            if (BATCH_ID.size() != INSTANCES_LENGTH) {
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

    public List<float[]> getNormalUpList() {
        return NORMAL_UP;
    }

    public void setNormalUpList(List<float[]> value) {
        NORMAL_UP = value;
    }

    public float[] getNormalUp(int featureId) {
        return NORMAL_UP != null ? NORMAL_UP.get(featureId) : null;
    }

    public void setNormalUp(int featureId, float[] value) {
        NORMAL_UP.set(featureId, value);
    }

    public int addNormalUp(float[] value) {
        if (NORMAL_UP == null) {
            NORMAL_UP = new ArrayList<>();
        }
        NORMAL_UP.add(value);
        return NORMAL_UP.size() - 1;
    }

    public float[] removeNormalUp(int featureId) {
        return NORMAL_UP != null ? NORMAL_UP.remove(featureId) : null;
    }

    public List<float[]> getNormalRightList() {
        return NORMAL_RIGHT;
    }

    public void setNormalRightList(List<float[]> value) {
        NORMAL_RIGHT = value;
    }

    public float[] getNormalRight(int featureId) {
        return NORMAL_RIGHT != null ? NORMAL_RIGHT.get(featureId) : null;
    }

    public float[] setNormalRight(int featureId, float[] value) {
        return NORMAL_RIGHT.set(featureId, value);
    }

    public int addNormalRight(float[] value) {
        if (NORMAL_RIGHT == null) {
            NORMAL_RIGHT = new ArrayList<>();
        }
        NORMAL_RIGHT.add(value);
        return NORMAL_RIGHT.size() - 1;
    }

    public float[] removeNormalRight(int featureId) {
        return NORMAL_RIGHT != null ? NORMAL_RIGHT.remove(featureId) : null;
    }

    public List<int[]> getNormalUpOct32pList() {
        return NORMAL_UP_OCT32P;
    }

    public void setNormalUpOct32pList(List<int[]> value) {
        NORMAL_UP_OCT32P = value;
    }

    public int[] getNormalUpOct32p(int featureId) {
        return NORMAL_UP_OCT32P != null ? NORMAL_UP_OCT32P.get(featureId) : null;
    }

    public int[] setNormalUpOct32p(int featureId, int[] value) {
        return NORMAL_UP_OCT32P.set(featureId, value);
    }

    public int addNormalUpOct32p(int[] value) {
        if (NORMAL_UP_OCT32P == null) {
            NORMAL_UP_OCT32P = new ArrayList<>();
        }
        NORMAL_UP_OCT32P.add(value);
        return NORMAL_UP_OCT32P.size() - 1;
    }

    public int[] removeNormalUpOct32p(int featureId) {
        return NORMAL_UP_OCT32P != null ? NORMAL_UP_OCT32P.remove(featureId) : null;
    }

    public List<int[]> getNormalRightOct32pList() {
        return NORMAL_RIGHT_OCT32P;
    }

    public void setNormalRightOct32pList(List<int[]> value) {
        NORMAL_RIGHT_OCT32P = value;
    }

    public int[] getNormalRightOct32p(int featureId) {
        return NORMAL_RIGHT_OCT32P != null ? NORMAL_RIGHT_OCT32P.get(featureId) : null;
    }

    public int[] setNormalRightOct32p(int featureId, int[] value) {
        return NORMAL_RIGHT_OCT32P.set(featureId, value);
    }

    public int addNormalRightOct32p(int[] value) {
        if (NORMAL_RIGHT_OCT32P == null) {
            NORMAL_RIGHT_OCT32P = new ArrayList<>();
        }
        NORMAL_RIGHT_OCT32P.add(value);
        return NORMAL_RIGHT_OCT32P.size() - 1;
    }

    public int[] removeNormalOct32p(int featureId) {
        return NORMAL_RIGHT_OCT32P != null ? NORMAL_RIGHT_OCT32P.remove(featureId) : null;
    }

    public List<Float> getScaleList() {
        return SCALE;
    }

    public void setScaleList(List<Float> value) {
        SCALE = value;
    }

    public Float getScale(int featureId) {
        if (SCALE == null) {
            return null;
        }
        return SCALE.get(featureId);
    }

    public Float setScale(int featureId, float value) {
        return SCALE.set(featureId, value);
    }

    public int addScale(float value) {
        if (SCALE == null) {
            SCALE = new ArrayList<>();
        }
        SCALE.add(value);
        return SCALE.size() - 1;
    }

    public Float removeScale(int featureId) {
        return SCALE != null ? SCALE.remove(featureId) : null;
    }

    public List<float[]> getScaleNonUniformList() {
        return SCALE_NON_UNIFORM;
    }

    public void setScaleNonUniformList(List<float[]> value) {
        SCALE_NON_UNIFORM = value;
    }

    public float[] getScaleNonUniform(int featureId) {
        return SCALE_NON_UNIFORM != null ? SCALE_NON_UNIFORM.get(featureId) : null;
    }

    public float[] setScaleNonUniform(int featureId, float[] value) {
        return SCALE_NON_UNIFORM.set(featureId, value);
    }

    public int addScaleNonUniform(float[] value) {
        if (SCALE_NON_UNIFORM == null) {
            SCALE_NON_UNIFORM = new ArrayList<>();
        }
        SCALE_NON_UNIFORM.add(value);
        return SCALE_NON_UNIFORM.size() - 1;
    }

    public float[] removeScaleNonUniform(int featureId) {
        return SCALE_NON_UNIFORM != null ? SCALE_NON_UNIFORM.remove(featureId) : null;
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

    public int getInstancesLength() {
        return INSTANCES_LENGTH;
    }

    public void setInstancesLength(int value) {
        INSTANCES_LENGTH = value;
    }

    public float[] getRtcCenter() {
        return RTC_CENTER;
    }

    public void setRtcCenter(float[] RTC_CENTER) {
        this.RTC_CENTER = RTC_CENTER;
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

    public Boolean getEastNorthUp() {
        return EAST_NORTH_UP;
    }

    public void setEastNorthUp(Boolean value) {
        EAST_NORTH_UP = value;
    }

    private static List<Float> createFloatList(float[] data, int listSize) {
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    private static List<float[]> createFloat3List(float[] data, int listSize) {
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

    private static List<Integer> createUnsignedShortList(int[] data, int listSize) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    private static List<Long> createUnsignedIntList(long[] data, int listSize) {
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            list.add(data[i]);
        }
        return list;
    }

    private static List<int[]> createUnsignedShortVecList(int[] data, int listSize, int componentLength) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            int[] el = new int[componentLength];
            System.arraycopy(data, componentLength * i, el, 0, componentLength);
            list.add(el);
        }
        return list;
    }

    public static I3dmFeatureTable read(InputStream is, I3dmHeader header, JsonParser parser) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
        I3dmFeatureTable table = new I3dmFeatureTable();
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
        Integer intValue = FeatureUtils.getIntegerScalarFeatureValue(jsonHeaderMap, binaryByteBuff, "INSTANCES_LENGTH");
        if (intValue == null) {
            throw new IllegalArgumentException("INSTANCES_LENGTH is required for i3dm file.");
        }
        int instanceLength = intValue;
        table.setInstancesLength(instanceLength);
        table.setRtcCenter(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "RTC_CENTER"));
        table.setQuantizedVolumeOffset(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "QUANTIZED_VOLUME_OFFSET"));
        table.setQuantizedVolumeScale(FeatureUtils.getFloatVec3FeatureValue(jsonHeaderMap, binaryByteBuff, "QUANTIZED_VOLUME_SCALE"));
        table.setEastNorthUp(TypeUtils.toBoolean(jsonHeaderMap.get("EAST_NORTH_UP")));

        float[] floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "POSITION", instanceLength * 3);
        if (floatArrayValue != null) {
            table.setPositionList(createFloat3List(floatArrayValue, instanceLength));
        }
        int[] unsignedShortArrayValue = FeatureUtils.getUnsignedShortArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "POSITION_QUANTIZED", instanceLength * 3);
        if (unsignedShortArrayValue != null) {
            table.setPositionQuantizedList(createUnsignedShortVecList(unsignedShortArrayValue, instanceLength, 3));
        }
        floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL_UP", instanceLength * 3);
        if (floatArrayValue != null) {
            table.setNormalUpList(createFloat3List(floatArrayValue, instanceLength));
        }
        floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL_RIGHT", instanceLength * 3);
        if (floatArrayValue != null) {
            table.setNormalRightList(createFloat3List(floatArrayValue, instanceLength));
        }
        unsignedShortArrayValue = FeatureUtils.getUnsignedShortArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL_UP_OCT32P", instanceLength * 2);
        if (unsignedShortArrayValue != null) {
            table.setNormalUpOct32pList(createUnsignedShortVecList(unsignedShortArrayValue, instanceLength, 2));
        }
        unsignedShortArrayValue = FeatureUtils.getUnsignedShortArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "NORMAL_RIGHT_OCT32P", instanceLength * 2);
        if (unsignedShortArrayValue != null) {
            table.setNormalRightOct32pList(createUnsignedShortVecList(unsignedShortArrayValue, instanceLength, 2));
        }
        floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "SCALE", instanceLength);
        if (floatArrayValue != null) {
            table.setScaleList(createFloatList(floatArrayValue, instanceLength));
        }
        floatArrayValue = FeatureUtils.getFloatArrayFeatureValue(jsonHeaderMap, binaryByteBuff, "SCALE_NON_UNIFORM", instanceLength * 3);
        if (floatArrayValue != null) {
            table.setScaleNonUniformList(createFloat3List(floatArrayValue, instanceLength));
        }
        long[] unsignedIntArrayValue = FeatureUtils.getBatchId(jsonHeaderMap, binaryByteBuff, instanceLength);
        if (unsignedIntArrayValue != null) {
            table.setBatchIdList(createUnsignedIntList(unsignedIntArrayValue, instanceLength));
        }
        return table;
    }

    private Map<String, Object> createReference(int offset, @Nullable String componentType) {
        Map<String, Object> reference = new HashMap<>();
        reference.put("byteOffset", offset);
        if (componentType != null) {
            reference.put("componentType", componentType);
        }
        return reference;
    }

    private int writeFloatList(LittleEndianDataOutputStream dos, List<Float> list) throws IOException {
        int writes = 0;
        for (float el : list) {
            dos.writeFloat(el);
            writes += 4;
        }
        return writes;
    }

    private int writeFloatArrayList(LittleEndianDataOutputStream dos, List<float[]> list) throws IOException {
        int writes = 0;
        for (float[] el : list) {
            for (float v : el) {
                dos.writeFloat(v);
                writes += 4;
            }
        }
        return writes;
    }

    private int writeUnsignedShortArrayList(LittleEndianDataOutputStream dos, List<int[]> list) throws IOException {
        int writes = 0;
        for (int[] el : list) {
            for (int i : el) {
                dos.writeShort(i);
                writes += 2;
            }
        }
        return writes;
    }

    private void fillPadding(LittleEndianDataOutputStream dos, int padding) throws IOException {
        for (int i = 0; i < padding; ++i) {
            dos.write(0);
        }
    }

    public byte[] createBuffer(I3dmHeader header, JsonParser parser) throws IOException {
        validate();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("INSTANCES_LENGTH", getInstancesLength());
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
        Boolean eastNorthUp = getEastNorthUp();
        if (eastNorthUp != null) {
            data.put("EAST_NORTH_UP", eastNorthUp);
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            try (LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(bos)){
                int offset = 0;
                int size = 0;
                int paddingUnit;
                int padding = 0;
                int writes;
                List<float[]> positionList = getPositionList();
                if (positionList != null && !positionList.isEmpty()) {
                    paddingUnit = 4;
                    data.put("POSITION", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeFloatArrayList(dos, positionList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<int[]> positionQuantizedList = getPositionQuantizedList();
                if (positionQuantizedList != null && !positionQuantizedList.isEmpty()) {
                    paddingUnit = 2;
                    data.put("POSITION_QUANTIZED", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeUnsignedShortArrayList(dos, positionQuantizedList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<float[]> normalUpList = getNormalUpList();
                if (normalUpList != null && !normalUpList.isEmpty()) {
                    paddingUnit = 4;
                    data.put("NORMAL_UP", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeFloatArrayList(dos, normalUpList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<float[]> normalRightList = getNormalRightList();
                if (normalRightList != null && !normalRightList.isEmpty()) {
                    paddingUnit = 4;
                    data.put("NORMAL_RIGHT", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeFloatArrayList(dos, normalRightList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<int[]> normalUpOct32pList = getNormalUpOct32pList();
                if (normalUpOct32pList != null && !normalUpOct32pList.isEmpty()) {
                    paddingUnit = 2;
                    data.put("NORMAL_UP_OCT32P", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeUnsignedShortArrayList(dos, normalUpOct32pList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<int[]> normalRightOct32pList = getNormalRightOct32pList();
                if (normalRightOct32pList != null && !normalRightOct32pList.isEmpty()) {
                    paddingUnit = 2;
                    data.put("NORMAL_RIGHT_OCT32P", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeUnsignedShortArrayList(dos, normalRightOct32pList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<Float> scaleList = getScaleList();
                if (scaleList != null && !scaleList.isEmpty()) {
                    paddingUnit = 4;
                    data.put("SCALE", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeFloatList(dos, scaleList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<float[]> scaleNonUniformList = getScaleNonUniformList();
                if (scaleNonUniformList != null && !scaleNonUniformList.isEmpty()) {
                    paddingUnit = 4;
                    data.put("SCALE_NON_UNIFORM", createReference(offset, null));
                    fillPadding(dos, padding);
                    writes = writeFloatArrayList(dos, scaleNonUniformList);
                    offset += writes;
                    size = offset;
                    padding = calcPadding(offset, paddingUnit);
                    offset += padding;
                }
                List<Long> batchIdList = getBatchIdList();
                if (batchIdList != null && !batchIdList.isEmpty()) {
                    Long maxValue = batchIdList.stream().max(Long::compareTo).orElse(null);
                    fillPadding(dos, padding);
                    if (maxValue > 0xFFFF) {
                        // paddingUnit = 4;
                        data.put("BATCH_ID", createReference(offset, "UNSIGNED_INT"));
                        writes = 0;
                        for (Long aLong : batchIdList) {
                            dos.writeInt((int) (long) aLong);
                            writes += 4;
                        }
                    } else if (maxValue > 0xFF) {
                        // paddingUnit = 2;
                        data.put("BATCH_ID", createReference(offset, "UNSIGNED_SHORT"));
                        writes = 0;
                        for (Long aLong : batchIdList) {
                            dos.writeShort((int) (long) aLong);
                            writes += 2;
                        }
                    } else {
                        // paddingUnit = 1;
                        data.put("BATCH_ID", createReference(offset, "UNSIGNED_BYTE"));
                        writes = 0;
                        for (Long aLong : batchIdList) {
                            dos.write((int) (long) aLong);
                            writes += 1;
                        }
                    }
                    offset += writes;
                    size = offset;
                    // padding = calcPadding(offset, paddingUnit);
                    // offset += padding;
                }
                padding = calcPadding(size, 8);
                fillPadding(dos, padding);
                byte[] jsonBytes = parser.toJsonString(data).getBytes(StandardCharsets.UTF_8);
                int jsonPadding = calcPadding(jsonBytes.length, 8);
                header.setFeatureTableJSONByteLength(jsonBytes.length + jsonPadding);
                byte[] binaryBytes = bos.toByteArray();
                header.setFeatureTableBinaryByteLength(binaryBytes.length);
                byte[] out = new byte[jsonBytes.length + jsonPadding + binaryBytes.length];
                System.arraycopy(jsonBytes, 0, out, 0, jsonBytes.length);
                for (int i = 0; i < jsonPadding; ++i) {
                    out[jsonBytes.length + i] = 0x20;
                }
                System.arraycopy(binaryBytes, 0, out, jsonBytes.length + jsonPadding, binaryBytes.length);
                return out;
            }
        }
    }
}
