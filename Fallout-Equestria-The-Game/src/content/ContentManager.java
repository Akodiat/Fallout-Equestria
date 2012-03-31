package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.JDOMException;
import org.newdawn.slick.openal.Audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import entityFramework.IEntityArchetype;

import scripting.ILineScript;
import scripting.ScriptLoader;

import graphics.ShaderEffect;
import graphics.Texture2D;
import graphics.TextureFont;

/**
 * 
 * @author Gustav Alm Rosenblad and Lukas Kurtyan
 *
 */
public final class ContentManager {

	
	private static final BiMap<String, Texture2D> textures = HashBiMap.create();
	private static final BiMap<String, ShaderEffect> shaderEffects = HashBiMap.create();
	private static final BiMap<String, ILineScript> scripts =  HashBiMap.create();
	private static final BiMap<String, IEntityArchetype> archetypes = HashBiMap.create();
	private static final BiMap<String, Audio> sounds = HashBiMap.create();
	private static final BiMap<String, TextureFont> fonts = HashBiMap.create();
	
	private static final ShaderLoader shaderLoader 			   = new ShaderLoader();
	private static final TextureLoader textureLoader	  	   = new TextureLoader();	
	private static final ScriptLoader scriptLoader 		       = new ScriptLoader();
	private static final EntityArchetypeLoader archetypeLoader = new EntityArchetypeLoader();
	private static final SoundLoader soundLoader 			   = new SoundLoader();
	private static final TextureFontLoader fontLoader 		   = new TextureFontLoader();
	
	
	

	
	
	public static Texture2D loadTexture(String name) {
		
		Texture2D texture = textures.get(name);
		if(texture != null) {
			return texture;
		} else {
			
		}
		
		File textureFile = new File("resources" + File.separator + "textures"
				+ File.separator + name);

		try {
			texture = textureLoader.loadContent(new FileInputStream(textureFile));
			textures.put(name, texture);
			
			return texture;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Texture from file " + name);
		}
	}

	public static String getTextureName(Texture2D texture) {
		return textures.inverse().get(texture);
	}
	
	public static ShaderEffect loadShaderEffect(String shader) {
		ShaderEffect effect = shaderEffects.get(shader);
		if(effect != null) {
			return effect;
		}
		
		File shaderFile = new File("resources" + File.separator + "shaders"
							+ File.separator + shader);
		try {
			effect = shaderLoader.loadContent(new FileInputStream(shaderFile));
			shaderEffects.put(shader, effect);
			return effect;
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to load Shader from file " + shader);
		}
		
	}
	
	public static String getShaderName(ShaderEffect effect) {
		return shaderEffects.inverse().get(effect);
	}
	
	public static ILineScript loadScript(String scriptPath) {
		ILineScript script = scripts.get(scriptPath);
		if(script != null) {
			return script;
		}
		
		File scriptFile = new File("resources" + File.separator + "scripts"
				+ File.separator + scriptPath);	
		try {
			script = scriptLoader.loadScript(new FileInputStream(scriptFile));
			scripts.put(scriptPath, script);
			return script;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load Script: " + scriptPath);
		}
	}
	
	public static String getScriptName(ILineScript script) {
		return scripts.inverse().get(script);
	}
	
	public static IEntityArchetype loadArchetype(String path) {
		File file = new File("resources" + File.separator + "archetypes" + File.separator + path);
		IEntityArchetype archetype = archetypes.get(path);
		if(archetype != null) {
			return archetype;
		}	
		
		try {
			archetype = archetypeLoader.loadArchetype(new FileInputStream(file));
			return archetype;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Failed to load archetype: " + path);
		}
		
	}
	
	public InputStream loadStuff(String name) {
		File file = new File("resources" + File.separator + name);
		
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Did not find the file!" + name);
		}
	}

	public static Audio loadSound(String path) {
		File file = new File("resources" + File.separator + "sounds" + File.separator + path);
		Audio audio = sounds.get(path);
		if(audio != null) {
			return audio;
		}
		
		try {
			audio = soundLoader.loadSound(new FileInputStream(file));
			sounds.put(path, audio);
			return audio;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load sound: " + path);
		}
	}

	public static TextureFont loadFont(String path) {
		File file = new File("resources" + File.separator + "fonts" + File.separator + path);
		TextureFont font = fonts.get(path);
		if(font != null) {
			return font;
		}	
		try {
			font = fontLoader.loadContent(new FileInputStream(file));
			fonts.put(path, font);
			return font;
		} catch (Exception e) {
			throw new RuntimeException("Failed to load font: " + path);
		}
	}
	
	public static String getAudioName(Audio audio) {
		return sounds.inverse().get(audio);
	}

	public static String getArchetypeName(IEntityArchetype archetype) {
		return archetypes.inverse().get(archetype);
	}
}
