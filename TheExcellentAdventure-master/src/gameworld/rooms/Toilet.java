package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.PlayerConstraintImpl;

public class Toilet extends Room{
	
	public static final int rid = 5;

	public Toilet(){
		id = Toilet.rid;
		ItemManagerImpl iManager = new ItemManagerImpl();
		//Create a new player constraint where the player needs to have the torch and add it to the arraylist.
		PlayerConstraint hasTorch = new PlayerConstraintImpl(Orientation.EAST, iManager.getItemWithIdAndState(4, ItemState.FALSE), "Has Torch");
		this.playerConstraint.add(hasTorch);
	}
	
	@Override
	public String toString() {
		return "Toilet";
	}
}
