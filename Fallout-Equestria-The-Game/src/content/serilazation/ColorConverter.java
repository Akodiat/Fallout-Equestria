package content.serilazation;

import graphics.Color;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ColorConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class cls) {
		return cls.equals(Color.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Color color = (Color)value;
		
		writer.startNode("Color");
		writer.setValue(color.R + "," + color.G + "," + color.B + "," + color.A);
		writer.endNode();
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String colorValue;
		reader.moveDown();
		colorValue = reader.getValue();
		reader.moveUp();
		
		String[] elements = colorValue.split(",");
		return new Color(Float.parseFloat(elements[0]),
						 Float.parseFloat(elements[1]),
						 Float.parseFloat(elements[2]),
						 Float.parseFloat(elements[3]));
	}

}
