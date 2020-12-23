package entities;

import main.Game.MoveDirection;

/**
 * The base class for all map objects which can move or be interacted with.
 * @author Thomas
 *
 */
public class Entity {
	private int xPos, yPos, tgtXPos, tgtYPos;
	private MoveDirection moveDir = MoveDirection.NONE;
	private int movementOffset = 0;
	private int eventStreamIndex = -1;
	
	/**
	 * Creates a new Entity.
	 */
	public Entity() {
		
	}
	
	/**
	 * Runs once per frame.
	 */
	public void tick() {
		
	}
	
	/**
	 * Returns the x position of this entity on the current map.
	 * @return The x coordinate.
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * Sets the x position of this entity on the current map to the specified value.
	 * @param xPos The new value.
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Returns the y position of this entity on the current map.
	 * @return The y coordinate.
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * Sets the y position of this entity on the current map to the specified value.
	 * @param yPos The new value.
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Returns the x position this entity is currently trying to move to.
	 * @return The x coordinate.
	 */
	public int getTgtXPos() {
		return tgtXPos;
	}
	
	/**
	 * Sets the x position this entity is currently trying to move to to the specified value.
	 * @param newPos The new x coordinate.
	 */
	public void setTgtXPos(int newPos) {
		tgtXPos = newPos;
	}
	
	/**
	 * Returns the y position this entity is currently trying to move to.
	 * @return The y coordinate.
	 */
	public int getTgtYPos() {
		return tgtYPos;
	}
	
	/**
	 * Sets the y position this entity is currently trying to move to to the specified value.
	 * @param newPos The new y coordinate.
	 */
	public void setTgtYPos(int newPos) {
		tgtYPos = newPos;
	}
	
	/**
	 * Returns the current number of pixels this entity is offset during its movement.
	 * @return The number of pixels.
	 */
	public int getMovementOffset() {
		return movementOffset;
	}
	
	/**
	 * Sets the current number of pixels this entity is offset by to the specified value.
	 * @param newOffset The new offset.
	 */
	public void setMovementOffset(int newOffset) {
		movementOffset = newOffset;
	}
	
	/**
	 * Returns the direction this entity is currently moving in.
	 * @return The direction.
	 */
	public MoveDirection getMoveDirection() {
		return moveDir;
	}
	
	/**
	 * Sets the direction this entity is currently moving in to the specified direction.
	 * @param newDir The new direction.
	 */
	public void setMoveDirection(MoveDirection newDir) {
		moveDir = newDir;
	}
	
	/**
	 * Returns the local ID of the EventStream that should be enqueued when this entity is interacted with.
	 * @return the local ID.
	 */
	public int getInteractEventStreamID() {
		return eventStreamIndex;
	}
}
