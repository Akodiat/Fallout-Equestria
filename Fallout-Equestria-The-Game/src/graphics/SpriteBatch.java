package graphics;
import java.util.Arrays;
import java.util.Comparator;

import utils.Rectangle;

import math.Matrix4;
import math.Vector2;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/** A class used to do batched 2D rendering.
 * The design is heavily influenced by the class
 * with the same name SpriteBatch in the XNA framework. 
 * However this used OpenGL 3 instead of DirectX 9
 * 
 * @author Lukas Kurtyan
 *
 */
public class SpriteBatch {
	
	//VertexBuffers
	private final DynamicVertexBuffer vertexBuffer;
	
	//IndexBuffer
	private final IndexBuffer indexBuffer;
	
	private boolean renderingToRenderTarget = false;
	
	//The x and y offsets of the different corners.
	private static final float[] xOffsets  = {
		0f,
		1f,
		1f,
		0f,
	};	
	private static final float[] yOffsets  = {
		0f,
		0f,
		1f,
		1f
	};	
	
	//The current number of sprites in the batch.
	private int spriteCount;
	
	//The max number of sprites in the batch.
	private final int maxSpriteCount = 1024;
	
	//The OpenGL VOA object used for rendering.
	private int vertexArrayObject;
	
	//The viewport we are rendering to.
	private Rectangle viewport;
	private Rectangle oldViewPort;
	
	
	//SpriteEffects
	private static ShaderEffect basicEffect;
	private ShaderEffect activeEffect;
	
	private Sprite[] sprites;
	
	private SortMode sortMode;
	private SpriteBatch.TextureSorter textureSorter;
	
	private class Sprite {
		public Texture2D texture;
		public float[] color;
		public float[] verticies;
		public float[] uvCoords;
	}
	
	public enum SortMode 
	{
		/**No sorting is done.
		 * 
		 */
		None, 
		/**Sprites are sorted based on their textures.
		 * 
		 */
		Texture,
	}
	
	private class TextureSorter implements Comparator<Sprite>
	{
		//We basicly just want to see if the textures are the same or not.
		public int compare(Sprite first, Sprite second) {
			return Integer.compare(first.texture.OpenGLID, second.texture.OpenGLID);
		}
	}
	
	//A flag indicating if we are inside of a begin-call sequence.
	private boolean betweenBeginAndEnd;
	
	private Matrix4 view;
	
	/**Creates a new SpriteBatch.
	 * 
	 * @param viewport the dimensions of the screen rendered to.
	 */
	public SpriteBatch(Rectangle viewport) {
		//Create the buffers with the correct length. 
		this.vertexBuffer = new DynamicVertexBuffer(this.maxSpriteCount * 32);
		this.indexBuffer = new IndexBuffer(this.maxSpriteCount * 12);
		this.sprites = new Sprite[this.maxSpriteCount];
		this.textureSorter = new TextureSorter();
		this.initializeSpriteArray();
		
		this.viewport = viewport;
		
		this.initializeBuffers();
		
		//Initialize the basicEffect. 
		this.initializeShaders();
		this.activeEffect = basicEffect;
	}

	private void initializeSpriteArray() {
		for (int i = 0; i < this.sprites.length; i++) {
			Sprite sprite = new Sprite();
			sprite.uvCoords = new float[8];
			sprite.verticies = new float[8];
			sprite.color = new float[4];
			this.sprites[i] = sprite;
		}		
	}

