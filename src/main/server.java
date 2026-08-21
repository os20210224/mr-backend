package main;

import thread.lobby;

public class server {
	
	private lobby lobby;
	
	public server () {
		lobby = new lobby();
		lobby.start();
	}
	
	public static void main(String[] args) {
		new server();
	}
	
}
