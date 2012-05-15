package utils;

import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;

public class ServerLoader {

	private List<InetAddress> addresses;

	public ServerLoader(Client client) {
		this.initialize(client);
	}

	public void initialize(Client client) {
		client.start();
		Network.registerClasses(client);

		this.addresses = client.discoverHosts(54777, 10);
	}

	public List<InetAddress> getAddresses() {
		return addresses;
	}
}
