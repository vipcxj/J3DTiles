package me.cxj.j3dtiles.utils;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class TypeUtils {

    private static int checkArrayLength(Object array, Integer length) {
        int size = Array.getLength(array);
        if (length != null && size != length) {
            throw new IllegalArgumentException("Mismatched array length. Expect " + length + ", " + size + " in fact.");
        }
        return size;
    }

    private static int checkListLength(List list, Integer length) {
        int size = list.size();
        if (length != null && size != length) {
            throw new IllegalArgumentException("Mismatched list length. Expect " + length + ", " + size + " in fact.");
        }
        return size;
    }

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

    public static byte toUnsignedByte(Object data) {
        if (data instanceof Byte) {
            return (byte) data;
        }
        if (data instanceof Number) {
            return (byte) ((Number) data).intValue();
        }
        if (data instanceof String) {
            return (byte) Short.parseShort((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to an unsigned byte value");
    }

    public static byte[] toByteArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof byte[]) {
            return (byte[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            byte[] out = new byte[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toByte(Array.get(data, i));
            }
            return out;
         }
         if (data instanceof List) {
             List list = (List) data;
             int size = checkListLength(list, length);
             byte[] out = new byte[size];
             for (int i = 0; i < size; ++i) {
                 out[i] = toByte(list.get(i));
             }
             return out;
         }
        throw new IllegalArgumentException("Unable to transform " + data + " to a byte array value");
    }

    public static byte[] toUnsignedByteArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof byte[]) {
            return (byte[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            byte[] out = new byte[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedByte(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            byte[] out = new byte[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedByte(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a unsigned byte array value");
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

    public static short toUnsignedShort(Object data) {
        if (data instanceof Short) {
            return (Short) data;
        }
        if (data instanceof Number) {
            return (short) ((Number) data).intValue();
        }
        if (data instanceof String) {
            return (short) Integer.parseInt((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a unsigned short value");
    }

    public static short[] toShortArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof short[]) {
            return (short[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            short[] out = new short[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toShort(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            short[] out = new short[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toShort(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a short array value");
    }

    public static short[] toUnsignedShortArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof short[]) {
            return (short[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            short[] out = new short[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedShort(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            short[] out = new short[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedShort(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to an unsigned short array value");
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

    public static int toUnsignedInteger(Object data) {
        if (data instanceof Integer) {
            return (Integer) data;
        }
        if (data instanceof Number) {
            return (int) ((Number) data).longValue();
        }
        if (data instanceof String) {
            return (int) Long.parseLong((String) data);
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to an unsigned integer value");
    }

    public static int[] toIntegerArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof int[]) {
            return (int[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            int[] out = new int[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toInteger(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            int[] out = new int[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toInteger(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a int array value");
    }

    public static int[] toUnsignedIntegerArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof int[]) {
            return (int[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            int[] out = new int[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedInteger(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            int[] out = new int[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toUnsignedInteger(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to an unsigned int array value");
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

    public static long[] toLongArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof long[]) {
            return (long[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            long[] out = new long[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toLong(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            long[] out = new long[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toLong(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a long array value");
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

    public static float[] toFloatArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof float[]) {
            return (float[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            float[] out = new float[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toFloat(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            float[] out = new float[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toFloat(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a float array value");
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

    public static double[] toDoubleArray(Object data, Integer length) {
        if (data == null) {
            return null;
        }
        if (data instanceof double[]) {
            return (double[]) data;
        }
        if (data.getClass().isArray()) {
            int size = checkArrayLength(data, length);
            double[] out = new double[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toDouble(Array.get(data, i));
            }
            return out;
        }
        if (data instanceof List) {
            List list = (List) data;
            int size = checkListLength(list, length);
            double[] out = new double[size];
            for (int i = 0; i < size; ++i) {
                out[i] = toDouble(list.get(i));
            }
            return out;
        }
        throw new IllegalArgumentException("Unable to transform " + data + " to a double array value");
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
}
