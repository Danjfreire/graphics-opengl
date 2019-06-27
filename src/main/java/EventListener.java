import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.sun.scenario.effect.impl.BufferUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class EventListener implements GLEventListener {

    float[] vertices;
    int[] triangulos;
    IntBuffer indices;

    private void loadVertices() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/input/vaso.BYU"));
            String line = reader.readLine();
            String[] line1 = line.split(" ");
            int vertNum = Integer.parseInt(line1[0]);
            int triangleNum = Integer.parseInt(line1[1]);

            vertices = new float[vertNum * 3];
            triangulos = new int[triangleNum * 3];

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
                } else {
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[0]);
                    triangleIterator++;
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[1]);
                    triangleIterator++;
                    triangulos[triangleIterator] = Integer.parseInt(splitLine[2]);
                    triangleIterator++;
                }
                aux++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,1);
        gl.glShadeModel(GLLightingFunc.GL_FLAT);
        setupVertex(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        GLUT glut = new GLUT();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(0,0,800,0,0,0,0,1,0);
//        gl.glScalef(1,2,1);

        gl.glDrawElements(GL.GL_TRIANGLE_STRIP,triangulos.length,GL.GL_UNSIGNED_INT,this.indices);
//        gl.glDrawArrays(GL.GL_POINTS, 0,triangulos.length);
//        gl.glBegin(GL.GL_TRIANGLES);
//            gl.glVertex3f(vertices[0],vertices[1],vertices[2]);
//            gl.glVertex3f(vertices[3],vertices[4],vertices[5]);
//            gl.glVertex3f(vertices[6],vertices[7],vertices[8]);
//        gl.glEnd();
//        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glViewport(0,0, width, height);
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glFrustum(-1,1,1,-1,1.5,1000);
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }

    private void setupVertex(GL2 gl){

        loadVertices();

        FloatBuffer verticesBuffer = BufferUtil.newFloatBuffer(vertices.length);

        for (float vertex : vertices) {
            verticesBuffer.put(vertex);
        }
        verticesBuffer.rewind();

        IntBuffer indicesBuffer = BufferUtil.newIntBuffer(triangulos.length);

        for(int indice : triangulos){
            indicesBuffer.put(indice);
        }
        indicesBuffer.rewind();

        gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL.GL_FLOAT,0,verticesBuffer);
        this.indices = indicesBuffer;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
    }

}
