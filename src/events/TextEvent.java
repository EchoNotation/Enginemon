package events;

/**
 * The class for all events which create dialog/text boxes.
 * @author Thomas
 *
 */
public class TextEvent extends Event {

	/**
	 * Creates a new sequential TextEvent.
	 * @param textData The strings that are to be displayed.
	 * @param optionText Any text options the user may choose from, which will be provided after the last message.
	 */
	public TextEvent(String[] textData, String[] optionText) {
		super();
	}
	
	/**
	 * Creates a new parallel TextEvent.
	 * @param idToWaitFor The index of the sequential events that this parallel event should start at.
	 * @param framesToDelay The number of frames to delay once the correct index is reached before starting.
	 * @param inputIndex The index of the sequential events that should form the input for this event.
	 * @param textData The strings that are to be displayed.
	 * @param optionText Any text options the user may choose from, which will be provided after the last message.
	 */
	public TextEvent(int idToWaitFor, int framesToDelay, int inputIndex, String[] textData, String[] optionText) {
		super(idToWaitFor, framesToDelay, inputIndex);
	}
	
}
