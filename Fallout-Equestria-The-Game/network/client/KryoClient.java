package client;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryonet.Client;

import common.Network;
import components.InputComp;

public class KryoClient {
	
	public static void main(String[] args) throws IOException{
		Client client = new Client();
		client.start();
		client.connect(5000, "129.16.180.59", 54555, 54777);
		
		Network.registerClasses(client);
		
		try {
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputComp inpComp = new InputComp();
		while(true){
			inpComp.setForwardButtonPressed(Keyboard.isKeyDown(Keyboard.KEY_W));
			client.sendUDP(inpComp);
			Display.update();
		}

	}
}
