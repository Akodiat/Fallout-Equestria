package statuses;

import components.RenderingComp;

import entityFramework.IEntity;
import graphics.Color;

public class RadiatedStatus implements IStatus {
	private IEntity target;
	private Color originalColor;
	private float radiationLevel;
	
	public RadiatedStatus(IEntity target, float radiationLevel) {
		this.target = target;
		this.radiationLevel = radiationLevel;
	}
	
	@Override
	public void activateStatusEffect() {
		this.originalColor = target.getComponent(RenderingComp.class).getColor();
		target.getComponent(RenderingComp.class).setColor(Color.Green);
		
	}

	@Override
	public void deactivateStatusEffect() {
		target.getComponent(RenderingComp.class).setColor(originalColor);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
