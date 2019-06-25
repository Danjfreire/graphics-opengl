import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

public class Test{


    private static  GLWindow window = null;

    public static void main(String[]args){
        GLProfile.initSingleton();
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);

        window = GLWindow.create(caps);
        window.setSize(800,800);
        window.setResizable(false);

        window = GLWindow.create(caps);
        window.setSize(800,800);
        window.setResizable(false);
        window.addGLEventListener(new EventListener());
        window.setVisible(true);
        FPSAnimator animator = new FPSAnimator(window,60);
        animator.start();
    }
}
