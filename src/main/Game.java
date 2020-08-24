package main;

import entities.Player;
import io.Camera;
import io.ControllerManager;
import io.InputManager;
import io.KeyManager;
import io.Window;
import io.Camera.CameraMode;
import util.MapLoader;
import util.Tilesets;

public class Game {
	public enum GameState {
		MENU,
		WORLD,
		BATTLE;
	}
	
	private boolean running;
	private GameState gameState;
	private Window window;
	private KeyManager keys;
	private ControllerManager controller;
	private InputManager input;
	private Player player;
	private Camera camera;

	public Game() {
		keys = new KeyManager();
		controller = new ControllerManager();
		input = new InputManager(keys, controller);
		window = new Window("Enginemon", 640, 480, keys);
	}
	
	/**
	 * Called to start the game immediately after construction.
	 */
	public void init() {
		running = true;
		gameState = GameState.WORLD;
		MapLoader.loadMap(1, 1);
		MapLoader.swapToMap(1, 1);
		Tilesets.loadTilesets();
		Tilesets.changeTileset(0);
		player = new Player();
		camera = new Camera(player);
		camera.changeCameraMode(CameraMode.FOCUS_ON_PLAYER);
		player.setXPos(0);
		player.setYPos(0);
		run();
	}
	
	/**
	 * Called after start, contains the main game loop and fps logic.
	 */
	private void run() {
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		long timer = 0;
		int frames = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1){
				tick();
				frames++;
				delta--;
				//System.out.println(delta); //Use this to measure overall runtime lost.
			}
			
			if(timer > 1000000000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = 0;
			}
		}
		
		shutdown();
	}
	
	/**
	 * Stops game execution after current frame.
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Run after last tick.
	 */
	private void shutdown() {
		
	}
	
	/**
	 * Called exactly once every frame, all game and render logic starts here.
	 */
	private void tick() {
		switch(gameState) {
		case WORLD:
			if(input.up) {
				player.setYPos(player.getYPos() - 1);
			}
			if(input.down) {
				player.setYPos(player.getYPos() + 1);
			}
			if(input.left) {
				player.setXPos(player.getXPos() - 1);
			}
			if(input.right) {
				player.setXPos(player.getXPos() + 1);
			}
			break;
		case BATTLE:
			break;
		case MENU:
			break;
		default:
			break;
		}
		
		player.tick();
		camera.tick();
		window.tick(gameState, camera);
		input.tick();
	}
	
	/**
	 * Returns the current GameState.
	 * @return the current GameState.
	 */
	public GameState getGameState() {
		return gameState;
	}
}
