import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerWorker extends Thread{
	
	Socket sock;
	int id;
	
	public ServerWorker(Socket sock, int id) {
		this.sock = sock;
		this.id = id;
	}
	
	public void run() {
		System.out.println("Serverworker " + this.id + " mit " + sock.getInetAddress() + ":" + sock.getPort() + " verbunden.");
		
		//Reader vorbereiten
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try {
			while (true) {
				
				String clientMessage = bReader.readLine();
				
				// bei Clientseitigem Abbruch den Thread schließen
				if(clientMessage == null) {
					System.out.println("Client" + sock.getInetAddress() + ":" + sock.getPort() + " hat die Verbindung beendet");
					this.sock.close();
					currentThread().interrupt();
					break;
				}else {
					//Statistik updaten
					StatisticData.update(clientMessage);
					System.out.println("Empfangen: " + clientMessage + " von: " + sock.getInetAddress() + ":" + sock.getPort());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
					
					//Antwort senden
					writer.write("Länge: " + clientMessage.length() + ", (min: " + StatisticData.getMinLength() + ", max: " + 
					StatisticData.getMaxLength() + ") - Wortzahl: " + StatisticData.getLastWordCount() + " (min: " + StatisticData.getMinWords() +
					", max: " + StatisticData.getMaxWords() + ")");
					writer.newLine();
					writer.flush();	 
				}
				
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Empfangen der Nachricht");
			e.printStackTrace();
		}
	}

}
