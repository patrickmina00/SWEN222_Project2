package gameworld.logic;

import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.Item;

/**
 * Player constraints for ensuring that a player has a given item
 * @author michaelwinton
 *
 */
public class PlayerConstraintImpl implements PlayerConstraint{

	private Orientation constraintOrientation;
	private Item item;
	private String constraintName;
	
	public PlayerConstraintImpl(Orientation o, Item i, String constraint){
		
		this.constraintOrientation = o;
		this.item = i;
		this.constraintName = constraint;
		
	}
	
	
	@Override
	public String getConstraintName() {
		return this.constraintName;
	}

	/**
	 * Checks that the player passes the constraint
	 * @param p
	 * @return
	 */
	@Override
	public boolean passesConstraint(Player p) {
		
		for(Item i : p.getItems()){
			if(i.equalsID(item) && i.getState() == item.getState()){
				return true;
			}
		}
		
		return false;
	
	}

	/**
	 * Returns the constraint's orientation
	 * @return
	 */
	@Override
	public Orientation getOrientation() {
		return this.constraintOrientation;
	}

}
