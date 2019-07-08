import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import com.sun.scenario.effect.impl.BufferUtil;
import models.ColorParams;
import models.Triangle;
import models.Vector3;
import operations.Vector3Operations;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class EventListener implements GLEventListener, KeyListener, MouseListener {

    private Helper helper = new Helper();
    private IntBuffer indices;

    private int lastX = -1;
    private int lastY = -1;

    private float[] l_pos;
    private float[] shininess;
    private float[] l_amb;
    private float[] l_diffuse;
    private float[] l_spec;
    private float[] m_amb;
    private float[] m_diffuse;
    private float[] m_spec;
    private float[] m_emis;

    private float[] C;
    private float[] N;
    private float[] V;
    private float fovy;
    private float near;
    private float far;
    private float[] center;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        setupCameraParams();
        setupLightParams();
        setupVertex(gl);

        gl.glClearColor(0, 0, 0, 1);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, this.shininess, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, this.m_amb, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, this.m_diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, this.m_emis, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, this.m_spec, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, this.l_pos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, this.l_amb, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, this.l_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, this.l_spec, 0);
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
        glu.gluLookAt(this.C[0], this.C[1], this.C[2], this.C[0] + this.N[0], this.C[1] + this.N[1], this.C[2] + this.N[2], this.V[0], this.V[1], this.V[2]);
        gl.glDrawElements(GL.GL_TRIANGLES, helper.triangles.size() * 3, GL.GL_UNSIGNED_INT, this.indices);
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
        GLU glu = new GLUgl2();
        gl2.glViewport(0, 0, width, height);
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(this.fovy, width / height, this.near, this.far);
//        gl2.glFrustum(-1,1,1,-1,1.5,1000);
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
//        gl2.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    }

    private void setupCameraParams() {
        try {
            helper.loadCameraParams();
            this.C = helper.cameraParams.getC();
            this.N = helper.cameraParams.getN();
            this.V = helper.cameraParams.getV();
            this.fovy = helper.cameraParams.getFovy();
            this.far = helper.cameraParams.getFar();
            this.near = helper.cameraParams.getNear();
            this.center = new float[]{this.C[0] + this.N[0], this.C[1] + this.N[1], this.C[2] + this.N[2]};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupVertex(GL2 gl) {

        helper.loadVertices();
        List<Vector3> normalizedTriangles = helper.normalizeTriangles();
        List<Vector3> normais = helper.normalizeEdges(normalizedTriangles);

        FloatBuffer verticesBuffer = BufferUtil.newFloatBuffer(helper.vectors.size() * 3);

        for (Vector3 vector : helper.vectors) {
            verticesBuffer.put((float) vector.getX());
            verticesBuffer.put((float) vector.getY());
            verticesBuffer.put((float) vector.getZ());
        }
        verticesBuffer.rewind();

        FloatBuffer normaisBuffer = BufferUtil.newFloatBuffer(normais.size() * 3);

        for (Vector3 normal : normais) {
//            System.out.println(normal.toString());
            normaisBuffer.put((float) normal.getX());
            normaisBuffer.put((float) normal.getY());
            normaisBuffer.put((float) normal.getZ());
        }
        normaisBuffer.rewind();

        IntBuffer indicesBuffer = BufferUtil.newIntBuffer(helper.triangles.size() * 3);

        for (Triangle triangle : helper.triangles) {
            indicesBuffer.put(triangle.getV1() - 1);
            indicesBuffer.put(triangle.getV2() - 1);
            indicesBuffer.put(triangle.getV3() - 1);
        }
        indicesBuffer.rewind();

        gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, verticesBuffer);
        gl.glNormalPointer(GL.GL_FLOAT, 0, normaisBuffer);
        this.indices = indicesBuffer;
    }

    private void setupLightParams() {
        try {
            helper.loadColorParams();

            this.l_pos = helper.colorParams.getPl();
            this.shininess = helper.colorParams.getShine();
            this.l_amb = helper.colorParams.getL_amb();
            this.l_diffuse = helper.colorParams.getL_diffuse();
            this.l_spec = helper.colorParams.getL_spec();
            this.m_amb = helper.colorParams.getM_amb();
            this.m_diffuse = helper.colorParams.getM_diffuse();
            this.m_spec = helper.colorParams.getM_spec();
            this.m_emis = helper.colorParams.getM_emis();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                zoomCamera(1.5f);
                break;
            case 's':
                zoomCamera(-1.5f);
                break;
            case 'a':
                moveHorizontal(0.8f);
                break;
            case 'd':
                moveHorizontal(-0.8f);
                break;
            case 'r':
                this.setupCameraParams();
                this.setupLightParams();
                break;
        }
    }

    private void zoomCamera(float speed) {
        this.C[0] += speed * this.N[0];
        this.C[1] += speed * this.N[1];
        this.C[2] += speed * this.N[2];
    }

    private void moveHorizontal(float speed) {
        Vector3 n = new Vector3(this.N[0], this.N[1], this.N[2]);
        Vector3 v = new Vector3(this.V[0], this.V[1], this.V[2]);
        float[] U = Vector3Operations.getInstance().getU(n, v).toArray();
        this.C[0] += speed * U[0];
        this.C[1] += speed * U[1];
        this.C[2] += speed * U[2];
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {


        if (e.getX() > lastX) {
            lastX = e.getX();
            this.N[0] += 0.01f;
//            this.N[1] += 0.01f;
//            this.N[2] += 0.01f;
        }
        if (e.getX() < lastX) {
            lastX = e.getX();
            this.N[0] -= 0.01f;
//            this.N[1] -= 0.01f;
//            this.N[2] -= 0.01f;
        }
        if (e.getY() > lastY) {
            lastY = e.getY();
            this.V[0] -= 0.01f;
            this.V[1] -= 0.01f;
            this.V[2] -= 0.01;
            verticalDrag();
        }
        if (e.getY() < lastY) {
            lastY = e.getY();
            this.V[0] += 0.01f;
            this.V[1] += 0.01f;
            this.V[2] += 0.01;
            verticalDrag();
        }

    }

    private void verticalDrag() {
        Vector3 v = new Vector3(this.V[0], this.V[1], this.V[2]);
        Vector3 n = new Vector3(this.N[0], this.N[1], this.N[2]);
        Vector3 o = Vector3Operations.instance.orthogonalize(v, n);
        this.V = o.toArray();
    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {

    }
}
