package util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Contains all the tilesets and required tile sprites for the game.
 * @author Thomas
 *
 */
public class Tilesets {
	private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	private static int numberOfTilesets = 1;
	private static int numberOfTilesPerSet = 8;
	private static int numberOfTilesPerRow = 8;
	private static BufferedImage[] currentTiles;
	private static BufferedImage[][] tilesets;
	
	/**
	 * Loads all tilesets in the game.
	 */
	public static void loadTilesets() {
		currentTiles = new BufferedImage[numberOfTilesPerSet];
		tilesets = new BufferedImage[numberOfTilesets][numberOfTilesPerSet];
		
		for(int i = 0; i < numberOfTilesets; i++) {
			tilesets[i] = loadTileset(i);
		}
	}
	
	/**
	 * Loads a particular tileset according to its ID number.
	 */
	private static BufferedImage[] loadTileset(int tilesetID) {
		BufferedImage[] tiles = new BufferedImage[numberOfTilesPerSet];
		BufferedImage fullImage;
		
		try {
			if(Variables.runningAsJar) {
				String filepath = "tilesets/TILESET" + tilesetID + ".png";
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);
				fullImage = ImageIO.read(is);
			}
			else {
				String filepath = "resources/tilesets/TILESET" + tilesetID + ".png";
				fullImage = ImageIO.read(new File(filepath));
			}

			int numberOfRows = numberOfTilesPerSet / numberOfTilesPerRow;
			int size = Constants.pixelsPerTile;

			
			for(int i = 0; i < numberOfRows; i++) {
				int offset = i * numberOfTilesPerRow;
				
				for(int j = 0; j < numberOfTilesPerRow; j++) {
					BufferedImage img = fullImage.getSubimage(j * size, i * size, size, size);				
					tiles[offset + j] = toCompatibleImage(img);
				}
			}
		} 
		catch (IOException e) {
			System.out.println("Issue loading tileset with ID: " + tilesetID);
		}
		
		return tiles;
	}
	
	/**
	 * Updates the current tileset the game is using to the passed ID number.
	 * @param newTilesetID
	 */
	public static void changeTileset(int newTilesetID) {
		for(int i = 0; i < numberOfTilesPerSet; i++) {
			currentTiles[i] = tilesets[newTilesetID][i];
		}
	}
	
	/**
	 * Returns the currently selected tileset.
	 * @return tileset currently selected by changeTileset()
	 */
	public static BufferedImage[] getCurrentTiles() {
		return currentTiles;
	}
	
	/**
	 * Converts the passed image to the correct format for the canvas. Improves rendering speed.
	 * @param img the image to convert to the correct format
	 * @return the converted image.
	 */
	private static BufferedImage toCompatibleImage(BufferedImage img) {
		if (img.getColorModel().equals(GFX_CONFIG.getColorModel())) {
	        return img;
	    }
		
		 final BufferedImage new_image = GFX_CONFIG.createCompatibleImage(img.getWidth(), img.getHeight(), img.getTransparency());

	    final Graphics2D g2d = (Graphics2D) new_image.getGraphics();
	    g2d.drawImage(img, 0, 0, null);
	    g2d.dispose();

	    return new_image;
	}
}
