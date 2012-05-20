package animation;

import common.PlayerCharacteristics;
import common.Race;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;

public class AnimationFromCharacterHelper {
	public static final String assetDictionaryPath = "rddict.tdict";
	public static final String animSetPath = "rdset.animset";

	public static AnimationPlayer animationPlayerFromCharacter(PlayerCharacteristics pChar, ContentManager manager){
		AnimationPlayer player = manager.load(animSetPath, AnimationPlayer.class);
		TextureDictionary assetDictionary = manager.load(assetDictionaryPath, TextureDictionary.class);

		PonyColorChangeHelper.setBodyColor(pChar.bodyColor, player);
		PonyColorChangeHelper.setEyeColor(pChar.eyeColor, player);
		PonyColorChangeHelper.setManeColor(pChar.maneColor, player);

		PonyTextureChangeHelper.setMarkStyle(pChar.markTexture, player, assetDictionary);
		PonyTextureChangeHelper.setEyeStyle(pChar.eyeTexture, player, assetDictionary);
		PonyTextureChangeHelper.setManeStyle(pChar.maneStyle, player, assetDictionary);
		
		if(pChar.race == Race.EARTHPONY.getValue()){
			player.setBoneHidden(Bones.WINGS.getValue(), true);
			player.setBoneHidden(Bones.HORN.getValue(), true);
		}else if(pChar.race == Race.PEGASUS.getValue()){
			player.setBoneHidden(Bones.WINGS.getValue(), false);
			player.setBoneHidden(Bones.HORN.getValue(), true);
		}else if(pChar.race == Race.UNICORN.getValue()){
			player.setBoneHidden(Bones.WINGS.getValue(), true);
			player.setBoneHidden(Bones.HORN.getValue(), false);
		}

		return player;
	}
}
