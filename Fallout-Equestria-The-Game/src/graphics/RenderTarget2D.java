package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS;

import java.nio.ByteBuffer;

import utils.Rectangle;

public class RenderTarget2D {
	
	private final Texture2D texture;
	private int frameBufferObjectOpenGLID;
	private boolean isDestroyed;
	
	public Texture2D getTexture() {
		return texture;
	}
		
	public RenderTarget2D(int width, int height) {
		this.texture = createTexture(width, height);
		

		this.frameBufferObjectOpenGLID = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, this.frameBufferObjectOpenGLID);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.OpenGLID, 0);
		glRenderbufferStorageMultisample(GL_RENDERBUFFER, 8, GL_RGBA, width, height);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		
		this.validateBuffer();
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);		
	}

	private Texture2D createTexture(int width, int height) {
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 
				 0, 
				 GL_RGBA, 
				 width, 
				 height, 
				 0, 
				 GL_RGBA, GL_UNSIGNED_BYTE, 
				 (ByteBuffer) null);
			
		return new Texture2D(textureID, width, height);
		
	}
	
	public void bindGl() {
		glBindFramebuffer(GL_FRAMEBUFFER, this.frameBufferObjectOpenGLID);
	}
	
	public void unbindGL() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void destroy() {
		if(!this.isDestroyed) {
			glDeleteFramebuffers(frameBufferObjectOpenGLID);
			texture.destroyTexture();
			this.isDestroyed = true;
		}
	}
	
	protected void finalize() {
		if(!this.isDestroyed) {
			this.destroy();
		}
	}
	
	public void blitToTarget(RenderTarget2D target, Rectangle area) {
		glBindFramebuffer(GL_READ_FRAMEBUFFER, this.frameBufferObjectOpenGLID);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, target.frameBufferObjectOpenGLID);
		
		glBlitFramebuffer(0, 0, this.getTexture().Width, this.getTexture().Height, 
				area.X, area.Y, area.getRight(), area.getBottom(), GL_COLOR_BUFFER_BIT, GL_LINEAR);
	}
	
	private void validateBuffer() {
		int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		if(status != GL_FRAMEBUFFER_COMPLETE) {
			System.out.println("huh...");
			if(status == GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT) {
				System.out.println("inc Attatchment!");
			} if(status == GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER) {
				System.out.println("Inc Drawbuffer");
			} if(status == GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS) {
				System.out.println("Inc layer targets");
			} if(status == GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT) {
				System.out.println("Inc missting att");
			} if(status == GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE) {
				System.out.println("Inc mulsample");
			} if(status == GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER) {
				System.out.println("Inc read buffer!");
			}
		}
	}
}
