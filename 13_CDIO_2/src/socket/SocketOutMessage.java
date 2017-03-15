package socket;

public class SocketOutMessage {
	private String message;
//	private Command command;
	//TODO Add more ?

	public SocketOutMessage(String message) {
		this.message = message;
//		this.command = command;
	}
	
	public String getMessage() {
		return message;
	}
//	
//	public Command getCommand(){
//		return command;
//	}
//
//	public enum Command{
//		ES
//	}
	
}
