import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class EventListener implements GLEventListener {

    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("init");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,1);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT );
        gl.glColor3f(1,1,1);
//        gl.glOrtho(0,1,0,1,-1,1);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.5f,-0.5f);
            gl.glVertex2f(0.5f,-0.5f);
            gl.glVertex2f(0.5f,0.5f);
            gl.glVertex2f(-0.5f,0.5f);
        gl.glEnd();
//        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl2 = drawable.getGL().getGL2();
    }
}
