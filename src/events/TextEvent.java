package events;

/**
 * The class for all events which create dialog/text boxes.
 * @author Thomas
 *
 */
public class TextEvent extends Event {

	/**
	 * Creates a new sequential TextEvent.
	 * @param id The global ID for this event.
	 * @param textData The strings that are to be displayed.
	 * @param optionText Any text options the user may choose from, which will be provided after the last message.
	 */
	public TextEvent(int id, String[] textData, String[] optionText) {
		super(id, 0);
	}
	
}
