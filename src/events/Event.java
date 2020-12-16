package events;

/**
 * The base class for all Event types.
 * @author Thomas
 *
 */
public class Event {
	protected boolean readyToFinish = false;
	protected int inputData;
	protected boolean isParallel;
	protected boolean hasStarted;
	protected int idToWaitFor = -1;
	protected int frameDelay = 0;
	protected int framesDelayed = 0;
	protected int inputIndex = 0;
	
	/**
	 * Creates a new sequential Event, should only be used as super() in other Event classes.
	 * @param inputIndex The index of the sequential events that should form the input for this event.
	 */
	public Event() {
		isParallel = false;
	}
	
	/**
	 * Creates a new parallel Event, should only be used as super() in other Event classes.
	 * @param idToWaitFor The index of the sequential events list that this event should start at.
	 * @param frameDelay The number of frames to delay after the correct index is reached.
	 * @param inputIndex The index of the sequential events that should form the input for this event.
	 */
	public Event(int idToWaitFor, int frameDelay, int inputIndex) {
		isParallel = true;
		this.idToWaitFor = idToWaitFor;
		this.frameDelay = frameDelay;
		this.inputIndex = inputIndex;
	}
	
	/**
	 * Runs once when the super event type is initialized.
	 * @param input The input value to this event.
	 */
	public void init(int input) {
		inputData = input;
		hasStarted = true;
		readyToFinish = false;
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
	 * Returns what index in the sequential list this event should be run at (assuming this is a parallel event).
	 * @return The index number.
	 */
	public int indexToWaitFor() {
		return idToWaitFor;
	}
	
	/**
	 * Returns the number of frames to delay before starting once appropriate index is reached (assumes parallel event).
	 * @return The number of frames to delay.
	 */
	public int getFramesToDelay() {
		return frameDelay;
	}
	
	/**
	 * Returns which element of the sequential list this event should take input from.
	 * @return The index of the list.
	 */
	public int getInputIndex() {
		return inputIndex;
	}
	
	/**
	 * Returns whether or not this event is ready to start (assumes parallel event).
	 * @return Whether or not this event is ready to start.
	 */
	public boolean readyToStart() {
		if(framesDelayed > frameDelay) {
			framesDelayed = 0;
			return true;
		}
		else {
			//System.out.println("Not ready to start yet! Elapsed: " + framesDelayed + " out of: " + frameDelay);
			framesDelayed++;
			return false;
		}
	}
}
