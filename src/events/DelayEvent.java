package events;

/**
 * The event type that complets after a specified number of frames pass.
 * @author Thomas
 *
 */
public class DelayEvent extends Event {
	private int framesToDelay, framesDelayed;
	
	/**
	 * Creates a new sequential DelayEvent.
	 * @param framesToDelay The number of frames to delay before completing.
	 */
	public DelayEvent(int framesToDelay) {
		super();
		this.framesToDelay = framesToDelay;
	}
	
	@Override
	public void tick() {
		if(framesDelayed >= framesToDelay) {
			framesDelayed = 0;
			readyToFinish = true;
		}
		else {
			framesDelayed++;
		}
	}
}
