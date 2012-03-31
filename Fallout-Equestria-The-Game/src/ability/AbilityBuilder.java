package ability;

import java.util.HashMap;
import java.util.Map;

public class AbilityBuilder {

	public static Map<Abilities, AbstractAbilityProcessor> build(){
		Map<Abilities, AbstractAbilityProcessor> abilities = new HashMap<>();
		
		abilities.put(Abilities.Teleport, new TeleportAbility());
		abilities.put(Abilities.Telekinesis, new TelekinesisAbility());
		abilities.put(Abilities.Bullet, new BulletAbility());
		abilities.put(Abilities.CircleBullet, new CircleProjectileAbility());
		
		
		
		
		
		
		
		
		
		return abilities;
	}
}
