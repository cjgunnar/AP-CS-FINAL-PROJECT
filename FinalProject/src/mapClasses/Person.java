package mapClasses;
import java.awt.Image;

/**
 * A person who can occupy a tile and has health, hunger, thirst, name, etc
 * @author Caden
 *
 */
public class Person
{
	Tile occupiedTile;
	
	int x;
	int y;

	protected Image image;
	
	public boolean isPlayer;
	
	private int health;
	private int hunger;
	private int thirst;
	private int gold;
	
	String name;
	
	/**
	 * Create a new person with default values
	 * <p>
	 * Hunger, thirst, and health = 100
	 * @param name
	 */
	public Person(String name)
	{
		this.name = name;
		
		isPlayer = false;
		
		health = 100;
		hunger = 100;
		thirst = 100;
	}
	
	/**
	 * Returns the image used to display this perosn
	 * @return
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * Gets the absolute x of the tile the person is occupying
	 * @return
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Set the absolute x of the tile the person is occupying
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Get the absolute y of the tile the person is occupying
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Set the absolute y of the tile the person is occupying
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Returns the name of this person, capital letter first
	 * @return name of person
	 */
	public String getName()
	{
		return name;
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
	 * @param hunger the hunger to set. Can't exceed 100
	 */
	public void setHunger(int hunger)
	{
		if(hunger > 100)
		{
			hunger = 100;
		}
		
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
		if(thirst > 100)
		{
			thirst = 100;
		}
		this.thirst = thirst;
	}

	/**
	 * @return the gold
	 */
	public int getGold()
	{
		return gold;
	}

	/**
	 * @param gold the gold to set
	 */
	public void setGold(int amount)
	{
		this.gold = amount;
	}
	
	/**
	 * Add to gold supply the amount of gold
	 * @param gold
	 */
	public void addGold(int amount)
	{
		this.gold += amount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return name;
	}
}
