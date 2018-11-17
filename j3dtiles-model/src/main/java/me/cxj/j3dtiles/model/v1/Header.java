package me.cxj.j3dtiles.model.v1;

/**
 * Created by vipcxj on 2018/11/14.
 */
public abstract class Header {

    private int byteLength;

    public abstract byte[] getMagic();

    public abstract int getHeaderLength();

    public int getVersion() {
        return 1;
    }

    public int getByteLength() {
        return byteLength;
    }

    public void setByteLength(int byteLength) {
        this.byteLength = byteLength;
    }
}
