package gameOfLife;

import java.util.Random;
import math.Point2;
import math.Vector2;
import com.google.common.collect.ImmutableSet;
import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;
import entityFramework.GroupedEntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import graphics.Color;

public class GameOfLifeLogicSystem extends GroupedEntitySystem {
	
	private ContentManager contentManager;
	private float scale;
	
	public GameOfLifeLogicSystem(IEntityWorld world, ContentManager contentManager, int gridBlockSize, Point2 gridSize, String cellArchetype, String group) {
		super(world, group, RenderingComp.class, TransformationComp.class);
		this.cellGrid = new boolean[gridSize.Y][gridSize.X];		
		this.gridBlockSize = gridBlockSize;
		this.cellArchetype = cellArchetype;
		this.contentManager = contentManager;
		this.scale = (float)(64 / 500.0f);
		this.gridBlockSize = (int)(0.2 * this.gridBlockSize);
		
		
	}
	
	private int gridBlockSize;
	private final String cellArchetype;
	private boolean[][] cellGrid;
	
	
	@Override
	public void initialize() {
		IEntityArchetype archetype = contentManager.loadArchetype(cellArchetype);
		
		for (int row = 0; row < this.cellGrid.length; row++) {
			for (int column = 0; column < this.cellGrid[0].length; column++) {
				IEntity entity = this.getEntityManager().createEntity(archetype);
				entity.setLabel(row + "|" + column);
				
				entity.addToGroup(getGroup());
				this.positionAtCell(entity, row, column);	
				entity.refresh();
			}
		}
	}

	private void positionAtCell(IEntity entity, int row, int column) {
		float x = (column * gridBlockSize + gridBlockSize / 2);
		float y = (row * gridBlockSize + gridBlockSize / 2);
		
		
		TransformationComp transform = entity.getComponent(TransformationComp.class);
		transform.setPosition(x,y); 
		transform.setScale(new Vector2(scale));
	}
	
	private void clearGrid() {
		for (int row = 0; row < this.cellGrid.length; row++) {
			for (int column = 0; column < this.cellGrid[0].length; column++) {
				this.cellGrid[row][column] = false;		
			}
		}
	}

	public void seedNewLife(long seed) {
		this.clearGrid();
		Random random = new Random(seed);
		int num = this.cellGrid.length * this.cellGrid[0].length / 3;
		for (int i = 0; i < num; i++) {
			int row = random.nextInt(this.cellGrid.length);
			int column = random.nextInt(this.cellGrid[0].length);
			this.cellGrid[row][column] = true;
		}	
	}
		
	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		boolean[][] nextStep = applyGOLRules();		
		for (int row = 0; row < nextStep.length; row++) {
			for (int column = 0; column < nextStep[0].length; column++) {
				IEntity entity = this.getEntityManager().getEntity(row + "|" + column);
				RenderingComp comp = entity.getComponent(RenderingComp.class);
				if(nextStep[row][column]) {
					if(this.countNeighbours(row, column) == 2) {
						comp.setColor(Color.Green);
					} else {
						comp.setColor(Color.Blue);
					}
				} else {
					comp.setColor(new Color(0f,0f,0f,0f));
				}
			}
		}
		this.cellGrid = nextStep;
	}

	private boolean[][] applyGOLRules() {
		boolean[][] grid = new boolean[this.cellGrid.length][this.cellGrid[0].length];
		
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[0].length; column++) {
				int neighboursCount = this.countNeighbours(row,column);
				boolean result = updateCell(neighboursCount, row, column);
				grid[row][column] = result;
			}
		}
		return grid;	
	}

	private boolean updateCell(int count, int row, int column) {
		boolean alive = this.cellGrid[row][column];
		if(alive) {
			if(count == 2 || count == 3 ) {
				return true;
			} else {
				return false;
			}
		} else {
			if(count == 3 ) {
				return true;
			} else {
				return false;
			}
		}
	}

	private int countNeighbours(int row, int column) {
		int count = 0;
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = column - 1; c <= column + 1; c++) {
				if(r == row && c == column) 
					continue;
				
				if(getGridValue(r, c)) {
					count++;
				}
			}
		}
		return count;
	}
	
	private boolean getGridValue(int row, int column) {
		//The corner cases
		if(row == -1) {
			row = this.cellGrid.length - 1;
		} else if(row == this.cellGrid.length) {
			row = 0;
		}
		
		if(column == -1) {
			column = this.cellGrid[0].length - 1;
		} else if(column == this.cellGrid[0].length) {
			column = 0;
		}
		
		return this.cellGrid[row][column];
	}

}
