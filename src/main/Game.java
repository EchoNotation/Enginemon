package main;

import entities.Player;
import events.EventSequencer;
import events.EventTable;
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
	private Player player;
	private Camera camera;
	
	private int[][] collisionData;
	private int[][] eventIndices;

	public Game() {
		keys = new KeyManager();
		controller = new ControllerManager();
		InputManager.init(keys, controller);
		window = new Window("Enginemon", 720, 624, keys);
	}
	
	/**
	 * Called to start the game immediately after construction.
	 */
	public void init() {		
		running = true;
		gameState = GameState.WORLD;
		
		MapLoader.loadMap(1, 2);
		EventTable.loadEvents(1, 2);
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
		
		collisionData = MapLoader.currentCollisionData;
		eventIndices = new int[collisionData.length][];
		//foundIndices = new ArrayList<Integer>();
		//foundIndices.add(0);
		
		for(int i = 0; i < collisionData.length; i++) {
			eventIndices[i] = new int[collisionData[i].length];
			for(int j = 0; j < eventIndices[i].length; j++) {			
				eventIndices[i][j] = -1;
			}
		}
		EventSequencer.enqueueEventStream(EventTable.getEventStream(1, 2, 0));
		EventSequencer.enqueueEventStream(EventTable.getEventStream(1, 2, 1));
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
		//System.out.println("Tick!");
		EventSequencer.tick();
		switch(gameState) {
		case WORLD:
			if(InputManager.function) {
				if(camera.getCameraMode() == CameraMode.FOCUS_ON_PLAYER) {
					camera.changeCameraMode(CameraMode.FREE);
				}
				else {
					camera.changeCameraMode(CameraMode.FOCUS_ON_PLAYER);
				}
			}
			
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
		window.tick(gameState, camera, player);
		InputManager.tick();
	}
	
	/**
	 * Calling this function will update the movement of the player appropriately in most situations, except during events or other scripted sequences.
	 */
	public void standardPlayerMovement() {
		boolean upPassable, downPassable, leftPassable, rightPassable;
		int upCollision, downCollision, leftCollision, rightCollision;
		
		if(Variables.playerMovingOverTile) {
			if(player.getMovementOffset() >= Constants.pixelsPerTile) {
				player.setXPos(player.getTgtXPos());
				player.setYPos(player.getTgtYPos());
				player.setMovementOffset(0);
				Variables.playerMovingOverTile = false;
				player.setMoveDirection(MoveDirection.NONE);
				
				if(!(player.getXPos() < 0 || player.getXPos() >= eventIndices[0].length || player.getYPos() < 0 || player.getYPos() >= eventIndices.length)) {
					if(EventSequencer.numberOfEnqueuedStreams() == 0) {
						//System.out.println("Enqueued by step!");
						EventSequencer.enqueueEventStream(EventTable.getEventStream(eventIndices[player.getYPos()][player.getXPos()]));
					}
				}
				
				if(InputManager.upR) {
					upCollision = getCollisionID(player.getXPos(), player.getYPos() - 1);
					upPassable = parseCollision(upCollision, MoveDirection.UP);
					
					if(upPassable) {
						player.setTgtYPos(player.getYPos() - 1);
						Variables.playerMovingOverTile = true;
						player.setMoveDirection(MoveDirection.UP);
					}					
				}
				else if(InputManager.downR) {
					downCollision = getCollisionID(player.getXPos(), player.getYPos() + 1);
					downPassable = parseCollision(downCollision, MoveDirection.DOWN);
					
					if(downPassable) {
						player.setTgtYPos(player.getYPos() + 1);
						Variables.playerMovingOverTile = true;
						player.setMoveDirection(MoveDirection.DOWN);
					}				
				}
				else if(InputManager.leftR) {
					leftCollision = getCollisionID(player.getXPos() - 1, player.getYPos());
					leftPassable = parseCollision(leftCollision, MoveDirection.LEFT);
					
					if(leftPassable) {
						player.setTgtXPos(player.getXPos() - 1);
						Variables.playerMovingOverTile = true;
						player.setMoveDirection(MoveDirection.LEFT);
					}				
				}
				else if(InputManager.rightR) {
					rightCollision = getCollisionID(player.getXPos() + 1, player.getYPos());	
					rightPassable = parseCollision(rightCollision, MoveDirection.RIGHT);
					
					if(rightPassable) {
						player.setTgtXPos(player.getXPos() + 1);
						Variables.playerMovingOverTile = true;
						player.setMoveDirection(MoveDirection.RIGHT);
					}				
				}
				
				if(Variables.playerMovingOverTile) {
					Variables.playerTileCrossSpeed = InputManager.cancelR ? Constants.pixelsPerFrameRunning : Constants.pixelsPerFrameWalking;
				}
			}
			else {
				player.setMovementOffset(player.getMovementOffset() + Variables.playerTileCrossSpeed);
			}
		}
		else {
			if(InputManager.upR) {
				upCollision = getCollisionID(player.getXPos(), player.getYPos() - 1);
				upPassable = parseCollision(upCollision, MoveDirection.UP);
				
				if(upPassable) {
					player.setTgtYPos(player.getYPos() - 1);
					Variables.playerMovingOverTile = true;
					player.setMoveDirection(MoveDirection.UP);
				}		
			}
			else if(InputManager.downR) {
				downCollision = getCollisionID(player.getXPos(), player.getYPos() + 1);
				downPassable = parseCollision(downCollision, MoveDirection.DOWN);
				
				if(downPassable) {
					player.setTgtYPos(player.getYPos() + 1);
					Variables.playerMovingOverTile = true;
					player.setMoveDirection(MoveDirection.DOWN);
				}			
			}
			else if(InputManager.leftR) {
				leftCollision = getCollisionID(player.getXPos() - 1, player.getYPos());
				leftPassable = parseCollision(leftCollision, MoveDirection.LEFT);
				
				if(leftPassable) {
					player.setTgtXPos(player.getXPos() - 1);
					Variables.playerMovingOverTile = true;
					player.setMoveDirection(MoveDirection.LEFT);
				}			
			}
			else if(InputManager.rightR) {
				rightCollision = getCollisionID(player.getXPos() + 1, player.getYPos());	
				rightPassable = parseCollision(rightCollision, MoveDirection.RIGHT);
				
				if(rightPassable) {
					player.setTgtXPos(player.getXPos() + 1);
					Variables.playerMovingOverTile = true;
					player.setMoveDirection(MoveDirection.RIGHT);
				}			
			}
			
			if(Variables.playerMovingOverTile) {
				Variables.playerTileCrossSpeed = InputManager.cancelR ? Constants.pixelsPerFrameRunning : Constants.pixelsPerFrameWalking;
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
	
	/**
	 * Returns the collision data for the specified tile on the current map.
	 * @param xPos The x position of that tile.
	 * @param yPos The y position of that tile.
	 * @return The collision data for that tile (0 if xPos and yPos are out of bounds).
	 */
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
