package me.cxj.j3dtiles.utils;

import me.cxj.j3dtiles.model.v1.FloatVec3;
import me.cxj.j3dtiles.model.v1.FloatVec4;

import java.util.List;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class TypeUtils {

    public static byte toByte(Object data) {
        if (data instanceof Byte) {
            return (Byte) data;
        }
        if (data instanceof Number) {
            return ((Number) data).byteValue();
        }
        if (data instanceof String) {
            return Byte.parseByte((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a byte value");
    }

    public static byte[] toByteArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a byte array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        byte[] out = new byte[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toByte(data.get(i));
        }
        return out;
    }

    public static short toShort(Object data) {
        if (data instanceof Short) {
            return (Short) data;
        }
        if (data instanceof Number) {
            return ((Number) data).shortValue();
        }
        if (data instanceof String) {
            return Short.parseShort((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a short value");
    }

    public static short[] toShortArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a short array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        short[] out = new short[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toShort(data.get(i));
        }
        return out;
    }

    public static int toInteger(Object data) {
        if (data instanceof Integer) {
            return (Integer) data;
        }
        if (data instanceof Number) {
            return ((Number) data).intValue();
        }
        if (data instanceof String) {
            return Integer.parseInt((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a integer value");
    }

    public static int[] toIntegerArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a integer array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        int[] out = new int[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toInteger(data.get(i));
        }
        return out;
    }

    public static long toLong(Object data) {
        if (data instanceof Long) {
            return (Long) data;
        }
        if (data instanceof Number) {
            return ((Number) data).longValue();
        }
        if (data instanceof String) {
            return Long.parseLong((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a long value");
    }

    public static long[] toLongArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a long array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        long[] out = new long[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toLong(data.get(i));
        }
        return out;
    }

    public static float toFloat(Object data) {
        if (data instanceof Float) {
            return (Float) data;
        }
        if (data instanceof Number) {
            return ((Number) data).floatValue();
        }
        if (data instanceof String) {
            return Float.parseFloat((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a float value");
    }

    public static float[] toFloatArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a float array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        float[] out = new float[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toFloat(data.get(i));
        }
        return out;
    }

    public static double toDouble(Object data) {
        if (data instanceof Double) {
            return (Double) data;
        }
        if (data instanceof Number) {
            return ((Number) data).doubleValue();
        }
        if (data instanceof String) {
            return Double.parseDouble((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a double value");
    }

    public static double[] toDoubleArray(List data, int length) {
        if (length >= 0 && data.size() != length) {
            throw new IllegalArgumentException("Unable to transform " + data + " to a double array with length " + length + ". Length mismatched.");
        }
        int size = length >= 0 ? length : data.size();
        double[] out = new double[size];
        for (int i = 0; i < size; ++i) {
            out[i] = toDouble(data.get(i));
        }
        return out;
    }

    public static Boolean toBoolean(Object data) {
        if (data == null) {
            return null;
        }
        if (data instanceof Boolean) {
            return (Boolean) data;
        }
        if ("true".equals(data.toString().toLowerCase())) {
            return true;
        }
        if ("false".equals(data.toString().toLowerCase())) {
            return false;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a boolean value");
    }

    public static FloatVec3 toFloatVec3(Object data) {
        if (data instanceof FloatVec3) {
            return (FloatVec3) data;
        }
        if (data instanceof List && ((List) data).size() == 3) {
            List listData = (List) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX(toFloat(listData.get(0)));
            vec.setY(toFloat(listData.get(1)));
            vec.setZ(toFloat(listData.get(2)));
            return vec;
        }
        if (data instanceof Object[] && ((Object[]) data).length == 3) {
            Object[] arrayData = (Object[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX(toFloat(arrayData[0]));
            vec.setY(toFloat(arrayData[1]));
            vec.setZ(toFloat(arrayData[2]));
            return vec;
        }
        if (data instanceof float[] && ((float[]) data).length == 3) {
            float[] arrayData = (float[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX(arrayData[0]);
            vec.setY(arrayData[1]);
            vec.setZ(arrayData[2]);
            return vec;
        }
        if (data instanceof double[] && ((double[]) data).length == 3) {
            double[] arrayData = (double[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            return vec;
        }
        if (data instanceof int[] && ((int[]) data).length == 3) {
            int[] arrayData = (int[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            return vec;
        }
        if (data instanceof long[] && ((long[]) data).length == 3) {
            long[] arrayData = (long[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            return vec;
        }
        if (data instanceof short[] && ((short[]) data).length == 3) {
            short[] arrayData = (short[]) data;
            FloatVec3 vec = new FloatVec3();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            return vec;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a float vector3 value");
    }

    public static FloatVec4 toFloatVec4(Object data) {
        if (data instanceof FloatVec4) {
            return (FloatVec4) data;
        }
        if (data instanceof List && ((List) data).size() == 3) {
            List listData = (List) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX(toFloat(listData.get(0)));
            vec.setY(toFloat(listData.get(1)));
            vec.setZ(toFloat(listData.get(2)));
            vec.setW(toFloat(listData.get(3)));
            return vec;
        }
        if (data instanceof Object[] && ((Object[]) data).length == 4) {
            Object[] arrayData = (Object[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX(toFloat(arrayData[0]));
            vec.setY(toFloat(arrayData[1]));
            vec.setZ(toFloat(arrayData[2]));
            vec.setW(toFloat(arrayData[3]));
            return vec;
        }
        if (data instanceof float[] && ((float[]) data).length == 4) {
            float[] arrayData = (float[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX(arrayData[0]);
            vec.setY(arrayData[1]);
            vec.setZ(arrayData[2]);
            vec.setW(arrayData[3]);
            return vec;
        }
        if (data instanceof double[] && ((double[]) data).length == 4) {
            double[] arrayData = (double[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            vec.setW((float) arrayData[3]);
            return vec;
        }
        if (data instanceof int[] && ((int[]) data).length == 4) {
            int[] arrayData = (int[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            vec.setW((float) arrayData[3]);
            return vec;
        }
        if (data instanceof long[] && ((long[]) data).length == 4) {
            long[] arrayData = (long[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            vec.setW((float) arrayData[3]);
            return vec;
        }
        if (data instanceof short[] && ((short[]) data).length == 4) {
            short[] arrayData = (short[]) data;
            FloatVec4 vec = new FloatVec4();
            vec.setX((float) arrayData[0]);
            vec.setY((float) arrayData[1]);
            vec.setZ((float) arrayData[2]);
            vec.setW((float) arrayData[3]);
            return vec;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a float vector3 value");
    }
}
