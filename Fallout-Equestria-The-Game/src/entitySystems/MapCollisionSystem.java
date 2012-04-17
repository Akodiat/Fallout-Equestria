package entitySystems;
import static math.MathHelper.*;

import utils.Circle;
import utils.Rectangle;
import math.MathHelper;
import math.Point2;
import math.Vector2;

import com.google.common.collect.ImmutableSet;

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

	@Override
	public void initialize() {
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			TransformationComp posiCom = tCM.getComponent(entity);
			SpatialComp spatiCom = sCM.getComponent(entity);
			keepInsideScene(posiCom, spatiCom);
			checkSceneGridCollision(posiCom, spatiCom);
			
		}

	}

	private void checkSceneGridCollision(TransformationComp posiCom,
			SpatialComp spatiCom) {
		CollisionLayer colLayer = this.scene.getCollisionLayers().get(0);
		if(colLayer != null) {
			boolean[][] grid = colLayer.getCollisionGrid();
			Vector2 topLeft = new Vector2(posiCom.getPosition().X - spatiCom.getBounds().getRadius(),
										  posiCom.getPosition().Y - spatiCom.getBounds().getRadius());
			Point2 topLeftIndex = getTopLeftIndex(topLeft, grid);
			
			int nBlocks = (int)(spatiCom.getBounds().getRadius() * 2 / scene.getBlockSize());
			int minX = min(topLeftIndex.X + nBlocks, grid[0].length);
			int minY = min(topLeftIndex.Y + nBlocks, grid.length);
			
			for (int row = topLeftIndex.Y; row < minY; row++) {
				for (int column = topLeftIndex.X; column < minX; column++) {
					if(grid[row][column]) {
						checkAndResolveBlockCollision(posiCom, spatiCom, row, column);
					}
				}
			}
		}
	}

	private void checkAndResolveBlockCollision(TransformationComp posiCom,
			SpatialComp spatiCom, int row, int column) {
		
		int left = column * this.scene.getBlockSize();
		int right = (column + 1) * this.scene.getBlockSize();
		int top = row * this.scene.getBlockSize();
		int bottom = (row + 1) * this.scene.getBlockSize();
		
		float radius = spatiCom.getBounds().getRadius();
		float x = posiCom.getPosition().X;
		float y = posiCom.getPosition().Y;
		
		if (x + radius > left && 
			x - radius < right &&
			y + radius > top &&
			y - radius < bottom) {
			Vector2 center = new Vector2(left + this.scene.getBlockSize() / 2, 
										 top + this.scene.getBlockSize() / 2);
			
			Vector2 dir = Vector2.subtract(posiCom.getPosition(), center);
			float moveDistance = this.scene.getBlockSize() + radius - dir.length(); 
			dir = Vector2.norm(dir);
			dir = Vector2.mul(moveDistance, dir);
			
			posiCom.setPosition(Vector2.add(dir, posiCom.getPosition()));
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
