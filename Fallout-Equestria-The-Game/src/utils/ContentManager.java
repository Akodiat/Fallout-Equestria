package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import graphics.Texture2D;
import graphics.TextureLoader;

public final class ContentManager {

	public static Texture2D loadTexture(String name) throws IOException {
		File textureFile = new File("resources" + File.separator + "textures"
				+ File.separator + name);

		return TextureLoader.loadTexture(new FileInputStream(textureFile));
	}

	public static InputStream loadStuff(String name) throws IOException{
		File file = new File("resources" + File.separator + name);
		
		return new FileInputStream(file);
	}
}
