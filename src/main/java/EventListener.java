import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.texture.TextureIO;
import com.sun.scenario.effect.impl.BufferUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventListener implements GLEventListener {

    float[] vertices;
    int[] triangulos;
    IntBuffer indices;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,1);
        setupVertex(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
//        gl.glDrawArrays(GL.GL_TRIANGLES,0,vertices.length);
        gl.glDrawElements(GL.GL_TRIANGLES,3,GL.GL_UNSIGNED_INT,indices);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glViewport(0,0, 1200, 600);
        gl2.glOrtho(0,1200,600,0,0,1);;
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
    }

    private void setupVertex(GL2 gl){

        loadVertices();

//        float[] vertices = new float[]
//                {25f, 25f, 100, 325, 175, 25, 175, 325, 250, 25, 325, 325};
        FloatBuffer verticesBuffer = BufferUtil.newFloatBuffer(vertices.length);

        for (float vertex : vertices) {
            verticesBuffer.put(vertex);
        }
        verticesBuffer.rewind();

        IntBuffer indicesBuffer = BufferUtil.newIntBuffer(triangulos.length);

        for(int indice : triangulos){
            System.out.println(indice);
            indicesBuffer.put(indice);
        }
        indicesBuffer.rewind();


        gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL.GL_FLOAT,0,verticesBuffer);
        this.indices = indicesBuffer;
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

}
