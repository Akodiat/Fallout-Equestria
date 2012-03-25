package content;

import java.io.InputStream;
import components.*;

import com.thoughtworks.xstream.XStream;
import entityFramework.IEntityArchetype;

public class EntityArchetypeLoader {
	
	private static final XStream xstream = new XStream();
	
	public static void initialize() {
		xstream.processAnnotations(ActionPointsComponent.class);
		xstream.processAnnotations(AttackComponent.class);
		xstream.processAnnotations(BasicAIComp.class);
		xstream.processAnnotations(DeathComp.class);
		xstream.processAnnotations(HealthComponent.class);
		xstream.processAnnotations(InputComponent.class);
		xstream.processAnnotations(PhysicsComponent.class);
		xstream.processAnnotations(RenderingComponent.class);
		xstream.processAnnotations(SoundEffectComponent.class);
		xstream.processAnnotations(SpatialComponent.class);
		xstream.processAnnotations(StatusChangeComponent.class);
		xstream.processAnnotations(TransformationComp.class);
	}
	
	
	public static IEntityArchetype loadArchetype(InputStream stream) {
		IEntityArchetype archetype = (IEntityArchetype)xstream.fromXML(stream);
		return archetype;
	}

}
