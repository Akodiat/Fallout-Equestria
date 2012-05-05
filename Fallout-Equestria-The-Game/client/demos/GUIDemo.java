package demos;


import math.Point2;
import misc.IEventListener;
import GUI.*;
import GUI.controls.*;
import GUI.graphics.*;

import content.ContentManager;
import graphics.*;
import utils.*;

public class GUIDemo extends Demo {
	private static Rectangle screenDim = new Rectangle(0,0,1366,768);

	private SpriteBatch spriteBatch;
	private Mouse mouse;
	private Keyboard keyboard;
	private ContentManager ContentManager;
	
	private GUIRenderingContext context;
	private GUIFocusManager manager;
	private ScrollPanel panel;
	
	public static void main(String[] args) {
		new GUIDemo().start();
	}
	
	public GUIDemo() {
		super(screenDim, 60);
	}

	@Override
	public void update(GameTime time) {
		this.mouse.poll(screenDim);
		this.keyboard.poll();
		panel.checkMouseInput(new Point2(0,0), mouse);
		panel.checkKeyboardInput(keyboard);
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		panel.render(context, time);
	}

	@Override
	protected void initialize() {
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.ContentManager = new ContentManager("resources");
		
		LookAndFeel lookAndFeel = ContentManager.load("gui.tdict", LookAndFeel.class);
		lookAndFeel.setDefaultFont(ContentManager.loadFont("arialb20.xml"));
		
		context = new GUIRenderingContext(spriteBatch, lookAndFeel, ContentManager.loadShaderEffect("GrayScale.effect"));
		
		panel = new ScrollPanel();
		panel.setBounds(new Rectangle(0,0,1366,768));
		

		ToggleButton falloutButton0 = new ToggleButton();
		falloutButton0.setBounds(new Rectangle(600,400,256,256));
		falloutButton0.setText("Click Me!");
		falloutButton0.setFgColor(new Color(200,50,200,255));
		falloutButton0.setBgColor(new Color(200,50,200,255));
		
		panel.addChild(falloutButton0);
		
		Label buttonLabel = new Label();
		buttonLabel.setBounds(800,345,128,50);
		buttonLabel.setFgColor(Color.Orange);
		buttonLabel.setText("Disabled \nbutton over here!");
		panel.addChild(buttonLabel);
		


		Button disabledButton = new Button();
		disabledButton.setBounds(new Rectangle(800,400,128,128));
		disabledButton.setText("Click Me!");
		disabledButton.setFgColor(new Color(200,50,200,255));
		disabledButton.setEnabled(false);
		panel.addChild(disabledButton);

		
		Label textFieldLabel = new Label();
		textFieldLabel.setBounds(new Rectangle(200,200,250,23));
		textFieldLabel.setFgColor(Color.Orange);
		textFieldLabel.setText("Label over a textfield!");
		panel.addChild(textFieldLabel);
		

		final Textfield field = new Textfield();
		field.setBounds(new Rectangle(200,225,250,23));
		field.setText("Testing...");
		field.setFont(ContentManager.loadFont("Andale Mono20.xml"));
		field.setFgColor(Color.Goldenrod);
		field.setMaxLength(25);
		panel.addChild(field);
		
		final Label sliderLabel = new Label();
		sliderLabel.setBounds(new Rectangle(200,400,250, 50));
		sliderLabel.setFgColor(Color.Orange);
		sliderLabel.setText("Label over a slider that \ncontrols color!");
		panel.addChild(sliderLabel);
		
		
		Button sliderButton = new Button();
		sliderButton.setBounds(new Rectangle(0,0,10,35));
		sliderButton.setFgColor(Color.Purple);
		
	
		Slider slider = new Slider();
		slider.setBounds(new Rectangle(200,455,250,30));
		slider.setFgColor(Color.Red);
		slider.setScrollMax(255);
		slider.setHorizontal(true);
		slider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				sliderLabel.setFgColor(new Color(e.getNewValue(),0,150,255));
			}
		});
		
		panel.addChild(slider);		
		
		Label scrollBarLabel = new Label();
		scrollBarLabel.setBounds(new Rectangle(1100,0,200,50));
		scrollBarLabel.setFgColor(Color.Orange);
		scrollBarLabel.setText("Scrollbars! \nThey can scroll :O");
		panel.addChild(scrollBarLabel);				
		
		final ScrollBar vBar = new ScrollBar();
		vBar.setBounds(1300,50,20,200);
		vBar.setVertical(true);
		vBar.setScrollMax(100);
		this.panel.addChild(vBar);
		
						
		final ScrollBar hBar = new ScrollBar();
		hBar.setBounds(1100,250,200,20);
		hBar.setHorizontal(true);
		hBar.setScrollMax(100);
		this.panel.addChild(hBar);
		
		
		final ImageBox imageBox = new ImageBox();
		imageBox.setBounds(1100,50,200,200);
		imageBox.setImage(ContentManager.loadTexture("tilesheets/Stars_Wallpaper_by_Colliemom.png"));
		imageBox.setImageSrcRect(new Rectangle(0,0,200,200));
		this.panel.addChild(imageBox);
		
		IEventListener<ScrollEventArgs> scroll = new IEventListener<ScrollEventArgs>() {			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				Rectangle maxDim = imageBox.getImage().getBounds();
				int xStep = (maxDim.Width - 200) / hBar.getScrollMax();
				int yStep = (maxDim.Height - 200) / vBar.getScrollMax();
				Rectangle viewdRect = new Rectangle(xStep * hBar.getScrollValue(), 
													yStep * vBar.getScrollValue(),
													200, 200);
				imageBox.setImageSrcRect(viewdRect);
			}
		};
		vBar.addScrollListener(scroll);
		hBar.addScrollListener(scroll);
		
		
		final ListBox<String> listBox = new ListBox<>();
		listBox.setBounds(470,50,150,250);
		listBox.setFont(ContentManager.loadFont("Andale Mono20.xml"));
		listBox.setBgColor(new Color(220,220,220,100));
		listBox.setFgColor(Color.Black);
		this.panel.addChild(listBox);
		
		


		Button addItemButton = new Button();
		addItemButton.setBounds(new Rectangle(200,40,158,158));
		addItemButton.setText("Add item!");
		addItemButton.setImage(ContentManager.loadTexture("GUI/Fallout-2-icon.png"));
		addItemButton.setFont(ContentManager.loadFont("Pericles20bi.xml"));
		panel.addChild(addItemButton);
		
		addItemButton.addMouseClicked(new IEventListener<MouseEventArgs>() {
			
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
				listBox.addItem(field.getText());				
			}
		});
		
		manager = new GUIFocusManager(panel);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setBounds(650,50,250,250);
		scrollPanel.setBgColor(new Color(200,200,200,250));
		this.panel.addChild(scrollPanel);
		
		
		Button testButton0 = new Button();
		testButton0.setBounds(50,0, 256, 256);
		testButton0.setFgColor(Color.Purple);
		scrollPanel.addChild(testButton0);			
		
		Button testButton1 = new Button();
		testButton1.setBounds(50,200, 256, 256);
		testButton1.setFgColor(Color.Purple);
		scrollPanel.addChild(testButton1);	
		
		TextArea area = new TextArea();
		area.setBounds(100,500,300,150);
		area.setFont(ContentManager.loadFont("Courier New20.xml"));
		area.setText("This is a textarea alot of text that can be scrolled through in needed the scrollbar is always active but we rly anyways this can be scrolled laalalallalalallalalalallalalallalalalallalalalallalalallalalala");
		
		this.panel.addChild(area);
		
		Label chatPanelLabel = new Label();
		chatPanelLabel.setBounds(1000,350,250,50);
		chatPanelLabel.setText("This is a chatframe");
		chatPanelLabel.setFgColor(Color.Red);
		this.panel.addChild(chatPanelLabel);
		
		ChatPanel chatPanel = new ChatPanel();
		chatPanel.setBounds(1000,400,250,250);
		chatPanel.setFont(ContentManager.loadFont("Courier New20.xml"));
		this.panel.addChild(chatPanel);
		
		final ImageBox imageBox1 = new ImageBox();
		imageBox1.setRenderOrder(-1);
		imageBox1.setBounds(1000,400,300,300);
		imageBox1.setImage(ContentManager.loadTexture("tilesheets/Stars_Wallpaper_by_Colliemom.png"));
		imageBox1.setImageSrcRect(new Rectangle(0,0,200,200));
		this.panel.addChild(imageBox1);
		
		
		Texture2D minus = ContentManager.loadTexture("GUI/minus.png");
		Texture2D plus = ContentManager.loadTexture("GUI/plus.png");
		
		
		Spinner spinner = new Spinner(20,0,1,10,plus,minus);
		spinner.setBounds(20, 20, 160, 40);
		spinner.setFont(ContentManager.loadFont("arialb20.xml"));
		this.panel.addChild(spinner);

		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setBounds(20,260, 200, 23);
		comboBox.setBgColor(new Color(0,0,0,0));
		comboBox.setFont(ContentManager.loadFont("Pericles20bi.xml"));
		comboBox.addItem("Hej");
		comboBox.addItem("Hej1");
		comboBox.addItem("Hej2");
		comboBox.addItem("Hej3");
		comboBox.addItem("Hej4");
		comboBox.addItem("Hej5");
		comboBox.addItem("Hej6");
		comboBox.addItem("Hej7");
		comboBox.addItem("Hej8");
		comboBox.addItem("Hej9");
		
		comboBox.addSelectedChangedListener(new IEventListener<ItemEventArgs<String>>() {
			
			@Override
			public void onEvent(Object sender, ItemEventArgs<String> e) {
				System.out.println(e.getItem());
			}
		});
	
		this.panel.addChild(comboBox);
		
	}

}
