package io;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import util.ControlMap;
import util.Variables;

public class ControllerManager {
	public enum Actions {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		CONFIRM,
		CANCEL,
		MENU,
		FUNCTION,
		UNASSIGNED;
	}
	
	public enum Controls {
		X_AXIS_POS,
		X_AXIS_NEG,
		Y_AXIS_POS,
		Y_AXIS_NEG,
		Z_AXIS_POS,
		Z_AXIS_NEG,
		X2_AXIS_POS,
		X2_AXIS_NEG,
		Y2_AXIS_POS,
		Y2_AXIS_NEG,
		POV_TL,
		POV_TM,
		POV_TR,
		POV_R,
		POV_BR,
		POV_BM,
		POV_BL,
		POV_L,
		BUTTON;
	}
	
	private boolean upS, downS, leftS, rightS, confirmS, cancelS, menuS, functionS;
	public boolean upF, downF, leftF, rightF, confirmF, cancelF, menuF, functionF;
	public boolean upR, downR, leftR, rightR, confirmR, cancelR, menuR, functionR;
	private Controller[] controllers;

	public ControllerManager() {
		controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		upS = true;
		downS = true;
		leftS = true;
		rightS = true;
		confirmS = true;
		cancelS = true;
		menuS = true;
		functionS = true;
		
		Variables.numberOfConnectedControllers = controllers.length;
		
		for(int i = 0; i < ControlMap.buttons.length; i++) {
			ControlMap.buttons[i] = Actions.UNASSIGNED;
		}
		
		loadControllerSettings();
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
		
		Event event = new Event();
		
		for (int i = 0; i < controllers.length; i++) {
			//System.out.println("Found controller " + i + " Type: " + controllers[i].getType());
			if(controllers[i].getType() != Controller.Type.GAMEPAD) continue;
			
		    /* Remember to poll each one */
		    controllers[i].poll();

		    /* Get the controllers event queue */
		    EventQueue queue = controllers[i].getEventQueue();

		    /* For each object in the queue */
		    while (queue.getNextEvent(event)) {
		        /* Get event component */
		        Component comp = event.getComponent();
		        //System.out.println(comp.getIdentifier());	        
		        //System.out.println(comp.getPollData());
		        float value = comp.getPollData();
		        Actions action = Actions.UNASSIGNED;
		        boolean pressed = false;
		        
		        if(comp.getIdentifier().toString().compareTo("x") == 0) {
		        	//x axis
		        	if(value > 0.75) {
		        		action = ControlMap.xAxisPos;
		        		pressed = true;
		        	}
		        	else if(value < -0.75) {
		        		action = ControlMap.xAxisNeg;
		        		pressed = true;
		        	}
		        	else {
		        		manageControls(ControlMap.xAxisPos, false);
		        		
		        		action = ControlMap.xAxisNeg;
		        		pressed = false;
		        	}
		        }
		        else if(comp.getIdentifier().toString().compareTo("y") == 0) {
		        	//y axis
		        	if(value > 0.75) {
		        		action = ControlMap.yAxisPos;
		        		pressed = true;
		        	}
		        	else if(value < -0.75) {
		        		action = ControlMap.yAxisNeg;
		        		pressed = true;
		        	}
		        	else {
		        		manageControls(ControlMap.yAxisPos, false);
		        		
		        		action = ControlMap.yAxisNeg;
		        		pressed = false;
		        	}
		        }
		        else if(comp.getIdentifier().toString().compareTo("rx") == 0) {
		        	//x2 axis
		        	if(value > 0.75) {
		        		action = ControlMap.x2AxisPos;
		        		pressed = true;
		        	}
		        	else if(value < -0.75) {
		        		action = ControlMap.x2AxisNeg;
		        		pressed = true;
		        	}
		        	else {
		        		manageControls(ControlMap.x2AxisPos, false);
		        		
		        		action = ControlMap.x2AxisNeg;
		        		pressed = false;
		        	}
		        }
		        else if(comp.getIdentifier().toString().compareTo("ry") == 0) {
		        	//y2 axis
		        	if(value > 0.75) {
		        		action = ControlMap.y2AxisPos;
		        		pressed = true;
		        	}
		        	else if(value < -0.75) {
		        		action = ControlMap.y2AxisNeg;
		        		pressed = true;
		        	}
		        	else {
		        		manageControls(ControlMap.y2AxisPos, false);
		        		
		        		action = ControlMap.y2AxisNeg;
		        		pressed = false;
		        	}
		        }
		        else if(comp.getIdentifier().toString().compareTo("z") == 0) {
		        	//z axis
		        	if(value > 0.75) {
		        		action = ControlMap.zAxisPos;
		        		pressed = true;
		        	}
		        	else if(value < -0.75) {
		        		action = ControlMap.zAxisNeg;
		        		pressed = true;
		        	}
		        	else {
		        		manageControls(ControlMap.zAxisPos, false);
		        		
		        		action = ControlMap.zAxisNeg;
		        		pressed = false;
		        	}
		        }
		        else if(comp.getIdentifier().toString().compareTo("pov") == 0) {
		        	//hat switch
		        	if(value == Component.POV.CENTER) {
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povL;
		        		pressed = false;
		        	}
		        	else if(value == Component.POV.UP_LEFT) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povTL;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.UP) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povTM;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.UP_RIGHT) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povTR;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.RIGHT) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povR;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.DOWN_RIGHT) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povBR;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.DOWN) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povBM;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.DOWN_LEFT) {
		        		manageControls(ControlMap.povL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povTL, false);
		        		
		        		action = ControlMap.povBL;
		        		pressed = true;
		        	}
		        	else if(value == Component.POV.LEFT) {
		        		manageControls(ControlMap.povTL, false);
		        		manageControls(ControlMap.povTM, false);
		        		manageControls(ControlMap.povTR, false);
		        		manageControls(ControlMap.povR, false);
		        		manageControls(ControlMap.povBR, false);
		        		manageControls(ControlMap.povBM, false);
		        		manageControls(ControlMap.povBL, false);
		        		
		        		action = ControlMap.povL;
		        		pressed = true;
		        	}
		        }
		        else {
		        	//Some button
		        	action = ControlMap.buttons[Integer.parseInt(comp.getIdentifier().toString())];
		        	pressed = value == 1.0 ? true : false;
		        }
		        
		        manageControls(action, pressed);
		    }
		}
	}
	
	private void manageControls(Actions action, boolean pressed) {
		switch(action) {
		case UP:
			upR = pressed;
			
			if(!pressed) {
				upS = true;
			}
			else {
				upF = true;
				upS = false;
			}
			break;
		case DOWN:
			downR = pressed;
			
			if(!pressed) {
				downS = true;
			}
			else {
				downF = true;
				downS = false;
			}
			break;
		case LEFT:
			leftR = pressed;
			
			if(!pressed) {
				leftS = true;
			}
			else {
				leftF = true;
				leftS = false;
			}
			break;
		case RIGHT:
			rightR = pressed;
			
			if(!pressed) {
				rightS = true;
			}
			else {
				rightF = true;
				rightS = false;
			}
			break;
		case CONFIRM:
			confirmR = pressed;
			
			if(!pressed) {
				confirmS = true;
			}
			else {
				confirmF = true;
				confirmS = false;
			}
			break;
		case CANCEL:
			cancelR = pressed;
			
			if(!pressed) {
				cancelS = true;
			}
			else {
				cancelF = true;
				cancelS = false;
			}
			break;
		case MENU:
			menuR = pressed;
			
			if(!pressed) {
				menuS = true;
			}
			else {
				menuF = true;
				menuS = false;
			}
			break;
		case FUNCTION:
			functionR = pressed;
			
			if(!pressed) {
				functionS = true;
			}
			else {
				functionF = true;
				functionS = false;
			}
			break;
		case UNASSIGNED:
			break;
		default:
			System.out.println("Invalid action in manageControls! Action: " + action);
			break;
		}
	}
	
	public void loadControllerSettings() {
		ControlMap.povTM = Actions.UP;
		ControlMap.povL = Actions.LEFT;
		ControlMap.povR = Actions.RIGHT;
		ControlMap.povBM = Actions.DOWN;
		ControlMap.buttons[0] = Actions.CANCEL;
		ControlMap.buttons[1] = Actions.CONFIRM;
		ControlMap.buttons[2] = Actions.FUNCTION;
		ControlMap.buttons[3] = Actions.MENU;
	}
	
	public void rebindControl(Actions action, Controls control, int buttonID) {
		switch(control) {
		case BUTTON:
			ControlMap.buttons[buttonID] = action;
			break;
		case POV_BL:
			ControlMap.povBL = action;
			break;
		case POV_BM:
			ControlMap.povBM = action;
			break;
		case POV_BR:
			ControlMap.povBR = action;
			break;
		case POV_L:
			ControlMap.povL = action;
			break;
		case POV_R:
			ControlMap.povR = action;
			break;
		case POV_TL:
			ControlMap.povTL = action;
			break;
		case POV_TM:
			ControlMap.povTM = action;
			break;
		case POV_TR:
			ControlMap.povTR = action;
			break;
		case X2_AXIS_NEG:
			ControlMap.x2AxisNeg = action;
			break;
		case X2_AXIS_POS:
			ControlMap.x2AxisPos = action;
			break;
		case X_AXIS_NEG:
			ControlMap.xAxisNeg = action;
			break;
		case X_AXIS_POS:
			ControlMap.xAxisPos = action;
			break;
		case Y2_AXIS_NEG:
			ControlMap.y2AxisNeg = action;
			break;
		case Y2_AXIS_POS:
			ControlMap.y2AxisPos = action;
			break;
		case Y_AXIS_NEG:
			ControlMap.yAxisNeg = action;
			break;
		case Y_AXIS_POS:
			ControlMap.yAxisPos = action;
			break;
		case Z_AXIS_NEG:
			ControlMap.zAxisNeg = action;
			break;
		case Z_AXIS_POS:
			ControlMap.zAxisPos = action;
			break;
		default:
			break;
		
		}
	}
}
