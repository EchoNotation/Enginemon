package events;

import util.MapLoader;

public class WarpEvent extends Event {
	private int regionID, mapID, destX, destY;
	
	/**
	 * Creates a new sequential WarpEvent.
	 * @param inputIndex The index of the sequential outputs to use as input.
	 * @param regionID The ID of the region to select the map from.
	 * @param mapID The ID of the map to warp to.
	 * @param destX The x coordinate of the player on arrival.
	 * @param destY The y coordinate of the player on arrival.
	 */
	public WarpEvent(int inputIndex, int regionID, int mapID, int destX, int destY) {
		super(inputIndex);
		this.regionID = regionID;
		this.mapID = mapID;
		this.destX = destX;
		this.destY = destY;
	}
	
	/**
	 * Runs once when it is time to warp.
	 * @param input The input value for this WarpEvent.
	 */
	@Override
	public void init(int input) {
		inputData = input;
		hasStarted = true;
		readyToFinish = true;
		//MapLoader.swapToMap(regionID, mapID);
	}
}
