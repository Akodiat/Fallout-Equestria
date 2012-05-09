package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.TransformationComp;

import utils.Camera2D;
import entityFramework.*;

public class CameraControlSystem extends GroupedEntitySystem{

	public static final String GROUP_NAME = "CAMERA-TARGETS";
	private final Camera2D camera;
	
	
	public CameraControlSystem(IEntityWorld world, Camera2D camera) {
		super(world, GROUP_NAME, TransformationComp.class);
		this.camera = camera;
	}
	
	public ComponentMapper<TransformationComp> transCM;

	@Override
	public void initialize() {
		transCM = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
	}


	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		float x = 0, y = 0;
		for (IEntity entity : entities) {
			TransformationComp transformation = this.transCM.getComponent(entity);
			x -= transformation.getPosition().X;
			y -= transformation.getPosition().Y - transformation.getHeight();
		}
		
		x /= entities.size();
		y /= entities.size();	
		
		//TODO figure out a way to handle if camera targets are not present!
		camera.setPosition(Vector2.subtract(new Vector2(x,y), camera.getScreenOffset()));
		
	}

}