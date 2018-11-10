package me.cxj.j3dtiles.model.v1;

import java.util.ArrayList;
import java.util.List;

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

    enum ColorFormat {
        RGB, RGBA, RGB565
    }
}
