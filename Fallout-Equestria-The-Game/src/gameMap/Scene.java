package gameMap;

import java.util.ArrayList;
import java.util.List;

import utils.HeightMap;
import utils.Rectangle;

import com.google.common.collect.ImmutableList;

public class Scene {
	
	private final ImmutableList<TileLayer> tileLayers;
	private final ImmutableList<CollisionLayer> collisionLayers;
	private final ImmutableList<SceneNode> nodes;
	private final Rectangle bounds;
	private final int blockSize;
	private final HeightMap heightMap;
	
	public Scene(List<TileLayer> tileLayers, List<CollisionLayer> collisionLayers, List<SceneNode> nodes, Rectangle bounds, int blockSize, HeightMap heightMap) {
		this.tileLayers = ImmutableList.copyOf(tileLayers);
		this.collisionLayers = ImmutableList.copyOf(collisionLayers);	
		this.nodes 			 = ImmutableList.copyOf(nodes);
		this.bounds = bounds;
		this.blockSize = blockSize;
		this.heightMap = heightMap;
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
	
	public SceneNode getNodeByID(String id) {
		for (SceneNode node : this.nodes) {
			if(node.getNodeID().equals(id)) {
				return node;
			}
		}
		throw new RuntimeException("Node not found!" + id);
	}
	
	public List<TexturedSceneNode> getTexturedNodes() {
		List<TexturedSceneNode> tNodes = new ArrayList<>();
		for (SceneNode node : this.nodes) {
			if(node instanceof TexturedSceneNode) {
				tNodes.add((TexturedSceneNode)node);
			}
		}
		return tNodes;
	}
	
	public ImmutableList<SceneNode> getNodes() {
		return this.nodes;
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
		return new Rectangle(r.X 	  * blockSize,
						     r.Y	  * blockSize,
						     r.Width  * blockSize,
						     r.Height * blockSize);
	}

	public HeightMap getHeightMap() {
		return heightMap;
	}
}
