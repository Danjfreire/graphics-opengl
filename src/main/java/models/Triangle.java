package models;

public class Triangle {

    private int v1;
    private int v2;

    public Triangle(int v1,int v2, int v3){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public boolean containsEdge(int edgeNum) {
        if(edgeNum == this.v1 || edgeNum == this.v2 || edgeNum == this.v3)
            return true;
        else
            return false;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public int getV3() {
        return v3;
    }

    private int v3;

}
