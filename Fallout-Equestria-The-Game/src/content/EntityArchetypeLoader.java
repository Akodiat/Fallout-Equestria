package content;

import java.io.InputStream;
import java.io.OutputStream;

import components.*;

import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;

import content.serilazation.ColorConverter;
import content.serilazation.ContentConverter;
import content.serilazation.Vector2Converter;
import entityFramework.IEntityArchetype;

public class EntityArchetypeLoader extends ContentLoader<IEntityArchetype>{
	private final ContentManager contentManager;		
	private final XStream xstream;
	
	public EntityArchetypeLoader(ContentManager contentManager, String folder) {
		super(folder);
		this.contentManager = contentManager;
		this.xstream = new XStream();
		this.initialize();
	}


	public void initialize() {

		xstream.registerConverter(new ContentConverter(contentManager));
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
		IEntityArchetype archetype = (IEntityArchetype)xstream.fromXML(in);
		return archetype;
	}

	public void save(OutputStream out, IEntityArchetype archetype) {
		xstream.toXML(archetype, out);
	}
}
