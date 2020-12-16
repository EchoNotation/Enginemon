package events;

public class DelayEvent extends Event {
	private int framesToDelay;
	
	public DelayEvent(int id, int framesToDelay) {
		super(id, 0);
		this.framesToDelay = framesToDelay;
	}
	
	@Override
	public void tick() {
		if(framesToDelay <= 0) {
			readyToFinish = true;
		}
		else {
			framesToDelay--;
		}
	}
}
