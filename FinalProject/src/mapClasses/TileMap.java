package mapClasses;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import mapGen.MapGenerator;

@SuppressWarnings("serial")
public class TileMap extends JPanel
{	
	public static final int SIZE = 11;
	public static final int NUM_ROWS = SIZE;
	public static final int NUM_COLS = SIZE;
	
	public static int TILE_SIZE_PIXELS = 100;
	
	int xPos;
	int yPos;
	
	MapGenerator mapGen;
	
	Tile[][] displayTiles;
	
	Player player;
	
	public TileMap(Player player)
	{
		this.player = player;
		
		//generate a map that meets requirements
		
		boolean reqMet = false;
		int attempt = 0;
		int max = 100;
		while(!reqMet && attempt <= max)
		{
			attempt++;
			System.out.println("Creating map #" + attempt);
			
			mapGen = new MapGenerator();
			displayTiles = mapGen.generateMapArea(-SIZE/2, -SIZE/2, NUM_COLS);
			
			if(getRelativeTile(0, 0).biome != Tile.BIOME.OCEAN)
			{
				System.out.println("SUCCESS: requirements met!");
				reqMet = true;
			}
			else
			{
				System.out.println("FAILED: Starting biome of " + getRelativeTile(0, 0).biome.name);
			}
		}
		
		
		setPreferredSize(new Dimension(SIZE * TILE_SIZE_PIXELS, SIZE * TILE_SIZE_PIXELS));
	}
	
	public Rectangle getBoundsOfTile(int row, int col)
	{
		int x = col * TILE_SIZE_PIXELS;
		int y = row * TILE_SIZE_PIXELS;
		int sideLen = TILE_SIZE_PIXELS;
		Rectangle bounds = new Rectangle(x, y, sideLen, sideLen);
		return bounds;
	}
	
//	/**
//	 * Takes in absolute coordinates and returns tile if loaded
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	public Tile getTile(int x, int y)
//	{		
//		int relX = x + xPos;
//		int relY = y + yPos;
//		
//		//System.out.println("tile of absolute pos (" + x + "," + y + "), convert to (" + relX + "," + relY + ")");
//		
//		//check if the tile is loaded
//		if(relX < 0) return null;
//		if(relY < 0) return null;
//		if(relX >= SIZE) return null;
//		if(relY >= SIZE) return null;
//		
//		return displayTiles[relY][relX];
//	}
	
	/**
	 * Returns a tile relative to the player, (0,0 at top left, increasing down and right)
	 * @param x distance on x from player
	 * @param y distance on y from player
	 * @return the Tile if loaded, null otherwise
	 */
	public Tile getRelativeTile(int x, int y)
	{
		int relX = 5 + x;
		int relY = 5 + y;
		return displayTiles[relY][relX];
	}
	
	public int getMapHeight()
	{
		return SIZE * TILE_SIZE_PIXELS;
	}
	
	public int getMapWidth()
	{
		return SIZE * TILE_SIZE_PIXELS;
	}
	
	public void setCenterPos(int x, int y)
	{
		xPos = y;
		yPos = x;
		genMapArea();
		//System.out.println("Map centered to (" + x + "," + y + ")");
	}
	
	private void genMapArea()
	{
		displayTiles = mapGen.generateMapArea(xPos, yPos, NUM_COLS);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		
		for(int row = 0; row < SIZE; row++)
		{
			for(int col = 0; col < SIZE; col++)
			{
				int x = col * TILE_SIZE_PIXELS;
				int y = row * TILE_SIZE_PIXELS;

				displayTiles[row][col].drawTile(x, y, TILE_SIZE_PIXELS, g);
				if(displayTiles[row][col].hasPlayer) g.drawString("Here is the player", x + 25, y + 25);
				//g.drawString("R(" + col + "," + row + ")", x, y);
				//g.drawString("A(" + (xPos + col) + "," + (yPos + row) + ")", x, y + 25);
			}
		}

		g.drawImage(MapPanel.player.getImage(), TILE_SIZE_PIXELS * 5, TILE_SIZE_PIXELS * 5, null);
		//System.out.println("Drawing TileMap");

	}

}
