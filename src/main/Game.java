package main;

import entities.Player;
import io.Camera;
import io.ControllerManager;
import io.InputManager;
import io.KeyManager;
import io.Window;
import io.Camera.CameraMode;
import util.Constants;
import util.MapLoader;
import util.Tilesets;
import util.Variables;

public class Game {
	public enum GameState {
		MENU,
		WORLD,
		BATTLE;
	}
	
	public enum MoveDirection {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NONE;
	}
	
	private boolean running;
	private GameState gameState;
	private Window window;
	private KeyManager keys;
	private ControllerManager controller;
	private InputManager input;
	private Player player;
	private Camera camera;
	
	private int[][] collisionData;

	public Game() {
		keys = new KeyManager();
		controller = new ControllerManager();
		input = new InputManager(keys, controller);
		window = new Window("Enginemon", 720, 624, keys);
	}
	
	/**
	 * Called to start the game immediately after construction.
	 */
	public void init() {		
		running = true;
		gameState = GameState.WORLD;
		
		MapLoader.loadMap(1, 2);
		MapLoader.swapToMap(1, 2);
		Tilesets.loadTilesets();
		Tilesets.changeTileset(0);
		player = new Player();
		camera = new Camera(player);
		camera.changeCameraMode(CameraMode.FOCUS_ON_PLAYER);
		player.setXPos(1);
		player.setYPos(3);
		player.setTgtXPos(1);
		player.setTgtYPos(3);
		run();
	}
	
	/**
	 * Called after start, contains the main game loop and logic for maintaining a reasonably constant framerate.
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
			collisionData = MapLoader.currentCollisionData;
			
			if(!Variables.lockPlayerMovement) {
				standardPlayerMovement();
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
	 * Calling this function will update the movement of the player appropriately in most situations, except during events or other scripted sequences.
	 */
	public void standardPlayerMovement() {
		boolean upPassable, downPassable, leftPassable, rightPassable;
		int upCollision, downCollision, leftCollision, rightCollision;
		
		if(Variables.movingOverTile) {
			if(Variables.movementOffset >= Constants.pixelsPerTile) {
				player.setXPos(player.getTgtXPos());
				player.setYPos(player.getTgtYPos());
				Variables.movementOffset = 0;
				Variables.movingOverTile = false;
				Variables.moveDir = MoveDirection.NONE;
				
				if(input.upR) {
					upCollision = getCollisionID(player.getXPos(), player.getYPos() - 1);
					upPassable = parseCollision(upCollision, MoveDirection.UP);
					
					if(upPassable) {
						player.setTgtYPos(player.getYPos() - 1);
						Variables.movingOverTile = true;
						Variables.moveDir = MoveDirection.UP;
					}					
				}
				else if(input.downR) {
					downCollision = getCollisionID(player.getXPos(), player.getYPos() + 1);
					downPassable = parseCollision(downCollision, MoveDirection.DOWN);
					
					if(downPassable) {
						player.setTgtYPos(player.getYPos() + 1);
						Variables.movingOverTile = true;
						Variables.moveDir = MoveDirection.DOWN;
					}				
				}
				else if(input.leftR) {
					leftCollision = getCollisionID(player.getXPos() - 1, player.getYPos());
					leftPassable = parseCollision(leftCollision, MoveDirection.LEFT);
					
					if(leftPassable) {
						player.setTgtXPos(player.getXPos() - 1);
						Variables.movingOverTile = true;
						Variables.moveDir = MoveDirection.LEFT;
					}				
				}
				else if(input.rightR) {
					rightCollision = getCollisionID(player.getXPos() + 1, player.getYPos());	
					rightPassable = parseCollision(rightCollision, MoveDirection.RIGHT);
					
					if(rightPassable) {
						player.setTgtXPos(player.getXPos() + 1);
						Variables.movingOverTile = true;
						Variables.moveDir = MoveDirection.RIGHT;
					}				
				}
				
				if(Variables.movingOverTile) {
					Variables.tileCrossSpeed = input.cancelR ? Constants.pixelsPerFrameRunning : Constants.pixelsPerFrameWalking;
				}
			}
			else {
				Variables.movementOffset += Variables.tileCrossSpeed;
			}
		}
		else {
			if(input.upR) {
				upCollision = getCollisionID(player.getXPos(), player.getYPos() - 1);
				upPassable = parseCollision(upCollision, MoveDirection.UP);
				
				if(upPassable) {
					player.setTgtYPos(player.getYPos() - 1);
					Variables.movingOverTile = true;
					Variables.moveDir = MoveDirection.UP;
				}		
			}
			else if(input.downR) {
				downCollision = getCollisionID(player.getXPos(), player.getYPos() + 1);
				downPassable = parseCollision(downCollision, MoveDirection.DOWN);
				
				if(downPassable) {
					player.setTgtYPos(player.getYPos() + 1);
					Variables.movingOverTile = true;
					Variables.moveDir = MoveDirection.DOWN;
				}			
			}
			else if(input.leftR) {
				leftCollision = getCollisionID(player.getXPos() - 1, player.getYPos());
				leftPassable = parseCollision(leftCollision, MoveDirection.LEFT);
				
				if(leftPassable) {
					player.setTgtXPos(player.getXPos() - 1);
					Variables.movingOverTile = true;
					Variables.moveDir = MoveDirection.LEFT;
				}			
			}
			else if(input.rightR) {
				rightCollision = getCollisionID(player.getXPos() + 1, player.getYPos());	
				rightPassable = parseCollision(rightCollision, MoveDirection.RIGHT);
				
				if(rightPassable) {
					player.setTgtXPos(player.getXPos() + 1);
					Variables.movingOverTile = true;
					Variables.moveDir = MoveDirection.RIGHT;
				}			
			}
			
			if(Variables.movingOverTile) {
				Variables.tileCrossSpeed = input.cancelR ? Constants.pixelsPerFrameRunning : Constants.pixelsPerFrameWalking;
			}
		}		
	}
	
