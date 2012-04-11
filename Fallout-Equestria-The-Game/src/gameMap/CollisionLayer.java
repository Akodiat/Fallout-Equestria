package gameMap;

public class CollisionLayer {
	public final boolean[][] collisionGrid;
	
	public CollisionLayer(boolean[][] grid) {
		this.collisionGrid = (boolean[][])grid.clone();
	}
	
	public boolean isCellColidable(int row, int column) {
		return collisionGrid[row][column];
	}
	
	public boolean[][] getCollisionGrid() {
		return (boolean[][])this.collisionGrid.clone();
	}
}
