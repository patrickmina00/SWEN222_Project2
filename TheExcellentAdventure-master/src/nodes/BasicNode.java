package nodes;

import gameworld.items.ItemState;

public interface BasicNode {
	
	public int getId();
	
	public String getTypeString();
	
	public String getStateString();
	
	public ItemState getState();
}
