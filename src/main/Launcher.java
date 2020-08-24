package main;

import util.Variables;

public class Launcher {

	public static void main(String[] args) {	
		Variables.runningAsJar = false;
		
		Game game = new Game();
		game.init();
	}
	
}
