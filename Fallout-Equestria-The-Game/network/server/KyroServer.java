package server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import common.Network;
import components.InputComp;

public class KyroServer {
	
	public static void main(String[] args) throws IOException{
		Server server = new Server();
		server.start();
		server.bind(54555, 54777);
		
		Network.registerClasses(server);
		
		
		server.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof InputComp) {
			         InputComp request = (InputComp)object;
			         System.out.println("W: "+request.isForwardButtonPressed());
			      }
			   }
			});
	}
	
	

}
