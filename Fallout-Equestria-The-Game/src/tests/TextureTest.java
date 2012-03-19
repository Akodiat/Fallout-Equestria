package tests;

import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureLoader;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import math.Matrix4;
import math.Vector2;

import utils.Rectangle;

public class TextureTest {

	
	private static SpriteBatch graphics;
	private static Texture2D texture;
	static DisplayMode mode;
	
	public static void main(String[] args) throws IOException, LWJGLException {

		Display.create();
		mode = Display.getDisplayMode();
		
		graphics = new SpriteBatch(new Rectangle(0, 0, mode.getWidth(), mode.getHeight()));
		texture = TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("HEJHEJ.png"));
		
		
		Matrix4 screenOffset = Matrix4.createtranslation(new Vector2(-mode.getWidth() / 2, -mode.getHeight() / 2));
		Matrix4 scaleMatrix = Matrix4.createScale(.5f);
		Matrix4 translationMatrix = Matrix4.createtranslation(new Vector2(mode.getWidth() / 2, mode.getHeight() / 2));
		
		float angle = 0.1f;
		while(!Display.isCloseRequested()) {
			graphics.clearScreen(new Color(Color.Black, 0.0f));
			
			Matrix4 combined = Matrix4.mul(screenOffset, scaleMatrix);
			combined = Matrix4.mul(combined, Matrix4.createRotationZ(angle));
			combined = Matrix4.mul(combined, translationMatrix);
			
			graphics.begin(null, combined);
			angle += 0.01f;
			for (int i = 0; i < 10000; i++) {
				float x = (float)Math.random() * mode.getWidth();
				float y = (float)Math.random() * mode.getHeight();
				float r = (float)(Math.random());
				float b = (float)(Math.random());			
				
				graphics.draw(texture, new Vector2(x,y), new Color(r,0.5f, b, 1.0f));
			}
			graphics.end();
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		
	}
}
