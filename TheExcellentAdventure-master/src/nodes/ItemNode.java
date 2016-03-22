package nodes;

import gameworld.items.ItemState;
import gameworld.items.Type;

public class ItemNode implements BasicNode{

	private int ID;
	private ItemState State;
	private Type Type; //ANCHORED REMOVABLE

	public ItemNode(int id, ItemState s, Type t){
		ID = id;
		State = s;
		Type = t;
	}

	public int getId() {
		return ID;
	}

	public ItemState getState(){
		return this.State;
	}
	
	public Type getType(){
		return this.Type;
	}

	public void setState(ItemState s){
		State = s;
	}
	public String toString(){
		return "ItemNode(ID:"+ID+") | State : "+State.toString()+" | Type : "+Type.toString();
	}

	public String getStateString() {
		if(State == State.TRUE){return "T";}
		else if(State == State.FALSE){return "F";}
		else {return "D";}
	}

	public String getTypeString() {
		if (Type == Type.ANCHORED){return "A ";}
		else {return "R ";}
	}
}
