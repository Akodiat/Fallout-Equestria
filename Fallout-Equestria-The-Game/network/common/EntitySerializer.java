package common;

import java.nio.ByteBuffer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.StringSerializer;

import entityFramework.IEntity;

public class EntitySerializer extends Serializer{
	@SuppressWarnings("unused")
	private Kryo kryo;
	
	public EntitySerializer(Kryo kryo) {
		this.kryo = kryo;
	}
	
	
	@Override
	public <T> T readObjectData(ByteBuffer arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeObjectData(ByteBuffer arg0, Object entityObj) {
		IEntity entity = (IEntity)entityObj;
		arg0.putInt(entity.getUniqueID());
		StringSerializer.put(arg0, entity.getLabel());
	}

}
