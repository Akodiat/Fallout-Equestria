package gameMap;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import utils.ContentManager;

import graphics.SpriteBatch;
import graphics.Texture2D;

public class MapTester {
	private TileMap tileMap;
	private TileMapPainter tileMapPainter;
	private Texture2D textureForAllTiles;
	private SpriteBatch spriteBatch;	
	public static void main(String[] args) throws IOException, LWJGLException{
		Display.create();
		MapTester tester = new MapTester(new SpriteBatch(new utils.Rectangle(0, 0, Display.getWidth(), Display.getHeight())));
		while(!Display.isCloseRequested()){
			tester.draw();
			Display.update();
		}
	}
	
	public MapTester(SpriteBatch graphics) throws IOException{
		this.textureForAllTiles =  ContentManager.loadTexture("ground.png");
		this.spriteBatch = graphics;
		
		int tileSize = 400;
		int mapHeight = 100;
		int mapWidth = 200;
		tileMap = new TileMap(new Tile[mapHeight][mapWidth], tileSize);
		
		for(int i=0; i<mapHeight; i++){
			for (int j=0; j<mapWidth; j++){
				tileMap.setTileAt(i, j, new Tile(textureForAllTiles));
			}
		}
		tileMapPainter = new TileMapPainter(tileMap);
		
	}
	public void draw(){
		tileMapPainter.draw(this.getSpriteBatch());
	}

	/**
	 * @return the spriteBatch
	 */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	/**
	 * @param spriteBatch the spriteBatch to set
	 */
	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
}
