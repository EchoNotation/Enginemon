package util;

import main.Game.MoveDirection;

public class Variables {
	
	//Make sure to change this whenever building as a runnable JAR
	public static boolean runningAsJar = false;
	
	//Debug
	public static int numberOfConnectedControllers = 0;
	
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