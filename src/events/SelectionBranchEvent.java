package events;

public class SelectionBranchEvent extends Event {
	private int[] streamIndices;
	
	/**
	 * Creates a new SelectionBranchEvent.
	 * @param inputIndex The index of the sequential outputs that this event should take input from.
	 * @param streams The array of EventStreams to choose from.
	 */
	public SelectionBranchEvent(int inputIndex, int[] streamIndices) {
		super(inputIndex);
		this.streamIndices = streamIndices;
	}
	
	/**
	 * Adds one of the provided EventStreams to the queue.
	 * @param input This variable chooses which of the streams to choose.
	 */
	@Override
	public void init(int input) {
		//System.out.println(input);
		hasStarted = true;
		readyToFinish = true;
		
		if(input < 0 || input >= streamIndices.length) {
			System.out.println("Invalid input into SelectionBranchEvent!");
			return;
		}
		
		EventSequencer.enqueueEventStream(EventTable.getEventStream(streamIndices[input]));
	}
}
