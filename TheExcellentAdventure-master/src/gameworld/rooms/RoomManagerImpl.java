package gameworld.rooms;

import gameworld.items.Item;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;

import java.util.ArrayList;

public class RoomManagerImpl implements RoomManager{

	/**
	 * Given a valid ID this method returns it's corresponding room
	 * @param id
	 * @return
	 */
	@Override
	public Room getRoomWithIdAndInitialItems(int id) {

		ItemManager itemManager = new ItemManagerImpl();

		switch(id){

		case 0: StartingArea startingArea = new StartingArea(); 
		startingArea.addItem(itemManager.getItemWithIdAndInitialState(0));
		return startingArea;

		case 1: LeverRoom leverRoom = new LeverRoom();
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(1)); //Lever 1
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(2)); //Lever 2
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(3)); //Lever 3
		return leverRoom;
		
		case 2: GenericRoom1 GenRoom1 = new GenericRoom1();
		return GenRoom1;
		
		case 3: LockerRoom lockerRoom = new LockerRoom();
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(5)); //Locker
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(6)); //Rad suit
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(13)); //Chair
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(4));//Torch
		return lockerRoom;
		
		case 4: DarkRoom darkRoom = new DarkRoom();
		return darkRoom;
		
		case 5: Toilet toilet = new Toilet();
		toilet.addItem(itemManager.getItemWithIdAndInitialState(8));//sink
		toilet.addItem(itemManager.getItemWithIdAndInitialState(7));//toilet
		toilet.addItem(itemManager.getItemWithIdAndInitialState(5));//key (not visible)
		return toilet;
		
		case 6: GenericRoom2 GenRoom2 = new GenericRoom2();
		return GenRoom2;
		
		case 7: IrradiatedRoom irradiatedRoom = new IrradiatedRoom();
		//radBarrel
		irradiatedRoom.addItem(itemManager.getItemWithIdAndInitialState(9));//screwdriver
		//radPuddle
		return irradiatedRoom;
		
		case 8: Exit exit = new Exit();
		exit.addItem(itemManager.getItemWithIdAndInitialState(10));//Panel
		exit.addItem(itemManager.getItemWithIdAndInitialState(11));//wires
		return exit;

		default: throw new IllegalArgumentException();

		}

	}

	/**
	 * Given a valid ID this method returns it's corresponding room with the given items
	 * @param id
	 * @return
	 */
	@Override
	public Room getRoomWithIdAndItems(int id, ArrayList<Item> items) {
		
		Room room = getRoomWithIdAndInitialItems(id);
		
		room.setItems(items);
		
		return room;
	}

}
