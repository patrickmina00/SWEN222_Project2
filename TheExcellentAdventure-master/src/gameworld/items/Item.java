package gameworld.items;

import nodes.BasicNode;
import nodes.ItemNode;

/**
 * Item is the logical representation of all the items which can be held by a player or room in the game
 * 
 * @author michaelwinton
 *
 */
public class Item {

	private int ID;
	private ItemState state = ItemState.DEFAULT;
	private String name;
	private Type type;
	
	public Item(int id, String name, ItemState state, Type type) {
		this.ID = id;
		this.name = name;
		this.state = state;
		this.type = type;
	}
	
	public boolean equalsID(Item other){
		return this.ID == other.getID();
	}
	
	public int getID(){
		return this.ID;
	}

	public ItemState getState() {
		return state;
	}

	public void setState(ItemState state) {
		this.state = state;
	}
	
	public String getName(){
		return name;
	}
	
	public Type getType(){
		return type;
	}

	@Override
	public String toString() {
		return "Item [ID=" + ID + ", state=" + state + ", name=" + name
				+ ", type=" + type + "]";
	}

	/**
	 * Converts the item into it's node variant
	 * @return
	 */
	public BasicNode convertToItemNode() {
		ItemNode itemNode = new ItemNode(this.ID, this.state, this.type);
		return itemNode;
	}
	
	

}
