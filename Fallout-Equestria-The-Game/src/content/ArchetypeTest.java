package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;

import utils.Circle;
import math.Vector2;
import components.*;

import entityFramework.EntityArchetype;
import entityFramework.IComponent;
import entityFramework.IEntityArchetype;

public class ArchetypeTest {
	
	private static final String path = "TestArchetype.archetype";
	
	public static void main(String[] args) throws Exception {
		EntityArchetypeLoader.initialize();
		testSave();
		testLoad();
	}
	
	private static void testSave() {
		TransformationComp transComp = new TransformationComp();
		HealthComponent healthComp = new HealthComponent(450, 0f, 0f);
		InputComponent inputComp = new InputComponent();
		PhysicsComponent physicsComp = new PhysicsComponent();
		SpatialComponent spatialComp = new SpatialComponent(new Circle(Vector2.Zero, 30.0f));
		ImmutableSet<IComponent> set = ImmutableSet.of(transComp, healthComp, inputComp, physicsComp, spatialComp);
		IEntityArchetype archetype = new EntityArchetype(set);
		
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		
		try {
			
			xstream.toXML(archetype, new FileOutputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void testLoad() throws Exception {
		InputStream stream = new FileInputStream(new File(path));
		
		IEntityArchetype archetype = EntityArchetypeLoader.loadArchetype(stream);
		System.out.println(archetype);
	}

}
