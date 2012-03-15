package graphics;

public class Texture2D {

	private final int openGLID;
	private final int width;
	private final int height;
	
	
	protected Texture2D(int openGLID, int width, int height) {
		this.openGLID = openGLID;
		this.width = width;
		this.height = height;
	}
	
	public int getOpenGLID() {
		return this.openGLID;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}	
}
