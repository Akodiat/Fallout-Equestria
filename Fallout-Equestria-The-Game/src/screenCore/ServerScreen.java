package screenCore;

import java.util.List;

import com.esotericsoftware.kryonet.Client;

import content.ContentManager;
import math.Vector2;
import GUI.controls.Button;
import utils.Rectangle;
import utils.time.TimeSpan;

public class ServerScreen extends TransitioningGUIScreen{
	private List<Client> clients;
	private String serverName;
	
	public ServerScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		Rectangle vp = this.ScreenManager.getViewport();
		int x = vp.Width - 250;
		
		Button playButton = new Button();
		playButton.setBounds(x, 678, 200, 50);
		playButton.setText("Start the game!");
		this.addGuiControl(playButton, new Vector2(vp.Width + 200, 678), new Vector2(x, 678),new Vector2(-200, 678));
		
		
	}
}
