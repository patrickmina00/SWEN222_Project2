package nodes;

import java.util.ArrayList;

public interface ContainerNode {

	public int getID();
	
	public ArrayList<BasicNode>getChildren();
	
	/**Add the given BasicNode as a child of this node.
	 * 
	 * @param bn - basic node to add
	 */
	
	public void addChild(BasicNode bn);
	
}
