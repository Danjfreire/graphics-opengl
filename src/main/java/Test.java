import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Test{


    private static  GLWindow window = null;

    public static void main(String[]args){
        GLProfile.initSingleton();
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);

        GLEventListener listener = new EventListener();

        window = GLWindow.create(caps);
        window.setSize(600,600);
        window.setResizable(true);
        window.addGLEventListener(listener);
        window.addKeyListener((KeyListener) listener);
        window.addMouseListener((MouseListener) listener);
        window.setVisible(true);
        FPSAnimator animator = new FPSAnimator(window,60);
        animator.start();
    }
}
