package gameMap;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import utils.ContentManager;

import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureLoader;

public class MapTester {
	private TileMap tileMap;
	private TileMapPainter tileMapPainter;
	private Texture2D textureForAllTiles;
	private SpriteBatch spriteBatch;	
	public static void main(String[] args) throws IOException, LWJGLException{
		Display.create();
		MapTester tester = new MapTester();
		while(!Display.isCloseRequested()){
			tester.draw();
			Display.update();
		}
	}
	
	public MapTester() throws IOException{
		this.textureForAllTiles =  ContentManager.loadTexture("ground.png");
		this.spriteBatch = new SpriteBatch(new utils.Rectangle(0, 0, Display.getWidth(), Display.getHeight()));

		
		int mapHeight = 100;
		int mapWidth = 200;
		tileMap = new TileMap(new Tile[100][200], 400);
		
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
