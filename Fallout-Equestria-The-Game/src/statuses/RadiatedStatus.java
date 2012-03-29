package statuses;

import components.RenderingComp;

import entityFramework.IEntity;
import graphics.Color;

public class RadiatedStatus implements IStatus {
	private IEntity target;
	private int radiationLevel;
	
	public RadiatedStatus(IEntity target, int radiationLevel) {
		this.target = target;
		this.radiationLevel = radiationLevel;
	}
	
	@Override
	public void activateStatusEffect() {
		target.getComponent(RenderingComp.class).setColor(Color.Green);
		
	}

	@Override
	public void deactivateStatusEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
