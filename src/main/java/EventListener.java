import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import com.sun.scenario.effect.impl.BufferUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class EventListener implements GLEventListener {

    float[] vertices;
    int[] triangulos;
    float[] normais;
    IntBuffer indices;

    FloatBuffer mat_specular;
    FloatBuffer mat_shininess;
    FloatBuffer light_position;
    FloatBuffer white_light;
    FloatBuffer lmodel_ambient;


    private float vectorNorm(float x, float y, float z) {
        double x2 = Math.pow(x, 2);
        double y2 = Math.pow(y, 2);
        double z2 = Math.pow(z, 2);
        double norm = Math.sqrt(x2 + y2 + z2);
        return (float) norm;
    }

    private void loadVertices() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/input/calice2.BYU"));
            String line = reader.readLine();
            String[] line1 = line.split(" ");
            int vertNum = Integer.parseInt(line1[0]);
            int triangleNum = Integer.parseInt(line1[1]);

            vertices = new float[vertNum * 3];
            triangulos = new int[triangleNum * 3];
            normais = new float[vertNum];

            int vertexIterator = 0;
            int triangleIterator = 0;

            int aux = 0;
            String[] splitLine;
            line = reader.readLine();
            while (line != null) {
                splitLine = line.split(" ");
                if (aux < vertNum) {
                    vertices[vertexIterator] = Float.parseFloat(splitLine[0]);
                    vertexIterator++;
                    vertices[vertexIterator] = Float.parseFloat(splitLine[1]);
                    vertexIterator++;
                    vertices[vertexIterator] = Float.parseFloat(splitLine[2]);
                    vertexIterator++;
                    normais[aux] = vectorNorm(Float.parseFloat(splitLine[0]),Float.parseFloat(splitLine[1]),Float.parseFloat(splitLine[2]));
                } else {
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[0]) -1;
                    triangleIterator++;
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[1]) -1;
                    triangleIterator++;
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[2]) -1;
                    triangleIterator++;
                }
                aux++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupLightParams(){
        float[] m_specular = new float[]{1,1,1,1};
        float[] m_shineness = new float[]{100};
        float[] l_position = new float[]{0,1,1,0};
        float[] w_light = new float[]{1,1,1,1};
        float[] m_ambient = new float[]{0.1f,0.1f,0.1f,1};

        loadBuffer(this.mat_specular,m_specular);
        loadBuffer(this.mat_shininess,m_shineness);
        loadBuffer(this.light_position,l_position);
        loadBuffer(this.white_light,w_light);
        loadBuffer(this.lmodel_ambient,m_ambient);
    }

    private void loadBuffer(FloatBuffer buffer, float[] params){
        buffer = BufferUtil.newFloatBuffer(params.length);
        for (float param : params) {
            buffer.put(param);
        }
        buffer.rewind();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        setupLightParams();
        GL2 gl = drawable.getGL().getGL2();
        setupVertex(gl);

        float[] m_specular = new float[]{0.5f,0.5f,0.5f,1};
        float[] m_shineness = new float[]{100};
        float[] l_position = new float[]{-1,-1,1,0};
        float[] w_light = new float[]{0.5f, 0.85f, 1, 1};
        float[] emission = new float[]{ 0, 0, 0, 1};
        float[] m_ambient = new float[]{0.4f,0.4f,0.4f,1};

        gl.glClearColor(0,0,0,1);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT,m_ambient,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_EMISSION,emission,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,m_specular,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS,m_shineness,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION,l_position,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_DIFFUSE,w_light,0);
        gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_SPECULAR,w_light,0);
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT,m_ambient,0);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        GLUT glut = new GLUT();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//        glut.glutSolidSphere(1,20,16);
        gl.glLoadIdentity();
        glu.gluLookAt(0,-500,500,0,0,0,0,-1,0);
        gl.glDrawElements(GL.GL_TRIANGLES,triangulos.length,GL.GL_UNSIGNED_INT,this.indices);
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        GLU glu = new GLUgl2();
        gl2.glViewport(0,0, width, height);
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
//        gl2.glOrtho(-1.5 * height/width,1.5 * width/height,-1.5,1.5,-10.0,10);
        glu.gluPerspective(45, width/height,5, 10000);
//        gl2.glFrustum(-1,1,1,-1,1.5,1000);
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
//        gl2.glLoadIdentity();
    }

    private void setupVertex(GL2 gl){

        loadVertices();

        FloatBuffer verticesBuffer = BufferUtil.newFloatBuffer(vertices.length);

        for (float vertex : vertices) {
            verticesBuffer.put(vertex);
        }
        verticesBuffer.rewind();

        FloatBuffer normaisBuffer = BufferUtil.newFloatBuffer(normais.length);

        for (float normal : normais) {
            normaisBuffer.put(normal);
        }
        normaisBuffer.rewind();

        IntBuffer indicesBuffer = BufferUtil.newIntBuffer(triangulos.length);

        for(int indice : triangulos){
            indicesBuffer.put(indice);
        }
        indicesBuffer.rewind();

        gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
        gl.glVertexPointer(3, GL.GL_FLOAT,0,verticesBuffer);
        gl.glNormalPointer(GL.GL_FLOAT,0,normaisBuffer);
        this.indices = indicesBuffer;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    }

}
