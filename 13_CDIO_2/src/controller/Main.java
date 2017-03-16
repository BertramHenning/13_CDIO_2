package controller;

import socket.SocketController;
import weight.IWeightInterfaceController;
import weight.gui.WeightInterfaceControllerGUI;
import socket.ISocketController;
/**
 * Simple class to fire up application and inject implementations
 * @author Christian
 *
 */
public class Main {
	private static boolean gui = true;
	private static int arg;
	
	public static void main(String[] args) {
		
		//If port needs to be changed from 8000
		try{
		if(args[0] != null){
			arg = Integer.parseInt(args[0]);
			SocketController.changePort(arg);
		}
		}catch(ArrayIndexOutOfBoundsException AE){
			//DO nothing
		}
		
		ISocketController socketHandler = new SocketController();
		IWeightInterfaceController weightController = new WeightInterfaceControllerGUI();
		//Injecting socket and uiController into mainController - Replace with improved versions...
		IMainController mainCtrl = new MainController(socketHandler, weightController);
		//.init and .start could be merged
		mainCtrl.start();
		
	}
}
