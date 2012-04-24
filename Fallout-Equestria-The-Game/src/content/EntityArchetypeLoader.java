package content;

import java.io.InputStream;
import java.io.OutputStream;

import scripting.Behaviour;

import components.*;

import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;

import content.serilazation.ColorConverter;
import content.serilazation.ContentConverter;
import content.serilazation.Vector2Converter;
import entityFramework.IEntityArchetype;

public class EntityArchetypeLoader implements IContentLoader<IEntityArchetype>{
	
	private static final XStream xstream = new XStream();
	private static boolean isInitalized = false;
	public static void initialize() {

		xstream.registerConverter(new ContentConverter());
		xstream.registerConverter(new Vector2Converter());
		xstream.registerConverter(new ColorConverter());
		xstream.alias("Set", ImmutableSet.class);
		
		xstream.processAnnotations(AbilityComp.class);
		xstream.processAnnotations(AttackComp.class);
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
		xstream.processAnnotations(BehaviourComp.class);
	}

	@Override
	public Class<IEntityArchetype> getClassAbleToLoad() {
		return IEntityArchetype.class;
	}

	@Override
	public IEntityArchetype loadContent(InputStream in) throws Exception {
		if(!isInitalized) {
			isInitalized = true;
			initialize();
		}
		
		IEntityArchetype archetype = (IEntityArchetype)xstream.fromXML(in);
		return archetype;
	}

	public void save(OutputStream out, IEntityArchetype archetype) {
		xstream.toXML(archetype, out);
	}
	
	@Override
	public String getFolder() {
		return "archetypes";
	}

}
