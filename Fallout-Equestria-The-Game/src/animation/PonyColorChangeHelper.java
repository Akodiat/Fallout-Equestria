package animation;

import graphics.Color;
import components.AnimationComp;

public class PonyColorChangeHelper {
	public static void setBodyColor(Color color, AnimationComp animComp){
		AnimationPlayer player = animComp.getAnimationPlayer();
		player.setBoneColor(Bones.ROOT.getValue(), color);
		player.setBoneColor(Bones.BODY.getValue(), color);
		player.setBoneColor(Bones.EAR.getValue(), color);
		player.setBoneColor(Bones.HEAD.getValue(), color);
		player.setBoneColor(Bones.MOUTH.getValue(), color);
		player.setBoneColor(Bones.EYE.getValue(), color);
		player.setBoneColor(Bones.HORN.getValue(), color);
		player.setBoneColor(Bones.LEFTARM.getValue(), color);
		player.setBoneColor(Bones.LEFTLEG.getValue(), color);
		player.setBoneColor(Bones.NECK.getValue(), color);
		player.setBoneColor(Bones.RIGHTARM.getValue(), color);
		player.setBoneColor(Bones.RIGHTLEG.getValue(), color);
		player.setBoneColor(Bones.WINGS.getValue(), color);
	}

	public static void setManeColor(Color color, AnimationComp animComp){
		AnimationPlayer player = animComp.getAnimationPlayer();
		player.setBoneColor(Bones.LOWERMANE.getValue(), color);
		player.setBoneColor(Bones.LOWERTAIL.getValue(), color);
		player.setBoneColor(Bones.UPPERMANE.getValue(), color);
		player.setBoneColor(Bones.UPPERTAIL.getValue(), color);
	}

	public static void setEyeColor(Color color, AnimationComp animComp){
		AnimationPlayer player = animComp.getAnimationPlayer();
		player.setBoneColor(Bones.IRIS.getValue(), color);
	}
}
