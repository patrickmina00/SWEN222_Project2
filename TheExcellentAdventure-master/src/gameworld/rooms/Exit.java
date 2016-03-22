package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.PlayerConstraintImpl;

public class Exit extends Room{

	public static final int rid = 8;
	
	public Exit(){
		id = Exit.rid;
		ItemManagerImpl iManager = new ItemManagerImpl();
		//Create a new player constraint where the player needs to have the torch and add it to the arraylist.
		PlayerConstraint hasKey = new PlayerConstraintImpl(Orientation.EAST, iManager.getItemWithIdAndState(12, ItemState.FALSE), "Has Key");
		this.playerConstraint.add(hasKey);
	}
	
	@Override
	public String toString() {
		return "Exit";
	}
	
}
