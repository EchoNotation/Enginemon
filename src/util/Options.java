package util;

import java.awt.event.KeyEvent;

import io.ControllerManager.Actions;

public class Options {

	//Key & Controller Bindings
	public static int upKey = KeyEvent.VK_UP;
	public static int downKey = KeyEvent.VK_DOWN;
	public static int leftKey = KeyEvent.VK_LEFT;
	public static int rightKey = KeyEvent.VK_RIGHT;
	public static int confirmKey = KeyEvent.VK_X;
	public static int cancelKey = KeyEvent.VK_Z;
	public static int menuKey = KeyEvent.VK_S;
	public static int functionKey = KeyEvent.VK_A;
	public static Actions xAxisPos = Actions.UNASSIGNED;
	public static Actions xAxisNeg = Actions.UNASSIGNED;
	public static Actions yAxisPos = Actions.UNASSIGNED; 
	public static Actions yAxisNeg = Actions.UNASSIGNED;
	public static Actions x2AxisPos = Actions.UNASSIGNED;
	public static Actions x2AxisNeg = Actions.UNASSIGNED;
	public static Actions y2AxisPos = Actions.UNASSIGNED;
	public static Actions y2AxisNeg = Actions.UNASSIGNED;
	public static Actions zAxisPos = Actions.UNASSIGNED;
	public static Actions zAxisNeg = Actions.UNASSIGNED;
	public static Actions povTL = Actions.UNASSIGNED;
	public static Actions povTM = Actions.UNASSIGNED;
	public static Actions povTR = Actions.UNASSIGNED;
	public static Actions povR = Actions.UNASSIGNED;
	public static Actions povBR = Actions.UNASSIGNED;
	public static Actions povBM = Actions.UNASSIGNED;
	public static Actions povBL = Actions.UNASSIGNED;
	public static Actions povL = Actions.UNASSIGNED;
	public static Actions[] buttons = new Actions[20];
	
	//Other options:
	public static int framesPerChar = 1;
	
}