package events;

public class PlayerMoveEvent extends Event {
	
	public PlayerMoveEvent(int id, int[] path, int speed) {
		super(id, 0);
	}

}
