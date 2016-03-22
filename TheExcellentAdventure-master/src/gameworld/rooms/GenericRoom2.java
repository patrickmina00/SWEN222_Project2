package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.PlayerConstraintImpl;

public class GenericRoom2 extends Room{
	
	public static final int rid = 6;
	
	public GenericRoom2(){
		id = GenericRoom2.rid;
		ItemManagerImpl iManager = new ItemManagerImpl();
		//Create a new player constraint where the player needs to have the torch and add it to the arraylist.
		PlayerConstraint hasTorch = new PlayerConstraintImpl(Orientation.SOUTH, iManager.getItemWithIdAndState(4, ItemState.FALSE), "Has Torch");
		PlayerConstraint hasKey = new PlayerConstraintImpl(Orientation.WEST, iManager.getItemWithIdAndState(12, ItemState.FALSE), "Has Key");
		PlayerConstraint hasSuit = new PlayerConstraintImpl(Orientation.NORTH, iManager.getItemWithIdAndState(6, ItemState.FALSE), "Has Radiation Suit");
		this.playerConstraint.add(hasSuit);
		this.playerConstraint.add(hasTorch);
		this.playerConstraint.add(hasKey);
	}
	
	@Override
	public String toString() {
		return "Generic Room 2";
	}

}
