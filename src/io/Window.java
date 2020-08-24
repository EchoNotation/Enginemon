package io;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import entities.Entity;
import main.Game.GameState;
import util.Constants;
import util.Tilesets;
import util.Variables;

public class Window {
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	
	private int tilesPerColumn = Constants.cameraTileHeight;
	private int tilesPerRow = Constants.cameraTileWidth;
	private int pixelsPerTile = Constants.pixelsPerTile;

	private BufferedImage[] currentTileset;
	
	public Window(String title, int width, int height, KeyManager keys) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		frame = new JFrame(title);
		frame.setSize(new Dimension(width, height));
		frame.setFocusable(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setSize(new Dimension(width, height));
		canvas.setVisible(true);
		canvas.setFocusable(true);
		canvas.addKeyListener(keys);
		
		frame.add(canvas);
		frame.pack();
	}

	public void tick(GameState gameState, Camera camera) {
		currentTileset = Tilesets.getCurrentTiles();
		render(gameState, camera);
	}
	
	/**
	 * Performs all of the rendering logic for everything between background tiles and UI.
	 * @param gameState the current state of the game.
	 */
	private void render(GameState gameState, Camera camera) {
		if(canvas.getBufferStrategy() == null) {
			canvas.createBufferStrategy(3);
		}
		
		BufferStrategy bs = canvas.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		BufferedImage img = new BufferedImage(pixelsPerTile * tilesPerRow, pixelsPerTile * tilesPerColumn, currentTileset[0].getType());
		
		//System.out.println("img height: " + img.getHeight());
		
		if(gameState == GameState.WORLD) {
			BufferedImage prePlayerImg = renderEntities(renderBackground(img, camera.getTilesInView()), camera.getEntitiesInView());
			
			int xOffset = 0;
			int yOffset = 0;
			
			switch(Variables.moveDir) {
			case UP:
				yOffset = -Variables.movementOffset;
				break;
			case DOWN:
				yOffset = Variables.movementOffset;
				break;
			case LEFT:
				xOffset = -Variables.movementOffset;
				break;
			case RIGHT:
				xOffset = Variables.movementOffset;
				break;
			case NONE:
				break;
			default:
				System.out.println("Invalid moveDir when calculating x and y offsets! Direction: " + Variables.moveDir);
				break;
			}
			
			//System.out.println("pixelsPerTile + yOffset: " + (pixelsPerTile + yOffset) + " Height: " + pixelsPerTile * (tilesPerRow - 2));
			//System.out.println("prePlayerImg height: " + prePlayerImg.getHeight());
			BufferedImage shiftedImg = prePlayerImg.getSubimage(pixelsPerTile + xOffset, pixelsPerTile + yOffset, pixelsPerTile * (tilesPerRow - 2), pixelsPerTile * (tilesPerColumn - 2));
			BufferedImage finalImg = renderPlayer(shiftedImg);
			
			g.drawImage(finalImg, 0, 0, width, height, null);
		}
		
		bs.show();
		g.dispose();
	}
	
	private BufferedImage renderBackground(BufferedImage img, int[][] tilesToDraw) {		
		for(int i = 0; i < tilesToDraw.length; i++) {
			for(int j = 0; j < tilesToDraw[i].length; j++) {
				img.getGraphics().drawImage(currentTileset[tilesToDraw[i][j]], j * Constants.pixelsPerTile, i * Constants.pixelsPerTile, null);
			}
		}
		
		return img;
	}
	
	private BufferedImage renderEntities(BufferedImage img, Entity[] entitiesToDraw) {
		int widthOffset = ((Constants.cameraTileWidth - 3) * 8) + 2;
		int heightOffset = ((Constants.cameraTileHeight - 3) * 8) + 2;
				
		for(int i = 0; i < entitiesToDraw.length; i++) {					
			img.getGraphics().setColor(Color.GRAY);
			img.getGraphics().fillRect((entitiesToDraw[i].getXPos() * 16) + widthOffset, (entitiesToDraw[i].getYPos() * 16) + heightOffset, 12, 12);
		}
		
		return img;
	}
	
	private BufferedImage renderPlayer(BufferedImage img) {
		int widthOffset = ((Constants.cameraTileWidth - 3) * 8) + 2;
		int heightOffset = ((Constants.cameraTileHeight - 3) * 8) + 2;
		
		img.getGraphics().setColor(Color.WHITE);
		img.getGraphics().fillRect(widthOffset, heightOffset, 12, 12);
		
		return img;
	}
}
