package mapClasses;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import mapGen.MapGenerator;

public class TileMap extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914987204901582447L;
	
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
		
		mapGen = new MapGenerator();
		
		displayTiles = mapGen.generateMapArea(-SIZE/2, -SIZE/2, NUM_COLS);
		
		setPreferredSize(new Dimension(10 * TILE_SIZE_PIXELS, 10 * TILE_SIZE_PIXELS));
	}
	
	public Rectangle getBoundsOfTile(int row, int col)
	{
		int x = col * TILE_SIZE_PIXELS;
		int y = row * TILE_SIZE_PIXELS;
		int sideLen = TILE_SIZE_PIXELS;
		Rectangle bounds = new Rectangle(x, y, sideLen, sideLen);
		return bounds;
	}
	
	/**
	 * Takes in absolute coordinates and returns tile if loaded
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y)
	{
		int relX = x + 5;
		int relY = y + 5;
		
		//System.out.println("tile of absolute pos (" + x + "," + y + "), convert to (" + relX + "," + relY + ")");
		
		//check if the tile is loaded
		if(relX < 0) return null;
		if(relY < 0) return null;
		if(relX >= SIZE) return null;
		if(relY >= SIZE) return null;
		
		return displayTiles[relY][relX];
	}
	
	public int getMapHeight()
	{
		return NUM_ROWS * TILE_SIZE_PIXELS;
	}
	
	public int getMapWidth()
	{
		return NUM_COLS * TILE_SIZE_PIXELS;
	}
	
	public void setCenterPos(int x, int y)
	{
		xPos = y;// - SIZE/2;
		yPos = x;// - SIZE/2;
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
		
		/*
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
		*/

		//move graphics over to center player
		//g.translate(-camX, -camY);

		//g.translate(-SIZE * TILE_SIZE_PIXELS /2, -SIZE * TILE_SIZE_PIXELS /2);
		
		for(int row = 0; row < SIZE; row++)
		{
			for(int col = 0; col < SIZE; col++)
			{
				int x = col * TILE_SIZE_PIXELS;
				int y = row * TILE_SIZE_PIXELS;

				displayTiles[row][col].drawTile(x, y, TILE_SIZE_PIXELS, g);
				g.drawString("R(" + col + "," + row + ")", x, y);
				g.drawString("A(" + (xPos + col) + "," + (yPos + row) + ")", x, y + 25);
			}
		}

		g.drawImage(MapPanel.player.getImage(), TILE_SIZE_PIXELS * 5, TILE_SIZE_PIXELS * 5, null);

	}

}
