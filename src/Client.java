import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread{
	
	Scanner sc;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.init();
	}
	
	public Client() {
		sc = new Scanner(System.in);
	}
		
	public void init() {
		
		Socket client = null;
		BufferedReader reader= null;
		BufferedWriter writer = null;
		
		// set server
		final String server = "192.168.5.2";
		// set port
		final int port = 59999;		
		
	    try {
			client = new Socket(server,port);
			writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e1) {
			System.out.println("Socket konnte nicht erstellt werden");
			e1.printStackTrace();
		}
	    
	    while(true) {
	    	try {
	    		System.out.println("Nachricht: ");
	    		String input = this.sc.nextLine().trim();
	    		
	    		// Bei Eingabe von "exit" schließen
	    		if(input.toLowerCase().equals("exit")) {
	    			sc.close();
	    			try {
	    				client.close();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}	    			
	    			break;
	    		}
	    		
	    		// Buffer füllen und senden
	    		writer.write(input);
	    		writer.newLine();
	    		writer.flush();	      	      
	    		
	    		System.out.println(reader.readLine());
	    		
		    } catch (IOException e) {
		    	System.out.println("Client crashed");
		    	e.printStackTrace();
		    }
	    }
	    
	    
	    	  	
	}

}
