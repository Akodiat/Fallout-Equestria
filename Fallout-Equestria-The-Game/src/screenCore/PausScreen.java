package screenCore;

import misc.EventArgs;
import misc.IEventListener;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import GUI.controls.Button;
import content.ContentManager;
import utils.GameTime;
import utils.Rectangle;
import utils.TimeSpan;

public class PausScreen extends GUIScreen {

	public PausScreen(String lookAndFeelPath) {
		super(true, TimeSpan.Zero, TimeSpan.Zero, lookAndFeelPath);
	}
	
	@Override
	public void initialize(ContentManager manager) {
		super.initialize(manager);
		
		Rectangle vp = this.ScreenManager.getViewport();
		
		int x = vp.Width / 2 - 100;
		int y = vp.Height  / 2;
		
		Button button0 = new Button();
		button0.setBounds(x, y - 75,200,50);
		button0.setText("Resume");
		this.controlPanel.addChild(button0);
		
		Button button1 = new Button();
		button1.setBounds(x,y + 25,200,50);
		button1.setText("Exit To Menu");
		this.controlPanel.addChild(button1);
		
		button1.addClicked(new IEventListener<EventArgs>() {
			public void onEvent(Object sender, EventArgs e) {
				backToMenu();	
			}
		});
	}
	
	protected void backToMenu() {
		this.exitScreen();
	}

	@Override
	public void render(GameTime time, SpriteBatch batch) {
		batch.begin();
		batch.draw(Texture2D.getPixel(), this.ScreenManager.getViewport(), new Color(Color.Green, 0.3f), null);
		batch.end();
		
		super.render(time, batch);
	}
}
