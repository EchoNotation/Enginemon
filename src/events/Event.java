package events;

/**
 * The base class for all Event types.
 * @author Thomas
 *
 */
public class Event {
	protected int globalID = -1;
	protected boolean readyToFinish = false;
	protected int inputData;
	protected boolean isParallel;
	protected boolean hasStarted;
	protected int idToWaitFor = -1;
	protected int inputIndex = 0;
	
	/**
	 * Creates a new sequential Event, should only be used as super() in other Event classes.
	 * @param id the global ID of this event.
	 * @param inputIndex the index of the sequential events that should form the input for this event.
	 */
	public Event(int id, int inputIndex) {
		this.globalID = id;
		isParallel = false;
		this.inputIndex = inputIndex;
	}
	
	/**
	 * Creates a new parallel Event, should only be used as super() in other Event classes.
	 * @param id
	 * @param idToWaitFor
	 * @param inputIndex
	 */
	public Event(int id, int idToWaitFor, int inputIndex) {
		this.globalID = id;
		isParallel = true;
		this.idToWaitFor = idToWaitFor;
		this.inputIndex = inputIndex;
	}
	
	/**
	 * Returns the global ID of this event.
	 * @return The global ID.
	 */
	public int getID() {
		return globalID;
	}
	
	/**
	 * Runs once when the super event type is initialized.
	 * @param input The input value to this event.
	 */
	public void init(int input) {
		inputData = input;
		hasStarted = true;
	}
	
	/**
	 * Called continuously when the event is running, should almost always be overrided.
	 */
	public void tick() {
		//System.out.println("Unoverrided tick method! Global Event ID: " + globalID);
	}
	
	/**
	 * Called when the isDone() method retuns true.
	 * @return The output value for this event.
	 */
	public int end() {
		return 0;
	}
	
	/**
	 * Returns whether or not this event is ready to finish.
	 * @return Whether or not this event is ready to finish.
	 */
	public boolean isDone() {
		return readyToFinish;
	}
	
	/**
	 * Returns whether or not this event should be run in parallel.
	 * @return Whether or not this event should be run in parallel.
	 */
	public boolean isParallel() {
		return isParallel;
	}
	
	/**
	 * Returns whether or not this event has started running.
	 * @return Whether or not this event has started running.
	 */
	public boolean hasStarted() {
		return hasStarted;
	}
	
	/**
	 * Returns what ID in the sequential list this event should be run at (assuming this is a parallel event).
	 * @return The ID number.
	 */
	public int idToWaitFor() {
		return idToWaitFor;
	}
	
	/**
	 * Returns which element of the sequential list this event should take input from.
	 * @return The index of the list.
	 */
	public int getInputIndex() {
		return inputIndex;
	}
}
