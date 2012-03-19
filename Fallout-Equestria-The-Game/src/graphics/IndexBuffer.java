package graphics;

import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer {
	
	public final int BufferOpenGLID;
	private final ShortBuffer shortBuffer;
	
	
	public IndexBuffer(int size) {
		this.BufferOpenGLID = glGenBuffers();
		shortBuffer = BufferUtils.createShortBuffer(size);	
	}
	
	public void setData(short data) {
		this.shortBuffer.put(data);
	}
	
	public void setData(short[] data) {
		this.shortBuffer.put(data);
	}
	
	public void setData(short data, int index) {
		this.shortBuffer.put(index, data);
	}
	
	public void bindGL() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.BufferOpenGLID);
	}
	
	public void unbindGL() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void flushGL() {
		this.shortBuffer.flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.shortBuffer, GL_STATIC_DRAW);
		this.shortBuffer.clear();
	}

	public int capacity() {
		return this.shortBuffer.capacity();
	}
}
