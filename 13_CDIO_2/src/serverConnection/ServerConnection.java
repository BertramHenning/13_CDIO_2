package serverConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerConnection{
  
    public static void main(String args[]){
    	String hostName = "127.0.0.1";
    	int portNumber = Integer.parseInt("8000");
    	Socket sock;
    	PrintWriter out = null;
    	BufferedReader in = null;
    	
    	//Connects a socket to the weight
    	try {
    	   	sock = new Socket(hostName, portNumber);
            out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	    
    	String fromServer;
    	    
    	try {
    		while(true){
    		out.println("");
    		fromServer = in.readLine();
    		System.out.println("Server: " + fromServer);
    		
    		//Gets user ID
    		out.println("RM20 8 usernr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				if(fromServer.equals("12")){
					out.println("P111 Anders And?");
					fromServer = in.readLine();
					fromServer = in.readLine();
					break;
				} else{
					out.println("RM20 8 Prøv_igen");
				}
			}
			
			//Gets batch nr
			out.println("RM20 8 batchnr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				if(fromServer.equals("1234")){
					out.println("P111 Salt?");
					fromServer = in.readLine();
					fromServer = in.readLine();
					break;
				} else{
					out.println("RM20 8 Prøv_igen");
				}
			}
			
			//Instructions for user
			out.println("P111 Tøm vægten");
			fromServer = in.readLine();
			fromServer = in.readLine();
			
			out.println("T");
			fromServer = in.readLine();
			
			out.println("P111 Placer tara");
			fromServer = in.readLine();
			fromServer = in.readLine();
			
			out.println("T");
			fromServer = in.readLine();
			double tara = Double.parseDouble(fromServer.split(" ")[2]);
			
			System.out.println("tara: " + tara);
			
			out.println("P111 Placer netto");
			fromServer = in.readLine();
			fromServer = in.readLine();
			
			out.println("S");
			fromServer = in.readLine();
			double netto = Double.parseDouble(fromServer.split(" ")[2]);
			
			System.out.println("netto: " + netto);

			out.println("T");
			fromServer = in.readLine();
			
			out.println("P111 Fjern brutto");
			fromServer = in.readLine();
			fromServer = in.readLine();
			
			out.println("S");
			fromServer = in.readLine();
			double brutto = Double.parseDouble(fromServer.split(" ")[2]);
			
			System.out.println("brutto: " + brutto);
			
			//Check if weight was correctly used
			if (brutto + tara + netto == 0){
				out.println("P111 OK");
			} else {
				out.println("P111 Kasseret");
			}
			fromServer = in.readLine();
			fromServer = in.readLine();
			
			out.println("T");
			fromServer = in.readLine();
			
    		}
		} catch (Exception e) {
			System.out.println("Vægt slukket");
		}
    	
    	
    	
    }
}