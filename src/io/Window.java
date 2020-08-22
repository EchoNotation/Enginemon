package io;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.Game;
import main.Game.GameState;
import util.MapLoader;
import util.Tilesets;

public class Window {
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	
	private int tilesPerColumn = 13;
	private int tilesPerRow = 24;
	
	private BufferedImage[] currentTiles;
	
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

	public void tick(GameState gameState) {
		currentTiles = Tilesets.getCurrentTiles();
		render(gameState);
	}
	
	private void render(GameState gameState) {
		if(canvas.getBufferStrategy() == null) {
			canvas.createBufferStrategy(3);
		}
		
		BufferStrategy bs = canvas.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		BufferedImage img = new BufferedImage(width, height, currentTiles[0].getType());
		
		if(gameState == GameState.WORLD) {
			img = renderEntities(renderBackground(img));
			g.drawImage(img, 0, 0, width, height, null);
		}
		
		bs.show();
		g.dispose();
	}
	
	private BufferedImage renderBackground(BufferedImage img) {
		int[][] background = MapLoader.currentTileData;
		
		for(int i = 0; i < background.length; i++) {
			for(int j = 0; j < background[i].length; j++) {
				img.getGraphics().drawImage(currentTiles[background[i][j]], j * 16, i * 16, null);
			}
		}
		
		return img;
	}
	
	private BufferedImage renderEntities(BufferedImage img) {
		//Obviously needs an actual implementation.
		return img;
	}
}
