package gameworld.logic;

import gameworld.Orientation;
import gameworld.Player;

/**
 * Player constraints for ensuring that a player has a given item
 * @author michaelwinton
 *
 */
public interface PlayerConstraint extends Constraint{

	/**
	 * Checks that the player passes the constraint
	 * @param p
	 * @return
	 */
	public boolean passesConstraint(Player p);
	
	/**
	 * Returns the constraint's orientation
	 * @return
	 */
	public Orientation getOrientation();
	
}
