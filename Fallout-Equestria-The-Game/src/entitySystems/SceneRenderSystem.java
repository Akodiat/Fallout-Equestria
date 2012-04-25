package entitySystems;

import entityFramework.*;
import gameMap.Scene;
import gameMap.Tile;
import gameMap.TileLayer;
import graphics.Color;
import graphics.SpriteBatch;

import java.util.*;

import utils.Camera2D;
import utils.Rectangle;

import math.MathHelper;
import math.Point2;
import math.Vector2;

public class SceneRenderSystem extends EntitySystem {	
	Scene scene;
	SpriteBatch spriteBatch;
	Camera2D camera;
	
	public SceneRenderSystem(IEntityWorld world, Scene scene, SpriteBatch spriteBatch, Camera2D camera) {
		super(world);
		this.scene = scene;
		this.camera = camera;
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void process() {
		List<TileLayer> tileLayers = scene.getTileLayers();

		Point2 tileGridDim = new Point2(scene.getGridBounds().Width, scene.getGridBounds().Height);
		Point2 minIndex = getMinIndex(tileGridDim);
		Point2 maxIndex = getMaxIndex(tileGridDim);
		
		for (TileLayer tileLayer : tileLayers) {
			Tile[][] tiles = tileLayer.getTileGrid();
			drawTileGrid(tiles, minIndex, maxIndex);
		}
	}

	private void drawTileGrid(Tile[][] tiles, Point2 minIndex, Point2 maxIndex) {
		
		
		
		for (int row = minIndex.Y; row < maxIndex.Y; row++) {
			for (int column = minIndex.X; column < maxIndex.X; column++) {
				Tile tile = tiles[row][column];
				if(tile != null)
					drawTile(tile, row, column);								
			}
		}
	}

	private Point2 getMaxIndex(Point2 gridDim) {
		
		int blockSize = scene.getBlockSize();
		
		Rectangle visibleArea = camera.getVisibleArea();
		Point2 p = new Point2((visibleArea.getRight() + blockSize) / blockSize,
				  			  (visibleArea.getBottom() + blockSize) / blockSize);
		p.X = MathHelper.clamp(0, gridDim.X, p.X);
		p.Y = MathHelper.clamp(0, gridDim.Y, p.Y);
			
		return p;
	}

	private Point2 getMinIndex(Point2 gridDim) {

		int blockSize = scene.getBlockSize();
		Rectangle visibleArea = camera.getVisibleArea();
		Point2 p = new Point2(visibleArea.X / blockSize,
				  			  visibleArea.Y / blockSize);

		p.X = MathHelper.clamp(0, gridDim.X, p.X);
		p.Y = MathHelper.clamp(0, gridDim.Y, p.Y);
		return p;
	}

	private void drawTile(Tile tile, int row, int column) {
		
		
		Rectangle position = new Rectangle(column * scene.getBlockSize(),
									   	   row    * scene.getBlockSize(),
									   	   scene.getBlockSize(), 
									   	   scene.getBlockSize());
		
		Color color = new Color(Color.White, tile.getAlpha());
		
		this.spriteBatch.draw(tile.getTexture(), position, color, tile.getSourceRect(), Vector2.Zero, 0f, false);
							
	}
}