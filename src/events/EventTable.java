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
			seq[0] = new PrintEvent(-1, "This is a test of the...");
			seq[1] = new DelayEvent(-1, 60);
			seq[2] = new PrintEvent(-1, "Sequential Event System (TM)");
			Event[] par = new Event[1];
			par[0] = new PrintEvent(1, 60, -1, "Parallel Event System (TM)");
			return new EventStream(0, 0, seq, par, -1, -1);
		}
		else if(eventID == 1) {
			Event[] seq = new Event[2];
			String[] textData = new String[3];
			textData[0] = "This is a test of the text display system...";
			textData[1] = "...And this is the follow-up message!";
			textData[2] = "Capiche?";
			String[] optionData = new String[3];
			optionData[0] = "Sure";
			optionData[1] = "Never!";
			optionData[2] = "What?";
			seq[0] = new TextEvent(-1, textData, optionData);
			int[] streamIDs = new int[3];
			streamIDs[0] = 2;
			streamIDs[1] = 3;
			streamIDs[2] = 1;
			seq[1] = new SelectionBranchEvent(0, streamIDs);
			return new EventStream(1, 1, seq, new Event[0], -1, -1);
		}
		else if(eventID == 2) {
			String[] textData2 = new String[1];
			textData2[0] = "Sweet.";
			Event[] seq2 = new Event[1];
			seq2[0] = new TextEvent(-1, textData2, new String[0]);
			return new EventStream(2, 2, seq2, new Event[0], -1, -1);
		}
		else if(eventID == 3) {
			String[] textData3 = new String[1];
			textData3[0] = "Very well Mr. Bond, but now I will have to kill you.";
			Event[] seq3 = new Event[1];
			seq3[0] = new TextEvent(-1, textData3, new String[0]);
			return new EventStream(3, 3, seq3, new Event[0], -1, -1);
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
