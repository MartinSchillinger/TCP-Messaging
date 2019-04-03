import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server extends Thread {
	
	ServerSocket serverSocket;
	
	int numWorkers = 0;
	
	private HashMap<Integer, ServerWorker> clientSockets;

	public static void main(String argv[]) {
		Server server = new Server();
		server.init();
		
	}

	public Server() {
		
		//Serversocket erstellen
		try {
			this.serverSocket = new ServerSocket(59999);
			System.out.println("ServerSocket erstellt");
		} catch (IOException e) {
			System.out.println("Serversocket konnte nicht erstellt werden");
			e.printStackTrace();
		}
	}
	
	private void init() {		

		Socket connectionSocket = null;
		clientSockets = new HashMap<>();

		System.out.println("Server gestartet");
		
		// Bei Connection einen neuen Thread starten
		while (true) {
			
			// Clientsocket erstellen
			try {
				connectionSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Connectionsocket konnte nicht erstellt werden");
				e.printStackTrace();
			}
			
			// Serverworker speichern und starten
			ServerWorker worker = new ServerWorker(connectionSocket, numWorkers);			
			this.clientSockets.put(numWorkers, worker);			
			this.clientSockets.get(numWorkers).start();			
			this.numWorkers++;
		}

	}
}
