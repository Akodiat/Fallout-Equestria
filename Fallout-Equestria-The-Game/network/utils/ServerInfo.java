package utils;

import java.util.List;

import com.esotericsoftware.kryonet.Client;

public class ServerInfo {
	private String serverName;
	private List<Client> players;
	
	public ServerInfo(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public List<Client> getPlayers() {
		return players;
	}

	public void setPlayers(List<Client> players) {
		this.players = players;
	}
}
