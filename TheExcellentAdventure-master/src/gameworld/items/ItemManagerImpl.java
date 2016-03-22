package gameworld.items;

public class ItemManagerImpl implements ItemManager {

	/**
	 * Given a valid ID this method returns it's corresponding item
	 * @param id
	 * @return
	 */
	@Override
	public Item getItemWithIdAndInitialState(int id) {
		
		switch(id){
		
		case 0: return new Item(id, "Diary", ItemState.DEFAULT, Type.ANCHORED);
		case 1: return new Item(id, "Lever 1", ItemState.FALSE, Type.ANCHORED);
		case 2: return new Item(id, "Lever 2", ItemState.FALSE, Type.ANCHORED);
		case 3: return new Item(id, "Lever 3", ItemState.FALSE, Type.ANCHORED);
		case 4: return new Item(id, "Torch", ItemState.TRUE, Type.REMOVABLE);
		case 5: return new Item(id, "Locker", ItemState.DEFAULT, Type.ANCHORED);
		case 6: return new Item(id, "Radiation Suit", ItemState.TRUE, Type.REMOVABLE);
		case 7: return new Item(id, "Toilet", ItemState.DEFAULT, Type.ANCHORED);
		case 8: return new Item(id, "Sink", ItemState.DEFAULT, Type.ANCHORED);
		case 9: return new Item(id, "Screwdriver", ItemState.TRUE, Type.REMOVABLE);
		case 10: return new Item(id, "Panel", ItemState.TRUE, Type.ANCHORED);
		case 11: return new Item(id, "Wires", ItemState.FALSE, Type.ANCHORED);
		case 12: return new Item(id, "Key", ItemState.TRUE, Type.REMOVABLE);
		case 13: return new Item(id, "Chair", ItemState.DEFAULT, Type.ANCHORED);

		default: throw new IllegalArgumentException();
		
		}
	}

	/**
	 * Given a valid ID this method returns it's corresponding item in the given state
	 * @param id
	 * @return
	 */
	@Override
	public Item getItemWithIdAndState(int id, ItemState state) {
		
		Item item = this.getItemWithIdAndInitialState(id);
		item.setState(state);		
		return item;
	}

}
