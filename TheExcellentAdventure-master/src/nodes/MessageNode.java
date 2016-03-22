package nodes;

public class MessageNode {

	String message;
	
	public MessageNode(String m){
		message = m;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
	
}
