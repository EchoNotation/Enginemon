package util;

import main.Game.MoveDirection;

public class Variables {
	
	//Location:
	public static int currentRegionID;
	public static int currentMapID;
	
	//World Movement:
	public static boolean lockPlayerMovement = false;
	public static boolean movingOverTile = false;
	public static int movementOffset = 0;
	public static int tileCrossSpeed = 0;
	public static MoveDirection moveDir = MoveDirection.NONE;
	
}