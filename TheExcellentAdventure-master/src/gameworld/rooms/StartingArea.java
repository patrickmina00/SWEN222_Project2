package gameworld.rooms;

public class StartingArea extends Room{

	public static final int rid = 0;
	
	public StartingArea(){
		id = StartingArea.rid;
	}

	@Override
	public String toString() {
		return "StartingArea";
	}

}
