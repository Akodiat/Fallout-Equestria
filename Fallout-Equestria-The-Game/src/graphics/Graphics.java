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

	private final FloatBuffer buffer = BufferUtils.createFloatBuffer(8);
	private int vertexBufferObject;
	private int vertexArrayObject;
	private int shaderProgram;
	private Matrix4 projectionMatrix;
	
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
		
		glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, this.buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 32);
		
		glBindVertexArray(0);
		
		
		
		
		
		
		ArrayList<Integer> shaders = new ArrayList<Integer>();
		shaders.add(ShaderLoader.loadShader(GL_VERTEX_SHADER, "MVP.vert"));
		shaders.add(ShaderLoader.loadShader(GL_FRAGMENT_SHADER, "TextureShader.frag"));
	
		this.shaderProgram = ShaderLoader.createProgram(shaders);
		this.projectionMatrix = Matrix4.createOrthogonalProjection(screen.getLeft(), screen.getRight(), 
												screen.getBottom(), screen.getTop(), 1, -1);
		
	}
	
	
	public void clearScreen(Color color) {
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()); 
		glClear(GL_COLOR_BUFFER_BIT);	 
	}
	
	public void draw(Texture2D texture, Vector2 position, Color color) {
		glBindVertexArray(this.vertexArrayObject);
		
		
		Matrix4 scaleMatrix = Matrix4.createScale(1f);
		Matrix4 translationMatrix = Matrix4.createtranslation(position);
		
		Matrix4 tmp = Matrix4.mul(projectionMatrix, scaleMatrix);
		tmp = Matrix4.mul(tmp, translationMatrix);
		
		
		glBindVertexArray(0);
	}
	
	public void draw(Texture2D texture, Vector2 position, Rectangle srcRect ,
					 Color color, boolean mirror, float rotation, float scale ) {
		
	}
	
	public void draw(Texture2D texture, Vector2 position, Rectangle srcRect ,
			 Color color, boolean mirror, float rotation, Vector2 scale ) {
		
	}
	
	private void InternalRender() {
		
	}
 }
