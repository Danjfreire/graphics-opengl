package models;

public class Vector3 implements Comparable<Vector3> {
    private double x;
    private double y;
    private double z;

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public int compareTo(Vector3 v2) {
        if (this.getY() < v2.getY()) {
            return 1;
        }
        if (this.getY() > v2.getY()) {
            return -1;
        }
        return 0;
    }

    public float[] toArray() {
        return new float[]{(float) this.x, (float) this.y, (float) this.z};
    }
}
