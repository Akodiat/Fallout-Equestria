package graphics;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;


public final class TextureLoader {
	
	public static Texture2D loadTexture(InputStream stream) throws IOException{
		
		PNGDecoder decoder = new PNGDecoder(stream);
		
		//TODO: remove this temporary fix. 
		ByteBuffer buffer =
				ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		
		decoder.decodeFlipped(buffer, 4 * decoder.getWidth(), Format.RGBA);
		buffer.flip();
		
		int openGLID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, openGLID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glTexImage2D(GL_TEXTURE_2D, 0,
					 GL_RGBA, 
					 decoder.getWidth(),
					 decoder.getHeight(), 
					 0,
					 GL_RGBA, 
					 GL_UNSIGNED_BYTE, 
					 buffer);
		
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		buffer.clear();
		buffer = null;
		
		return new Texture2D(openGLID, decoder.getWidth(), decoder.getHeight());
	}
}
