package graphics;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS;

import java.nio.ByteBuffer;

public class RenderTarget2D {
	
	private final Texture2D texture;
	private int frameBufferObjectOpenGLID;
	
	public Texture2D getTexture() {
		return texture;
	}
		
	public RenderTarget2D(int width, int height) {
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 
				 0, 
				 GL_RGBA8, 
				 width, 
				 height, 
				 0, 
				 GL_RGBA, GL_UNSIGNED_BYTE, 
				 (ByteBuffer) null);
		
		glBindTexture(GL_TEXTURE_2D, 0);
			
		this.texture = new Texture2D(textureID, width, height);
		

		this.frameBufferObjectOpenGLID = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, this.frameBufferObjectOpenGLID);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		
		this.validateBuffer();
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);		
	}
	
	public void bindGl() {
		glBindFramebuffer(GL_FRAMEBUFFER, this.frameBufferObjectOpenGLID);
	}
	
	public void unbindGL() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
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