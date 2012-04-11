package entitySystems;

import entityFramework.*;
import gameMap.Scene;
import gameMap.Tile;
import gameMap.TileLayer;
import graphics.Color;
import graphics.SpriteBatch;

import java.util.*;

import utils.Camera2D;

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

		for (TileLayer tileLayer : tileLayers) {
			Tile[][] tiles = tileLayer.getTileGrid();
			drawTileGrid(tiles);
		}
	}

	private void drawTileGrid(Tile[][] tiles) {
		
		Point2 minIndex = getMinIndex();
		Point2 maxIndex = getMaxIndex(new Point2(tiles[0].length, tiles.length));
		
		for (int row = minIndex.Y; row < maxIndex.Y; row++) {
			for (int column = minIndex.X; column < maxIndex.X; column++) {
				Tile tile = tiles[row][column];
				if(tile != null)
					drawTile(tile, row, column);								
			}
		}
	}

	private Point2 getMaxIndex(Point2 gridDim) {
		
		float blockSize = (float)scene.getBlockSize();
		
		Vector2 bottomRight = camera.getViewToWorldCoords(new Vector2(spriteBatch.getViewport().Width, spriteBatch.getViewport().Height));
		Point2 p = new Point2((int)((bottomRight.X + blockSize)/ blockSize),
				  			  (int)((bottomRight.Y + blockSize)/ blockSize));
		if(p.X > gridDim.X)
			p.X = gridDim.X; 
		
		if(p.Y > gridDim.Y) {
			p.Y = gridDim.Y;
		}
		
		return p;
	}

	private Point2 getMinIndex() {
		Vector2 topLeft = camera.getViewToWorldCoords(Vector2.Zero);
		Point2 p = new Point2((int)(topLeft.X / (float)scene.getBlockSize()),
							  (int)(topLeft.Y / (float)scene.getBlockSize()));
		if(p.X < 0 )
			p.X = 0; 
		else if(p.Y < 0 ) {
			p.Y = 0;
		}
		
		return p;
	}

	private void drawTile(Tile tile, int row, int column) {
		
		
		Vector2 position = new Vector2(column * scene.getBlockSize(),
									   row    * scene.getBlockSize());
		
		Color color = new Color(Color.White, tile.getAlpha());
		
		this.spriteBatch.draw(tile.getTexture(),
							  position,
							  color,
							  tile.getSorceRect(),
							  Vector2.Zero,
							  Vector2.One,
							  0f,
							  false);
	}
}