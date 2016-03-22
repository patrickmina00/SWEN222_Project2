package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemState;
import gameworld.items.ItemManagerImpl;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.PlayerConstraintImpl;

public class GenericRoom1 extends Room{

	public static final int rid = 2;

	public GenericRoom1(){
		id = GenericRoom1.rid;
		//Generate an item manage to create a torch.
		ItemManagerImpl iManager = new ItemManagerImpl();
		//Create a new player constraint where the player needs to have the torch and add it to the arraylist.
		PlayerConstraint hasTorch = new PlayerConstraintImpl(Orientation.NORTH, iManager.getItemWithIdAndState(4, ItemState.FALSE), "Has Torch");
		this.playerConstraint.add(hasTorch);
	}
	

	
	@Override
	public String toString() {
		return "GenericRoom1";
	}
	



}
