package client;

import java.io.IOException;
import java.net.InetAddress;

import math.Point2;
import misc.IEventListener;
import graphics.Color;
import graphics.SpriteBatch;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;

import GUI.GUIFocusManager;
import GUI.controls.ChatPanel;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import GUI.TextEventArgs;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import common.ChatMessage;
import common.Network;

import demos.Demo;

public class ChatClient extends Demo {
	private static Rectangle sr = new Rectangle(0,0, 400,400);
	private ChatPanel panel;
	
	private GUIRenderingContext context;
	@SuppressWarnings("unused")
	private GUIFocusManager manager;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	private Keyboard keyboard;
	
	private String name;
	
	public static void main(String[] args) {
		new ChatClient().start();
	}
	
	public ChatClient() {
		super(sr, 60);
		
	}
	
	@Override
	public void update(GameTime time) {
		this.mouse.poll(sr);
		this.keyboard.poll();
		panel.checkMouseInput(new Point2(0,0), mouse);
		panel.checkKeyboardInput(this.keyboard);
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		panel.render(context, time);
	}

	@Override
	protected void initialize() {
		this.spriteBatch = new SpriteBatch(sr);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		LookAndFeel feel = ContentManager.load("gui.tdict", LookAndFeel.class);
		context = new GUIRenderingContext(spriteBatch, feel, ContentManager.loadShaderEffect("GrayScale.effect"));
		
		
		panel = new ChatPanel();
		panel.setBounds(0,0,400,400);
		panel.setBgColor(Color.Blue);
		panel.setFont(ContentManager.loadFont("arialb20.xml"));
		manager = new GUIFocusManager(panel);
		
		
		final Client client = new Client();
		client.start();
		Network.registerClasses(client);
		
		try {
			InetAddress address = client.discoverHost(54777, 5000);
			System.out.println(address);
			client.connect(5000, address, 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.addListener(new Listener() {
			@Override
			public void received(Connection arg0, Object arg1) {
				if(arg1 instanceof String) {
					name = arg1.toString();
				} else if(arg1 instanceof ChatMessage) {
					ChatMessage message = (ChatMessage)arg1;
					panel.addText(message.playerName + "  " + message.message);
				}
			}
		});
		
		panel.addTextSendListener(new IEventListener<TextEventArgs>() {
			@Override
			public void onEvent(Object sender, TextEventArgs e) {
				ChatMessage message = new ChatMessage();
				message.playerName = name;
				message.message = e.getText();
				client.sendTCP(message);
			}	
		});
		
	}
	
	
}
