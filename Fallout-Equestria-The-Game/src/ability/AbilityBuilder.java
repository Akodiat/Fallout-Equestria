package ability;

import java.util.HashMap;
import java.util.Map;

import content.ContentManager;

public class AbilityBuilder {

	public static Map<Abilities, AbstractAbilityProcessor> build(){
		Map<Abilities, AbstractAbilityProcessor> abilities = new HashMap<>();
		
		abilities.put(Abilities.Teleport, new TeleportAbility());
		abilities.put(Abilities.CircleBullet, new CircleProjectileAbility());
		
		
		
		
		
		
		
		
		
		return abilities;
	}
}
