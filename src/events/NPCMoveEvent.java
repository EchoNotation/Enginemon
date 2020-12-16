package events;

public class NPCMoveEvent extends Event {
	
	public NPCMoveEvent(int id, int npcID, int[] path, int speed) {
		super(id, 0);
	}
}
