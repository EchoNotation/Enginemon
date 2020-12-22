package util;

public class Constants {

	//Input constants
	public static final int frameInputDelay = 60;
	
	//Graphics constants
	public static final int pixelsPerTile = 16;	
	public static final int cameraTileWidth = 17; //Actual dimensions are 15x13 when standing still, but extra border is needed for smooth scrolling.
	public static final int cameraTileHeight = 15;
	public static final int framesToDisplayText = 3;
	
	//World Movement
	public static final int pixelsPerFrameWalking = 1;
	public static final int pixelsPerFrameRunning = 2;
	
	//Option related constants
	public static final int slowTextSpeed = 2;
	public static final int mediumTextSpeed = 1;
	public static final int fastTextSpeed = 0;
}
