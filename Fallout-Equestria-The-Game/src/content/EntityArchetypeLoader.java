package content;

import java.io.InputStream;

import components.*;

import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;
import entityFramework.IEntityArchetype;

public class EntityArchetypeLoader {
	
	private static final XStream xstream = new XStream();
	private static boolean isInitalized = false;
	public static void initialize() {

		xstream.registerConverter(new TextureConverter());
		xstream.registerConverter(new Vector2Converter());
		xstream.registerConverter(new ColorConverter());
		xstream.registerConverter(new AudioConverter());
		xstream.alias("Set", ImmutableSet.class);
		
		xstream.processAnnotations(AbilityComp.class);
		xstream.processAnnotations(AttackComp.class);
		xstream.processAnnotations(BasicAIComp.class);
		xstream.processAnnotations(DeathComp.class);
		xstream.processAnnotations(HealthComp.class);
		xstream.processAnnotations(InputComp.class);
		xstream.processAnnotations(PhysicsComp.class);
		xstream.processAnnotations(RenderingComp.class);
		xstream.processAnnotations(SpatialComp.class);
		xstream.processAnnotations(TransformationComp.class);
		xstream.processAnnotations(AnimationComp.class);
		xstream.processAnnotations(WeaponComp.class);
		xstream.processAnnotations(StatusComp.class);
	}
	
	
	public IEntityArchetype loadArchetype(InputStream stream) {
		if(!isInitalized) {
			isInitalized = true;
			initialize();
		}
		
		IEntityArchetype archetype = (IEntityArchetype)xstream.fromXML(stream);
		return archetype;
	}

}
