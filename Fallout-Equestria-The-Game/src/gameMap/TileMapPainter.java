package gameMap;

import utils.Rectangle;
import graphics.SpriteBatch;

public class TileMapPainter {
	private TileMap map;
	
	public TileMapPainter(TileMap map){
		this.map = map;
	}
	public void draw(SpriteBatch spriteBatch){
		//spriteBatch.begin();
		
		for(int i=0; i<map.width(); i++){
			for(int j=0; j<map.height(); j++){
				spriteBatch.draw(map.getTileAt(j, i).getTexture(),
						new Rectangle(i*map.getSizeOfTile(),
								j*map.getSizeOfTile(),
								map.getSizeOfTile(),
								map.getSizeOfTile()), 
						graphics.Color.White, null);
			}
		}
		
	//	spriteBatch.end();
	}

}
