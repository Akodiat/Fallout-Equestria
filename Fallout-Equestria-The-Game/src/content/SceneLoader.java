package content;

import static utils.StringHelper.*;
import gameMap.SceneNode;
import gameMap.CollisionLayer;
import gameMap.Scene;
import gameMap.TexturedSceneNode;
import gameMap.Tile;
import gameMap.TileLayer;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import math.Vector2;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import utils.HeightMap;
import utils.Rectangle;
import graphics.Texture2D;

public class SceneLoader extends ContentLoader<Scene>{
	
	private final static char primarySeparator 		= '|';
	private final static char secondarySeparator 	= '$';

	private final static String dimentionAttrib 	= "Dimention";
	private final static String textureElemName 	= "Textures";
	private final static String tileLayerElemName   = "TileLayer";
	private final static String layerDepthElemName  = "DrawOrder";
	private final static String colLayerElemName    = "CollisionLayer";
	private final static String blockSizeElemName	= "BlockSize";
	private final static String textureSubFolder   = "tilesheets/";
	
	
	private ContentManager ContentManager;
	
	public SceneLoader(ContentManager manager, String folder) {
		super(folder);
		this.ContentManager = manager;
	}
	
	
	@Override
	public Class<Scene> getClassAbleToLoad() {
		return Scene.class;
	}

