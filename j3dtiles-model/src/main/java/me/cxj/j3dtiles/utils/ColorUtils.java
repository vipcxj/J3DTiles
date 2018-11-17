package me.cxj.j3dtiles.utils;

/**
 * Created by vipcxj on 2018/11/12.
 */
public class ColorUtils {

    public static int getRed(int color) {
        return (color & 0x00FF0000) >>> 16;
    }

    public static int getGreen(int color) {
        return (color & 0x0000FF00) >>> 8;
    }

    public static int getBlue(int color) {
        return color & 0x000000FF;
    }

    public static int getAlpha(int color) {
        return (color & 0xFF000000) >>> 24;
    }

    public static byte[] toRGBA(int color) {
        return new byte[] {(byte) getRed(color), (byte) getGreen(color), (byte) getBlue(color), (byte) getAlpha(color)};
    }

    public static byte[] toRGB(int color) {
        return new byte[] {(byte) getRed(color), (byte) getGreen(color), (byte) getBlue(color)};
    }

    public static short toRGB565(int color) {
        int r = getRed(color);
        int g = getGreen(color);
        int b = getBlue(color);
        return (short) (r >>> 3 << 11 | g >>> 2 << 5 | b >>> 3);
    }
}
