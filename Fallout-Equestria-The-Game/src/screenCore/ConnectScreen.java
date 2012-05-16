package screenCore;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;

import graphics.Color;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import GUI.controls.Button;
import GUI.controls.Label;
import GUI.controls.ListBox;
import content.ContentManager;
import utils.Rectangle;
import utils.ServerLoader;
import utils.TimeSpan;

public class ConnectScreen extends TransitioningGUIScreen{
	private ServerLoader loader;
	private Client client;
	private ListBox<String> serverListBox;
	private List<InetAddress> addresses;
	private Label infoLabel;

	public ConnectScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		Rectangle vp = this.ScreenManager.getViewport();
		int x = vp.Width - 250;
		
		this.serverListBox = new ListBox<>();
		serverListBox.setBounds(50, 30, 1016, 400);
		serverListBox.setFont(manager.loadFont("Andale Mono20.xml"));
		serverListBox.setBgColor(new Color(0,0,0,255));
		serverListBox.setFgColor(Color.Black);
		this.addGuiControl(serverListBox, new Vector2(50, -400), new Vector2(50, 30), new Vector2(50, 800));
		
		Button connectBtn = new Button();
		connectBtn.setBounds(x, 40, 200, 50);
		connectBtn.setText("Connect to server");
		this.addGuiControl(connectBtn, new Vector2(vp.Width + 200,40), new Vector2(x,40),new Vector2(-200,40));
		
		Button refreshBtn = new Button();
		refreshBtn.setBounds(x,140,200,50);
		refreshBtn.setText("Refresh the list");
		this.addGuiControl(refreshBtn, new Vector2(vp.Width + 200,140), new Vector2(x,140),new Vector2(-200,140));
		
		Button backBtn = new Button();
		backBtn.setBounds(x,340,200,50);
		backBtn.setText("Back");
		this.addGuiControl(backBtn, new Vector2(vp.Width + 200,340), new Vector2(x,340),new Vector2(-200,340));
		
		this.infoLabel = new Label();
		infoLabel.setBounds(100, 450, 400, 30);
		this.addGuiControl(infoLabel, new Vector2(100,1366), new Vector2(100, 450), new Vector2(100,1366));

		connectBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				int selected = serverListBox.getSelecteItemIndex();
				try {
					client.connect(5000, addresses.get(selected), 54555, 54777);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		refreshBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				onTransitionFinished();		
			}
		});
		
		backBtn.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				gotoTest2();
			}
		});
	}
	
	@Override
	public void onTransitionFinished() {
		this.client = new Client();
		this.loader = new ServerLoader(client);
		
		this.addresses = loader.getAddresses();
		
		if(loader.getAddresses().size() > 0) {
			for(int i = 0; i < this.addresses.size(); i++) {
				serverListBox.addItem("      " + loader.getAddresses().get(i).getHostName());
				infoLabel.setFgColor(Color.Green);
				infoLabel.setText(loader.getAddresses().size() + " server(s) online.");
			}
		} else {
			infoLabel.setText("No servers online.");
			infoLabel.setFgColor(Color.Red);
		}
	}
	
	protected void gotoTest2() {
		this.exitScreen();
	}
}