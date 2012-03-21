package gameMap;

public class TileMap {
	private Tile[][] tiles;
	private int sizeOfTile;
	
	public TileMap(Tile[][] tiles, int sizeOfTile){
		this.tiles = tiles;
		this.sizeOfTile = sizeOfTile;
	}
	/**
	 * @return the tiles
	 */
	public Tile[][] getTiles() {
		return tiles;
	}
	public Tile getTileAt(int row, int column){
		return tiles[row][column];
	}
	public void setTileAt(int row, int column, Tile tile){
		tiles[row][column] = tile;
	}
	public int width(){
		return tiles[0].length;
	}
	public int height(){
		return tiles.length;
	}

	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	/**
	 * @return the sizeOfTile
	 */
	public int getSizeOfTile() {
		return sizeOfTile;
	}
	/**
	 * @param sizeOfTile the sizeOfTile to set
	 */
	public void setSizeOfTile(int sizeOfTile) {
		this.sizeOfTile = sizeOfTile;
	}
}
