package me.cxj.j3dtiles.utils;

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

    public static byte[] createPaddingBytes(byte[] bytes, int paddingUnit, byte paddingCode) {
        int size = bytes.length;
        int padding = calcPadding(size, paddingUnit);
        byte[] out = new byte[size + padding];
        System.arraycopy(bytes, 0, out, 0, size);
        for (int i = 0; i < padding; ++i) {
            out[size + i] = paddingCode;
        }
        return out;
    }
}
