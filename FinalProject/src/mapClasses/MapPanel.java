package mapClasses;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * Contains sprites and game logic
 * @author cjgunnar
 *
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel
{
	/** Time between frames (ms) */
	final static int FRAME_DELAY = 1;
	
	/** The environment */
	TileMap map;
	
	/** Status of the player */
	StatusPanel sp;
	
	/** The player */
	public static Player player;
	
	TileMapWindow window;
	
	/** Create the Board  */
	public MapPanel(TileMapWindow window)
	{
		//reference to window
		this.window = window;
		
		//create a new player
		player = new Player();
		
		//create UI pieces
		map = new TileMap(player);
		sp = new StatusPanel(player);
		InventoryPanel ip = new InventoryPanel(player);
		
		map.setCenterPos(player.getX(), player.getY());
		
		//map.getTile(player.getX(), player.getY()).setVisibility(true);
		movePlayer(player.getX(), player.getY());
		
		//refill player thirst at start
		player.setThirst(100);
		
		//add UI pieces
		setLayout(new BorderLayout());
		add(map, BorderLayout.CENTER);
		add(sp, BorderLayout.NORTH);
		add(ip, BorderLayout.SOUTH);
		
		//initialize status panel with values
		sp.UpdateInfo();
		
		//add the input handler to listen for keys
		addKeyListener(new InputHandler());
		//allow the board to listen to key events
		setFocusable(true);
	}
	
	/**
	 * Attempts to move the player to the tile in the specified direction. False if no tile to move to
	 * @param dx tiles to move in left direction
	 * @param dy tiles to move in downard direction
	 * @return true if success, false if no tiles available in that direction
	 */
	boolean movePlayer(int dx, int dy)
	{	
		int xPos = player.getX();
		int yPos = player.getY();
		
		int moveXPos = xPos + dx;
		int moveYPos = yPos + dy;
		
		//System.out.println("Moving player to (" + moveXPos + "," + moveYPos + ")");
		
		//check not moving into water
		if(map.getRelativeTile(dx, dy).biome == Tile.BIOME.OCEAN) return false;
		
		
		player.setX(moveXPos);
		player.setY(moveYPos);
		
		//map.getRelativeTile(0, 0).removePerson(player);
		map.getRelativeTile(dx, dy).addPerson(player);
		map.getRelativeTile(dx, dy).hasPlayer = true;
		
		//System.out.println("Leaving: " + player.getOccupiedTile());
		
		player.setOccupiedTile(map.getRelativeTile(dx, dy));
		
		updateThirst();
		
		//success
		return true;
	}
	
	private void updateThirst()
	{
		//update status if moving was successful
		//update based on terrain
		String biomeName = player.getOccupiedTile().biome.name;
		String structure = player.getOccupiedTile().structure.name;
		
		if(structure.equals("City") || structure.equals("Village"))
		{
			player.setThirst(100);
		}
		else if(biomeName.equalsIgnoreCase("Desert") || biomeName.equalsIgnoreCase("Tundra") || biomeName.equals("Ice Sheet"))
		{
			player.setThirst(player.getThirst() - 5);
		}
		else
		{
			player.setThirst(player.getThirst() - 2);
		}
		

		if(player.getThirst() < 0)
		{
			setVisible(false);
			//this window is in a jPanel in a jPanel in a layeredPane in a rootPane in a Jpanel in a TileMapWindow
			//or something this is just wild west coding
			window.showGameOverPanel("You got too thirsty. That happens when wandering around too much. "
					+ "Take care of yourself next time, alright? Go stop for a drink. \nYou know you want to.");
		}
	}
	
	public void cycle()
	{				
		//perform game logic
		
		//regenerate map
		map.setCenterPos(player.getX(), player.getY());
		
		//repaint map
		repaint();
		
		//run events
		player.getOccupiedTile().runEvents(window.getEventPanel());
		
		//update status bar
		sp.UpdateInfo();
	}
	
	/**
	 * Handles keyboard input to move player
	 * @author cjgunnar
	 *
	 */
	private class InputHandler extends KeyAdapter
	{
		/**
		 * CONTROLS
		 * W - UP
		 * A - LEFT
		 * S - DOWN
		 * D - RIGHT
		 */
		
		final static int UP = KeyEvent.VK_W;
		final static int DOWN = KeyEvent.VK_S;
		final static int LEFT = KeyEvent.VK_A;
		final static int RIGHT = KeyEvent.VK_D;
		
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			
			//respond to keypress
			if(key == UP)
			{
				movePlayer(0, -1);
			}
			else if(key == DOWN)
			{
				movePlayer(0, 1);
			}
			else if(key == LEFT)
			{
				movePlayer(-1, 0);
			}
			else if(key == RIGHT)
			{
				movePlayer(1, 0);
			}
			
			//everytime player moves, update everything
			cycle();
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			//
		}
		
	}
}
