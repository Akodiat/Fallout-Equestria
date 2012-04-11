package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.Audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import entityFramework.IEntityArchetype;

import scripting.ILineScript;

import graphics.ShaderEffect;
import graphics.Texture2D;
import graphics.TextureFont;

/**
 * 
 * @author Gustav Alm Rosenblad and Lukas Kurtyan
 *
 */
public final class ContentManager {
	
	private static final Map<Class<?>, IContentLoader<?>> contentLoaders = new HashMap<>();
	private static final BiMap<String, Object> loadedContent = HashBiMap.create();
	private static String resourceFoulderPath = "resources";
	
	static {
		addContentLoader(new TextureLoader());
		addContentLoader(new ScriptLoader());
		addContentLoader(new ShaderLoader());
		addContentLoader(new SoundLoader());
		addContentLoader(new TextureFontLoader());
		addContentLoader(new EntityArchetypeLoader());
		addContentLoader(new SceneLoader());
		//TODO add more if needed!. 
	}
	
	public static void setResourceFoulderPath(String path) {
		resourceFoulderPath = path;
	}
	
	/**Adds a loader able to load a new type of content.
	 * 
	 * @param contentLoader the contentLoader.
	 */
	public static void addContentLoader(IContentLoader<?> contentLoader) {
		contentLoaders.put(contentLoader.getClassAbleToLoad(), contentLoader);
	}

	/**Gets the name of the content provided.
	 * @param content the content.
	 * @return the name of the content.
	 */
	public static String getContentName(Object content) {
		return loadedContent.inverse().get(content);
	}
	
	/**Loads content from disc.
	 * 
	 * @param path the relative path of the content.
	 * @param contentType the type of the content.
	 * @return a piece of content stored on the disc.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T load(String path, Class<T> contentType) {
		
		//It's not necessary to reload content that is already loaded.
		T content = (T)loadedContent.get(path);
		if(content != null) {
			return content;
		}	
		
		IContentLoader<T> loader = (IContentLoader<T>) contentLoaders.get(contentType);
		
		//If we don't have a way to load the type specified throw an exception!.
		if(loader == null) {
			throw new RuntimeException("No loader able to load the specified type was found.");
		} 
		
		//Try to open a stream.
		InputStream stream = openStream(path, loader.getFoulder());
		
		try {
			content = loader.loadContent(stream);
			//Place it in the loaded content if successful.
			loadedContent.put(path, content);
			return content;
		} catch (Exception e) {
			throw new RuntimeException("Failed to load " + path + "." + "\n" + e.getMessage(), e);
		}
	}

	
	private static InputStream openStream(String relativePath, String foulder) {
		String correctPath = resourceFoulderPath + File.separator + foulder
										 + File.separator + relativePath;	
		File file = new File(correctPath);	
		
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}		
	}

	/**Loads a texture from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, Texture2D.class).
	 * @param path the path of the texture.
	 * @return a Texture2D object.
	 */
	public static Texture2D loadTexture(String path) {
		return load(path, Texture2D.class);
	}
	
	/**Loads a shader effect from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, ShaderEffect.class).
	 * @param path the path of the ShaderEffect.
	 * @return a ShaderEffect object.
	 */
	public static ShaderEffect loadShaderEffect(String path) {
		return load(path, ShaderEffect.class);
	}
	
	/**Loads a script from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, ILineScript.class).
	 * @param path the path of the script.
	 * @return a ILineScript object.
	 */
	public static ILineScript loadScript(String path) {
		return load(path, ILineScript.class);
	}
	
	/**Loads a archetype from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, IEntityArchetype.class).
	 * @param path the path of the script.
	 * @return a ILineScript object.
	 */
	public static IEntityArchetype loadArchetype(String path) {
		return load(path, IEntityArchetype.class);
	}
	
	/**Loads a sound from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, Audio.class).
	 * @param path the path of the sound.
	 * @return a Audio object.
	 */
	public static Audio loadSound(String path) {
		return load(path, Audio.class);
	}
	
	/**Loads a font from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, TextureFont.class).
	 * @param path the path of the font.
	 * @return a TextureFont object.
	 */
	public static TextureFont loadFont(String path) {
		return load(path, TextureFont.class);
	}
	
}
