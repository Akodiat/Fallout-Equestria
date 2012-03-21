package graphics;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import utils.Rectangle;

import static org.lwjgl.opengl.GL11.*;

public final class Texture2D {

	public final int OpenGLID;	
	public final int Width;
	public final int Height;
	
	protected Texture2D(int openGLID, int width, int height) {
		this.OpenGLID = openGLID;
		this.Width = width;
		this.Height = height;
	}
	
	public Rectangle getBounds() {	
		return new Rectangle(0, 0, Width, Height);
	}

	public void bindGL() {
		glBindTexture(GL_TEXTURE_2D, OpenGLID);
	}
	
	
	private static Texture2D pixel;
	public static Texture2D getPixel() {
		if(pixel == null) {
			createPixel();
		}
		return pixel;
	}

	private static void createPixel() {
		int openGLID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, openGLID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(4);
		buffer.put(new byte[] { (byte)0xFF, 
								(byte)0xFF, 
								(byte)0xFF, 
								(byte)0xFF });
		buffer.flip();
		glTexImage2D(GL_TEXTURE_2D, 0,
					 GL_RGBA, 
					 1,
					 1, 
					 0,
					 GL_RGBA, 
					 GL_UNSIGNED_BYTE, 
					 buffer);
		
		buffer = null;
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		pixel = new Texture2D(openGLID, 1, 1);
	}
}
