package builders;


import content.AnimationLoader;
import content.AnimationSetLoader;
import content.ContentManager;
import content.EntityArchetypeLoader;
import content.LookAndFeelLoader;
import content.PlayerCharacteristicsLoader;
import content.SceneLoader;
import content.ShaderLoader;
import content.SoundLoader;
import content.TextureDictionaryLoader;
import content.TextureFontLoader;
import content.TextureLoader;

public class ContentManagerBuilder {
	
	public static ContentManager buildStandardContentManager(String rootPath) {

		ContentManager manager = new ContentManager(rootPath);
		
		manager.addContentLoader(new TextureLoader("textures"));
		manager.addContentLoader(new ShaderLoader("shaders"));
		manager.addContentLoader(new SoundLoader("sounds"));
		manager.addContentLoader(new TextureFontLoader(manager, "fonts"));
		manager.addContentLoader(new EntityArchetypeLoader(manager,"archetypes"));
		manager.addContentLoader(new SceneLoader(manager, "scenes"));
		manager.addContentLoader(new AnimationLoader(manager, "animations"));
		manager.addContentLoader(new TextureDictionaryLoader(manager,"animations/dictionaries"));
		manager.addContentLoader(new LookAndFeelLoader(new TextureDictionaryLoader(manager,"animations/dictionaries", "GUI"), "lookAndFeels"));
		manager.addContentLoader(new AnimationSetLoader(manager, "animations/animationsets"));
		manager.addContentLoader(new PlayerCharacteristicsLoader("characters"));
		
		return manager;
	}
}
