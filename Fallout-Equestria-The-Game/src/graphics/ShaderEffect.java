package graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import math.Matrix3;
import math.Matrix4;
import math.Vector2;
import math.Vector3;
import math.Vector4;

import static org.lwjgl.opengl.GL20.*;

public class ShaderEffect {
	
	public final int ShaderProgramOpenGLID;
	private static FloatBuffer matrix3Buffer = BufferUtils.createFloatBuffer(9);
	private static FloatBuffer matrix4Buffer = BufferUtils.createFloatBuffer(16);
	
	protected ShaderEffect(int shaderProgram) {
		this.ShaderProgramOpenGLID = shaderProgram;		
	}
	
	public void bindShaderProgram() {
		glUseProgram(this.ShaderProgramOpenGLID);
	}
	
	public void unbindShaderProgram() {
		glUseProgram(0);
	}
	
	public int getUniformLocation(String uniform) {
		int uniformLoc = glGetUniformLocation(this.ShaderProgramOpenGLID, uniform);
		if(uniformLoc == -1) {
			throw new ShaderException("The uniform " + uniform + "does not exists!");
		}
		return uniformLoc;
	}
	
	public void setUniform(String uniform, float value) {
		glUniform1f(this.getUniformLocation(uniform), value);
	}
	
	public void setUniform(String uniform, float value0, 
										   float value1) {
		glUniform2f(this.getUniformLocation(uniform), 
											value0, value1);
	}
	
	public void setUniform(String uniform, Vector2 value) {
		this.setUniform(uniform, value.X, value.Y);
	}
	
	public void setUniform(String uniform, float value0, 
							 float value1, float value2) {
		glUniform3f(this.getUniformLocation(uniform), 
				value0, value1, value2);
	}
	
	public void setUniform(String uniform, Vector3 value) {
		this.setUniform(uniform, value.X, value.Y, value.Z);
	}
	
	public void setUniform(String uniform, float value0, float value1, 
										   float value2, float value3) {
		glUniform4f(this.getUniformLocation(uniform), 
				value0, value1, value2, value3);
	}
	
	public void setUniform(String uniform, Vector4 value) {
		this.setUniform(uniform, value.X, value.Y, value.Z, value.W);
	}
	
	public void setUniform(String uniform, Matrix3 value) {
		matrix3Buffer.clear();
		glUniformMatrix3(this.getUniformLocation(uniform), false, 
						value.toFlippedFloatBuffer(matrix3Buffer));
	}
	
	public void setUniform(String uniform, Matrix4 value) {
		matrix4Buffer.clear();
		glUniformMatrix4(this.getUniformLocation(uniform), false, 
						value.toFlippedFloatBuffer(matrix4Buffer));
	}
	
	public void setUniformSampler(String unifrom, int sampler) {
		glUniform1i(this.getUniformLocation(unifrom), sampler);
	}

	public static ShaderEffect fromFile(String vertexShader, String fragmentShader) {
		return ShaderLoader.loadShader(vertexShader, fragmentShader);
	}
	
}
