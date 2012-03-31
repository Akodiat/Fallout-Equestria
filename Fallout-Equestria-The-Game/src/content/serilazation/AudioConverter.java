package content.serilazation;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import content.ContentManager;

public class AudioConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(Audio.class) || clazz.equals(AudioImpl.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Audio audio = (Audio)value;
		String assetName = ContentManager.getAudioName(audio);
		writer.startNode("AudioAsset");
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
		
		Audio audio = ContentManager.loadSound(assetName);
		return audio;
	}

}
