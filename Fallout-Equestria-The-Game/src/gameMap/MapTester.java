package gameMap;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureLoader;
import tests.TextureTest;

public class MapTester {
	private TileMap tileMap;
	private TileMapPainter tileMapPainter;
	private Texture2D textureForAllTiles = TextureLoader.loadTexture(MapTester.class.getResourceAsStream("ground.png"));
	private SpriteBatch spriteBatch = new SpriteBatch(new utils.Rectangle(0, 0, Display.getWidth(), Display.getHeight()));
	public static void main(String[] args) throws IOException, LWJGLException{
		Display.create();
		MapTester tester = new MapTester();
		while(!Display.isCloseRequested()){
			tester.draw();
			Display.update();
		}
	}
	
	public MapTester() throws IOException{
		int mapHeight = 100;
		int mapWidth = 200;
		tileMap = new TileMap(new Tile[100][200], 20);
		
		for(int i=0; i<mapHeight; i++){
			for (int j=0; j<mapWidth; j++){
				tileMap.setTileAt(i, j, new Tile(textureForAllTiles));
			}
		}
		tileMapPainter = new TileMapPainter(tileMap);
		
	}
	public void draw(){
		tileMapPainter.draw(this.spriteBatch);
	}
}
