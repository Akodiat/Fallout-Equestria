package content;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;

import common.PlayerCharacteristics;

import content.serilazation.ColorConverter;

public class PlayerCharacteristicsLoader extends ContentLoader<PlayerCharacteristics>{

	private XStream xstream;

	public PlayerCharacteristicsLoader() {
		this("characters");
	}


	public PlayerCharacteristicsLoader(String folderPath) {
		super(folderPath);
		this.xstream = new XStream();
		xstream.registerConverter(new ColorConverter());
	}


	@Override
	public Class<PlayerCharacteristics> getClassAbleToLoad() {
		return PlayerCharacteristics.class;
	}

	@Override
	public PlayerCharacteristics loadContent(InputStream in) throws Exception {

		PlayerCharacteristics pChar = (PlayerCharacteristics)xstream.fromXML(in);
		System.out.println(pChar);
		return pChar;
	}
}
