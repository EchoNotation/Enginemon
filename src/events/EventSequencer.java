package events;

import java.util.ArrayList;

import util.Variables;

public class EventSequencer {
	public static ArrayList<EventStream> eventStreamQueue = new ArrayList<EventStream>();
	
	/**
	 * Runs continuously while any streams are enqueued.
	 */
	public static void tick() {
		if(numberOfEnqueuedStreams() <= 0) return;
		
		if(eventStreamQueue.get(0).hasStarted()) {
			eventStreamQueue.get(0).tick();
			
			if(eventStreamQueue.get(0).hasFinished()) {
				eventStreamQueue.get(0).end();
				eventStreamQueue.remove(0);
				
				if(numberOfEnqueuedStreams() == 0) {
					Variables.lockPlayerMovement = false;
				}
			}
		}
		else {
			eventStreamQueue.get(0).init();
			Variables.lockPlayerMovement = true;
		}
	}
	
	/**
	 * Adds a new EventStream to the end of the queue.
	 * @param toAdd The EventStream to add.
	 */
	public static void enqueueEventStream(EventStream toAdd) {
		if(toAdd.getGlobalID() != -1) {
			eventStreamQueue.add(toAdd);
		}
	}
	
	/**
	 * Returns the number of EventStreams currently in the queue.
	 * @return The number of EventStreams in the queue.
	 */
	public static int numberOfEnqueuedStreams() {
		return eventStreamQueue.size();
	}
}
