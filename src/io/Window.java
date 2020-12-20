package io;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import io.Camera.CameraMode;
import main.Game.GameState;
import util.Constants;
import util.Options;
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
	private Camera.CameraMode cMode;
	private int cameraX, cameraY;
	
	private int textMessageIndex = 0;
	private String currentlyDisplayedPortion = "";
	private int charFrameCounter = 1;
	private int textOptionsCursorPos = 0;
	private int textboxHeight = 120;
	
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

	public void tick(GameState gameState, Camera camera, Player player) {
		currentTileset = Tilesets.getCurrentTiles();
		cMode = camera.getCameraMode();
		cameraX = camera.getFocusX();
		cameraY = camera.getFocusY();
		
		render(gameState, camera, player);
	}
	
	/**
	 * Performs all of the rendering logic for everything between background tiles and UI.
	 * @param gameState the current state of the game.
	 */
	private void render(GameState gameState, Camera camera, Player player) {
		
		if(canvas.getBufferStrategy() == null) {
			canvas.createBufferStrategy(3);
		}
		
		BufferStrategy bs = canvas.getBufferStrategy();
		Graphics gOverall = bs.getDrawGraphics();
		Graphics2D g;
		
		gOverall.clearRect(0, 0, width, height);
		BufferedImage img = new BufferedImage(pixelsPerTile * tilesPerRow, pixelsPerTile * tilesPerColumn, currentTileset[0].getType());
		g = img.createGraphics();
		
		//System.out.println("img height: " + img.getHeight());		
		if(gameState == GameState.WORLD) {
			//Render background tiles to the screen.
			int[][] tilesToDraw = camera.getTilesInView();			
			for(int i = 0; i < tilesToDraw.length; i++) {
				for(int j = 0; j < tilesToDraw[i].length; j++) {
					g.drawImage(currentTileset[tilesToDraw[i][j]], j * Constants.pixelsPerTile, i * Constants.pixelsPerTile, null);
				}
			}
			
			
			
			//Render entities to the screen.
			Entity[] entitiesToDraw = camera.getEntitiesInView();
			int widthOffset = ((Constants.cameraTileWidth - 3) * 8) + 2;
			int heightOffset = ((Constants.cameraTileHeight - 3) * 8) + 2;
			g.setColor(Color.GRAY);
			for(int i = 0; i < entitiesToDraw.length; i++) {								
				g.fillRect((entitiesToDraw[i].getXPos() * Constants.pixelsPerTile) + widthOffset, (entitiesToDraw[i].getYPos() * Constants.pixelsPerTile) + heightOffset, 12, 12);
			}
			
			
			BufferedImage postShiftingImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
			
			//Render the player to the screen.
			if(cMode == CameraMode.FOCUS_ON_PLAYER) {
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
				postShiftingImg = img.getSubimage(pixelsPerTile + xOffset, pixelsPerTile + yOffset, pixelsPerTile * (tilesPerRow - 2), pixelsPerTile * (tilesPerColumn - 2));
				g = postShiftingImg.createGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(widthOffset, heightOffset, 12, 12);
			}
			else if(cMode == CameraMode.FREE) {
				switch(Variables.moveDir) {
				case UP:
					heightOffset -= Variables.movementOffset;
					break;
				case DOWN:
					heightOffset += Variables.movementOffset;
					break;
				case LEFT:
					widthOffset -= Variables.movementOffset;
					break;
				case RIGHT:
					widthOffset += Variables.movementOffset;
					break;
				case NONE:
					break;
				default:
					System.out.println("Invalid moveDir when calculating x and y offsets! Direction: " + Variables.moveDir);
					break;
				}
				postShiftingImg = img.getSubimage(pixelsPerTile, pixelsPerTile, pixelsPerTile * (tilesPerRow - 2), pixelsPerTile * (tilesPerColumn - 2));
				g = postShiftingImg.createGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(((player.getXPos() - cameraX) * Constants.pixelsPerTile) + widthOffset, ((player.getYPos() - cameraY) * Constants.pixelsPerTile) + heightOffset, 12, 12);
			}
			//BufferedImage prePlayerImg = renderEntities(renderBackground(img, camera.getTilesInView()), camera.getEntitiesInView());
			
			
			
			BufferedImage finalImg = new BufferedImage(width, height, postShiftingImg.getType());
			g = finalImg.createGraphics();
			g.drawImage(postShiftingImg, 0, 0, width, height, null);
			//Render any curently needed textboxes to the screen, and account for current textbox logic.
			boolean needOptionsBox = false;
			//System.out.println("render function running.");
			if(Variables.displayingText) {
				//System.out.println("Attempting to render text...");
				if(Variables.messageCompleted) {
					if(Variables.displayTextOptions) {
						needOptionsBox = true;
						
						if(InputManager.confirm) {
							Variables.chosenTextOption = textOptionsCursorPos;
						}
					}
					else if(InputManager.confirmR || InputManager.cancelR) {
						Variables.readyForNextTextBox = true;
						textMessageIndex = 0;
					}
				}
				else {
					if(Options.framesPerChar <= charFrameCounter) {
						charFrameCounter = 1;
						textMessageIndex += 1;
					}
					else {
						charFrameCounter++;
					}
				}
				
				//TODO This section will eventially be rewritten to use a custom font and properly separate long strings on to multiple lines.
				//This section is only temporary.
				g.setColor(Color.DARK_GRAY);
				//g.fillRect(100, 0, 100, 100);
				g.fillRoundRect(0, height - textboxHeight, width-1, textboxHeight-1, 5, 5);
				g.setColor(Color.LIGHT_GRAY);
				//g.drawRect(0, height - textboxHeight, width-1, textboxHeight-1);
				g.drawRoundRect(0, height - textboxHeight, width-1, textboxHeight-1, 5, 5);
				if(textMessageIndex >= Variables.currentMessage.length()) {
					textMessageIndex = Variables.currentMessage.length();
					Variables.messageCompleted = true;
				}
				String textToDisplay = Variables.currentMessage.substring(0, textMessageIndex);
				//System.out.println("TextToDisplay: " + textToDisplay);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, 25));
				g.drawString(textToDisplay, 20, height - textboxHeight + 40);
			}
			
			gOverall.drawImage(finalImg, 0, 0, width, height, null);
		}
		
		bs.show();
		gOverall.dispose();
	}
}