	/**
	 * Determines whether the given collisionID can be walked onto from a particular direction.
	 * @param collisionID The ID to check.
	 * @param dir The direction to walk onto that tile from.
	 * @return Whether or not this direction is passable.
	 */
	private boolean parseCollision(int collisionID, MoveDirection dir) {
		switch(collisionID) {
		case 0:
			//Always passable
			return true;
		case 1:
			//Always impassable
			return false;
		case 2:
			//TL Ledge
			return dir == MoveDirection.UP || dir == MoveDirection.LEFT;
		case 3:
			//T Ledge
			return dir == MoveDirection.UP || dir == MoveDirection.LEFT || dir == MoveDirection.RIGHT;
		case 4:
			//TR Ledge
			return dir == MoveDirection.UP || dir == MoveDirection.RIGHT;
		case 5:
			//R Ledge
			return dir == MoveDirection.UP || dir == MoveDirection.RIGHT || dir == MoveDirection.DOWN;
		case 6:
			//BR Ledge
			return dir == MoveDirection.RIGHT || dir == MoveDirection.DOWN;
		case 7:
			//B Ledge
			return dir == MoveDirection.LEFT || dir == MoveDirection.DOWN || dir == MoveDirection.RIGHT;
		case 8:
			//BL Ledge
			return dir == MoveDirection.DOWN || dir == MoveDirection.RIGHT;
		case 9:
			//L Ledge
			return dir == MoveDirection.UP || dir == MoveDirection.DOWN || dir == MoveDirection.LEFT;
		default:
			System.out.println("Unrecognized collision ID! ID: " + collisionID);
			return true;
		}		
	}
	
	private int getCollisionID(int xPos, int yPos) {
		if(xPos < 0 || xPos >= collisionData[0].length || yPos < 0 || yPos >= collisionData.length) return 0;
		
		return collisionData[yPos][xPos];
	}
	
	/**
	 * Returns the current GameState.
	 * @return the current GameState.
	 */
	public GameState getGameState() {
		return gameState;
	}
}
