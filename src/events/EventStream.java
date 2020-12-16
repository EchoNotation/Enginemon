package events;

public class EventStream {
	private Event[] sequentialEvents;
	private Event[] parallelEvents;
	private int[] outputs;
	private int seqIndex;
	private int parIndex;
	private boolean hasStarted;
	private boolean readyToFinish;
	

	public EventStream(Event[] seqEvents, Event[] parEvents) {
		sequentialEvents = seqEvents;
		parallelEvents = parEvents;
		hasStarted = false;
		outputs = new int[seqEvents.length];
	}
	
	public void init() {
		if(hasStarted) return;
		seqIndex = 0;
		parIndex = 0;
		readyToFinish = false;
		hasStarted = true;
		System.out.println("Event stream begin!");
		sequentialEvents[0].init(-1);
	}
	
	public void tick() {		
		if(seqIndex < sequentialEvents.length) {
			sequentialEvents[seqIndex].tick();
			if(sequentialEvents[seqIndex].isDone()) {
				outputs[seqIndex] = sequentialEvents[seqIndex].end();
				seqIndex++;
				
				if(seqIndex >= sequentialEvents.length) {
					readyToFinish = true;
				}
				else {
					sequentialEvents[seqIndex].init(outputs[seqIndex-1]);
				}
			}
		}
		if(parIndex < parallelEvents.length) {
			if(parallelEvents[parIndex].hasStarted()) {
				parallelEvents[parIndex].tick();
				
				if(parallelEvents[parIndex].isDone()) {
					parallelEvents[parIndex].end();
					parIndex++;
				}
			}
			else if(parallelEvents[parIndex].idToWaitFor == seqIndex) {
				parallelEvents[parIndex].init(parallelEvents[parIndex].getInputIndex());
			}
		}
		if(seqIndex >= sequentialEvents.length && parIndex >= parallelEvents.length) {
			readyToFinish = true;
		}
	}
	
	public void end() {
		System.out.println("Event stream end!");
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
	
	public boolean hasFinished() {
		return readyToFinish;
	}
}
