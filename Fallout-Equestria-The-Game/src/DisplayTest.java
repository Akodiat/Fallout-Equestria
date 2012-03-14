import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayTest {
	
	public static void main(String[] args) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(1280,768));
		Display.create();
		while (!Display.isCloseRequested()) {
			
			Display.update();
		}
		
		Display.destroy();
	}
}
