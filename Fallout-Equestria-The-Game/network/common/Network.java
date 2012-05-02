package common;

import math.Vector2;
import utils.Key;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import components.InputComp;

public class Network {
	
	public static void registerClasses(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(InputComp.class);
		kryo.register(Key.class);
		kryo.register(Vector2.class);
	}
}
