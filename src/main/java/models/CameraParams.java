package models;

public class CameraParams {
    private Vector3 C;
    private Vector3 N;
    private Vector3 V;
    private float fovy;
    private float near;
    private float far;
    private int currentParam = 1;

    public void addParam(String line){

        switch (this.currentParam){
            case 1: this.C = getVector(line); break;
            case 2: this.N = getVector(line); break;
            case 3: this.V = getVector(line); break;
            case 4: this.fovy = Float.parseFloat(line);
            case 5: this.near = Float.parseFloat(line);
            case 6: this.far = Float.parseFloat(line);
        }
        this.currentParam++;
    }

    private Vector3 getVector(String line){
        String[] split = line.split(" ");
        return new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }



    public float[] getC() {
        return C.toArray();
    }

    public void moveLeft(){
        this.C.setX(this.C.getX() - 100);
    }


    public float[] getN() {
        return N.toArray();
    }

    public float[] getV() {
        return V.toArray();
    }

    public float getFovy() {
        return fovy;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }
}
