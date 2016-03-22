package gameworld.logic;

import gameworld.Orientation;
import gameworld.rooms.Room;

/**
 * Room constraints for ensuring that the room has a given item in a particular state
 * @author michaelwinton
 *
 */
public interface RoomConstraint extends Constraint {

	/**
	 * Checks that the room passes the constraint
	 * @param r
	 * @return
	 */
	public boolean passesConstraint(Room r);

	public Orientation getOrientation();
	
}
