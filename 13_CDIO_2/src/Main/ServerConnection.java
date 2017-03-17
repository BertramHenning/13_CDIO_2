package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerConnection{
  
    public static void main(String args[]){
    	String hostName = "127.0.0.1";
    	int portNumber = Integer.parseInt("8000");
    	Socket sock;
    	PrintWriter out = null;
    	BufferedReader in = null;
    	    
    	try {
    	   	sock = new Socket(hostName, portNumber);
            out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	    
    	String fromServer, fromUser = null;
    	    
    	try {
    		out.println("");
    		fromServer = in.readLine();
    		System.out.println("Server: " + fromServer);
    		
    		out.println("RM20 8 usernr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				if(fromServer.equals("12")){
					out.println("P111 Anders?");
					fromServer = in.readLine();
					break;
				} else{
					out.println("RM20 8 Prøv_igen");
				}
			}
			
			out.println("RM20 8 batchnr?");
			while ((fromServer = in.readLine()) != null) {
			
				System.out.println("Server: " + fromServer);
				
				fromServer = in.readLine();
				System.out.println("server: " + fromServer);
				if(fromServer.equals("1234")){
					out.println("P111 Anders And?");
					fromServer = in.readLine();
					break;
				} else{
					out.println("RM20 8 Prøv_igen");
				}
			}
			
			out.println("P111 Tøm vægten");
			fromServer = in.readLine();
			
			out.println("T");
			fromServer = in.readLine();
			
			out.println("P111 Placer tara");
			fromServer = in.readLine();
			
			System.out.println("yay");
			
			
			while ((fromServer = in.readLine()) != null) {
				
				System.out.println("Server: " + fromServer);
			    if (fromServer.equals("Bye.")){
			        break;
			    }
			    in.readLine();
			    out.println(fromUser);
			    
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}