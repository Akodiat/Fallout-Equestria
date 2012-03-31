package content.serilazation;

import org.newdawn.slick.openal.Audio;
import scripting.ILineScript;

import graphics.ShaderEffect;
import graphics.Texture2D;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import content.ContentManager;

public class ContentConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return (Texture2D.class.isAssignableFrom(clazz)) 		||
			   (Audio.class.isAssignableFrom(clazz))     		||
			   (ShaderEffect.class.isAssignableFrom(clazz))		||
			   (ILineScript.class.isAssignableFrom(clazz));
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext contex) {
		
		String assetName = ContentManager.getContentName(value);
		writer.startNode("Asset");
		writer.setValue(assetName);
		writer.endNode();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		String assetName;
		
		reader.moveDown();
		assetName = reader.getValue();
		reader.moveUp();
		
		return ContentManager.load(assetName, context.getRequiredType());
	}

}
