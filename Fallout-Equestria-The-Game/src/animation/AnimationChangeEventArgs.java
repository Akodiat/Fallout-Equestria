package animation;

import utils.EventArgs;

public class AnimationChangeEventArgs extends EventArgs {
	public String newAnimation;

	public AnimationChangeEventArgs() {
		newAnimation = "";
	}
}
