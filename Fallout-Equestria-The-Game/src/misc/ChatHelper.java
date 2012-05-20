package misc;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import common.ChatMessage;

import GUI.TextEventArgs;
import GUI.controls.ChatPanel;
import utils.Network;

public class ChatHelper {
	private final Object lock = new Object();
	private final Network network;
	private final ChatPanel panel;
	private final List<ChatMessage> messageBuffer;

	public ChatHelper(ChatPanel chatPanel, Network network) {
		this.panel = chatPanel;
		this.network = network;
		this.messageBuffer = new ArrayList<>();
	}

	public void intiialize() {
		if(this.network.isClient()) {
			panel.addTextSendListener(new IEventListener<TextEventArgs>() {
				@Override
				public void onEvent(Object sender, TextEventArgs e) {
					ChatMessage message = new ChatMessage();
					message.senderID = network.getClient().getID();;
					message.playerName = 0 + ""; //Should be playercharacteristics.getName();
					message.message = e.getText();
					network.getClient().sendTCP(message);
				}	
			});	
		} else {
			panel.addTextSendListener(new IEventListener<TextEventArgs>() {
				@Override
				public void onEvent(Object sender, TextEventArgs e) {
					ChatMessage message = new ChatMessage();
					message.senderID = 0;
					message.playerName = 0 + ""; //Should be playercharacteristics.getName();
					message.message = e.getText();
					network.getServer().sendToAllTCP(message);
				}	
			});	
		}
		
		this.network.addListener(new Listener() {
			@Override
			public void received(Connection arg0, Object arg1) {
				if(arg1 instanceof ChatMessage) {
					synchronized(lock){
						ChatMessage message = (ChatMessage)arg1;
						messageBuffer.add(message);
					}
				}
			}
		});

		
	}

	public void update() {
		synchronized (lock) {
			for (ChatMessage message : this.messageBuffer) {
				this.panel.addText(message.playerName + " " + message.message);
				if(this.network.isServer()) {
					this.network.getServer().sendToAllExceptTCP(message.senderID, message);
				}
			}
			this.messageBuffer.clear();
		}
	}
}