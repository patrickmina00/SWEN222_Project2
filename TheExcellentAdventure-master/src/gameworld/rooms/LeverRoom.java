package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.logic.RoomConstraint;
import gameworld.logic.RoomConstraintImpl;

public class LeverRoom extends Room{

	public static final int rid = 1;
	
	public LeverRoom(){
		id = LeverRoom.rid;
		
		ItemManager itemManager = new ItemManagerImpl();
		
		//RoomConstraints
		RoomConstraint lever1 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(1, ItemState.TRUE), "Lever 1");
		RoomConstraint lever2 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(2, ItemState.FALSE), "Lever 2");
		RoomConstraint lever3 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(3, ItemState.TRUE), "Lever 3");
		this.roomConstraint.add(lever1);
		this.roomConstraint.add(lever2);
		this.roomConstraint.add(lever3);
	}	

	@Override
	public String toString() {
		return "LeverRoom";
	}

}
