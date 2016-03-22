package nodes;

import gameworld.Orientation;

import java.util.ArrayList;

public class PlayerNode implements ContainerNode {

	private int roomID;
	private int ID;
	private ArrayList<BasicNode> children;
	private Orientation orientation;
	
	public PlayerNode(int id, int rid, Orientation o){
		ID = id;
		roomID = rid;
		children = new ArrayList<BasicNode>();
		orientation = o;
	}
	
	public int getID() {
		return ID;
	}

	public Orientation getOrientation(){
		return orientation;
	}
	
	public String getOrientationString(){
		if(orientation == Orientation.NORTH){return "N ";}
		if(orientation == Orientation.EAST){return "E ";}
		if(orientation == Orientation.SOUTH){return "S ";}
		else{ return "W ";}
	}
	
	public ArrayList<BasicNode> getChildren() {
		return children;
	}

	public void addChild(BasicNode bn) {
		children.add(bn);		
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int rid) {
		roomID = rid;
	}

	public String toString(){
		return "PlayerNode(ID:"+ID+") | Current Room: "+roomID+" | Orientation = "+orientation.toString();
	}
}
