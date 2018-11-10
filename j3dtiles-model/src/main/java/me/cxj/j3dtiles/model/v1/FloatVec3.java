package me.cxj.j3dtiles.model.v1;

import java.io.Serializable;

/**
 * Created by vipcxj on 2018/10/30.
 */
public class FloatVec3 implements Serializable {
    private float x;
    private float y;
    private float z;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloatVec3)) return false;

        FloatVec3 vec3 = (FloatVec3) o;

        if (Float.compare(vec3.getX(), getX()) != 0) return false;
        if (Float.compare(vec3.getY(), getY()) != 0) return false;
        return Float.compare(vec3.getZ(), getZ()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        result = 31 * result + (getZ() != +0.0f ? Float.floatToIntBits(getZ()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FloatVec3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
