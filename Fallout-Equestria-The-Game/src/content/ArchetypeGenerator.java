package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import math.Vector2;

import org.lwjgl.opengl.Display;

import utils.Circle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;
import components.*;

import entityFramework.EntityArchetype;
import entityFramework.IEntityArchetype;
import graphics.Color;

public class ArchetypeGenerator {
	
	
	public static void main(String[] args) throws Exception {
		Display.create();
		EntityArchetypeLoader.initialize();
		
		TransformationComp transComp = new TransformationComp();
		transComp.setPosition((float)Math.random() * 800,(float)Math.random() * 600);
		SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,50f));
		HealthComponent healthComp = new HealthComponent(50,1,50);
		DeathComp deathComp = new DeathComp();
		RenderingComponent rendComp = new RenderingComponent();
		rendComp.setTexture(ContentManager.loadTexture("HEJHEJ.png"));
		rendComp.setColor(Color.Red);
		PhysicsComponent physComp = new PhysicsComponent();
		transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		BasicAIComp AIComp = new BasicAIComp();
	
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(transComp, spatComp, healthComp, deathComp, rendComp,physComp,AIComp)
				, ImmutableList.of("Enemies"));
		
		
		generateArchetype("BasicAI.archetype", archetype);
		testLoad("BasicAI.archetype");
		Display.destroy();
	}
	
	private static void generateArchetype(String path, IEntityArchetype archetype) {
	
		File file = new File("resources" + File.separator+ "archetypes" + File.separator
		           + path);
		
		XStream xstream = new XStream();
		xstream.registerConverter(new TextureConverter());
		xstream.registerConverter(new Vector2Converter());
		xstream.registerConverter(new ColorConverter());
		xstream.alias("Set", ImmutableSet.class);
		xstream.autodetectAnnotations(true);
		
		
		try {
			
			xstream.toXML(archetype, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void testLoad(String path) throws Exception {
		@SuppressWarnings("unused")
		IEntityArchetype archetype = ContentManager.loadArchetype(path);
		//System.out.println(archetype);
	}
}
