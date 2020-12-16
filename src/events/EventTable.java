package events;

/**
 * The class that contains all of the EventStreams used throughout the game.
 * @author Thomas
 *
 */
public class EventTable {
	private int currentGlobalID = 0;
	
	/**
	 * Returns the EventStream designated by the passed ID from the current map.
	 * @param eventID The ID of the EventStream.
	 * @return The appropriate EventStream.
	 */
	public static EventStream getEventStream(int eventID) {
		if(eventID == 0) {
			Event[] seq = new Event[3];
			seq[0] = new PrintEvent("This is a test of the...");
			seq[1] = new DelayEvent(60);
			seq[2] = new PrintEvent("Sequential Event System (TM)");
			Event[] par = new Event[1];
			par[0] = new PrintEvent(0, 60, -1, "Parallel Event System (TM)");
			return new EventStream(0, 0, seq, par, -1, -1);
		}
		return new EventStream(-1, -1, new Event[0], new Event[0], -1, -1);
	}
	
	/**
	 * Will eventually return the selected EventStream from any particular map.
	 * @param regionID The ID of the region from which to choose the map.
	 * @param mapID The ID of the map from that selected region.
	 * @param eventID The ID of the EventStream within that particular map to return.
	 * @return The appropriate EventStream.
	 */
	public static EventStream getEventStream(int regionID, int mapID, int eventID) {
		//TODO Actual implementation.
		return new EventStream(-1, -1, new Event[0], new Event[0], -1, -1);
	}
	
	/**
	 * Will eventually load all the events coresponding to the specified map.
	 * @param regionID The ID of the region from which to draw the map.
	 * @param mapID The ID of the map within that region to load the events from.
	 */
	public static void loadEvents(int regionID, int mapID) {
		//TODO Actual implementation.
	}
	
	/**
	 * Will eventually load the events for all maps in the game.
	 */
	public static void loadAllEvents() {
		//TODO Actual implementation.
	}
	
}
