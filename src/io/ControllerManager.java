package io;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerManager {
	private boolean upS, downS, leftS, rightS, confirmS, cancelS, menuS, functionS;
	public boolean upF, downF, leftF, rightF, confirmF, cancelF, menuF, functionF;
	public boolean upR, downR, leftR, rightR, confirmR, cancelR, menuR, functionR;

	public ControllerManager() {
		
	}
	
	public void tick() {
		Event event = new Event();
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
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

		        /* Process event (your awesome code) */
		    }
		}
	}
	
}
