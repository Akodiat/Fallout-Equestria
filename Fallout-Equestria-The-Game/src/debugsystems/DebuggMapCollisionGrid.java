package debugsystems;

import math.MathHelper;
import math.Point2;
import math.Vector2;
import utils.Camera2D;
import utils.Rectangle;
import entityFramework.EntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntityWorld;
import gameMap.CollisionLayer;
import gameMap.Scene;
import gameMap.Tile;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class DebuggMapCollisionGrid extends EntitySystem {

	private SpriteBatch spriteBatch;
	private Scene scene;
	private Camera2D camera;
	
	public DebuggMapCollisionGrid(IEntityWorld world, Scene scene, SpriteBatch spriteBatch, Camera2D camera) {
		super(world);
		this.scene = scene;
		this.spriteBatch = spriteBatch;
		this.camera = camera;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() {
		for (CollisionLayer layer : this.scene.getCollisionLayers()) {
			drawCollisionGrid(layer.getCollisionGrid());
		}
	}



	private void drawCollisionGrid(boolean[][] collisionGrid) {
		
		Point2 tileGridDim = new Point2(collisionGrid[0].length, collisionGrid.length);
		Point2 minIndex = getMinIndex(tileGridDim);
		Point2 maxIndex = getMaxIndex(tileGridDim);
		
		for (int row = minIndex.Y; row < maxIndex.Y; row++) {
			for (int column = minIndex.X; column < maxIndex.X; column++) {
				boolean colidable = collisionGrid[row][column];
				if(colidable)
					drawBlock( row, column);								
			}
		}
	}

	private Point2 getMaxIndex(Point2 gridDim) {
		
		float blockSize = (float)scene.getBlockSize();
		
		Vector2 bottomRight = camera.getViewToWorldCoords(new Vector2(spriteBatch.getViewport().Width, spriteBatch.getViewport().Height));
		Point2 p = new Point2((int)((bottomRight.X + blockSize)/ blockSize),
				  			  (int)((bottomRight.Y + blockSize)/ blockSize));
		p.X = MathHelper.clamp(0, gridDim.X, p.X);
		p.Y = MathHelper.clamp(0, gridDim.Y, p.Y);
			
		return p;
	}

	private Point2 getMinIndex(Point2 gridDim) {
		Vector2 topLeft = camera.getViewToWorldCoords(Vector2.Zero);
		Point2 p = new Point2((int)(topLeft.X / (float)scene.getBlockSize()),
							  (int)(topLeft.Y / (float)scene.getBlockSize()));

		p.X = MathHelper.clamp(0, gridDim.X, p.X);
		p.Y = MathHelper.clamp(0, gridDim.Y, p.Y);
		return p;
	}

	private void drawBlock(int row, int column) {
		
		
		Rectangle position = new Rectangle(column * scene.getBlockSize(),
									   	   row    * scene.getBlockSize(),
									   	   scene.getBlockSize(), 
									   	   scene.getBlockSize());
		
		Color color = new Color(Color.Red, 0.6f);
		
		this.spriteBatch.draw(Texture2D.getPixel(), position, color, null);
							
	}
	
}
