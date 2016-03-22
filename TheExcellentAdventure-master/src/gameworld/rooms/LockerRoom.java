package gameworld.rooms;

public class LockerRoom extends Room{
	
	public static final int rid = 3;

	public LockerRoom(){
		id = LockerRoom.rid;
	}
	
	@Override
	public String toString() {
		return "LockerRoom";
	}
}
