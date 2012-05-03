package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.ChatClient;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import common.ChatMessage;
import common.Network;

public class KryoChatServer{
	private List<Connection> clients = new ArrayList<>();
	
	public static void main(String[] args) {
		KryoChatServer server = new KryoChatServer();
		server.initialize();
	}

	protected void initialize() {
		Server server = new Server();
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Network.registerClasses(server);
		
		server.addListener(new Listener() {
			@Override
			public void connected(Connection arg0) {
				clients.add(arg0);
				arg0.sendTCP("Player" + arg0.getID());
				System.out.println("Player" + arg0.getID());
			}
			
			@Override
			public void disconnected(Connection arg0) {
				if(clients.contains(arg0)) {
					clients.remove(arg0);
				}
			}
			
			@Override
			public void received(Connection arg0, Object arg1) {
				if(arg1 instanceof ChatMessage) {
					ChatMessage message = (ChatMessage)arg1;
					for (Connection client : clients) {
						if(client != arg0) {
							client.sendTCP(message);
						}
					}
				}
			}
		});
	}
	
}