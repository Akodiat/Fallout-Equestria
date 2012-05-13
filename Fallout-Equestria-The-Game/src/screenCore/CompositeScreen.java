package screenCore;

import content.ContentManager;

public abstract class CompositeScreen {
	private GameScreen[] screens;
	
	public CompositeScreen(GameScreen...inputScreens) {
		for(int i = 0; i < inputScreens.length; i++) {
			this.screens[i] = inputScreens[i];
		}
	}
	
	public void initialize(ContentManager manager) {
		for(int i = 0; i < this.screens.length; i++) {
			screens[i].initialize(manager);
		}
	}
}
