package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.TransformationComp;

import utils.Camera2D;
import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class CameraControlSystem extends EntitySystem{

	private final Camera2D camera;
	
	public CameraControlSystem(IEntityWorld world,Camera2D camera) {
		super(world);
		this.camera = camera;
	}
	
	public ComponentMapper<TransformationComp> transCM;

	@Override
	public void initialize() {
		transCM = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
	}

	@Override
	public void process() {
		ImmutableSet<IEntity> cameraTargets = this.getWorld().getEntityManager().getEntityGroup("CAMERA_TARGET");
		if(cameraTargets != null) {
			float x = 0, y = 0;
			for (IEntity entity : cameraTargets) {
				TransformationComp transformation = this.transCM.getComponent(entity);
				x -= transformation.getPosition().X;
				y -= transformation.getPosition().Y;
			}
			
			x /= cameraTargets.size();
			y /= cameraTargets.size();
			
			
			
			camera.setPosition(Vector2.subtract(new Vector2(x,y), camera.getScreenOffset()));
		}
	}

}