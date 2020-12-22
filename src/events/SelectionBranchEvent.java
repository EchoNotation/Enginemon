package events;

public class SelectionBranchEvent extends Event {
	private EventStream[] streams;
	
	/**
	 * Creates a new SelectionBranchEvent.
	 * @param streams The array of EventStreams to choose from.
	 */
	public SelectionBranchEvent(int inputIndex, EventStream[] streams) {
		super(inputIndex);
		this.streams = streams;
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
		
		if(input < 0 || input >= streams.length) {
			System.out.println("Invalid input into SelectionBranchEvent!");
			return;
		}
		
		EventSequencer.enqueueEventStream(streams[input]);
	}
}
