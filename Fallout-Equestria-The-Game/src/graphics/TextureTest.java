package graphics;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Color;
import static org.lwjgl.opengl.GL11.*;

import math.Vector2;

import utils.Rectangle;

public class TextureTest {

	
	private static Graphics graphics;
	private static Texture2D texture;
	
	
	public static void main(String[] args) throws IOException, LWJGLException {

		//Display.setFullscreen(true);
		Display.create();
		graphics = new Graphics(new Rectangle(0, 0, 1280, 720));
		texture = TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("HEJHEJ.png"));


		
		
		while(!Display.isCloseRequested()) {
			graphics.clearScreen(new Color(255,255,234, 235));
			for (int i = 0; i < 3000; i++) {
				float x = (float)Math.random() * 800;
				float y = (float)Math.random() * 600;
				
				graphics.draw(texture, new Vector2(x,y),  new Color(i % 255, 255,255 - i % 255,i % 255));
			}

			
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		
	}
	
}
