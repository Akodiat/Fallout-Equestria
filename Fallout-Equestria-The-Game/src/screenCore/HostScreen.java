package screenCore;

import java.io.IOException;

import graphics.Color;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import GUI.controls.Button;
import GUI.controls.Textfield;
import content.ContentManager;
import server.KyroServer;
import utils.Rectangle;
import utils.ServerInfo;
import utils.TimeSpan;

public class HostScreen extends TransitioningGUIScreen{
	private Textfield textField;
	private ServerInfo serverInfo;
	
	public HostScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		this.textField = new Textfield();
		textField.setBounds(1366/2 -200, 240, 400, 40);
		textField.setText("Enter server name...");
		textField.setFont(manager.loadFont("Andale Mono20.xml"));
		textField.setFgColor(Color.Goldenrod);
		textField.setMaxLength(25);
		this.addGuiControl(textField, new Vector2(1366, 240), new Vector2(1366/2 -200, 240), new Vector2(-400, 240));
		
		Button hostBtn = new Button();
		hostBtn.setBounds(1366/2 -200, 300, 400, 40);
		hostBtn.setText("Start server");
		this.addGuiControl(hostBtn, new Vector2(-400, 300), new Vector2(1366/2 -200, 300), new Vector2(1366, 300));
		
		Button backBtn = new Button(); 
		backBtn.setBounds(1366/2 -200, 360, 400, 40);
		backBtn.setText("Cancel");
		this.addGuiControl(backBtn, new Vector2(1366, 360), new Vector2(1366/2 -200, 360), new Vector2(-400, 360));
		
		hostBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				if(textField.getText().length() == 0 || textField.getText().matches("Enter server name...")) {
					
				} else {
					startServer();
					serverInfo = new ServerInfo(textField.getText());
				}
			}
		});
		
		backBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				goBack();
			}
		});
	}
	
	protected void goBack() {
		this.exitScreen();
	}
	
	public void startServer() {
		KyroServer server;
		
		try {
			server = new KyroServer(new Rectangle(0,0,800,600), 60);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
