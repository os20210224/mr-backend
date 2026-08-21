package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class lobby extends Thread {
	
	private ServerSocket serverSoket;
	private int port = 7259;
	ExecutorService threadPool = Executors.newFixedThreadPool(10);

	public lobby() {
		try {
			serverSoket = new ServerSocket(port);
		} catch (IOException ex) {
			System.out.println("> Socket initialization error: " + ex.getMessage() + "\n");
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!serverSoket.isClosed()) {
				System.out.println("> awaiting a connection request\n");
				Socket clientSocket = serverSoket.accept();
				// TODO print neki klijent id po konkeicji
				threadPool.execute(new clientHandler(clientSocket));
			}
		} catch(IOException e) {
			System.out.println("> lobby exception: " + e.getMessage());
			if (e.getMessage().equals("Socket closed")) {
				return;
			}
		}
	}
	
}
