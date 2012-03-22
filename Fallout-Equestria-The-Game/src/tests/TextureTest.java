package tests;

import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.Texture2D;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import math.Vector2;

import utils.Camera2D;
import utils.ContentManager;
import utils.Rectangle;

public class TextureTest {

	
	private static SpriteBatch graphics;
	public static Camera2D camera;
	private static Texture2D texture;
	private static ShaderEffect effect;
	static DisplayMode mode;
	
	public static void main(String[] args) throws IOException, LWJGLException {

		Display.create();
		mode = Display.getDisplayMode();
		
		graphics = new SpriteBatch(new Rectangle(0, 0, mode.getWidth(), mode.getHeight()));
		texture = ContentManager.loadTexture("HEJHEJ.png");
		camera = new Camera2D(new Rectangle(-mode.getWidth(), 0, (int)(mode.getWidth() * 3), (int)(mode.getHeight() * 1.1f)), new Rectangle(0, 0, mode.getWidth(), mode.getHeight()));
		
		camera.setZoom(new Vector2(2f, 1.1f));
		
		effect = ContentManager.loadShaderEffect("Standard.vert", "GrayScale.frag");
		
		while(!Display.isCloseRequested()) {
			graphics.clearScreen(new Color(Color.Teal, 0.0f));
			
			graphics.begin(effect, camera.getTransformation());
			
			for (int i = 0; i < 10000; i++) {
				float x = (float)Math.random() * mode.getWidth();
				float y = (float)Math.random() * mode.getHeight();
				float r = (float)(Math.random());
				float b = (float)(Math.random());			
				
				graphics.draw(texture, new Vector2(x,y), new Color(r,0.5f, b, 1.0f));
			}
			graphics.end();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				camera.move(new Vector2(5, 0));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				camera.move(new Vector2(-5, 0));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				camera.move(new Vector2(0, -5));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				camera.move(new Vector2(0, 5));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				camera.zoomIn(0.001f);
			} 

			
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		
	}
}
