package gameworld.logic;

import gameworld.Orientation;
import gameworld.items.Item;
import gameworld.rooms.Room;

/**
 * Room constraints for ensuring that the room has a given item in a particular state
 * @author michaelwinton
 *
 */
public class RoomConstraintImpl implements RoomConstraint{

	private Orientation constraintOrientation;
	private Item item;
	private String constraintName;
	
	public RoomConstraintImpl(Orientation o, Item i, String constraint){
		
		this.constraintOrientation = o;
		this.item = i;
		this.constraintName = constraint;
		
	}
	
	@Override
	public String getConstraintName() {
		return constraintName;
	}

	/**
	 * Checks that the room passes the constraint
	 * @param r
	 * @return
	 */
	@Override
	public boolean passesConstraint(Room r) {

		for(Item i : r.getItems()){
			if(i.equalsID(item) && i.getState() == item.getState()){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Orientation getOrientation() {
		return constraintOrientation;
	}

}
