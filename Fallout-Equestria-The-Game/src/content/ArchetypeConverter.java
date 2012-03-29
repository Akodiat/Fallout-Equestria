package content;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import entityFramework.EntityArchetype;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;

public class ArchetypeConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(EntityArchetype.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		IEntityArchetype archetype = (IEntityArchetype)value;
		String assetName = ContentManager.getArchetypeName(archetype);
		writer.startNode("Archetype");
		writer.setValue(assetName);
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String assetName;
		reader.moveDown();
		assetName = reader.getValue();
		reader.moveUp();
		
		IEntityArchetype archetype = ContentManager.loadArchetype(assetName);
		return archetype;
	}
}