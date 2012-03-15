package graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Color;

import utils.Rectangle;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;


import math.Matrix4;
import math.Vector2;

public class Graphics {

	private final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
	private int vertexBufferObject;
	private int vertexArrayObject;
	private int shaderProgram;
	private int colorLocation;
	int samplerLocation;
	private Matrix4 projectionMatrix;
	private Vector2 viewPort;
	
	private float[] vertexData  = {
		-0.5f, -.5f,
		-.5f, .5f,
		.5f, .5f,
		.5f, -.5f,
		
		0.0f, 0.0f,
		0.0f, 1.0f,
		1.0f, 1.0f,
		1.0f, 0.0f
	};
	
	
	public Graphics(Rectangle screen) {
		this.vertexBufferObject = glGenBuffers();
		
		this.vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(this.vertexArrayObject);
		
		buffer.put(this.vertexData);
		buffer.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, this.buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 32);
		
		glBindVertexArray(0);
		
		
		this.viewPort = new Vector2(screen.getWidth() / 2.0f, screen.getHeight() / 2.0f);
		
		
		
		ArrayList<Integer> shaders = new ArrayList<Integer>();
		shaders.add(ShaderLoader.loadShader(GL_VERTEX_SHADER, "MVP.vert"));
		shaders.add(ShaderLoader.loadShader(GL_FRAGMENT_SHADER, "TextureShader.frag"));
	
		
		this.shaderProgram = ShaderLoader.createProgram(shaders);
		
		this.samplerLocation = glGetUniformLocation(shaderProgram, "matrix");
		this.colorLocation = glGetUniformLocation(shaderProgram, "color");
		
		this.projectionMatrix = Matrix4.createOrthogonalProjection(screen.getLeft(), screen.getRight(), 
												screen.getBottom(), screen.getTop(), 1, -1);
		
		
	}
	
	
	public void clearScreen(Color color) {
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()); 
		glClear(GL_COLOR_BUFFER_BIT);	 
	}
	
	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public void draw(Texture2D texture, Vector2 position, Color color) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glBindVertexArray(this.vertexArrayObject);
		
		glUseProgram(this.shaderProgram);
		
		Matrix4 scaleMatrix = Matrix4.createScale(100f);
		Matrix4 translationMatrix = Matrix4.createtranslation(Vector2.subtract(position, this.viewPort));
		
		Matrix4 tmp = Matrix4.mul(scaleMatrix, translationMatrix);
		tmp = Matrix4.mul(tmp , this.projectionMatrix);
		
		
		glUniformMatrix4(this.samplerLocation, false, tmp.toFlippedFloatBuffer(this.matrixBuffer));	
		glUniform4f(colorLocation, color.getRed() / 256.0f,
								   color.getGreen() / 256.0f ,
								   color.getBlue() / 256.0f, 
								   color.getAlpha() / 256.0f);
		
		int samplerLoaction = glGetUniformLocation(this.shaderProgram, "textureTest");
		glUniform1i(samplerLoaction, 0);
		
	
		
		
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getOpenGLID());
		
		glDrawArrays(GL_QUADS, 0, 4);
		
		glUseProgram(0);
		glBindVertexArray(0);
	}
	
	public void draw(Texture2D texture, Vector2 position, Rectangle srcRect ,
					 Color color, boolean mirror, float rotation, float scale ) {
		
	}
	
	public void draw(Texture2D texture, Vector2 position, Rectangle srcRect ,
			 Color color, boolean mirror, float rotation, Vector2 scale ) {
		
	}
	
 }
