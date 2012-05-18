package animation;

import components.AnimationComp;

public class PonyTextureChangeHelper {
	public static void setManeStyle(ManeStyle style, AnimationComp comp, TextureDictionary dict){
		AnimationPlayer player = comp.getAnimationPlayer();
		setManeStyle(style, player, dict);
	}
	
	public static void setManeStyle(ManeStyle style, AnimationPlayer player, TextureDictionary dict){
		player.setBoneTexture(Bones.UPPERMANE.getValue(), dict.extractTextureEntry(style.UpperManeStyle));
		player.setBoneTexture(Bones.LOWERMANE.getValue(), dict.extractTextureEntry(style.LoweManeStyle));
		player.setBoneTexture(Bones.UPPERTAIL.getValue(), dict.extractTextureEntry(style.UpperTailStyle));
		player.setBoneTexture(Bones.LOWERTAIL.getValue(), dict.extractTextureEntry(style.LowerTailStyle));
	}
	
	public static void setEyeStyle(String eyeTexture, AnimationComp comp, TextureDictionary dict){
		AnimationPlayer player = comp.getAnimationPlayer();
		setEyeStyle(eyeTexture, player, dict);
	}
	
	public static void setEyeStyle(String eyeTexture, AnimationPlayer player, TextureDictionary dict){
		player.setBoneTexture(Bones.EYE.getValue(), dict.extractTextureEntry(eyeTexture));
	}
}
