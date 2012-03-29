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
		transComp.setPosition((float)Math.random() * 800,(float)Math.random() * 600);
		RenderingComp rendComp = new RenderingComp();
		rendComp.setTexture(ContentManager.loadTexture("house.png"));
		rendComp.setColor(Color.Gold);
		ExistanceComp existanceComp = new ExistanceComp(5f);
		transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		
		IEntityArchetype archetype = new EntityArchetype(ImmutableList.of(existanceComp, transComp,rendComp));
		
		
		generateArchetype("TimeBombCounter.archetype", archetype);
		testLoad("TimeBombCounter.archetype");
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
