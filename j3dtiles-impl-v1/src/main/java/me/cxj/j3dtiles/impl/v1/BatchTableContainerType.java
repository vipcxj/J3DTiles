package me.cxj.j3dtiles.impl.v1;

/**
 * Created by vipcxj on 2018/10/31.
 */
public enum BatchTableContainerType {

    SCALAR(1), VEC2(2), VEC3(3), VEC4(4);

    private final int size;

    BatchTableContainerType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static boolean is(String value) {
        for (BatchTableContainerType type : values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String allValues() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (BatchTableContainerType type : values()) {
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
