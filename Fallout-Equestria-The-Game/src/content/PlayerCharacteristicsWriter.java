package content;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import common.PlayerCharacteristics;
import content.serilazation.ColorConverter;

public class PlayerCharacteristicsWriter {

	private final String characterPath;
	private ContentManager contentManager;
	private XStream xstream = new XStream();

	public PlayerCharacteristicsWriter(ContentManager contentManager, String folderPath) {
		this(contentManager, folderPath, "characters");
	}


	public PlayerCharacteristicsWriter(ContentManager contentManager, String folderPath, String characterSubFolderPath) {
		this.characterPath = characterSubFolderPath + "/";
		this.contentManager = contentManager;

		this.xstream.registerConverter(new ColorConverter());
	}


	public Class<PlayerCharacteristics> getClassAbleToWrite() {
		return PlayerCharacteristics.class;
	}

	public void savePlayerCharacteristics(PlayerCharacteristics pChar) {
		File file = new File(this.contentManager.getResourceFolderPath() + File.separator + this.characterPath + File.separator +
				"Player.pchar");
		

		String s = 	xstream.toXML(pChar);
		System.out.println(s);
		try {
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(s.getBytes());
			ostream.close();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
