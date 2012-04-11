package gameMap;

public class TileLayer implements Comparable<TileLayer>{
	private final int depth;
	private final Tile[][] tiles;
	
	public TileLayer(Tile[][] tiles, int depth) {
		this.tiles = (Tile[][])tiles.clone();
		this.depth = depth;
	}
	
	public Tile getTileCell(int row, int column) {
		return this.tiles[row][column];
	}
	
	public int getDepth() {
		return this.depth;
	}

	@Override
	public int compareTo(TileLayer other) {
		return this.depth - other.depth;
	}

	public Tile[][] getTileGrid() {
		return tiles;
	}
}
