package uinttests;

import static org.junit.Assert.*;
import org.junit.Test;

import entityFramework.ResizableGrid;


public class ResizableGridTest {

	@Test 
	public void testGetAndSet() {
		ResizableGrid<Integer> integerGrid = new entityFramework.ResizableGrid<Integer>(10, 2);
		integerGrid.set(2, 1, 20);
		
		Integer actual = integerGrid.get(2, 1);
		assertEquals(new Integer(20), actual);
		
		assertNull(integerGrid.get(8, 1));
	}
	
	@Test
	public void testLargeGrow() {
		ResizableGrid<Integer> integerGrid = new ResizableGrid<Integer>();
		
		integerGrid.set(200, 40, 32);
		
		assertTrue(integerGrid.gridHeight() == 201);
		assertTrue(integerGrid.gridWidth() == 41);
	}
	@Test
	public void testIncrementalGrow() {
		ResizableGrid<Integer> integerGrid = new ResizableGrid<Integer>();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				integerGrid.set(i, j, i);
			}
		}
		
		int width = integerGrid.gridWidth();
		assertEquals(width, 20);
		int height = integerGrid.gridHeight();
		assertTrue(height > 20);
	}
	
	@Test 
	public void testEnsureLargeEnough() {
		ResizableGrid<Integer> integerGrid = new ResizableGrid<Integer>();
		integerGrid.ensureLargeEnough(199, 39);
		
		assertTrue(integerGrid.gridHeight() == 200);
		assertTrue(integerGrid.gridWidth() == 40);
	}

}
