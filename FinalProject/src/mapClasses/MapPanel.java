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
	private Player player;
	
	/** Create the Board  */
	public MapPanel()
	{
		player = new Player();
		
		map = new TileMap(player);
		sp = new StatusPanel(player);
		InventoryPanel ip = new InventoryPanel(player);
		
		map.getTile(0, 0).setVisibility(true);
		movePlayer(0, 0);
		
		setLayout(new BorderLayout());
		add(map, BorderLayout.CENTER);
		add(sp, BorderLayout.NORTH);
		add(ip, BorderLayout.SOUTH);
		
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
		int xPos = player.getCol();
		int yPos = player.getRow();
		
		int moveXPos = xPos + dx;
		int moveYPos = yPos + dy;
		
		//check not going off screen
		if(moveXPos < 0) return false;
		if(moveYPos < 0) return false;
		if(moveXPos >= TileMap.NUM_COLS) return false;
		if(moveYPos >= TileMap.NUM_ROWS) return false;
		
		player.setCol(moveXPos);
		player.setRow(moveYPos);
		
		map.getTile(xPos, yPos).removePerson(player);
		map.getTile(moveXPos, moveYPos).addPerson(player);
		
		player.setOccupiedTile(map.getTile(moveXPos, moveYPos));
		
		//make surroundings visible
		//tile above
		if(map.getTile(moveXPos, moveYPos - 1) != null)
			map.getTile(moveXPos, moveYPos - 1).setVisibility(true);
		//tile below
		if(map.getTile(moveXPos, moveYPos + 1) != null)
			map.getTile(moveXPos, moveYPos + 1).setVisibility(true);
		//tile left
		if(map.getTile(moveXPos - 1, moveYPos) != null)
			map.getTile(moveXPos - 1, moveYPos).setVisibility(true);
		//tile right
		if(map.getTile(moveXPos + 1, moveYPos) != null)
			map.getTile(moveXPos + 1, moveYPos).setVisibility(true);
		//tile upper left
		if(map.getTile(moveXPos - 1, moveYPos - 1) != null)
			map.getTile(moveXPos - 1, moveYPos - 1).setVisibility(true);
		//tile upper right
		if(map.getTile(moveXPos - 1, moveYPos + 1) != null)
			map.getTile(moveXPos - 1, moveYPos + 1).setVisibility(true);
		//tile lower left
		if(map.getTile(moveXPos + 1, moveYPos - 1) != null)
			map.getTile(moveXPos + 1, moveYPos - 1).setVisibility(true);
		//tile lower right
		if(map.getTile(moveXPos + 1, moveYPos + 1) != null)
			map.getTile(moveXPos + 1, moveYPos + 1).setVisibility(true);
		
		//success
		return true;
	}
	
	public void cycle()
	{
		//update status
		if(player.getOccupiedTile().getName().equals("desert"))
		{
			player.setThirst(player.getThirst() - 5);
		}
		else if(player.getOccupiedTile().getName().equals("Oasis"))
		{
			player.setThirst(100);
		}
		else if(player.getOccupiedTile().getName().equals("City"))
		{
			player.setThirst(100);
		}

		if(player.getThirst() < 0)
		{
			setVisible(false);
			//this window is in a jPanel in a jPanel in a layeredPane in a rootPane in a Jpanel in a TileMapWindow
			//or something this is just wild west coding
			TileMapWindow window = (TileMapWindow)this.getParent().getParent().getParent().getParent().getParent();
			window.gameOverPanel.Display("You got too thirsty. That happens when wandering the desert. "
					+ "Take care of yourself next time, alright? Go stop for a drink. \nYou know you want to.");
		}
		
		//update status bar
		sp.UpdateInfo();
		
		
		//perform game logic
		
		
		//repaint map
		repaint();
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
