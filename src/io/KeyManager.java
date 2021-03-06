package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import util.Options;

public class KeyManager implements KeyListener {
	private boolean upS, downS, leftS, rightS, confirmS, cancelS, menuS, functionS;
	public boolean upF, downF, leftF, rightF, confirmF, cancelF, menuF, functionF;
	public boolean upR, downR, leftR, rightR, confirmR, cancelR, menuR, functionR;
	
	public KeyManager() {
		upS = true;
		downS = true;
		leftS = true;
		rightS = true;
		confirmS = true;
		cancelS = true;
		menuS = true;
		functionS = true;
	}
	
	public void tick() {
		if(!upS) {
			upF = false;
		}
		if(!downS) {
			downF = false;
		}
		if(!leftS) {
			leftF = false;
		}
		if(!rightS) {
			rightF = false;
		}
		if(!confirmS) {
			confirmF = false;
		}
		if(!cancelS) {
			cancelF = false;
		}
		if(!menuS) {
			menuF = false;
		}
		if(!functionS) {
			functionF = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {		
		if(e.getKeyCode() == Options.upKey) {
			upR = true;
			
			if(upS) {
				upF = true;
				upS = false;
			}
		}
		else if(e.getKeyCode() == Options.downKey) {
			downR = true;
			
			if(downS) {
				downF = true;
				downS = false;
			}
		}
		else if(e.getKeyCode() == Options.leftKey) {
			leftR = true;
			
			if(leftS) {
				leftF = true;
				leftS = false;
			}
		}
		else if(e.getKeyCode() == Options.rightKey) {
			rightR = true;
			
			if(rightS) {
				rightF = true;
				rightS = false;
			}
		}
		else if(e.getKeyCode() == Options.confirmKey) {
			confirmR = true;
			
			if(confirmS) {
				confirmF = true;
				confirmS = false;
			}
		}
		else if(e.getKeyCode() == Options.cancelKey) {
			cancelR = true;
			
			if(cancelS) {
				cancelF = true;
				cancelS = false;
			}
		}
		else if(e.getKeyCode() == Options.menuKey) {
			menuR = true;
			
			if(menuS) {
				menuF = true;
				menuS = false;
			}
		}
		else if(e.getKeyCode() == Options.functionKey) {
			functionR = true;
			
			if(functionS) {
				functionF = true;
				functionS = false;
			}
		}
		else {
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == Options.upKey) {
			upR = false;
			upS = true;
		}
		else if(e.getKeyCode() == Options.downKey) {
			downR = false;
			downS = true;
		}
		else if(e.getKeyCode() == Options.leftKey) {
			leftR = false;
			leftS = true;
		}
		else if(e.getKeyCode() == Options.rightKey) {
			rightR = false;
			rightS = true;
		}
		else if(e.getKeyCode() == Options.confirmKey) {
			confirmR = false;
			confirmS = true;
		}
		else if(e.getKeyCode() == Options.cancelKey) {
			cancelR = false;
			cancelS = true;
		}
		else if(e.getKeyCode() == Options.menuKey) {
			menuR = false;
			menuS = true;
		}
		else if(e.getKeyCode() == Options.functionKey) {
			functionR = false;
			functionS = true;
		}
		else {
			
		}
	}

}
