package util;

import java.awt.event.KeyEvent;

import io.ControllerManager.Controls;

public class ControlMap {

	//Key & Controller Bindings
	public static int upKey = KeyEvent.VK_UP;
	public static int downKey = KeyEvent.VK_DOWN;
	public static int leftKey = KeyEvent.VK_LEFT;
	public static int rightKey = KeyEvent.VK_RIGHT;
	public static int confirmKey = KeyEvent.VK_X;
	public static int cancelKey = KeyEvent.VK_Z;
	public static int menuKey = KeyEvent.VK_S;
	public static int functionKey = KeyEvent.VK_A;
	public static Controls xAxisPos = Controls.UNASSIGNED;
	public static Controls xAxisNeg = Controls.UNASSIGNED;
	public static Controls yAxisPos = Controls.UNASSIGNED; 
	public static Controls yAxisNeg = Controls.UNASSIGNED;
	public static Controls x2AxisPos = Controls.UNASSIGNED;
	public static Controls x2AxisNeg = Controls.UNASSIGNED;
	public static Controls y2AxisPos = Controls.UNASSIGNED;
	public static Controls y2AxisNeg = Controls.UNASSIGNED;
	public static Controls zAxisPos = Controls.UNASSIGNED;
	public static Controls zAxisNeg = Controls.UNASSIGNED;
	public static Controls povTL = Controls.UNASSIGNED;
	public static Controls povTM = Controls.UNASSIGNED;
	public static Controls povTR = Controls.UNASSIGNED;
	public static Controls povR = Controls.UNASSIGNED;
	public static Controls povBR = Controls.UNASSIGNED;
	public static Controls povBM = Controls.UNASSIGNED;
	public static Controls povBL = Controls.UNASSIGNED;
	public static Controls povL = Controls.UNASSIGNED;
	public static Controls[] buttons = new Controls[20];
	
}