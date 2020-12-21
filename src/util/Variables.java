package util;

import main.Game.MoveDirection;

public class Variables {
	
	//Make sure to change this whenever building as a runnable JAR
	public static boolean runningAsJar = false;
	
	//Debug:
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
	
	//Text Displaying:
	public static boolean displayingText = false;
	public static boolean readyForNextTextBox = false;
	public static boolean messageCompleted = false;
	public static boolean displayTextOptions = false;
	public static boolean waitingAfterSelection = false;
	public static String currentMessage = "Message was not set!";
	public static String[] textOptions = new String[0];
	public static int chosenTextOption = -1;
}