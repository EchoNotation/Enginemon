package entities;

public class Player extends Entity {
	private String name;
	private int xPos, yPos;

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
}
