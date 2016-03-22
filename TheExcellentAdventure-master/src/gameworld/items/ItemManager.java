package gameworld.items;

public interface ItemManager {

	/**
	 * Given a valid ID this method returns it's corresponding item
	 * @param id
	 * @return
	 */
	public Item getItemWithIdAndInitialState(int id);

	/**
	 * Given a valid ID this method returns it's corresponding item in the given state
	 * @param id
	 * @return
	 */
	public Item getItemWithIdAndState(int id, ItemState state);
	
}
