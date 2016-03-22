package gameworld.rooms;

import gameworld.Orientation;
import gameworld.items.ItemState;
import gameworld.items.ItemManagerImpl;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.PlayerConstraintImpl;

public class IrradiatedRoom extends Room{
	
	public static final int rid = 7;
	
	public IrradiatedRoom(){
		id = IrradiatedRoom.rid;
		ItemManagerImpl iManager = new ItemManagerImpl();
		PlayerConstraint hasSuit = new PlayerConstraintImpl(Orientation.SOUTH, iManager.getItemWithIdAndState(6, ItemState.FALSE), "Has Radiation Suit");
		this.playerConstraint.add(hasSuit);
	}
	
	@Override
	public String toString() {
		return "Irradiated Room";
	}

}
