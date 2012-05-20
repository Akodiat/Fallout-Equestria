package screenCore;

import java.util.ArrayList;
import java.util.List;

import GUI.controls.GUIControl;
import math.Point2;
import math.Vector2;
import utils.time.GameTime;
import utils.time.TimeSpan;

public abstract class TransitioningGUIScreen extends GUIScreen {
	List<TranslationHelper> translations;
	
	private class TranslationHelper {
		public Vector2 transitionStartPos;
		public Vector2 transtionGoalPos;
		public Vector2 transitionEndPos;
		public GUIControl control;
	}

	public TransitioningGUIScreen(boolean popup, TimeSpan transOnTime,
			TimeSpan transOffTime, String lookAndFeelPath) {
		super(popup, transOnTime, transOffTime, lookAndFeelPath);

		this.translations = new ArrayList<>();
	}
	
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		
		for (TranslationHelper helper : this.translations) {	
			
			Vector2 actualPos = Vector2.lerp(helper.transtionGoalPos, helper.transitionStartPos, this.getTransitionPosition());
			if(this.getScreenState() == ScreenState.TransitionOff) {
				actualPos = Vector2.lerp(helper.transtionGoalPos, helper.transitionEndPos, this.getTransitionPosition());
			}
			
			Point2 point = new Point2((int)actualPos.X, (int)actualPos.Y);
			helper.control.setPosition(point);
		}
	}
	
	protected final void addGuiControl(GUIControl control, Vector2 startPos, Vector2 goalPos, Vector2 endPos) {
		TranslationHelper helper = new TranslationHelper();
		helper.control = control;
		helper.transitionStartPos = startPos;
		helper.transtionGoalPos = goalPos;
		helper.transitionEndPos = endPos;
		this.translations.add(helper);
		this.controlPanel.addChild(control);
	}
}