	@Override
	public Scene loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		
		int blockSize 						 = extractBlockSize(rootNode);
		Rectangle gridBounds 				 = extractBounds(rootNode);
		Map<String, Texture2D> usedTextures  = extractTextures(rootNode);
		List<TileLayer> tileLayers		     = extractSortedTileLayers(rootNode, gridBounds, usedTextures);
		List<CollisionLayer> collisionLayers = extractCollisionLayers(rootNode, gridBounds);
		List<SceneNode> nodes   			 = extractNodes(rootNode);
		HeightMap heightMap 				 = extractHeightMap(rootNode);


		
		return new Scene(tileLayers, collisionLayers, nodes, gridBounds, blockSize, heightMap);
	}

	private HeightMap extractHeightMap(Element rootNode) {
		Element heightMapElem = rootNode.getChild("HeightMap");
		
		@SuppressWarnings("unchecked")
		List<Element> pointElements = heightMapElem.getChildren("Point");	
		List<Vector2> points = new ArrayList<>();
		for (Element element : pointElements) {
			Vector2 point = extractPoint(element);
			points.add(point);
		}
		
		return new HeightMap(points, 0f);
	}


	private Vector2 extractPoint(Element element) {
		String[] point = split(element.getAttributeValue("Value"), primarySeparator);
		return extractVector2(point);
	}


	private List<SceneNode> extractNodes(Element rootNode) {
		Element nodesElement = rootNode.getChild("Nodes");
		
		@SuppressWarnings("unchecked")
		List<Element> nodeElements = nodesElement.getChildren("Node");
		List<SceneNode> nodes = new ArrayList<>();
		for (Element nodeElement : nodeElements) {
			String name = extractID(nodeElement);
			Vector2 position = extractPosition(nodeElement);
			SceneNode node = new SceneNode(name, position);
			nodes.add(node);
		}
		
		@SuppressWarnings("unchecked")
		List<Element> textredNodeElements = nodesElement.getChildren("TexturedNode");
		for (Element nodeElement : textredNodeElements) {
			String name = extractID(nodeElement);
			Vector2 position = extractPosition(nodeElement);
			Texture2D texture = extractTexture(nodeElement);
			Rectangle srcRect = extractSrcRect(nodeElement);
			SceneNode node = new TexturedSceneNode(name, position, texture,srcRect);
			nodes.add(node);
		}
		
		return nodes;
	}

	private Rectangle extractSrcRect(Element nodeElement) {
		String[] bounds = split(nodeElement.getAttributeValue("SrcRect"), primarySeparator);
		return this.parseRect(bounds);
	}


	private Texture2D extractTexture(Element nodeElement) {
		String assetPath = nodeElement.getAttributeValue("Texture");
		return this.ContentManager.loadTexture("tilesheets/" + assetPath);
	}


	private String extractID(Element nodeElement) {
		return nodeElement.getAttributeValue("Value");
	}

	private Vector2 extractPosition(Element nodeElement) {
		String[] pos = split(nodeElement.getAttributeValue("Position"), primarySeparator);
		System.out.println(nodeElement.getAttributeValue("Position"));
		return extractVector2(pos);
	}


	private Vector2 extractVector2(String[] pos) {
		NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
		//Float.parseString cannot parse european float values. So using the numberformater instead.
		Number nX;
		Number nY;
		try {
			nX = format.parse(pos[0]);
			nY = format.parse(pos[1]);
		} catch (ParseException e) {
			format = NumberFormat.getInstance(Locale.ENGLISH);
			try {
			nX = format.parse(pos[0]);
			nY = format.parse(pos[1]);
			} catch(ParseException e2) {
				throw new RuntimeException(e2);
			}
		}
		
		System.out.println("x" + nX + " | y" + nY);
		
		return new Vector2(nX.floatValue(),nY.floatValue());
	}

	private int extractBlockSize(Element rootNode) {
		Element blockSizeElem = rootNode.getChild(blockSizeElemName);
		String blockSizeVal = blockSizeElem.getValue();
		return Integer.parseInt(blockSizeVal);
	}

	private Rectangle extractBounds(Element rootNode) {
		Element dimentionElem = rootNode.getChild(dimentionAttrib);
 
		String[] values = split(dimentionElem.getValue(), primarySeparator);
		int width  = Integer.parseInt(values[0]);
		int height = Integer.parseInt(values[1]);
		
		return new Rectangle(0,0,width, height);
	}

	private Map<String, Texture2D> extractTextures(Element rootNode) {
		Map<String, Texture2D> textureMap = new HashMap<String, Texture2D>();
		
		Element textureElement = rootNode.getChild(textureElemName);
		String[] textureAssets = split(textureElement.getValue(), primarySeparator);
		populateMap(textureMap, textureAssets);
		
		return textureMap;
	}

	private void populateMap(Map<String, Texture2D> textureMap,
			String[] textureAssets) {
		
		for (int i = 0; i < textureAssets.length; i++) {
			String asset = textureAssets[i];
			Texture2D texture = ContentManager.loadTexture(textureSubFolder + asset);
			textureMap.put(asset, texture);
		}
	}

	@SuppressWarnings("unchecked")
	private List<TileLayer> extractSortedTileLayers(Element rootNode, Rectangle gridBounds,
			Map<String, Texture2D> usedTextures) {
		List<TileLayer> tileLayers  = new ArrayList<>();	
		
		List<Element> tileLayersElements = rootNode.getChildren(tileLayerElemName);
		for (Element layerElement : tileLayersElements) {
			TileLayer tileLayer = extractTileLayer(layerElement, gridBounds, usedTextures);
			tileLayers.add(tileLayer);
		}
		
		Collections.sort(tileLayers);
		
		return tileLayers;
	}

	private TileLayer extractTileLayer(Element layerElement, Rectangle gridBounds,
			Map<String, Texture2D> usedTextures) {
		
		String depthValue = layerElement.getAttributeValue(layerDepthElemName);
		int depth = Integer.parseInt(depthValue);
		
		List<Tile> tiles = extractTiles(layerElement, usedTextures);		
		Tile[][] tileGrid = extractTileGrid(layerElement,gridBounds, tiles);
		
		return new TileLayer(tileGrid, depth);
	}

	@SuppressWarnings("unchecked")
	private List<Tile> extractTiles(Element layerElement, Map<String, Texture2D> usedTextures) {
		List<Tile> tiles = new ArrayList<>();
		Element tilesElement = layerElement.getChild("Tiles");
		List<Element> tileElements = tilesElement.getChildren("Tile");
		for (Element tileElement : tileElements) {
			Tile tile = extractTile(tileElement, usedTextures);
			tiles.add(tile);
		}
		
		return tiles;
	}

	private Tile extractTile(Element tileElement,
			Map<String, Texture2D> usedTextures) {		
		String    textureAsset = tileElement.getAttributeValue("Texture");
		Texture2D texture = usedTextures.get(textureAsset);
		if(texture == null) 
			throw new NullPointerException("The texture " + textureAsset + " does not exist.");
		
		
		String[]  sorceRectValues = split(tileElement.getValue(),primarySeparator);
		Rectangle rect = parseRect(sorceRectValues);
		
		return new Tile(texture, rect);
	}
	

	private Tile[][] extractTileGrid(Element layerElement, Rectangle gridBounds, List<Tile> tiles) {
		Tile[][] grid = new Tile[gridBounds.Height][gridBounds.Width];
		Element gridElement = layerElement.getChild("TileGrid");
		String[] cellValues = split(gridElement.getValue(), primarySeparator);
		
		populateGridWithValues(grid, cellValues, tiles);
		
		return grid;
	}

	private void populateGridWithValues(Tile[][] grid, String[] cellValues, List<Tile> tiles) {
		int gridHeight = grid.length;
		int gridWidth  = grid[0].length;
		
		int i = 0;
		for(int row = 0; row < gridHeight; row++) {
			for(int column = 0; column < gridWidth; column++) {
				String[] tileDataValue = split(cellValues[i], secondarySeparator);
				int tileIndex = Integer.parseInt(tileDataValue[0]);
				
				if(tileIndex != -1) {
					int tmpAlpha = Integer.parseInt(tileDataValue[1]);
					float realAlpha = (float)tmpAlpha / 255.0f;
					
					Tile tile = new Tile(tiles.get(tileIndex), realAlpha);
					grid[row][column] = tile;
				}
				i++;
			}
		}
	}

	private Rectangle parseRect(String[] rectStrs) {
		int x 		= Integer.parseInt(rectStrs[0]);
		int y 		= Integer.parseInt(rectStrs[1]);
		int width   = Integer.parseInt(rectStrs[2]);
		int height  = Integer.parseInt(rectStrs[3]);
		
		return new Rectangle(x, y, width, height);
	}

	@SuppressWarnings("unchecked")
	private List<CollisionLayer> extractCollisionLayers(Element rootNode, Rectangle gridBounds) {
		List<CollisionLayer> colLayers = new ArrayList<>();
		
		List<Element> colisionLayerElements = rootNode.getChildren(colLayerElemName);	
		for (Element layerElement : colisionLayerElements) {
			CollisionLayer colLayer = extractCollisionLayer(layerElement, gridBounds);
			colLayers.add(colLayer);
		}
				
		return colLayers;		
	}
	
	private CollisionLayer extractCollisionLayer(Element layerElement,
			Rectangle gridBounds) {
			
		boolean[][] collisionGrid = extractCollisionGrid(layerElement,gridBounds);	
		return new CollisionLayer(collisionGrid);
		
	}

	private boolean[][] extractCollisionGrid(Element layerElement,
			Rectangle gridBounds) {
		boolean[][] colGrid = new boolean[gridBounds.Height][gridBounds.Width];
		Element gridElement = layerElement.getChild( "CollisionGrid");
		String[] gridCellVals = split(gridElement.getValue(), primarySeparator);
		
		populateColGrid(colGrid, gridCellVals);
		
		return colGrid;
	}

	private void populateColGrid(boolean[][] grid, String[] gridCellVals) {
		int gridHeight = grid.length;
		int gridWidth  = grid[0].length;		
		
		int i = 0;
		for(int row = 0; row < gridHeight; row++) {
			for(int column = 0; column < gridWidth; column++) {
				boolean cellCollsion = 
						Integer.parseInt(gridCellVals[i]) == 1;				
				
				grid[row][column] = cellCollsion;
				
				i++;
			}
		}
		
	}
}
