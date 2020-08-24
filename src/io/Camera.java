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
	
	public void changeCameraMode(CameraMode newMode) {
		cMode = newMode;
	}
	
	public CameraMode getCameraMode() {
		return cMode;
	}
	
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
	
	public Player getPlayer() {
		return player;
	}
	
	public int getFocusX() {
		return focusX;
	}
	
	public void setFocusX(int newFocusX) {
		focusX = newFocusX;
	}
	
	public int getFocusY() {
		return focusY;
	}
	
	public void setFocusY(int newFocusY) {
		focusY = newFocusY;
	}
	
}
