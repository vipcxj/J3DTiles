package me.cxj.j3dtiles.model.v1;

/**
 * Created by vipcxj on 2018/11/9.
 */
public abstract class BatchHeader extends Header {

    private int batchTableJSONByteLength;
    private int batchTableBinaryByteLength;

    public int getBatchTableJSONByteLength() {
        return batchTableJSONByteLength;
    }

    public void setBatchTableJSONByteLength(int batchTableJSONByteLength) {
        this.batchTableJSONByteLength = batchTableJSONByteLength;
    }

    public int getBatchTableBinaryByteLength() {
        return batchTableBinaryByteLength;
    }

    public void setBatchTableBinaryByteLength(int batchTableBinaryByteLength) {
        this.batchTableBinaryByteLength = batchTableBinaryByteLength;
    }
}
