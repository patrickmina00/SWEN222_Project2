package nodes;

import java.util.ArrayList;

public class RoomNode implements ContainerNode {

	public enum Wall{
		LOCKED, OPEN, CLEAR;
	}
	
	private int ID;
	private ArrayList<BasicNode> children; 
	private Wall north;
	private Wall east;
	private Wall south;
	private Wall west;
	
	public RoomNode(int id, Wall n, Wall e, Wall s, Wall w){
		ID = id;
		north = n;
		east = e;
		south = s;
		west = w;
		children = new ArrayList<BasicNode>();
	}
	
	public ArrayList<BasicNode> getChildren() {
		return children;
	}


	public void addChild(BasicNode bn) {
		children.add(bn);
		
	}

	public Wall getWall(String o){
		if (o.equals("north")){return north;}
		else if(o.equals("east")){return east;}
		else if(o.equals("south")){return south;}
		else {return west;}
	}
	
	public String toString(){
		return "RoomNode(ID:"+ID+") | NORTH = "+north.toString()+" | EAST = "+east.toString()+" | SOUTH = "+south.toString()+" | WEST = "+west.toString();
	}

	@Override
	public int getID() {
		return ID;
	}


}
