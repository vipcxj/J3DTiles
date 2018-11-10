package me.cxj.j3dtiles.model.v1;

import java.io.Serializable;

/**
 * Created by vipcxj on 2018/10/31.
 */
public class FloatVec4 implements Serializable {

    private float x;
    private float y;
    private float z;
    private float w;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloatVec4)) return false;

        FloatVec4 floatVec4 = (FloatVec4) o;

        if (Float.compare(floatVec4.getX(), getX()) != 0) return false;
        if (Float.compare(floatVec4.getY(), getY()) != 0) return false;
        if (Float.compare(floatVec4.getZ(), getZ()) != 0) return false;
        return Float.compare(floatVec4.getW(), getW()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        result = 31 * result + (getZ() != +0.0f ? Float.floatToIntBits(getZ()) : 0);
        result = 31 * result + (getW() != +0.0f ? Float.floatToIntBits(getW()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FloatVec4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
