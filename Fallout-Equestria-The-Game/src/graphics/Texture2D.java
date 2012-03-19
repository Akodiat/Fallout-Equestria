package graphics;

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
}
