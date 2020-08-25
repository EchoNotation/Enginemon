package io;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;
import util.Constants;
import util.MapLoader;

public class Camera {
	public enum CameraMode {
		FOCUS_ON_PLAYER,
		FREE;
	}
	
	private int focusX, focusY;
	private CameraMode cMode;
	private Player player;
	private int cameraTileHeight = Constants.cameraTileHeight;
	private int cameraTileWidth = Constants.cameraTileWidth;

	public Camera(Player player) {
		this.player = player;
	}
	
	public void tick() {
		switch(cMode) {
		case FOCUS_ON_PLAYER:
			focusX = player.getXPos();
			focusY = player.getYPos();
			break;
		case FREE:
			break;
		default:
			break;
		}
	}
	
	/**
	 * Returns the array of all tiles that could potentially be within the camera's view.
	 * @return The array of tiles.
	 */
	public int[][] getTilesInView() {
		int[][] tiles = MapLoader.currentTileData;
		int[][] tilesToDraw = new int[cameraTileHeight][cameraTileWidth];
		
		int startX = focusX - (cameraTileWidth - 1) / 2;
		int endX = focusX + (cameraTileWidth - 1) / 2;
		int startY = focusY - (cameraTileHeight - 1) / 2;
		int endY = focusY + (cameraTileHeight - 1) / 2;
		
		//System.out.println("SX: " + startX + " EX: " + endX + " SY: " + startY + " EY: " + endY);
		
		int validTileCount = 0;
		
		for(int i = startY; i <= endY; i++) {
			for(int j = startX; j <= endX; j++) {
				if(i >= tiles.length || j >= tiles[0].length || i < 0 || j < 0) {
					tilesToDraw[i - startY][j - startX] = 0;
				}
				else {
					tilesToDraw[i - startY][j - startX] = tiles[i][j];
					validTileCount++;
				}				
			}
		}
		
		//System.out.println("Valid tiles found: " + validTileCount);
		return tilesToDraw;
	}
	
	/**
	 * Retuns the array of all entities that could possibly be in the camera's view.
	 * @return The entity array.
	 */
	public Entity[] getEntitiesInView() {
		Entity[] entities = MapLoader.currentEntityData;
		ArrayList<Entity> entitiesToDraw = new ArrayList<Entity>();		
		
		int startX = focusX - (cameraTileWidth - 1) / 2;
		int startY = focusY - (cameraTileHeight - 1) / 2;
		
		Rectangle drawArea = new Rectangle(startX, startY, cameraTileWidth, cameraTileHeight);
		
		for(int i = 0; i < entities.length; i++) {
			Point entityLoc = new Point(entities[i].getXPos(), entities[i].getYPos());
			
			if(drawArea.contains(entityLoc)) {
				entitiesToDraw.add(entities[i]);
			}
				
		}
		
		return entitiesToDraw.toArray(new Entity[entitiesToDraw.size()]);
	}
	
	/**
	 * Sets the camera's focus mode to the specified mode.
	 * @param newMode The mode to switch to.
	 */
	public void changeCameraMode(CameraMode newMode) {
		cMode = newMode;
	}
	
	/**
	 * @return The camera's current focusing mode.
	 */
	public CameraMode getCameraMode() {
		return cMode;
	}
	
	/**
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return The x index of the tile that the camera is centered on.
	 */
	public int getFocusX() {
		return focusX;
	}
	
	/**
	 * Sets the x index of the tile the camera is centered on to the specified value.
	 * @param newFocusX The new x index.
	 */
	public void setFocusX(int newFocusX) {
		focusX = newFocusX;
	}
	
	/**
	 * @return The y index of the tile that the camera is centered on.
	 */
	public int getFocusY() {
		return focusY;
	}
	
	/**
	 * Sets the y index of the tile the camera is centered on to the specified value.
	 * @param newFocusY The new y index.
	 */
	public void setFocusY(int newFocusY) {
		focusY = newFocusY;
	}
	
}
