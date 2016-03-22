package test.room_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gameworld.Orientation;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.logic.RoomConstraint;
import gameworld.logic.RoomConstraintImpl;
import gameworld.rooms.Room;

import org.junit.Before;
import org.junit.Test;

public class RoomConstraintsShouldMoveTest {

	private ArrayList<RoomConstraint> roomConstraint = new ArrayList<RoomConstraint>();
	private Room testRoom;

	/**
	 * Sets up the roomConstraints and testRoom with valid items
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		ItemManager itemManager = new ItemManagerImpl();

		//RoomConstraints
		RoomConstraint lever1 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(1, ItemState.TRUE), "Lever 1");
		RoomConstraint lever2 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(2, ItemState.FALSE), "Lever 2");
		RoomConstraint lever3 = new RoomConstraintImpl(Orientation.NORTH, itemManager.getItemWithIdAndState(3, ItemState.TRUE), "Lever 3");
		this.roomConstraint.add(lever1);
		this.roomConstraint.add(lever2);
		this.roomConstraint.add(lever3);

		testRoom = new Room();
		testRoom.addItem(itemManager.getItemWithIdAndState(1, ItemState.TRUE)); //Lever 1
		testRoom.addItem(itemManager.getItemWithIdAndState(2, ItemState.FALSE)); //Lever 2
		testRoom.addItem(itemManager.getItemWithIdAndState(3, ItemState.TRUE)); //Lever 3
	}

	/**
	 * Tests the passesConstraints method which determines Player movements.
	 */
	@Test
	public void test() {

		boolean didPassConstraints = true;
		
		for(RoomConstraint rConstraint : this.roomConstraint ){			
			//check only the constraints of the direction that we are going in
			if(!rConstraint.passesConstraint(testRoom)){					
				
				didPassConstraints = false;
				
			}
		}
		
		assertTrue("No constraints were violated", didPassConstraints);
	}

}
