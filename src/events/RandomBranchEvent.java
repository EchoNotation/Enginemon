package events;

import java.util.concurrent.ThreadLocalRandom;

public class RandomBranchEvent extends Event {
	private int[] streamIndices;
	private int[] inputProbs;
	private int[] cumulativeProbs;
	
	/**
	 * Creates a new sequential RandomBranchEvent.
	 * @param inputIndex The input that this event should draw from.
	 * @param streamIndices The indices of the EventStreams that should be taken.
	 * @param inputProbs The corresponding probabilities for each EventStream (should add up to 100).
	 */
	public RandomBranchEvent(int inputIndex, int[] streamIndices, int[] inputProbs) {
		super(inputIndex);
		this.streamIndices = streamIndices;
		this.inputProbs = inputProbs;
		cumulativeProbs = new int[inputProbs.length];
		
		int runningTotal = 0;
		for(int i = 0; i < cumulativeProbs.length; i++) {
			runningTotal += inputProbs[i];
			cumulativeProbs[i] = runningTotal;
		}
	}
	
	/**
	 * Runs once when initialized.
	 * @param input The input to this RandomBranchEvent.
	 */
	@Override
	public void init(int input) {
		inputData = input;
		hasStarted = true;
		readyToFinish = true;
		
		int random = ThreadLocalRandom.current().nextInt(1, 101);
		
		for(int i = 0; i < cumulativeProbs.length; i++) {
			if(random >= cumulativeProbs[i]) {
				EventSequencer.enqueueEventStream(EventTable.getEventStream(streamIndices[i]));
				break;
			}
		}
	}
	
}
