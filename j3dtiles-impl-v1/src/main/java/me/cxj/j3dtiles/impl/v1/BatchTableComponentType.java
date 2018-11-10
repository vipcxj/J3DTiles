package me.cxj.j3dtiles.impl.v1;

/**
 * Created by vipcxj on 2018/10/31.
 */
public enum BatchTableComponentType {

    BYTE(1), UNSIGNED_BYTE(1), SHORT(2), UNSIGNED_SHORT(2), INT(4), UNSIGNED_INT(4), FLOAT(4), DOUBLE(8);

    private final int size;

    BatchTableComponentType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static boolean is(String value) {
        for (BatchTableComponentType type : values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String allValues() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (BatchTableComponentType type : values()) {
            if (first) {
                first = false;
                sb.append(type.name());
            } else {
                sb.append(", ").append(type.name());
            }
        }
        return sb.toString();
    }
}
