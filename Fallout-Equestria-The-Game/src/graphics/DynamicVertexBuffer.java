package graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public class DynamicVertexBuffer {
	
	public final int BufferOpenGLID;
	private FloatBuffer floatBuffer;
	
	
	public DynamicVertexBuffer(int size) {
		this.BufferOpenGLID = glGenBuffers();
		floatBuffer = BufferUtils.createFloatBuffer(size);
	}

	
	public void setData(float data) {
		this.floatBuffer.put(data);
	}
	
	public void setData(float[] data) {
		this.floatBuffer.put(data);
	}
	
	public void setData(float data, int index) {
		this.floatBuffer.put(index, data);
	}
	
	public void bindGL() {
		glBindBuffer(GL_ARRAY_BUFFER, this.BufferOpenGLID);
	}
	
	public void unbindGL() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void flushGL() {
		if(this.floatBuffer.position() != 0) {
			this.floatBuffer.flip();
			glBufferData(GL_ARRAY_BUFFER, this.floatBuffer, GL_STREAM_DRAW);
			this.floatBuffer.clear();
		}
	}


}
