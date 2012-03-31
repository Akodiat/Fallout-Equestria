package content.serilazation;

import graphics.ShaderEffect;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import content.ContentManager;

public class ShaderConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class cla) {
		return cla.equals(ShaderEffect.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ShaderEffect effect = (ShaderEffect)value;
		writer.startNode("ShaderAsset");
		writer.setValue(ContentManager.getShaderName(effect));
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String assetName;
		reader.moveDown();
		assetName = reader.getValue();
		reader.moveUp();
		return ContentManager.loadShaderEffect(assetName);
	}

}
