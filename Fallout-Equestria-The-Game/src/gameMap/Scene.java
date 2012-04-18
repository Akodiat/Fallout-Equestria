package gameMap;

import java.util.List;

import utils.Rectangle;

import com.google.common.collect.ImmutableList;

public class Scene {
	
	private ImmutableList<TileLayer> tileLayers;
	private ImmutableList<CollisionLayer> collisionLayers;
	private Rectangle bounds;
	private int blockSize;
	
	public Scene(List<TileLayer> tileLayers, List<CollisionLayer> collisionLayers, Rectangle bounds, int blockSize) {
		this.tileLayers = ImmutableList.copyOf(tileLayers);
		this.collisionLayers = ImmutableList.copyOf(collisionLayers);		
		this.bounds = bounds;
		this.blockSize = blockSize;
	}
	
	/**Gets a sorted list of tile layers, sorted by depth.
	 * 
	 * @return list of tile layers. 
	 */
	public ImmutableList<TileLayer> getTileLayers() {
		return tileLayers;
	}

	/**Gets a list of collision layers
	 * 
	 * @return a list of collision layers.
	 */
	public ImmutableList<CollisionLayer> getCollisionLayers() {
		return collisionLayers;
	}
	
	public int getBlockSize() {
		return this.blockSize;
	}
	
	/**Gets the borders of the map.
	 * 
	 * @return a rectangle containing the borders of the map.
	 */
	public Rectangle getGridBounds() {
		return this.bounds;
	}
	
	public Rectangle getWorldBounds() {
		Rectangle r = bounds;
		return new Rectangle(r.X * blockSize,
						     r.Y * blockSize,
						     r.Width * blockSize,
						     r.Height * blockSize);
	}
}