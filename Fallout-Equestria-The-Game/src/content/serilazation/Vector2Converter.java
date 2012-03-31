package content.serilazation;

import math.Vector2;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class Vector2Converter extends AbstractSingleValueConverter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class cls) {
		return cls.equals(Vector2.class);
	}

	@Override
	public Object fromString(String str) {
		String[] elements = str.split(",");
		return new Vector2(Float.parseFloat(elements[0]),
				Float.parseFloat(elements[1]));
	}

}
