package events;

import util.Constants;
import util.Variables;

/**
 * The class for all events which create dialog/text boxes.
 * @author Thomas
 *
 */
public class TextEvent extends Event {
	private String[] textData, optionText;
	private int textIndex;
	private int lastIndex;
	private int chosenOption;
	private boolean waitAFrame;
	private int framesToWait;
	private boolean stopping;

	/**
	 * Creates a new sequential TextEvent.
	 * @param inputIndex The index of the sequential outputs to use as input.
	 * @param textData The strings that are to be displayed.
	 * @param optionText Any text options the user may choose from, which will be provided after the last message.
	 */
	public TextEvent(int inputIndex, String[] textData, String[] optionText) {
		super(inputIndex);
		this.textData = textData;
		this.optionText = optionText;
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
		this.textData = textData;
		this.optionText = optionText;
	}
	
	/**
	 * Runs once when it is time to start.
	 * @param input The input value for this TextEvent.
	 */
	@Override
	public void init(int input) {
		//System.out.println("Begin text event! Starting message = " + textData[0]);
		if(textData.length <= 0) {
			readyToFinish = true;
			return;
		}
		inputData = input;
		hasStarted = true;
		readyToFinish = false;
		framesDelayed = 0;
		Variables.displayingText = true;
		Variables.currentMessage = textData[0];
		textIndex = 1;
		lastIndex = textData.length - 1;
		chosenOption = -1;
		waitAFrame = false;
		framesToWait = Constants.framesToDisplayText;
		stopping = false;
	}
	
	/**
	 * Runs continuously while text is being displayed.
	 */
	@Override
	public void tick() {
		if(stopping) {
			framesToWait--;
			//System.out.println("Waiting...");
			if(framesToWait <= 0) {
				//System.out.println("Waited!");
				Variables.displayTextOptions = false;
				Variables.messageCompleted = false;
				Variables.displayingText = false;
				Variables.readyForNextTextBox = false;
				Variables.waitingAfterSelection = false;
				textIndex = 0;
				readyToFinish = true;
			}
			return;
		}
		
		//System.out.println("Tick TextEvent!");
		if(!Variables.messageCompleted) return;
		
		if(textIndex > lastIndex) {
			//System.out.println("Hit the last textbox!");
			if(optionText.length > 0) {
				Variables.textOptions = optionText;
				Variables.displayTextOptions = true;
				
				if(waitAFrame) {
					if(Variables.readyForNextTextBox) {
						chosenOption = Variables.chosenTextOption;
						stopping = true;
						Variables.waitingAfterSelection = true;
					}
				}
				waitAFrame = true;
			}
			else {
				if(Variables.readyForNextTextBox) {
					Variables.displayingText = false;
					Variables.messageCompleted = false;
					Variables.readyForNextTextBox = false;
					textIndex = 0;
					readyToFinish = true;
				}
			}
		}
		else if(Variables.readyForNextTextBox) {
			Variables.messageCompleted = false;
			Variables.readyForNextTextBox = false;
			Variables.currentMessage = textData[textIndex];
			textIndex++;
		}
		
	}
	
	/**
	 * Runs once the last textbox has been cleared.
	 * @return The index of the options text that was chosen (-1 if no options text was provided).
	 */
	@Override
	public int end() {
		hasStarted = false;
		readyToFinish = false;
		//System.out.println("Chose option: " + chosenOption);
		return chosenOption;
	}
}
