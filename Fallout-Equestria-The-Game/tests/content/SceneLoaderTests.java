package content;

import static org.junit.Assert.*;

import gameMap.Scene;
import gameMap.Tile;
import gameMap.TileLayer;

import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import builders.ContentManagerBuilder;

import utils.Rectangle;

public class SceneLoaderTests {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Display.create();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Display.destroy();
	}

	private Scene loadScene(String sceneAsset) throws Exception {
		InputStream stream = this.getClass().getResourceAsStream(sceneAsset);
		SceneLoader loader = new SceneLoader(ContentManagerBuilder.buildStandardContentManager("resources"), "scenes");
		return loader.loadContent(stream);
	}
	
	
	@Test
	public void testIfCanLoadValidMap() throws Exception {
		Scene scene = loadScene("TestScene.xml");
		
		assertNotNull(scene);
	}
	
	@Test
	public void testIfLoadedSceneHasCorrectDimentions() throws Exception {
		Scene scene = loadScene("TestScene.xml");	
		assertEquals(128, scene.getBlockSize());
		Rectangle gridBounds = scene.getGridBounds();
		assertEquals(19, gridBounds.Width);
		assertEquals(7, gridBounds.Height);
	}
	
	@Test(expected = Exception.class)
	public void testIfCrashesOnInvalidScene() throws Exception {
		loadScene("BrokenScene.xml");
		fail("It should not get here!");
	}

	@Test
	public void testIfTileLayerIsCorrect() throws Exception {
		Scene scene = loadScene("TestScene.xml");
		TileLayer layer = scene.getTileLayers().get(0);
		assertEquals(0, layer.getDepth());
	
		Tile[][] tiles = layer.getTileGrid();
		
		//These values are from the Simple map
		Rectangle sorceRectA = tiles[0][1].getSourceRect();
		Rectangle sorceRectB = tiles[0][2].getSourceRect();
	
		assertNotSame(sorceRectA, sorceRectB);
	}


	@Test
	public void testIfCorectFolderAndClassManaged() throws Exception {
		SceneLoader loader = new SceneLoader(new ContentManager(""), "scenes");
		
		assertSame(Scene.class, loader.getClassAbleToLoad());
		assertSame("scenes", loader.getFolder());
	}
	
	
}
