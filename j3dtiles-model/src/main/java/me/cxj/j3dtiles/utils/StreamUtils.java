package me.cxj.j3dtiles.utils;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class StreamUtils {

    public static short bytes2ShortLE(byte b1, byte b2) {
        short mask = 0xff;
        return (short) ((b1 & mask) | (b2 & mask << 8));
    }

    public static short bytes2ShortLE(byte[] bytes, int offset) {
        return bytes2ShortLE(bytes[offset], bytes[offset + 1]);
    }

    public static short[] bytes2ShortArrayLE(byte[] bytes, int offset, int targetLength) {
        short[] out = new short[targetLength];
        for (int i = 0; i < targetLength; ++i) {
            out[i] = bytes2ShortLE(bytes, offset + i * 2);
        }
        return out;
    }

    public static int bytes2UnsignedShortLE(byte b1, byte b2) {
        short mask = 0xff;
        return (b1 & mask) | (b2 & mask << 8);
    }

    public static int bytes2UnsignedShortLE(byte[] bytes, int offset) {
        return bytes2UnsignedShortLE(bytes[offset], bytes[offset + 1]);
    }

    public static int[] bytes2UnsignedShortArrayLE(byte[] bytes, int offset, int targetLength) {
        int[] out = new int[targetLength];
        for (int i = 0; i < targetLength; ++i) {
            out[i] = bytes2UnsignedShortLE(bytes, offset + i * 2);
        }
        return out;
    }

    public static int bytes2IntLE(byte b1, byte b2, byte b3, byte b4) {
        int mask = 0xff;
        return (b1 & mask) | (b2 & mask << 8) | (b3 & mask << 16) | (b4 & mask << 24);
    }

    public static int bytes2IntLE(byte[] bytes, int offset) {
        return bytes2IntLE(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
    }

    public static long bytes2UnsignedIntLE(byte b1, byte b2, byte b3, byte b4) {
        long mask = 0xff;
        return (b1 & mask) | (b2 & mask << 8) | (b3 & mask << 16) | (b4 & mask << 24);
    }

    public static long bytes2UnsignedIntLE(byte[] bytes, int offset) {
        return bytes2IntLE(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
    }

    public static long bytes2LongLE(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        long mask = 0xff;
        return (b1 & mask)
                | (b2 & mask << 8)
                | (b3 & mask << 16)
                | (b4 & mask << 24)
                | (b5 & mask << 32)
                | (b6 & mask << 40)
                | (b7 & mask << 48)
                | (b8 & mask << 56);
    }

    public static long bytes2LongLE(byte[] bytes, int offset) {
        return bytes2LongLE(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3], bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
    }

    public static float bytes2FloatLE(byte b1, byte b2, byte b3, byte b4) {
        return Float.intBitsToFloat(bytes2IntLE(b1, b2, b3, b4));
    }

    public static float bytes2FloatLE(byte[] bytes, int offset) {
        return Float.intBitsToFloat(bytes2IntLE(bytes, offset));
    }

    public static float[] bytes2FloatArrayLE(byte[] bytes, int offset, int targetLength) {
        float[] out = new float[targetLength];
        for (int i = 0; i < targetLength; ++i) {
            out[i] = bytes2FloatLE(bytes, offset + i * 4);
        }
        return out;
    }

    public static double bytes2DoubleLE(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return Double.longBitsToDouble(bytes2LongLE(b1, b2, b3, b4, b5, b6, b7, b8));
    }

    public static double bytes2DoubleLE(byte[] bytes, int offset) {
        return Double.longBitsToDouble(bytes2LongLE(bytes, offset));
    }

    public static void byteArrayCopyToByteArrayLE(byte[] target, int offset, byte[] data) {
        int srcLen = data.length;
        if (offset + srcLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        System.arraycopy(data, 0, target, offset, srcLen);
    }

    public static void unsignedByteArrayCopyToByteArrayLE(byte[] target, int offset, int[] data) {
        int srcLen = data.length;
        if (offset + srcLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            target[offset + i] = (byte) (0xFF & data[i]);
        }
    }

    public static void shortArrayCopyToByteArrayLE(byte[] target, int offset, short[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 2;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            target[offset + 2 * i] = (byte) (0xFF & data[i]);
            target[offset + 2 * i + 1] = (byte) (0xFF & (data[i] >> 8));
        }
    }

    public static void unsignedShortArrayCopyToByteArrayLE(byte[] target, int offset, int[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 2;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            target[offset + 2 * i] = (byte) (0xFF & data[i]);
            target[offset + 2 * i + 1] = (byte) (0xFF & (data[i] >> 8));
        }
    }

    @SuppressWarnings("Duplicates")
    public static void intArrayCopyToByteArrayLE(byte[] target, int offset, int[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 4;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            target[offset + 4 * i] = (byte) (0xFF & data[i]);
            target[offset + 4 * i + 1] = (byte) (0xFF & (data[i] >> 8));
            target[offset + 4 * i + 2] = (byte) (0xFF & (data[i] >> 16));
            target[offset + 4 * i + 3] = (byte) (0xFF & (data[i] >> 24));
        }
    }

    @SuppressWarnings("Duplicates")
    public static void unsignedIntArrayCopyToByteArrayLE(byte[] target, int offset, long[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 4;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            target[offset + 4 * i] = (byte) (0xFF & data[i]);
            target[offset + 4 * i + 1] = (byte) (0xFF & (data[i] >> 8));
            target[offset + 4 * i + 2] = (byte) (0xFF & (data[i] >> 16));
            target[offset + 4 * i + 3] = (byte) (0xFF & (data[i] >> 24));
        }
    }

    public static void floatArrayCopyToByteArrayLE(byte[] target, int offset, float[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 4;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            int intValue = Float.floatToIntBits(data[i]);
            target[offset + 4 * i] = (byte) (0xFF & intValue);
            target[offset + 4 * i + 1] = (byte) (0xFF & (intValue >> 8));
            target[offset + 4 * i + 2] = (byte) (0xFF & (intValue >> 16));
            target[offset + 4 * i + 3] = (byte) (0xFF & (intValue >> 24));
        }
    }

    public static void doubleArrayCopyToByteArrayLE(byte[] target, int offset, double[] data) {
        int srcLen = data.length;
        int tarLen = srcLen * 8;
        if (offset + tarLen > target.length) {
            throw new IllegalArgumentException("Target buffer is too small.");
        }
        for (int i = 0; i < srcLen; ++i) {
            long longValue = Double.doubleToLongBits(data[i]);
            target[offset + 8 * i] = (byte) (0xFF & longValue);
            target[offset + 8 * i + 1] = (byte) (0xFF & (longValue >> 8));
            target[offset + 8 * i + 2] = (byte) (0xFF & (longValue >> 16));
            target[offset + 8 * i + 3] = (byte) (0xFF & (longValue >> 24));
            target[offset + 8 * i + 3] = (byte) (0xFF & (longValue >> 32));
            target[offset + 8 * i + 3] = (byte) (0xFF & (longValue >> 40));
            target[offset + 8 * i + 3] = (byte) (0xFF & (longValue >> 48));
            target[offset + 8 * i + 3] = (byte) (0xFF & (longValue >> 56));
        }
    }
}
