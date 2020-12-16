package events;

public class EventTable {
	
	public static EventStream getEventStream(int eventID) {
		if(eventID == 0) {
			Event[] seq = new Event[4];
			seq[0] = new PrintEvent(0, "Heyo!");
			seq[1] = new PrintEvent(1, "This is a test of the...");
			seq[2] = new DelayEvent(2, 120);
			seq[3] = new PrintEvent(3, "Event Stream System (TM)!");
			return new EventStream(seq, new Event[0]);
		}
		return new EventStream(new Event[0], new Event[0]);
	}

	public static void loadEvents() {
		
	}
	
}
