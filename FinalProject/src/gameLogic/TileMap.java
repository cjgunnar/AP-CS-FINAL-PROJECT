package gameLogic;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import allTiles.CityTile;
import allTiles.PlainTile;
import allTiles.OasisTile;

public class TileMap extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914987204901582447L;
	
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLS = 20;
	
	public static int TILE_SIZE_PIXELS = 100;
	
	Tile[][] tiles;
	
	Player player;
	
	public TileMap(Player player)
	{
		this.player = player;
		
		tiles = new Tile[NUM_ROWS][NUM_COLS];
		
		for(int row = 0; row < NUM_ROWS; row++)
		{
			for(int col = 0; col < NUM_COLS; col++)
			{
				Tile tile;
				
				double rand = Math.random() * 10;
				if(rand < 0.1)
					tile = new OasisTile();
				else if(rand < 0.2)
					tile = new CityTile();
				else
					tile = new PlainTile("desert");

				tiles[row][col] = tile;
			}
		}
	}
	
	public Rectangle getBoundsOfTile(int row, int col)
	{
		int x = col * TILE_SIZE_PIXELS;
		int y = row * TILE_SIZE_PIXELS;
		int sideLen = TILE_SIZE_PIXELS;
		Rectangle bounds = new Rectangle(x, y, sideLen, sideLen);
		return bounds;
	}
	
	public Tile getTile(int row, int col)
	{
		//check for bounds
		if(row < 0) return null;
		if(col < 0) return null;
		if(row >= NUM_ROWS) return null;
		if(col >= NUM_COLS) return null;
		
		return tiles[row][col];
	}
	
	public int getMapHeight()
	{
		return NUM_COLS * TILE_SIZE_PIXELS;
	}
	
	public int getMapWidth()
	{
		return NUM_ROWS * TILE_SIZE_PIXELS;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		
		//CALCULATE CAM POSITION
		int camX = getBoundsOfTile(player.getRow(), player.getCol()).x - getWidth() / 2;
		int camY = getBoundsOfTile(player.getRow(), player.getCol()).y - getHeight() / 2;

		int MAX_OFFSET_X = getMapWidth() - getWidth();
		int MAX_OFFSET_Y = getMapHeight() - getHeight();

		//adjust cam position to not go off screen
		if(camX > MAX_OFFSET_X)
			camX = MAX_OFFSET_X;
		else if(camX < 0)
			camX = 0;
		if(camY > MAX_OFFSET_Y)
			camY = MAX_OFFSET_Y;
		else if (camY < 0)
			camY = 0;

		//move graphics over to center player
		g.translate(-camX, -camY);

		for(int row = 0; row < NUM_ROWS; row++)
		{
			for(int col = 0; col < NUM_COLS; col++)
			{
				int x = row * TILE_SIZE_PIXELS;
				int y = col * TILE_SIZE_PIXELS;

				tiles[row][col].drawTile(x, y, g);
			}
		}



	}

}
