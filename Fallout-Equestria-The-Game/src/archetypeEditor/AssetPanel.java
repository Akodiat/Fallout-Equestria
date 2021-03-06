package archetypeEditor;
import entityFramework.IEntityArchetype;
import graphics.Texture2D;
import graphics.TextureFont;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.Audio;

import animation.AnimationPlayer;


import content.ContentManager;

import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@SuppressWarnings("serial")
public class AssetPanel extends JPanel {
	private JTextField textField;
	private String fileEnding;
	private String absolutePath;
	private Field field;
	private Object comp;
	private File resourcesDirectory = new File("resources");
	private ContentManager ContentManager;
	/**
	 * Create the panel.
	 */
	public AssetPanel(Field field, Object comp, ContentManager manager) {
		setLayout(null);
		this.field = field;
		this.comp = comp;
		this.ContentManager = manager;

		JLabel lblAsset = new JLabel(field.getName());
		lblAsset.setBounds(10, 8, 150, 14);
		add(lblAsset);

		if(field.getType() == Texture2D.class){
			fileEnding = "png";
		} else if(field.getType() == Audio.class){
			fileEnding = "ogg";
		}else if(field.getType() == IEntityArchetype.class){
			fileEnding = "archetype";
		}else if(field.getType() == TextureFont.class){
			fileEnding = "xml";
		}else if(field.getType() == AnimationPlayer.class){
			fileEnding = "animset";
		}
		else{
			throw new Error("CLASS NOT SUPPORTED BY ASSETPANEL");
		}

		String textFieldText = this.getTextFieldText();


		textField = new JTextField(textFieldText);
		textField.setBounds(155, 5, 147, 20);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Select");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(getResourcesDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						fileEnding, fileEnding);
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					absolutePath = chooser.getSelectedFile().getAbsolutePath();
					setText(chooser.getSelectedFile().getName());
					setFieldValue(absolutePath);
				}
			}
		}
				);
		btnNewButton.setBounds(10, 36, 89, 23);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Preview");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//TODO TEMPORARY. Opens with OS preferred application or notepad
				String filePath = "resources" + File.separator;

				//OPEN TEXTFILES WITH NOTEPAD
				if(getText().endsWith(".script") || 
						getText().endsWith(".archetype")||
						getText().endsWith(".animset"))
				{
					if (getText().endsWith(".archetype")){
						filePath += "archetypes" + File.separator + getText();
					}
					else if (getText().endsWith(".script")){
						filePath += "scripts" + File.separator + getText();
					}else if(getText().endsWith(".animset")){
						filePath += "animations" + File.separator + "animationsets" + File.separator + getText();
					}else{
						throw new Error("INVALID FILE");
					}
					openFileWithNotepad(filePath);
				}else{//OPEN OTHER TYPES OF FILES WITH USERS PREFERRED APPLICATION OR OPENGLSTUFF
					if(getText().endsWith(".png")){
						//int soundsIndex = absolutePath.lastIndexOf("textures", absolutePath.length());
						//filePath = absolutePath.substring(soundsIndex + "textures".length() + 1);
						openFileWithDefaultApplication(absolutePath);
					}else if (getText().endsWith(".xml")){
						String imageName = getText().replace(".xml",".png");
						filePath += "textures" + File.separator + "fonts" + File.separator + imageName;
						openFileWithDefaultApplication(filePath);
					}else if (getText().endsWith(".ogg")){
						int soundsIndex = absolutePath.lastIndexOf("sounds", absolutePath.length());
						filePath = absolutePath.substring(soundsIndex + "sounds".length() + 1);
						Audio sound = ContentManager.loadSound(filePath);
						AudioPlayerFrame af = new AudioPlayerFrame(sound);
						af.setVisible(true);						
					}
				}

			}

			private void openFileWithNotepad(String filePath) {
				Runtime load = Runtime.getRuntime();
				String program = "\"C:\\WINDOWS\\system32\\notepad.exe\"";;
				File toOpen = new File(filePath);
				try {
					load.exec(program + " " + toOpen.getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void openFileWithDefaultApplication(String filePath) {
				File textureFile = new File(filePath);
				Desktop dt = Desktop.getDesktop();;
				try {
					dt.open(textureFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(109, 36, 89, 23);
		add(btnNewButton_1);



	}

	private String getTextFieldText() {
		try {
			Object obj = field.get(this.comp);
			String str = ContentManager.getContentName(obj);
			return str;
		} catch (Exception e) {
			return null;
		}
	}

	public void setText(String text){
		this.textField.setText(text);
	}

	public String getText(){
		return this.textField.getText();
	}

	public File getResourcesDirectory(){
		return this.resourcesDirectory;
	}

	private void setFieldValue(String fileName){
		Object newFieldValue;
		try {
			Display.create();
			if(field.getType() == Texture2D.class){
				int soundsIndex = absolutePath.lastIndexOf("textures", absolutePath.length());
				String filePath = absolutePath.substring(soundsIndex + "textures".length() + 1);
				newFieldValue = ContentManager.loadTexture(filePath);
			} else if(field.getType() == Audio.class){
				int soundsIndex = absolutePath.lastIndexOf("sounds", absolutePath.length());
				String filePath = absolutePath.substring(soundsIndex + "sounds".length() + 1);
				newFieldValue = ContentManager.loadSound(filePath);
			} else if(field.getType() == IEntityArchetype.class){
				newFieldValue = ContentManager.loadArchetype(getText());
			} else if(field.getType() == TextureFont.class){
				newFieldValue = ContentManager.loadFont(getText());
			} else if (field.getType() == AnimationPlayer.class){
				newFieldValue = ContentManager.loadAnimationSet(getText());
			} else {
				Display.destroy();
				throw new Error("CLASS NOT SUPPORTED BY ASSETPANEL");
			} 
		} catch (LWJGLException e1) {
			e1.printStackTrace();
			Display.destroy();
			throw new Error();
		}
		Display.destroy();

		try {
			this.field.set(comp, newFieldValue);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR WHEN SETTING COMPONENT ASSET FIELD (Illegal argument)");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR WHEN SETTING COMPONENT ASSET FIELD (Illegal access)");
		}
	}
}
