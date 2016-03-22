package gameworld.rooms;

public class DarkRoom extends Room{

	public static final int rid = 4;
	
	public DarkRoom(){
		id = DarkRoom.rid;
	}
	
	@Override
	public String toString() {
		return "Dark Room";
	}
	
}
