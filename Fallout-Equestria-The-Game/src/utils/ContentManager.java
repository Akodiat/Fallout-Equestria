package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import graphics.ShaderEffect;
import graphics.ShaderLoader;
import graphics.Texture2D;
import graphics.TextureLoader;

public final class ContentManager {

	public static Texture2D loadTexture(String name) {
		File textureFile = new File("resources" + File.separator + "textures"
				+ File.separator + name);

		try {
			return TextureLoader.loadTexture(new FileInputStream(textureFile));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Texture from file " + name);
		}
	}

	public static ShaderEffect loadShaderEffect(String vertexShader, String fragmentShader) {
		File vertexFile = new File("resources" + File.separator + "shaders"
							+ File.separator + vertexShader);
		File fragFile = new File("resources" + File.separator + "shaders"
				+ File.separator + fragmentShader);
		
		try {
			return ShaderLoader.loadShader(new FileInputStream(vertexFile), new FileInputStream(fragFile));
		} catch (IOException e) {
			throw new RuntimeException("Failed t load Shader from files " + vertexShader + " " + fragmentShader);
		}
		
	}
	
	public static InputStream loadStuff(String name) {
		File file = new File("resources" + File.separator + name);
		
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Did not find the file!" + name);
		}
	}
}
