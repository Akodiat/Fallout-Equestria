package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import math.Vector2;

import org.lwjgl.opengl.Display;

import utils.Circle;
import utils.Timer;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;
import components.*;

import entityFramework.EntityArchetype;
import entityFramework.IEntityArchetype;
import graphics.Animation;
import graphics.Color;
import graphics.Frame;

public class ArchetypeGenerator {
	
	
	public static void main(String[] args) throws Exception {
		Display.create();
		EntityArchetypeLoader.initialize();
		
		TransformationComp transComp = new TransformationComp();
		PhysicsComp physComp = new PhysicsComp();
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("trixieprojectilesheet.png"));
		rendComp.setColor(Color.White);
		ExistanceComp existanceComp = new ExistanceComp(3f);
		transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 12,
										rendComp.getTexture().Height / 2));	
		AttackComp attackComp = new AttackComp(null, new Circle(Vector2.Zero, 30f), 60, false);
		
		ImmutableList<Frame> frames = Frame.generateFrames(Vector2.Zero, new Vector2(rendComp.getTexture().Width / 6,
										rendComp.getTexture().Height), 6, false);
		Animation ani = new Animation(frames,new Timer(0.1f, Integer.MAX_VALUE));
		Map<String,Animation> map =new HashMap<String,Animation>();
		map.put("default",ani);
		AnimationComp aniComp = new AnimationComp(map, "default");
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(physComp,existanceComp, attackComp, transComp,rendComp,aniComp));

		
		generateArchetype("spinProjectile.archetype", archetype);
		testLoad("spinProjectile.archetype");
		Display.destroy();
	}
	
	private static void generateArchetype(String path, IEntityArchetype archetype) {
	
		File file = new File("resources" + File.separator+ "archetypes" + File.separator
		           + path);
		
		XStream xstream = new XStream();
		xstream.registerConverter(new TextureConverter());
		xstream.registerConverter(new Vector2Converter());
		xstream.registerConverter(new ColorConverter());
		xstream.registerConverter(new AudioConverter());
		xstream.alias("Set", ImmutableSet.class);

		xstream.processAnnotations(AbilityPointsComp.class);
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
