package content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;

import components.AbilityComp;
import components.AnimationComp;
import components.BehaviourComp;
import components.HealthComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpatialComp;
import components.StatusComp;
import components.TransformationComp;

import content.serilazation.ColorConverter;
import content.serilazation.Vector2Converter;
import content.serilazation.Vector3Converter;
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
		xstream.registerConverter(new Vector3Converter());
		xstream.registerConverter(new ColorConverter());
		xstream.alias("Set", ImmutableSet.class);
		
		xstream.processAnnotations(AbilityComp.class);
		xstream.processAnnotations(HealthComp.class);
		xstream.processAnnotations(InputComp.class);
		xstream.processAnnotations(PhysicsComp.class);
		xstream.processAnnotations(RenderingComp.class);
		xstream.processAnnotations(SpatialComp.class);
		xstream.processAnnotations(TransformationComp.class);
		xstream.processAnnotations(AnimationComp.class);
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
		String s = 	xstream.toXML(archetype);
		System.out.println(s);
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
