package Main;
import java.io.*;
import java.net.*;
public class ServerConnection{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
	static int port;
    String message;
	static String addr;
    ServerConnection(){}
    void run()
    {
        try{
            //1. creating a socket to connect to the server
            requestSocket = new Socket(addr, port);
            System.out.println("Connected to "+addr+" on port "+port);
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //3: Communicating with the server
            do{
                try{
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
                    sendMessage("Hi my server");
                    message = "bye";
                    sendMessage(message);
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!message.equals("Q"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String args[]){
    	try{
    	port = Integer.parseInt(args[0]);
    	}catch(ArrayIndexOutOfBoundsException AIOB){
    		
    	}
    	addr = "127.0.0.1";
        ServerConnection client = new ServerConnection();
        client.run();
    }
}