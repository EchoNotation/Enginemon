package events;

/**
 * A debug class that allows the use of print statements in the EventStream logic.
 * @author Thomas
 *
 */
public class PrintEvent extends Event {
	private String message;
	
	/**
	 * Creates a new sequential PrintEvent.
	 * @param message The string to be printed to the console.
	 */
	public PrintEvent(int inputIndex, String message) {
		super(inputIndex);
		this.message = message;
	}
	
	/**
	 * Creates a new parallel PrintEvent.
	 * @param idToWaitFor The index of the sequential events array that this event should be run at.
	 * @param framesToDelay The number of frames to delay once that index is reached before starting.
	 * @param inputIndex The index of the sequential events array that this event should take input from.
	 * @param message The string to be printed to the console.
	 */
	public PrintEvent(int idToWaitFor, int framesToDelay, int inputIndex, String message) {
		super(idToWaitFor, framesToDelay, inputIndex);
		this.message = message;
	}
	
	@Override
	public void tick() {
		System.out.println(message);
		readyToFinish = true;
	}
}
