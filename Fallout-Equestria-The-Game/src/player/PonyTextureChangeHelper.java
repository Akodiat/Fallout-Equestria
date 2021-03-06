package player;

import animation.AnimationPlayer;
import animation.Bones;
import animation.TextureDictionary;
import components.AnimationComp;

public class PonyTextureChangeHelper {
	public static void setManeStyle(ManeStyle style, AnimationComp comp, TextureDictionary dict){
		AnimationPlayer player = comp.getAnimationPlayer();
		setManeStyle(style, player, dict);
	}
	
	public static void setManeStyle(ManeStyle style, AnimationPlayer player, TextureDictionary dict){
		player.setBoneTexture(Bones.UPPERMANE.getValue(), dict.extractTextureEntry(style.upperManeStyle));
		player.setBoneTexture(Bones.LOWERMANE.getValue(), dict.extractTextureEntry(style.lowerManeStyle));
		player.setBoneTexture(Bones.UPPERTAIL.getValue(), dict.extractTextureEntry(style.upperTailStyle));
		player.setBoneTexture(Bones.LOWERTAIL.getValue(), dict.extractTextureEntry(style.lowerTailStyle));
	}
	
	public static void setEyeStyle(String eyeTexture, AnimationComp comp, TextureDictionary dict){
		AnimationPlayer player = comp.getAnimationPlayer();
		setEyeStyle(eyeTexture, player, dict);
	}
	
	public static void setEyeStyle(String eyeTexture, AnimationPlayer player, TextureDictionary dict){
		player.setBoneTexture(Bones.EYE.getValue(), dict.extractTextureEntry(eyeTexture));
	}

	public static void setMarkStyle(String markTexture, AnimationComp comp, TextureDictionary dict) {
		AnimationPlayer player = comp.getAnimationPlayer();
		setMarkStyle(markTexture, player, dict);
	}
	
	public static void setMarkStyle(String markTexture, AnimationPlayer player, TextureDictionary dict) {
		player.setBoneTexture(Bones.MARK.getValue(), dict.extractTextureEntry(markTexture));
	}
}
