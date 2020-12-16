package events;

import java.util.ArrayList;

/**
 * The class that handles the logic and progression of different event types.
 * @author Thomas
 *
 */
public class EventStream {
	private int globalID = -1;
	private int localID = -1;
	private Event[] sequentialEvents;
	private Event[] parallelEvents;
	private ArrayList<Event> parallelEventsLeft;
	private int[] outputs;
	private int seqIndex;
	private boolean hasStarted;
	private boolean readyToFinish;
	private int x, y;

	/**
	 * Creates a new EventStream.
	 * @param gID The global ID number of this EventStream.
	 * @param lID The local ID number of this EventStream.
	 * @param seqEvents The array of Events that make up the sequential list.
	 * @param parEvents The array of Events that make up the parallel list.
	 * @param x The x coordinate where this event will be triggered. (-1 if nowhere in particular.)
	 * @param y The y coordinate where this event will be triggered. (-1 if nowhere in particular.)
	 */
	public EventStream(int gID, int lID, Event[] seqEvents, Event[] parEvents, int x, int y) {
		this.globalID = gID;
		this.localID = lID;
		sequentialEvents = seqEvents;
		parallelEvents = parEvents;
		parallelEventsLeft = new ArrayList<Event>();
		hasStarted = false;
		outputs = new int[seqEvents.length];
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Called once when the EventStream is slated to begin.
	 */
	public void init() {
		if(hasStarted) return;
		seqIndex = 0;
		readyToFinish = false;
		hasStarted = true;
		sequentialEvents[0].init(-1);
		
		for(int i = 0; i < parallelEvents.length; i++) {
			parallelEventsLeft.add(parallelEvents[i]);
		}
		
		System.out.println("Event stream begin!");
	}
	
	/**
	 * Called once per frame until the hasFinished() method returns true.
	 */
	public void tick() {		
		if(seqIndex < sequentialEvents.length) {
			sequentialEvents[seqIndex].tick();
			if(sequentialEvents[seqIndex].isDone()) {
				outputs[seqIndex] = sequentialEvents[seqIndex].end();
				seqIndex++;
				
				if(seqIndex < sequentialEvents.length) {
					sequentialEvents[seqIndex].init(outputs[seqIndex-1]);
				}
			}
		}
		if(parallelEventsLeft.size() > 0) {
			for(int i = parallelEventsLeft.size()-1; i >= 0; i--) {
				Event temp = parallelEventsLeft.get(i);
				if(temp.hasStarted()){
					parallelEventsLeft.get(i).tick();
				}
				else if(temp.indexToWaitFor() <= seqIndex && !temp.hasStarted() && temp.readyToStart()) {
					//System.out.println("Time to start parallel!");
					//It is time for this parallel event to start!
					if(temp.getInputIndex() == -1) {
						parallelEventsLeft.get(i).init(-1);
					}
					else {
						parallelEventsLeft.get(i).init(outputs[temp.getInputIndex()]);
					}
				}
				if(parallelEventsLeft.get(i).isDone()) {
					parallelEventsLeft.get(i).end();
					parallelEventsLeft.remove(i);
				}
			}
		}
		if(seqIndex >= sequentialEvents.length && parallelEventsLeft.size() <= 0) {
			readyToFinish = true;
		}
	}
	
	/**
	 * Called when the EventStream finishes.
	 */
	public void end() {
		hasStarted = false;
		readyToFinish = false;
		System.out.println("Event stream end!");
	}
	
	/**
	 * Returns whether or not this EventStream has started.
	 * @return Whether or not this EventStream has started.
	 */
	public boolean hasStarted() {
		return hasStarted;
	}
	
	/**
	 * Returns whether or not this EventStream has finished.
	 * @return Whether or not this EventStream has finished.
	 */
	public boolean hasFinished() {
		return readyToFinish;
	}
	
	/**
	 * Returns the x coordinate of the spot where this EventStream should be triggered.
	 * @return The x coordinate. (-1 if no tile should trigger it.)
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of the spot where this EventStream should be triggered.
	 * @return The y coorindate. (-1 if no tile should trigger it.)
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the global ID number of this EventStream.
	 * @return The global ID number.
	 */
	public int getGlobalID() {
		return globalID;
	}
	
	/**
	 * Returns the local ID number of this EventStream.
	 * @return The local ID number.
	 */
	public int getLocalID() {
		return localID;
	}
}
