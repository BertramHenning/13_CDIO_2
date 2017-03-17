package controller;

import java.util.regex.Pattern;

//import org.omg.Messaging.SyncScopeHelper;


import com.sun.xml.internal.ws.resources.SenderMessages;

import socket.ISocketController;
import socket.ISocketObserver;
import socket.SocketInMessage;
import socket.SocketOutMessage;
import weight.IWeightInterfaceController;
import weight.IWeightInterfaceObserver;
import weight.KeyPress;
/**
 * MainController - integrating input from socket and ui. Implements ISocketObserver and IUIObserver to handle this.
 * @author Christian Budtz
 * @version 0.1 2017-01-24
 *
 */
public class MainController implements IMainController, ISocketObserver, IWeightInterfaceObserver {

	private ISocketController socketHandler;
	private IWeightInterfaceController weightController;
	private KeyState keyState = KeyState.K1;
	private Double weight = 0.0000;
	private Double tara;
	private Double totalWeight = 0.0000;
	private String currDisplay = "";

	public MainController(ISocketController socketHandler, IWeightInterfaceController weightInterfaceController) {
		this.init(socketHandler, weightInterfaceController);
	}

	@Override
	public void init(ISocketController socketHandler, IWeightInterfaceController weightInterfaceController) {
		this.socketHandler = socketHandler;
		this.weightController = weightInterfaceController;
	}

	@Override
	public void start() {
		if (socketHandler != null && weightController != null){
			//Makes this controller interested in messages from the socket
			socketHandler.registerObserver(this);
			//Starts socketHandler in own thread
			new Thread(socketHandler).start();
			//TODO set up weightController - Look above for inspiration (Keep it simple ;))
			weightController.registerObserver(this);
			new Thread(weightController).start();
		} else {
			System.err.println("No controllers injected!");
		}
	}

	//Listening for socket input
	@Override
	public void notify(SocketInMessage message) {
		switch (message.getType()) {
		case B:
			//Checking if command is valid.
			if (checkDouble(message.getMessage() )){
				//Changing weight's screen to match new value.
				weightController.showMessagePrimaryDisplay(message.getMessage());
				this.notifyWeightChange(Double.parseDouble(message.getMessage()));
				socketHandler.sendMessage(new SocketOutMessage("B A"));
				
			} else{
				// If command is not a double value ES will be returned.
				socketHandler.sendMessage(new SocketOutMessage("ES\r\n"));
		}
			break;
		case D:			
			weightController.showMessagePrimaryDisplay(message.getMessage()); 
			socketHandler.sendMessage(new SocketOutMessage("D A\r\n"));
			break;
		case Q:
			socketHandler.sendMessage(new SocketOutMessage("Closing..."));
			System.exit(0);
			break;
		case RM204:

			weightController.showMessageSecondaryDisplay(message.getMessage());
			socketHandler.sendMessage(new SocketOutMessage("RM20 B\n\r"));
			break;
		case RM208:
			weightController.showMessageSecondaryDisplay(message.getMessage());
			socketHandler.sendMessage(new SocketOutMessage("RM20 B\n\r"));

			
			
			
			break;
		case S:
			socketHandler.sendMessage(new SocketOutMessage("S S " + weight.toString()+" kg \r\n"));
			break;
		case T:
			tara = weight;
			weight = 0.0000;
			String.format("Range = %.4f", weight);
			weightController.showMessagePrimaryDisplay(weight + " kg");
			totalWeight = totalWeight + tara;
			socketHandler.sendMessage(new SocketOutMessage("T S " + tara + ". Total weight " + totalWeight + "\r\n"));
			break;
		case DW:
			String.format("Range = %.4f", totalWeight);
			weightController.showMessagePrimaryDisplay(totalWeight + " kg"); 
			socketHandler.sendMessage(new SocketOutMessage("DW A \r\n"));
			break;
		case K:
			handleKMessage(message);
			break;
		case P111:
			if(message.getMessage().length() > 30){
				socketHandler.sendMessage(new SocketOutMessage("ES\r\n"));
				break;
			}
			weightController.showMessageSecondaryDisplay(message.getMessage()+"");
			socketHandler.sendMessage(new SocketOutMessage("P111 A\r\n"));
			break;
		case def:
			socketHandler.sendMessage(new SocketOutMessage("ES\r\n"));
			break;

		}

	}

	private void handleKMessage(SocketInMessage message) {
		System.out.println(message.getMessage());
		switch (message.getMessage()) {
		case "1" :
			this.keyState = KeyState.K1;
			break;
		case "2" :
			this.keyState = KeyState.K2;
			break;
		case "3" :
			this.keyState = KeyState.K3;
			break;
		case "4" :
			this.keyState = KeyState.K4;
			break;
		default:
			socketHandler.sendMessage(new SocketOutMessage("ES\n\r"));
			break;
		}
	}
	//Listening for UI input
	@Override
	public void notifyKeyPress(KeyPress keyPress) {
		//TODO implement logic for handling input from ui
		switch (keyPress.getType()) {
		case SOFTBUTTON:
			
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber()+ "    " + keyPress.getType());
			break;
		case TARA:
			
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber());
			break;
		case TEXT:
			int bogstav = keyPress.getCharacter();
//			if(bogstav )
			currDisplay += bogstav;
			weightController.showMessagePrimaryDisplay(currDisplay);
			
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber());
			break;
		case ZERO:
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber());
			break;
		case C:
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber());
			break;
		case EXIT:
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber()+ "    " + keyPress.getType());
			break;
		case SEND:
			System.out.println(keyPress.getCharacter() + " +- " + keyPress.getKeyNumber() + "    " + keyPress.getType());
			
			if (keyState.equals(KeyState.K4) || keyState.equals(KeyState.K3) ){
				socketHandler.sendMessage(new SocketOutMessage("K A 3"));
			}
			
			break;
		}

	}

	@Override
	public void notifyWeightChange(double newWeight) {
		// TODO Auto-generated method stub
		weight = newWeight;
	}
	
	public boolean checkDouble(String str){
		try{
			if(Double.parseDouble(str) <= 6){
				return true;
			}
			
			return false;
		}catch (NumberFormatException e){
			return false;
		}
	}

}
