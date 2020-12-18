package io;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private int textBoxHeight = 20;
	
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
		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		BufferedImage img = new BufferedImage(pixelsPerTile * tilesPerRow, pixelsPerTile * tilesPerColumn, currentTileset[0].getType());
		
		//System.out.println("img height: " + img.getHeight());
		BufferedImage finalImg;
		
		if(gameState == GameState.WORLD) {
			BufferedImage prePlayerImg = renderEntities(renderBackground(img, camera.getTilesInView()), camera.getEntitiesInView());
			
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
				
				//System.out.println("pixelsPerTile + yOffset: " + (pixelsPerTile + yOffset) + " Height: " + pixelsPerTile * (tilesPerRow - 2));
				//System.out.println("prePlayerImg height: " + prePlayerImg.getHeight());
				BufferedImage shiftedImg = prePlayerImg.getSubimage(pixelsPerTile + xOffset, pixelsPerTile + yOffset, pixelsPerTile * (tilesPerRow - 2), pixelsPerTile * (tilesPerColumn - 2));
				finalImg = renderTextbox(renderPlayer(shiftedImg, player));
			}
			else {
				finalImg = renderTextbox(renderPlayer(prePlayerImg.getSubimage(pixelsPerTile, pixelsPerTile, pixelsPerTile * (tilesPerRow - 2), pixelsPerTile * (tilesPerColumn - 2)), player));
			}
			
			g.drawImage(finalImg, 0, 0, width, height, null);
		}
		
		bs.show();
		g.dispose();
	}
	
	/**
	 * Draws the background tiles on a blank image.
	 * @param img A blank image of the correct size.
	 * @param tilesToDraw The 2D array of tiles that are potentially visible.
	 * @return Image with tiles drawn on it.
	 */
	private BufferedImage renderBackground(BufferedImage img, int[][] tilesToDraw) {		
		for(int i = 0; i < tilesToDraw.length; i++) {
			for(int j = 0; j < tilesToDraw[i].length; j++) {
				img.getGraphics().drawImage(currentTileset[tilesToDraw[i][j]], j * Constants.pixelsPerTile, i * Constants.pixelsPerTile, null);
			}
		}
		
		return img;
	}
	
	/**
	 * Draws all entities on screen to the specified image.
	 * @param img The image to draw the entities on.
	 * @param entitiesToDraw The entities to draw.
	 * @return The image with the entities drawn on it.
	 */
	private BufferedImage renderEntities(BufferedImage img, Entity[] entitiesToDraw) {
		int widthOffset = ((Constants.cameraTileWidth - 3) * 8) + 2;
		int heightOffset = ((Constants.cameraTileHeight - 3) * 8) + 2;
				
		for(int i = 0; i < entitiesToDraw.length; i++) {					
			img.getGraphics().setColor(Color.GRAY);
			img.getGraphics().fillRect((entitiesToDraw[i].getXPos() * Constants.pixelsPerTile) + widthOffset, (entitiesToDraw[i].getYPos() * Constants.pixelsPerTile) + heightOffset, 12, 12);
		}
		
		return img;
	}
	
	/**
	 * Draws the player onto the current image.
	 * @param img the image to draw the player on.
	 * @return The image with the player on it.
	 */
	private BufferedImage renderPlayer(BufferedImage img, Player player) {
		int widthOffset = ((Constants.cameraTileWidth - 3) * 8) + 2;
		int heightOffset = ((Constants.cameraTileHeight - 3) * 8) + 2;
		
		img.getGraphics().setColor(Color.WHITE);
		if(cMode == CameraMode.FOCUS_ON_PLAYER) {
			img.getGraphics().fillRect(widthOffset, heightOffset, 12, 12);
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
			img.getGraphics().fillRect(((player.getXPos() - cameraX) * Constants.pixelsPerTile) + widthOffset, ((player.getYPos() - cameraY) * Constants.pixelsPerTile) + heightOffset, 12, 12);
		}
		//img.getGraphics().drawString("" + Variables.numberOfConnectedControllers, 20, 20);
		
		return img;
	}
	
	private BufferedImage renderTextbox(BufferedImage img) {
		BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		temp.getGraphics().drawImage(img, 0, 0, width, height, null);
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
			
			//img.getGraphics().setColor(Color.DARK_GRAY);
			temp.getGraphics().fillRect(100, 0, 100, 100);
			//img.getGraphics().fillRect(0, height - textBoxHeight, width, textBoxHeight);
			if(textMessageIndex >= Variables.currentMessage.length()) {
				textMessageIndex = Variables.currentMessage.length() - 1;
				Variables.messageCompleted = true;
			}
			String textToDisplay = Variables.currentMessage.substring(0, textMessageIndex);
			//System.out.println("TextToDisplay: " + textToDisplay);
			//img.getGraphics().setColor(Color.WHITE);
			img.getGraphics().drawString(textToDisplay, 7, height - textBoxHeight + 7);
		}
		
		return temp;
	}
}
