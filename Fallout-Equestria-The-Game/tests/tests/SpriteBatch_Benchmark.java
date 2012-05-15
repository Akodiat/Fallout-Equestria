package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.SpriteBatch.SortMode;

import math.Matrix4;
import math.Vector2;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import utils.Clock;
import utils.GameTime;
import utils.Rectangle;
import content.ContentManager;

public class SpriteBatch_Benchmark {
	
	private final Rectangle screenDim;
	private final Clock clock;
	private int fps;
	protected ContentManager ContentManager;
	
	private SpriteBatch spriteBatch;
	private Sprite[] sprites = new Sprite[4048];
	
	
	private class Sprite {
		public Vector2 pos;
		public Color color;
		public Texture2D texture;
	}
	
	public Clock getClock() {
		return this.clock;
	}
	
	
	public static void main(String[] args) {
		new SpriteBatch_Benchmark(new Rectangle(0,0,800,600), 60).start();		
	}
	
	public SpriteBatch_Benchmark(Rectangle screenDim, int fps) {
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
		this.ContentManager = new ContentManager("resources");
	}	
	
	public void start() {
			try {		
				Display.setDisplayMode(new DisplayMode(screenDim.Width, screenDim.Height));
				Display.create();
				this.initialize();
				
				while(!Display.isCloseRequested()) {
					this.gameLoop();
					Display.update();
					Display.sync(fps);
				}	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Display.destroy();
				end();
			}
	}
	
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);
	}

	public void update(GameTime time) {
		Display.setTitle("Elapsed ms: " + time.getElapsedTime().getTotalMiliSeconds());
		
	}
	
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, Matrix4.Identity, null, true, SortMode.Depth);
		for (Sprite sprite : this.sprites) {
			this.spriteBatch.draw(sprite.texture, sprite.pos, sprite.color);
		}		
		this.spriteBatch.end();
		
	}
	
	protected  void initialize() {
		this.spriteBatch = new SpriteBatch(this.screenDim);
		
		System.out.println(GL11.glGetInteger(GL12.GL_MAX_ELEMENTS_VERTICES));
		
		List<Texture2D> texture = new ArrayList<>();
		texture.add(this.ContentManager.loadTexture("HEJHEJ.png"));
		texture.add(this.ContentManager.loadTexture("shadow.png"));
		texture.add(this.ContentManager.loadTexture("house.png"));
		texture.add(this.ContentManager.loadTexture("barrels.png"));
		texture.add(this.ContentManager.loadTexture("GUI/button.png"));
		
		Random rand = new Random();
		for (int i = 0; i < this.sprites.length; i++) {
			Sprite sprite = new Sprite();
			sprite.pos = new Vector2(rand.nextInt(this.screenDim.Width), rand.nextInt(this.screenDim.Height));
			sprite.texture = texture.get(rand.nextInt(texture.size()));
			sprite.color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
			this.sprites[i] = sprite;
		}
	}
	
}
