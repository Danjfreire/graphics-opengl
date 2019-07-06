package models;

public class ColorParams {

    private Vector3 Pl;
    private Vector3 l_amb;
    private Vector3 l_diffuse;
    private Vector3 l_spec;
    private Vector3 m_amb;
    private Vector3 m_diffuse;
    private Vector3 m_spec;
    private Vector3 m_emis;
    private int shine;
    private int currentParam = 1;

    public void addParam(String line){

        switch (this.currentParam){
            case 1: this.Pl = getVector(line); break;
            case 2: this.l_amb = getVector(line); break;
            case 3: this.l_diffuse = getVector(line); break;
            case 4: this.l_spec = getVector(line); break;
            case 5: this.m_amb = getVector(line); break;
            case 6: this.m_diffuse = getVector(line); break;
            case 7: this.m_spec = getVector(line); break;
            case 8: this.m_emis = getVector(line); break;
            case 9: this.shine = Integer.parseInt(line); break;

        }
        this.currentParam++;
    }

    private Vector3 getVector(String line){
        String[] split = line.split(" ");
        return new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }


    public float[] getPl() {
        return Pl.toArray();
    }

    public float[] getL_amb() {
        return l_amb.toArray();
    }

    public float[] getL_diffuse() {
        return l_diffuse.toArray();
    }

    public float[] getL_spec() {
        return l_spec.toArray();
    }

    public float[] getM_amb() {
        return m_amb.toArray();
    }

    public float[] getM_diffuse() {
        return m_diffuse.toArray();
    }

    public float[] getM_spec() {
        return m_spec.toArray();
    }

    public float[] getM_emis() {
        return m_emis.toArray();
    }

    public float[] getShine() {
        return new float[]{shine};
    }

}
