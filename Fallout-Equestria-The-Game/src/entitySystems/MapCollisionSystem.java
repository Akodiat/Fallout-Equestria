package entitySystems;
import static math.MathHelper.*;

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

	private static final int shadowOffset = 10;
	
	private Scene scene;

	public MapCollisionSystem(IEntityWorld world, Scene scene) {
		super(world, TransformationComp.class, SpatialComp.class, PhysicsComp.class);
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
			checkSceneGridCollision(posiCom, spatiCom, physCom);		
		}

	}

	private void checkSceneGridCollision(TransformationComp posiCom,
			SpatialComp spatiCom, PhysicsComp physCom) {
		CollisionLayer colLayer = this.scene.getCollisionLayers().get(0);
		if(colLayer != null && !spatiCom.isTrigger()) {
			boolean[][] grid = colLayer.getCollisionGrid();
			Vector2 entityPos = posiCom.getOriginPosition();
			Vector2 topLeft = new Vector2(entityPos.X - spatiCom.getBounds().getWidth() / 2,
										  entityPos.Y - spatiCom.getBounds().getDepth() / 2);
			
			Point2 topLeftIndex = getTopLeftIndex(topLeft, grid);
			Point2 bottomRight = new Point2(MathHelper.clamp(0, grid[0].length -1, topLeftIndex.X + 1),
										    MathHelper.clamp(0, grid.length -1 , topLeftIndex.Y + 1));
			
			for (int row = topLeftIndex.Y; row <= bottomRight.Y; row++) {
				for (int column = topLeftIndex.X; column <= bottomRight.X; column++) {
					if(grid[row][column]) {
						boolean wasColidedWith = checkAndResolveBlockCollision(posiCom, spatiCom,physCom, row, column);
						if(wasColidedWith) 
							return;
					}
				}
			}
		}
	}

	private boolean checkAndResolveBlockCollision(TransformationComp posiCom,
			SpatialComp spatiCom,PhysicsComp physComp, int row, int column) {
		
		int blockSize = this.scene.getBlockSize();
		
		Rectangle gridRect = new Rectangle((column) * blockSize, 
										   (row)    * blockSize, 
										   blockSize, 
										   blockSize);
		
		Rectangle entityBounds = createEntityRect(posiCom, spatiCom);
		
		if (entityBounds.intersects(gridRect)) {
			float time = (float)this.getWorld().getTime().getElapsedTime().getTotalSeconds();		
			Vector2 invVelo = Vector2.mul(-1 * time, physComp.getVelocity());		
			posiCom.setPosition(Vector2.add(invVelo, posiCom.getPosition()));
			return true;
		}
		
		
		return false;
		
	}
	
	
	private Rectangle createEntityRect(TransformationComp posiCom,
			SpatialComp spatiCom) {
		Vector2 originPos = posiCom.getOriginPosition();
		
		int w = (int)spatiCom.getBounds().getWidth();
		int h = (int)spatiCom.getBounds().getDepth();
		int x = (int)(originPos.X - w / 2);
		int y = (int)(originPos.Y  + h / 2 -scene.getHeightMap().getHeightAt(posiCom.getPosition()) - shadowOffset);
	    h = shadowOffset;
		
		return new Rectangle(x,y,w,h);
	}

	private Point2 getTopLeftIndex(Vector2 topLeft, boolean[][] grid) {
		int x, y;
		x = clamp(0, grid[0].length - 1,(int)(topLeft.X / scene.getBlockSize()));
		y =  clamp(0, grid.length - 1,(int)((topLeft.Y - scene.getHeightMap().getHeightAt(topLeft)) / scene.getBlockSize()));
		return new Point2(x,y);
	}

	private void keepInsideScene(TransformationComp posiCom,
			SpatialComp spatiCom) {
		
		Rectangle entityBounds = createEntityRect(posiCom, spatiCom);
		Rectangle worldBounds = this.scene.getWorldBounds();
		//Check collision against west border
		int left = worldBounds.getLeft();
		if (entityBounds.getLeft() < left ){
			posiCom.setOriginPosition(new Vector2(left + entityBounds.Width / 2, posiCom.getOriginPosition().Y));
		}
		
		//Check collision against North border
		int top = worldBounds.getTop();
		if (entityBounds.getTop() < top){
			posiCom.setOriginPosition(new Vector2(posiCom.getOriginPosition().X,top - (spatiCom.getBounds().getDepth() - shadowOffset) / 2));
		}
		
		//Check collision against east border
		int right = worldBounds.getRight();
		if (entityBounds.getRight() > right){
			posiCom.setOriginPosition(new Vector2(right - entityBounds.Width / 2, posiCom.getOriginPosition().Y));
			
		}
		
		//Check collision against south border
		int bottom = (int) (worldBounds.getBottom() - scene.getHeightMap().getHeightAt(entityBounds.getCenter()));
		if (entityBounds.getBottom() > bottom){
			posiCom.setOriginPosition(new Vector2(posiCom.getOriginPosition().X, worldBounds.getBottom() - spatiCom.getBounds().getDepth() / 2));
		}
	}

}
