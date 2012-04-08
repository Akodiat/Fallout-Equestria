package debugsystems;

import org.lwjgl.input.Keyboard;

import ability.Abilities;
import ability.AbilityInfo;
import components.WeaponComp;

import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.LabelEntitySystem;

public class AbilityDebugLogicSystem extends LabelEntitySystem {

	private final static Abilities[] abilities = Abilities.values();
	private int currentAbilityIndex = 0;
	
	public AbilityDebugLogicSystem(IEntityWorld world, String label) {
		super(world, label, WeaponComp.class);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		WeaponComp weapon = entity.getComponent(WeaponComp.class);		
		while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
		        	AbilityInfo info = new AbilityInfo(abilities[this.currentAbilityIndex]);
		        	this.currentAbilityIndex = (this.currentAbilityIndex + 1) % abilities.length;
		        	
		        	weapon.setPrimaryAbility(info);
		        }
		    }
		}
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		
	}

}
