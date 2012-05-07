package screenCore;

import content.ContentManager;
import utils.TimeSpan;

public class HostScreen extends TransitioningGUIScreen{
	
	public HostScreen(String lookAndFeelPath) {
		super(false, TimeSpan.fromSeconds(2.0f), TimeSpan.fromSeconds(1.0f), lookAndFeelPath);
	}
	
	public void initialize(ContentManager manager) {
		
	}
}
