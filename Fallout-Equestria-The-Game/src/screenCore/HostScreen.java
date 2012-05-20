package screenCore;
import graphics.Color;
import math.Vector2;
import GUI.Button;
import GUI.Textfield;
import content.ContentManager;
import utils.EventArgs;
import utils.IEventListener;
import utils.time.TimeSpan;

public class HostScreen extends TransitioningGUIScreen{
	private Textfield textField;
	
	public HostScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(1.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		this.textField = new Textfield();
		textField.setBounds(1366/2 -200, 240, 400, 40);
		textField.setText("");
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
				if(textField.getText().length() == 0) {
					
				} else {
					startServer();
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
		this.ScreenManager.getNetwork().HostGame();
		
		this.ScreenManager.removeAllScreens();
		this.ScreenManager.addScreen("Lobby");
		this.ScreenManager.addScreen("LobbyGUI");
	}
}
