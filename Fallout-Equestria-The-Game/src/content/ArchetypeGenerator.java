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

import ability.BulletAbility;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.thoughtworks.xstream.XStream;
import components.*;
import death.PPDeathAction;

import entityFramework.EntityArchetype;
import entityFramework.IEntityArchetype;
import graphics.Animation;
import graphics.Color;
import graphics.Frame;

public class ArchetypeGenerator {
	
	
	public static void main(String[] args) throws Exception {
		Display.create();
		EntityArchetypeLoader.initialize();
		
		PhysicsComp physComp = new PhysicsComp();
		InputComp inpComp = new InputComp();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,30f));

		HealthComp healthComp = new HealthComp();
		AbilityPointsComp apComp = new AbilityPointsComp();
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction(new PPDeathAction());
		
		IEntityArchetype weaponArchetype = ContentManager.loadArchetype("ppieBullet.archetype");
		WeaponComp weaponComp = new WeaponComp(new BulletAbility(weaponArchetype, 1, 0.1f, 10f), null);
		
		ImmutableList<Frame> frames = Frame.generateFrames(new Vector2(0,0), new Vector2(83 + 1.0f/3.0f, 75), 24, true);
		Timer aniTimer = new Timer(1f, Integer.MAX_VALUE);
		Animation animation = new Animation(frames, aniTimer);
		Map<String, Animation> animations = new HashMap<String, Animation>();
		animations.put("walk", animation);
		AnimationComp aniComp = new AnimationComp(animations,"walk");
		aniComp.getActiveAnimation().getTimer().Start();

		RenderingComp rendComp = new RenderingComp();
<<<<<<< HEAD
		rendComp.setTexture(ContentManager.loadTexture("house.png"));
		rendComp.setColor(Color.Gold);
		ExistanceComp existanceComp = new ExistanceComp(5f);
		transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(existanceComp, transComp,rendComp));
=======
		rendComp.setColor(Color.White);
		rendComp.setTexture(ContentManager.loadTexture("pinkiewalkweaponspriteSCALED.png"));
>>>>>>> BossFight and Player archetype
		
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(physComp, 
				                                                          inpComp, 
				                                                          posComp, 
				                                                          spatComp,
				                                                          healthComp,
				                                                          apComp,
				                                                          deathComp,
				                                                          weaponComp,
				                                                          aniComp,
				                                                          rendComp));
		
<<<<<<< HEAD
		generateArchetype("TimeBombCounter.archetype", archetype);
		testLoad("TimeBombCounter.archetype");
=======
		
		generateArchetype("Player.archetype", archetype);
		testLoad("Player.archetype");
>>>>>>> BossFight and Player archetype
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
