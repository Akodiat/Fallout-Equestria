package entitySystems;
import math.Vector2;
import components.AntiGravityComp;
import components.InputComp;
import components.PhysicsComp;
import components.TransformationComp;

import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TelekinesisSystem extends EntitySingleProcessingSystem{

	public TelekinesisSystem(IEntityWorld world) {
		super(world, TransformationComp.class, AntiGravityComp.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void processEntity(IEntity entity) {
		PhysicsComp physComp = entity.getComponent(PhysicsComp.class);
		TransformationComp transComp = entity.getComponent(TransformationComp.class);
		AntiGravityComp aGravComp = entity.getComponent(AntiGravityComp.class);
		
		IEntity controller = this.getWorld().getEntityManager().getEntity(aGravComp.getControllingEntityID());
		InputComp inpComp = controller.getComponent(InputComp.class); //Get the InputComponent from the controlling entity
		
		Vector2 velocity = (Vector2.subtract(inpComp.getMousePosition(), transComp.getPosition()));
		physComp.setVelocity(velocity);
		
		if(!inpComp.isLeftMouseButtonDown()){
			entity.removeComponent(AntiGravityComp.class); //Drop telekinesis if right mouse button is released
			entity.refresh();
		}
		
	}
	

}
