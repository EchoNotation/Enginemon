package events;

/**
 * A debug class that allows the use of print statements in the EventStream logic.
 * @author Thomas
 *
 */
public class PrintEvent extends Event {
	private String message;
	
	/**
	 * Creates a new sequential or parallel PrintEvent.
	 * @param id The global ID of this event.
	 * @param message The string to be printed to the console.
	 */
	public PrintEvent(int id, String message) {
		super(id, 0);
		this.message = message;
	}
	
	@Override
	public void tick() {
		System.out.println(message);
		readyToFinish = true;
	}
}
