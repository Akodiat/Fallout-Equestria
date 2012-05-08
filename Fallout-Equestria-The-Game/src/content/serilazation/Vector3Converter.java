package content.serilazation;

import math.Vector3;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class Vector3Converter extends AbstractSingleValueConverter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class cls) {
		return cls.equals(Vector3.class);
	}

	@Override
	public Object fromString(String str) {
		String[] elements = str.split(",");
		return new Vector3(Float.parseFloat(elements[0]),
						   Float.parseFloat(elements[1]),
						   Float.parseFloat(elements[2]));
	}
}
