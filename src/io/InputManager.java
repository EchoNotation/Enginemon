package io;

import util.Constants;

public class InputManager {
	private KeyManager keys;
	private ControllerManager controller;
	private int[] inputTimeouts;
	
	public boolean upF, downF, leftF, rightF, confirmF, cancelF, menuF, functionF;
	public boolean upR, downR, leftR, rightR, confirmR, cancelR, menuR, functionR;
	public boolean up, down, left, right, confirm, cancel, menu, function;
	
	public InputManager(KeyManager keys, ControllerManager controller) {
		this.keys = keys;
		this.controller = controller;
		inputTimeouts = new int[8];
	}

	public void tick() {
		upF = keys.upF || controller.upF;
		downF = keys.downF || controller.downF;
		leftF = keys.leftF || controller.leftF;
		rightF = keys.rightF || controller.rightF;
		confirmF = keys.confirmF || controller.confirmF;
		cancelF = keys.cancelF || controller.cancelF;
		menuF = keys.menuF || controller.menuF;
		functionF = keys.functionF || controller.functionF;
		
		upR = keys.upR || controller.upR;
		downR = keys.downR || controller.downR;
		leftR = keys.leftR || controller.leftR;
		rightR = keys.rightR || controller.rightR;
		confirmR = keys.confirmR || controller.confirmR;
		cancelR = keys.cancelR || controller.cancelR;
		menuR = keys.menuR || controller.menuR;
		functionR = keys.functionR || controller.functionR;

		if(!upF && upR) inputTimeouts[0]++; else if(!upR) inputTimeouts[0] = 0;
		if(!downF && downR) inputTimeouts[1]++; else if(!downR) inputTimeouts[1] = 0;
		if(!leftF && leftR) inputTimeouts[2]++; else if(!leftR) inputTimeouts[2] = 0;
		if(!rightF && rightR) inputTimeouts[3]++; else if(!rightR) inputTimeouts[3] = 0;
		if(!confirmF && confirmR) inputTimeouts[4]++; else if(!confirmR) inputTimeouts[4] = 0;
		if(!cancelF && cancelR) inputTimeouts[5]++; else if(!cancelR) inputTimeouts[5] = 0;
		if(!menuF && menuR) inputTimeouts[6]++; else if(!menuR) inputTimeouts[6] = 0;
		if(!functionF && functionR) inputTimeouts[7]++; else if(!functionR) inputTimeouts[7] = 0;
		
		int frameDelay = Constants.frameInputDelay;
		up = upF || inputTimeouts[0] > frameDelay;
		down = downF || inputTimeouts[1] > frameDelay;
		left = leftF || inputTimeouts[2] > frameDelay;
		right = rightF || inputTimeouts[3] > frameDelay;
		confirm = confirmF || inputTimeouts[4] > frameDelay;
		cancel = cancelF || inputTimeouts[5] > frameDelay;
		menu = menuF || inputTimeouts[6] > frameDelay;
		function = functionF || inputTimeouts[7] > frameDelay;
		
		//System.out.println("Up: " + upR + " Down: " + downR + " Left: " + leftR + " Right: " + rightR + " Confirm: " + confirmR + " Cancel: " + cancelR + " Menu: " + menuR + " Function: " + functionR);
		
		keys.tick();
		controller.tick();		
	}
	
}
