package entitySystems;
import static math.MathHelper.*;

import utils.Circle;
import utils.Rectangle;
import math.MathHelper;
import math.Point2;
import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.PhysicsComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import gameMap.CollisionLayer;
import gameMap.Scene;

public class MapCollisionSystem extends EntityProcessingSystem {

	private Scene scene;

	public MapCollisionSystem(IEntityWorld world, Scene scene) {
		super(world, TransformationComp.class, SpatialComp.class);
		this.scene = scene;
	}

	private ComponentMapper<SpatialComp> sCM;
	private ComponentMapper<TransformationComp> tCM;
	private ComponentMapper<PhysicsComp> pCM;

	@Override
	public void initialize() {
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComp.class);
		
		pCM = ComponentMapper.create(this.getDatabase(), PhysicsComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			TransformationComp posiCom = tCM.getComponent(entity);
			SpatialComp spatiCom = sCM.getComponent(entity);
			PhysicsComp physCom = pCM.getComponent(entity);
			keepInsideScene(posiCom, spatiCom);
			checkSceneGridCollision(posiCom, spatiCom,physCom);		
		}

	}

	private void checkSceneGridCollision(TransformationComp posiCom,
			SpatialComp spatiCom, PhysicsComp physCom) {
		CollisionLayer colLayer = this.scene.getCollisionLayers().get(0);
		if(colLayer != null) {
			boolean[][] grid = colLayer.getCollisionGrid();
			Vector2 topLeft = new Vector2(posiCom.getPosition().X - spatiCom.getBounds().getRadius(),
										  posiCom.getPosition().Y - spatiCom.getBounds().getRadius());
			
			Point2 topLeftIndex = getTopLeftIndex(topLeft, grid);
			Point2 bottomRight = new Point2(topLeftIndex.X + 1, topLeftIndex.Y + 1);
			
			for (int row = topLeftIndex.Y; row <= bottomRight.Y; row++) {
				for (int column = topLeftIndex.X; column <= bottomRight.X; column++) {
					if(grid[row][column]) {
						checkAndResolveBlockCollision(posiCom, spatiCom,physCom, row, column);
					}
				}
			}
		}
	}

	private void checkAndResolveBlockCollision(TransformationComp posiCom,
			SpatialComp spatiCom,PhysicsComp physComp, int row, int column) {
		
		int blockSize = this.scene.getBlockSize();
		
		Rectangle gridRect = new Rectangle((column) * blockSize, 
										   (row) * blockSize, 
										   blockSize, 
										   blockSize);
		
		int r = (int)spatiCom.getBounds().getRadius();
		int r2 = (int)(spatiCom.getBounds().getRadius()) * 2;
		int x = (int)posiCom.getPosition().X;
		int y = (int)posiCom.getPosition().Y;
		
		Rectangle playerBounds = new Rectangle(x -r, y- r, r2, r2);
		
		
		
				
		if (playerBounds.intersects(gridRect)) {
			
			float time = this.getWorld().getTime().DeltaTime;
			
			Vector2 invVelo = Vector2.mul(-1 * time, physComp.getVelocity());		
			posiCom.setPosition(Vector2.add(invVelo, posiCom.getPosition()));
		}
	}

	private Point2 getTopLeftIndex(Vector2 topLeft, boolean[][] grid) {
		int x, y;
		x = clamp(0, grid[0].length - 1,(int)(topLeft.X / scene.getBlockSize()));
		y =  clamp(0, grid.length - 1,(int)(topLeft.Y / scene.getBlockSize()));
		return new Point2(x,y);
	}

	private void keepInsideScene(TransformationComp posiCom,
			SpatialComp spatiCom) {
		Rectangle worldBounds = this.scene.getWorldBounds();
		//Check collision against west border
		int left = worldBounds.getLeft();
		if (posiCom.getPosition().X - spatiCom.getBounds().getRadius() < left ){
			posiCom.setPosition(new Vector2(spatiCom.getBounds().getRadius() + left ,posiCom.getPosition().Y ));
		}
		
		//Check collision against North border
		int top = worldBounds.getTop();
		if (posiCom.getPosition().Y - spatiCom.getBounds().getRadius() < top){
			posiCom.setPosition(new Vector2(posiCom.getPosition().X,spatiCom.getBounds().getRadius() + top));
		}
		
		//Check collision against east border
		int right = worldBounds.getRight();
		if (posiCom.getPosition().X + spatiCom.getBounds().getRadius() > right){
			posiCom.setPosition(new Vector2(right - spatiCom.getBounds().getRadius(), posiCom.getPosition().Y));
		}
		
		//Check collision against south border
		int bottom = worldBounds.getBottom();
		if (posiCom.getPosition().Y + spatiCom.getBounds().getRadius() > bottom){
			posiCom.setPosition(new Vector2(posiCom.getPosition().X,bottom - spatiCom.getBounds().getRadius()));
		}
	}

}
