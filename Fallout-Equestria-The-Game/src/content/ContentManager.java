package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import graphics.ShaderEffect;
import graphics.ShaderLoader;
import graphics.Texture2D;
import graphics.TextureLoader;

public final class ContentManager {

	
	private final static Map<String, Texture2D> textures = new HashMap<>();
	private final static Map<String, ShaderEffect> shaderEffects = new HashMap<>();
	
	public static Texture2D loadTexture(String name) {
		
		Texture2D texture = textures.get(name);
		if(texture != null) {
			return texture;
		} else {
			
		}
		
		File textureFile = new File("resources" + File.separator + "textures"
				+ File.separator + name);

		try {
			texture = TextureLoader.loadTexture(new FileInputStream(textureFile));
			textures.put(name, texture);
			
			return texture;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Texture from file " + name);
		}
	}

	public static ShaderEffect loadShaderEffect(String vertexShader, String fragmentShader) {
		
		String combinedEffectName = vertexShader + fragmentShader;
		ShaderEffect effect = shaderEffects.get(combinedEffectName);
		
		File vertexFile = new File("resources" + File.separator + "shaders"
							+ File.separator + vertexShader);
		File fragFile = new File("resources" + File.separator + "shaders"
				+ File.separator + fragmentShader);
		
		try {
			effect = ShaderLoader.loadShader(new FileInputStream(vertexFile), new FileInputStream(fragFile));
			shaderEffects.put(combinedEffectName, effect);
			return effect;
			
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
