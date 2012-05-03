package common;

import math.Vector2;
import utils.Key;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import components.InputComp;
import entityFramework.IEntityArchetype;
import graphics.Color;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class Network {
	
	public static void registerClasses(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
	
		kryo.register(Key.class);
		kryo.register(Vector2.class);
		kryo.register(IEntityArchetype.class);
		
		kryo.register(InputComp.class);
		kryo.register(components.PhysicsComp.class);
		kryo.register(components.RenderingComp.class);
		kryo.register(components.TransformationComp.class);
		kryo.register(components.AbilityComp.class);
		kryo.register(components.AnimationComp.class);
		kryo.register(components.BehaviourComp.class);
		kryo.register(components.SpecialComp.class);
		kryo.register(components.SpatialComp.class);
		kryo.register(components.DeathComp.class);
		kryo.register(components.ExistanceComp.class);
		
		kryo.register(NewPlayerMessage.class);
		kryo.register(PlayerCharacteristics.class);
		kryo.register(Color.class);
	}
}
