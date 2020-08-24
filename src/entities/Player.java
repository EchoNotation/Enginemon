package entities;

public class Player extends Entity {
	private String name;
	private int xPos, yPos, tgtXPos, tgtYPos;

	public Player() {
		
	}
	
	@Override
	public void tick() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTgtXPos() {
		return tgtXPos;
	}
	
	public void setTgtXPos(int newPos) {
		tgtXPos = newPos;
	}
	
	public int getTgtYPos() {
		return tgtYPos;
	}
	
	public void setTgtYPos(int newPos) {
		tgtYPos = newPos;
	}
}