	private void initializeBuffers() {
			
		this.vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(this.vertexArrayObject);
		
		//Bind the position buffer and prepare the VOA for positional input.
		this.vertexBuffer.bindGL();
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 32, 0);
		
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 32, 2 * 4);
		
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 32, 4 * 4);
		
		setupIndexBuffer();
				
		glBindVertexArray(0);
	}
	
	private void setupIndexBuffer() {
		short[] indecies = new short[this.indexBuffer.capacity()];
		for (int i = 0; i < this.maxSpriteCount; i++)
		{
			indecies[i * 6] = (short)(i * 4);
			indecies[i * 6 + 1] = (short)(i * 4 + 1);
			indecies[i * 6 + 2] = (short)(i * 4 + 2);
			indecies[i * 6 + 3] = (short)(i * 4);
			indecies[i * 6 + 4] = (short)(i * 4 + 2);
			indecies[i * 6 + 5] = (short)(i * 4 + 3);
		}
		//Bind the indexbuffer and buffer the data on the graphics-card. 
		this.indexBuffer.bindGL();
		this.indexBuffer.setData(indecies);
		this.indexBuffer.flushGL();
	}

	private void initializeShaders() {
		//Load a shader.
		basicEffect = this.createBasicEffect();
	}

	
	private ShaderEffect createBasicEffect() {
		String vertexShaderSource    = "#version 330 core \n layout(location = 0) in vec2 position; layout(location = 1) in vec2 textureCoord; layout(location = 2) in vec4 color; uniform mat4 projection; uniform mat4 view; uniform vec2 viewport; out vec2 _textureCoord; out vec4 _color; void main() { vec4 realPos = vec4(position.x,position.y, 0.0f, 1.0f); realPos =  view  * realPos; realPos.xy -= viewport.xy / 2;  gl_Position = projection * realPos; _textureCoord.xy = textureCoord.xy; _color = color;}";
		String fragmentShaderSource  ="#version 330 core \n uniform sampler2D colorTexture; in vec2 _textureCoord; in vec4 _color; out vec4 outputColor; void main() {outputColor = texture(colorTexture, _textureCoord); outputColor.rgb *= outputColor.a * _color.a; outputColor *=  _color;}";
		
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		
		int program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		
		return new ShaderEffect(program);
	}

	private void setupUniforms() {
		//Start using the active shader. This has to be done so we can set the uniforms.
		this.activeEffect.bindShaderProgram();
			
		
		//Set the shader uniforms.
		this.activeEffect.setUniform("viewport", viewport.Width, viewport.Height);
		this.activeEffect.setUniformSampler("colorTexture", 0);
		this.activeEffect.setUniform("view", this.view);
			
		Matrix4 projectionMatrix =
				Matrix4.createOrthogonalProjection(viewport.getLeft(),
												   viewport.getRight(), 
												   viewport.getBottom(), viewport.getTop(),
												     1, -1);
		
		glViewport(0, 0, viewport.Width, viewport.Height);
		
		this.activeEffect.setUniform("projection", projectionMatrix);
		//Stop using the active shader.
		this.activeEffect.unbindShaderProgram();
	}

	/** Fixes the spritebatch to render to the new screen dimensions.
	 * 
	 * @param newViewport the screen viewport.
	 */
	public void setViewport(Rectangle newViewport) {
		this.viewport = newViewport;
		setupUniforms();
	}
	
	/**Gets the dimensions of the screen rendered to.
	 * 
	 * @return a rectangle containing screen dimensions.
	 */
	public Rectangle getViewport() {
		return this.viewport;
	}
	
	public Matrix4 getView() {
		return this.view;
	}
	
	public ShaderEffect getActiveEffect() {
		return activeEffect;
	}
	
	
	private void setRenderTargetMode(boolean isRenderTo) {
		this.renderingToRenderTarget = isRenderTo;
	}
	
	
	/**Clear the screen with the specified color.
	 * 
	 * @param color the color to clear the screen with.
	 */
	public void clearScreen(Color color) {
		
		glClearColor(color.R, color.G, color.B, color.A); 
		glClear(GL_COLOR_BUFFER_BIT);	 
	}	
	
	/**Prepares the spritebatch for rendering. 
	 * This overload uses the standard shadereffect and the identity view.
	 * Blending is activated and the sortmode is none.
	 */
	public void begin() {
		this.begin(null);
	}
	
	
	/**Prepares the spritebatch for rendering.
	 * This overload uses the provided effect and the identity view.
	 * The default framebuffer is targeted, blending is activated and the sortmode is none.
	 * @param effect the custom effect used.
	 */
	public void begin(ShaderEffect effect) {
	
		this.begin(effect, Matrix4.Identity);
	}
	
	/**Prepares the spritebatch for rendering.
	 * This overload uses the provided effect and the provided view.
	 * The default framebuffer is targeted, blending is activated and the sortmode is none.
	 * @param effect the custom effect used.
	 * @param view the custom view used.
	 */
	public void begin(ShaderEffect effect, Matrix4 view) {
		this.begin(effect, view, null);
	}
	
	
	/**Prepares the spritebatch for rendering.
	 * This overload uses the provided effect, view and rendertarget.
	 * Blending is activated and the sortmode is none.
	 * @param effect the custom effect used.
	 * @param view the custom view used.
	 * @param target the rendertarget rendering to(null means the default render target).
	 */
	public void begin(ShaderEffect effect, Matrix4 view, RenderTarget2D target) {
		this.begin(effect, view, target, true);
	}
	
	
	
	/**Prepares the spritebatch for rendering.
	 * This overload uses the provided effect,view,rendertarget and blending flag
	 * The sortmode is none.
	 * @param effect the custom effect used.
	 * @param view the custom view used
	 * @param target the rendertarget rendering to(null means the default render target).
	 * @param useBlending a flag indicating wether or not we should use blending.
	 */
	public void begin(ShaderEffect effect, Matrix4 view, RenderTarget2D target, boolean useBlending) {
		this.begin(effect, view, target, useBlending, SortMode.None);
	}
		
	/**Prepares the spritebatch for rendering.
	 * This overload uses the provided effect and the provided view.
	 * @param effect the custom effect used.
	 * @param view the custom view used
	 * @param target the rendertarget rendering to(null means the default render target).
	 * @param useBlending a flag indicating wether or not we should use blending. 
	 * @param sortMode the sort mode to be used.
	 */
	public void begin(ShaderEffect effect, Matrix4 view, RenderTarget2D target, boolean useBlending, SortMode sortMode) {
		if(this.betweenBeginAndEnd) {
			throw new GraphicsException("You are already drawing you cannot begin again!");
		}
		
		
		glEnable(GL_TEXTURE_2D);
		glHint(GL_FRAGMENT_SHADER_DERIVATIVE_HINT, GL_NICEST);
		
		if(useBlending)	{
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		this.betweenBeginAndEnd = true;
		this.view = view;
		this.oldViewPort = this.viewport;
		
		if(target != null) { 
			this.setRenderTargetMode(true);
			target.bindGl();
			this.setViewport(new Rectangle(0,0,target.getTexture().Width, target.getTexture().Height));
		} else {
			this.setRenderTargetMode(false);
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
		}
		
		if(effect == null) {
			this.activeEffect = basicEffect;
		} else {
			this.activeEffect = effect;	
		}
		
		this.setupUniforms();
		
		this.sortMode = sortMode;
	}
	
	
	/** Ends the batch drawing, Draws the batched items to the current renderTarget.
	 */
	public void end() {
		if(!this.betweenBeginAndEnd) {
			throw new GraphicsException("Cannot end before you begin...");
		}

		this.renderBatch();
		this.viewport = oldViewPort;
		glDisable(GL_BLEND);
		
		this.betweenBeginAndEnd = false;
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 */
	public void draw(Texture2D texture, Vector2 position, Color color) {
		this.internalDraw(texture, position, color, null, Vector2.Zero, Vector2.One, 0.0f, false);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 *  
	 * @param texture the texture.
	 * @param destRectangle the bounds of the sprite.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 */
	public void draw(Texture2D texture, Rectangle destRectangle, Color color, Rectangle sorceRectangle) {
		Vector2 pos = new Vector2(destRectangle.X, destRectangle.Y);
		
		Vector2 scale = this.GetRectangleScale(texture, destRectangle, sorceRectangle);
				
		this.internalDraw(texture, pos, color, sorceRectangle, Vector2.Zero, scale, 0.0f, false);
	}
	
	private Vector2 GetRectangleScale(Texture2D texture,
			Rectangle destRectangle, Rectangle sorceRectangle) {
		if(sorceRectangle != null) {
			return new Vector2((float)destRectangle.Width / sorceRectangle.Width, 
					(float)destRectangle.Height / sorceRectangle.Height);
		} else {
			return new Vector2((float)destRectangle.Width / texture.Width,
							   (float)destRectangle.Height / texture.Height);
		}
		
	}

	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param destRectangle the bounds of the sprite.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 * @param origin the origin (rendering offset)
	 * @param rotation the rotation in a counterclockwise system Range 0 to 2Pi
	 * @param mirror a flag indicating if the texture should be horizontally flipped.
	 */
	public void draw(Texture2D texture, Rectangle destRectangle, Color color, Rectangle sorceRectangle, Vector2 origin,
					 float rotation, boolean mirror) {
		Vector2 pos = new Vector2(destRectangle.X, destRectangle.Y);
		Vector2 scale = this.GetRectangleScale(texture, destRectangle, sorceRectangle);
		this.internalDraw(texture, pos, color, sorceRectangle, origin, scale, rotation, mirror);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 */
	public void draw(Texture2D texture, Vector2 position, Color color, Rectangle sorceRectangle) {
		this.internalDraw(texture, position, color, sorceRectangle, Vector2.Zero, Vector2.One, 0.0f, false);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 * @param origin the origin (rendering offset)
	 */
	public void draw(Texture2D texture, Vector2 position,  Color color, Vector2 origin) {
		this.internalDraw(texture, position, color, null, origin, Vector2.One, 0.0f, false);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 * @param origin the origin (rendering offset)
	 */
	public void draw(Texture2D texture, Vector2 position,  Color color, Rectangle sorceRectangle, Vector2 origin) {
		this.internalDraw(texture, position, color, sorceRectangle, origin, Vector2.One, 0.0f, false);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 * @param origin the origin (rendering offset)
	 * @param scale the scale.
	 * @param rotation the rotation in a counterclockwise system Range 0 to 2Pi
	 * @param mirror a flag indicating if the texture should be horizontally flipped.
	 */
	public void draw(Texture2D texture, Vector2 position, Color color, Rectangle sorceRectangle, Vector2 origin,
			 float scale, float rotation, boolean mirror) {
		this.internalDraw(texture, position, color, sorceRectangle, origin, new Vector2(scale, scale), rotation, mirror);
	}
	
	/**Adds a sprite to the batch with the specified arguments.
	 * 
	 * @param texture the texture.
	 * @param position the top-left position.
	 * @param color the tint.
	 * @param sorceRectangle the part of the texture to draw. (null makes the whole texture draw)
	 * @param origin the origin (rendering offset)
	 * @param scale the scale.
	 * @param rotation the rotation in a counterclockwise system Range 0 to Tau
	 * @param mirror a flag indicating if the texture should be horizontally flipped.
	 */
	public void draw(Texture2D texture, Vector2 position, Color color, Rectangle sorceRectangle, Vector2 origin,
					 Vector2 scale, float rotation, boolean mirror) {
		this.internalDraw(texture, position, color, sorceRectangle, origin, scale, rotation, mirror);
	}
	
	
	
	
	public void drawString(TextureFont font , String text, Vector2 position, Color color) {		
		this.internalDrawString(font, text, position, color, Vector2.Zero, Vector2.One, 0.0f, false,0.0f);	
	}
	
	public void drawString(TextureFont font , String text, Vector2 position, Color color, Vector2 origin) {		
		this.internalDrawString(font, text, position, color, origin, Vector2.One, 0.0f, false,0.0f);	
	}
	
	public void drawString(TextureFont font , String text, Vector2 position, Color color, Vector2 origin, Vector2 scale) {		
		this.internalDrawString(font, text, position, color, origin, scale, 0.0f, false,0.0f);	
	}
	
	public void drawString(TextureFont font , String text, Vector2 position, Color color, Vector2 origin, Vector2 scale, float rotation, boolean mirror) {		
		this.internalDrawString(font, text, position, color, origin, scale, rotation, mirror, 0.0f);	
	}
	
	public void drawString(TextureFont font , String text, Vector2 position, Color color, Vector2 origin, Vector2 scale, float rotation, boolean mirror, float depth) {		
		this.internalDrawString(font, text, position, color, origin, scale, rotation, mirror, depth);	
	}
	
	
	private void internalDrawString(TextureFont font, String text,
			Vector2 destination, Color color, Vector2 origin, Vector2 scale, float rotation,
			boolean mirror, float depth) {
		Vector2 textDim = font.meassureString(text);
		textDim = new Vector2(textDim.X * scale.X, textDim.Y * scale.Y);
		float x = 0, y = 0, rx = 0;
		float angleX = (float)Math.cos(rotation), angleY = (float)Math.sin(rotation);
		float dist = 0;
		float distY = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(c == '\n') {
				distY += font.getLineSpacing() * scale.Y;
				dist = 0;
				continue;
			}
			Vector2 rorig = new Vector2(origin.X, origin.Y);
			Rectangle srcRect = font.getCharacterSourceRect(c);
			

			x =  dist * angleX - distY * angleY;
			y =  dist * angleY + distY * angleX + destination.Y;
			
			if(mirror) {
				rx = textDim.X - x + destination.X;
				rorig = new Vector2((origin.X+ srcRect.Width), origin.Y);
			}
			else {
				rx = x + destination.X;
			}
			
			this.internalDraw(font.getTexture(), new Vector2(rx,y),
						      color, srcRect, rorig, scale, rotation, mirror);
			
			dist += (srcRect.Width + font.getCharacterSpacing()) * scale.X;
			
		}
	}

	private void internalDraw(Texture2D texture, Vector2 destination, Color color, Rectangle sorceRectangle,
							  Vector2 origin, Vector2 scale, float rotation, boolean mirror)
	{
			ensureCanRender(texture);
			
			Sprite sprite = this.sprites[this.spriteCount];
			sprite.texture = texture;
			
			float destWidth;
			float destHeight;
			float texX, texY;
			float texWidth, texHeight;
			
			if(sorceRectangle != null) {
				destWidth = sorceRectangle.Width * scale.X;
				destHeight = sorceRectangle.Height * scale.Y;		
				texX = (float)sorceRectangle.X / texture.Width;
				texY = (float)sorceRectangle.Y / texture.Height;
				texWidth = (float)sorceRectangle.Width / texture.Width;
				texHeight = (float)sorceRectangle.Height / texture.Height;		
			} else {
				destWidth = texture.Width * scale.X;
				destHeight = texture.Height * scale.Y;
				texX = 0.0f;
				texY = 0.0f;
				texWidth = 1.0f;
				texHeight = 1.0f;
			}
			
			float angleX = (float)Math.cos(rotation);
			float angleY = (float)Math.sin(rotation);
			float origX = origin.X / destWidth * scale.X;
			float origY = origin.Y / destHeight * scale.Y;
			
			sprite.color = color.toArray();
			
			for (int i = 0; i < 4; i++) {
				float num0 = xOffsets[i];
				float num1 = yOffsets[i];
							
				float num2 = (num0 - origX) * destWidth;
				float num3 = (num1 - origY) * destHeight;
				
				if(mirror) {
					num0 = 1f - num0;
				}
				
				float x = destination.X + num2 * angleX - num3 * angleY;
				float y = destination.Y + num2 * angleY + num3 * angleX;
				
				if(this.renderingToRenderTarget) {
					y = this.viewport.Height - y;
				}
				
				float realTexCoordX = texX + num0 * texWidth;
				float realTexCoordY = texY + num1 * texHeight;
				
				int sX = i * 2, sY = sX + 1;
				sprite.uvCoords[sX] = realTexCoordX;
				sprite.uvCoords[sY] = realTexCoordY;
				sprite.verticies[sX] = x;
				sprite.verticies[sY] = y;
			}
			
			this.spriteCount++;
			if(this.spriteCount == this.maxSpriteCount) {
				this.renderBatch();
			}
	}

	private void ensureCanRender(Texture2D texture) {
		if(texture == null) {
			throw new NullPointerException("Texture cannot be null!");
		} else if(!this.betweenBeginAndEnd) {
			throw new GraphicsException("You cannot draw befoure begin is called!");
		}
	}
	
	
	private void fillBuffer(int start, int end) {
		for (int i = start; i <= end; i++) {
			Sprite sprite = this.sprites[i];
			for (int j = 0; j < 4; j++) {
				int x = j * 2, y = x + 1;
				this.vertexBuffer.setData(sprite.verticies[x]);
				this.vertexBuffer.setData(sprite.verticies[y]);
				this.vertexBuffer.setData(sprite.uvCoords[x]);
				this.vertexBuffer.setData(sprite.uvCoords[y]);
				this.vertexBuffer.setData(sprite.color);	
			}
		}
	}
	
	private void renderBatch() {
		
		if(this.sortMode == SortMode.Texture) {
			Arrays.sort(this.sprites, 0, this.spriteCount, this.textureSorter);
		}
		
		int start = 0; 
		Texture2D texture = null;
		for (int i = 0; i < this.spriteCount; i++) {
			Sprite sprite = this.sprites[i];
			Texture2D texture2 = sprite.texture;
			
			if(texture2 != texture) {
				if(i > start) {
					fillBuffer(start, i);
					render(texture, (i - start));
					start = i;
					texture = sprite.texture;	
				} 
				texture = texture2;
			}
		}	

		if(start < spriteCount) {
			fillBuffer(start, spriteCount - 1);
			render(this.sprites[this.spriteCount - 1].texture, (spriteCount - start));	
		}
		this.spriteCount = 0;
	}

	private void render(Texture2D texture, int count) {
		glBindVertexArray(this.vertexArrayObject);
		
		flushBuffers();
		
		this.activeEffect.bindShaderProgram();
		
		glActiveTexture(GL_TEXTURE0);
		texture.bindGL();
		
		glDrawElements(GL_TRIANGLES, count * 6, GL_UNSIGNED_SHORT, 0);
		
		this.activeEffect.unbindShaderProgram();
		
		
		
		glBindVertexArray(0);
	}


	private void flushBuffers() {
		this.vertexBuffer.bindGL();
		this.vertexBuffer.flushGL();
	}
}
