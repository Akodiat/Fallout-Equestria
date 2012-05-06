package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.Audio;

import animation.Animation;
import animation.AnimationPlayer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import components.PhysicsComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.EntityArchetype;
import entityFramework.IComponent;
import entityFramework.IEntityArchetype;

import graphics.ShaderEffect;
import graphics.Texture2D;
import graphics.TextureFont;

/**
 * 
 * @author Gustav Alm Rosenblad and Lukas Kurtyan
 *
 */
public final class ContentManager {
	
	private final Map<Class<?>, IContentLoader<?>> contentLoaders = new HashMap<>();
	private final BiMap<String, Object> loadedContent = HashBiMap.create();
	private String resourceFolderPath;
	
	private void initializeLoaders() {
		addContentLoader(new TextureLoader("textures"));
		addContentLoader(new ShaderLoader("shaders"));
		addContentLoader(new SoundLoader("sounds"));
		addContentLoader(new TextureFontLoader(this, "fonts"));
		addContentLoader(new EntityArchetypeLoader(this,"archetypes"));
		addContentLoader(new SceneLoader(this, "scenes"));
		addContentLoader(new AnimationLoader(this, "animations"));
		addContentLoader(new TextureDictionaryLoader(this,"animations/dictionaries"));
		addContentLoader(new LookAndFeelLoader(new TextureDictionaryLoader(this,"animations/dictionaries", "GUI"), "lookAndFeels"));
		addContentLoader(new AnimationSetLoader(this, "animations/animationsets"));
		
		//TODO add more if needed!. 
	}
	
	public ContentManager(String rootContentFolderPath) {
		this.resourceFolderPath = rootContentFolderPath;
		this.initializeLoaders();
	}
	
	
	public String getResourceFolderPath() {
		return this.resourceFolderPath;
	}
	
	/**Adds a loader able to load a new type of content.
	 * 
	 * @param contentLoader the contentLoader.
	 */
	public void addContentLoader(IContentLoader<?> contentLoader) {
		contentLoaders.put(contentLoader.getClassAbleToLoad(), contentLoader);
	}

	/**Gets the name of the content provided.
	 * @param content the content.
	 * @return the name of the content.
	 */
	public String getContentName(Object content) {
		return loadedContent.inverse().get(content);
	}
	
	/**Loads content from disc.
	 * 
	 * @param path the relative path of the content.
	 * @param contentType the type of the content.
	 * @return a piece of content stored on the disc.
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(String path, Class<T> contentType) {
		
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
		InputStream stream = openStream(path, loader.getFolder());
		
		try {
			content = loader.loadContent(stream);
			//Place it in the loaded content if successful.
			loadedContent.put(path, content);
			return content;
		} catch (Exception e) {
			throw new RuntimeException("Failed to load " + path + "." + "\n" + e.getMessage(), e);
		}
	}

	
	private InputStream openStream(String relativePath, String folder) {
		String correctPath = resourceFolderPath + File.separator + folder
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
	public Texture2D loadTexture(String path) {
		return load(path, Texture2D.class);
	}
	
	/**Loads a shader effect from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, ShaderEffect.class).
	 * @param path the path of the ShaderEffect.
	 * @return a ShaderEffect object.
	 */
	public ShaderEffect loadShaderEffect(String path) {
		return load(path, ShaderEffect.class);
	}
	
	/**Loads a archetype from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, IEntityArchetype.class).
	 * @param path the path of the script.
	 * @return a ILineScript object.
	 */
	public IEntityArchetype loadArchetype(String path) {
		return load(path, IEntityArchetype.class);
	}
	
	/**Loads a archetype from disc. Does the same thing as loadArchetype(String path),
	 * except that the returned archetype only contains a few selected (logical) components
	 * @param path path the path of the script.
	 * @return a ILineScript object.
	 */
	public IEntityArchetype loadArchetypeForServer(String path){
		IEntityArchetype originalArchetype = this.loadArchetype(path);
		
		ArrayList<IComponent> selectedComponents = new ArrayList<>();
		for (IComponent comp : originalArchetype.getComponents()) {
			if(
					comp.getClass() == TransformationComp.class || 
					comp.getClass() == SpatialComp.class || 
					comp.getClass() == PhysicsComp.class){
				selectedComponents.add(comp);
			}
		}
		
		return new EntityArchetype(selectedComponents);
	}
	
	/**Loads a sound from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, Audio.class).
	 * @param path the path of the sound.
	 * @return a Audio object.
	 */
	public Audio loadSound(String path) {
		return load(path, Audio.class);
	}
	
	/**Loads a font from disc.
	 * This is a convenience method it's the same thing as
	 * calling load(path, TextureFont.class).
	 * @param path the path of the font.
	 * @return a TextureFont object.
	 */
	public TextureFont loadFont(String path) {
		return load(path, TextureFont.class);
	}
	
	public AnimationPlayer loadAnimationSet(String path){
		return load(path, AnimationPlayer.class);
	}
}
