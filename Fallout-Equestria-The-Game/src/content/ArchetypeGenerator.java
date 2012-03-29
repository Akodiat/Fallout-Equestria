package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import math.Vector2;

import org.lwjgl.opengl.Display;

import utils.Circle;
import utils.Rectangle;
import utils.Timer;

import ability.Ability;
import ability.CircleProjectileAbility;
import ability.SpawnCreaturesAbility;
import ability.SuperTimeBomb;
import ability.TelekinesisAbility;
import ability.TeleportAbility;
import ability.TimeBombAbility;

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
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("TrixieScaledSheet.png"));

		transComp.setOrigin(rendComp.getTexture().getBounds().getCenter());
		
		HashMap<Class<? extends Ability>, Ability> map = new HashMap<Class<? extends Ability>, Ability>();
		map.put(SpawnCreaturesAbility.class, new SpawnCreaturesAbility(ContentManager.loadArchetype("ppieBullet.archetype"),5,10,5));
		map.put(CircleProjectileAbility.class, new CircleProjectileAbility(ContentManager.loadArchetype("spinProjectile.archetype"), 8, 8, 6, 10));
		map.put(SuperTimeBomb.class, new SuperTimeBomb(ContentManager.loadArchetype("TimeBomb.archetype"), ContentManager.loadArchetype("TimeBombCounter.archetype"), new Rectangle(0,0,2000,1000), 25, 16, 12));
		map.put(TeleportAbility.class, new TeleportAbility(8, 15, "effects/pew.ogg"));
		
		AbilityComp abComp = new AbilityComp(map, null);
		
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero, 20));
		
		PhysicsComp physComp = new PhysicsComp(Vector2.Zero,800,0f,false);
		
		ImmutableList<Frame> frames = Frame.generateFrames(Vector2.Zero, new Vector2(290/4, 120), 2, false);
				  Animation ani = new Animation(frames,new Timer(0.1f, Integer.MAX_VALUE));
				  Map<String,Animation> map2 =new HashMap<String,Animation>();
				  map2.put("default",ani);
				  AnimationComp aniComp = new AnimationComp(map2, "default");
		
		WeaponComp wComp = new WeaponComp(null,null);
		HealthComp healthComp = new HealthComp(200f,3f,200f);
		AbilityPointsComp apComp = new AbilityPointsComp(30, 30, 3);
	
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(transComp,rendComp,abComp, apComp, wComp, aniComp, healthComp, spatComp, physComp), "Trixie");
		
		
		generateArchetype("Trixie.archetype", archetype);
		testLoad("Trixie.archetype");
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
		xstream.processAnnotations(ExistanceComp.class);
		
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
