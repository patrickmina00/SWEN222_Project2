package gameworld.rooms;

import java.util.ArrayList;

import gameworld.items.Item;

public interface RoomManager {

	/**
	 * Given a valid ID this method returns it's corresponding room
	 * @param id
	 * @return
	 */
	public Room getRoomWithIdAndInitialItems(int id);
	
	/**
	 * Given a valid ID this method returns it's corresponding room with the given items
	 * @param id
	 * @return
	 */
	public Room getRoomWithIdAndItems(int id, ArrayList<Item> item);
	
}
