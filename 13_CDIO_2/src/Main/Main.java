package Main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader ServerIn;
		DataOutputStream ServerOut;
		String out, in;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			Socket socket = new Socket("localhost", Integer.parseInt(args[0]));
			
			if (socket.isConnected())
				System.out.println("Connected");
			ServerOut = new DataOutputStream(socket.getOutputStream());
			ServerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = inFromUser.readLine();
			
			ServerOut.writeBytes(out);
			socket.close();			
			
		} catch (UnknownHostException e) {
			System.out.println("Unable to connect to server with: \n"+e.toString());
		} catch (IOException e) {
			System.out.println("Unable to connect to server with: \n"+e.toString());
		}
		
	}
}