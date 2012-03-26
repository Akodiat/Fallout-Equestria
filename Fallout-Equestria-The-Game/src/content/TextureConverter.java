package content;

import graphics.Texture2D;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TextureConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(Texture2D.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext contex) {
		Texture2D texture = (Texture2D)value;
		String assetName = ContentManager.getTextureName(texture);
		writer.startNode("TextureAsset");
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
		
		Texture2D texture = ContentManager.loadTexture(assetName);
		return texture;
	}

}
