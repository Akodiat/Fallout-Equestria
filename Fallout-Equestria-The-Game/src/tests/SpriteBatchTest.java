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

import content.ContentManager;

import math.Matrix4;
import math.Vector2;

import utils.Camera2D;
import utils.Rectangle;

public class SpriteBatchTest {

	
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
			
			graphics.begin(effect, Matrix4.Identity);
			
			for (int i = 0; i < 1; i++) {			
				graphics.draw(texture, Vector2.Zero, Color.White, 
						new Vector2(texture.getBounds().Width / 2, texture.getBounds().Height / 2));
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