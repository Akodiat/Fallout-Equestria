package behavior;

import math.Vector2;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.IEntity;
import anotations.Editable;

@Editable
public class PortalBehavior extends Behavior{

	private @Editable String linkedPortalName;
	private @Editable String name;
	
	public PortalBehavior(String name, String linkedPortalName) {
		this.name = name;
		this.linkedPortalName = linkedPortalName;
	}
	
	public PortalBehavior(PortalBehavior other) {
		this.name = other.name;
		this.linkedPortalName = other.linkedPortalName;
	}

	@Override
	public void start() {
		this.Entity.setLabel(name);
		
	}

	@Override
	public Object clone() {
		return new PortalBehavior(this);
	}
	
	
	@Override
	public void onTriggerEnter(IEntity entity) {
		IEntity linkedPortal = this.EntityManager.getEntity(linkedPortalName);
		if(linkedPortal != null) {
			TransformationComp linkedTransformation = linkedPortal.getComponent(TransformationComp.class);
			SpatialComp linkedSpatial = linkedPortal.getComponent(SpatialComp.class);
			
			TransformationComp sendTrans = entity.getComponent(TransformationComp.class);
			SpatialComp sendSpatial = entity.getComponent(SpatialComp.class);
			
			Vector2 sendPos = new Vector2(linkedTransformation.getPosition().X,
										  linkedTransformation.getPosition().Y + linkedSpatial.getBounds().getHeight()
										  + sendSpatial.getBounds().getHeight() + 50);
			sendTrans.setPosition(sendPos);
		}
	}
}
