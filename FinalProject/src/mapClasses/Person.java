package mapClasses;
import java.awt.Image;

public class Person
{
	Tile occupiedTile;
	
	int row;
	int col;

	protected Image image;
	
	public boolean isPlayer;
	
	private int health;
	private int hunger;
	private int thirst;
	
	public Person()
	{
		isPlayer = false;
		
		health = 100;
		hunger = 100;
		thirst = 100;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}

	/**
	 * @return the occupiedTile
	 */
	public Tile getOccupiedTile()
	{
		return occupiedTile;
	}

	/**
	 * @param occupiedTile the occupiedTile to set
	 */
	public void setOccupiedTile(Tile occupiedTile)
	{
		this.occupiedTile = occupiedTile;
	}

	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health)
	{
		this.health = health;
	}

	/**
	 * @return the hunger
	 */
	public int getHunger()
	{
		return hunger;
	}

	/**
	 * @param hunger the hunger to set
	 */
	public void setHunger(int hunger)
	{
		this.hunger = hunger;
	}

	/**
	 * @return the thirst
	 */
	public int getThirst()
	{
		return thirst;
	}

	/**
	 * @param thirst the thirst to set
	 */
	public void setThirst(int thirst)
	{
		this.thirst = thirst;
	}
}
