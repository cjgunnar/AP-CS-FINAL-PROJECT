package mapClasses;

import java.util.ArrayList;
import java.util.List;

import mapClasses.MapEvent.Prerequisite;

/**
 * Represents a branching path of the events
 * @author Caden
 */
public class EventPath
{
	/** The options that this path leads to */
	private ArrayList<EventPath> options;
	
	/** The prerequisistes for this path to be available */
	ArrayList<Prerequisite> prerequisites;
	
	/** The name of this point in the path */
	String name;
	
	/** Context of the path and of the options */
	String text;
	
	/** Is this a path that ends the event and returns to map? */
	boolean endSafe;
	
	boolean losePath;
	
	/** Default Constructor */
	public EventPath()
	{
		this("none");
	}
	
	/**
	 * Create an event path that is not an end
	 * @param name
	 */
	public EventPath(String name)
	{
		this(name, false);
	}
	
	/**
	 * Create a EventPath with a name and specified end
	 * @param name
	 * @param end is this path an end?
	 */
	public EventPath(String name, boolean end)
	{
		this.name = name;
		
		this.endSafe = end;
		this.losePath = false;
		
		options = new ArrayList<EventPath>();
		prerequisites = new ArrayList<Prerequisite>();
	}
	
	/**
	 * Returns true if all prereq are met
	 * @param player reference to player
	 * @param tile
	 * @return true if all met, false otherwise
	 */
	public boolean hasPrerequisites(Player player, Tile tile)
	{
		for(Prerequisite prerequisite : prerequisites)
		{
			if(!prerequisite.checkPrerequisite(player, tile))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Add a prereq to this path
	 * @param prereq
	 */
	public void addPrerequisite(Prerequisite prereq)
	{
		prerequisites.add(prereq);
	}
	
	public List<EventPath> getOptions()
	{
		return options;
	}
	
	/**
	 * Add an option
	 * @param option
	 */
	public void addOption(EventPath option)
	{
		options.add(option);
	}
	
	/**
	 * gets the text
	 * @return text
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * sets the text
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	 * Return the option with the name. Case insensitive
	 * @param name
	 * @return EventPath with name or null if nothing found
	 */
	public EventPath pickOption(String name)
	{
		for(EventPath option : options)
		{
			if(option.getName().equalsIgnoreCase(name))
			{
				return option;
			}
		}
		
		//no EventPath of that name
		return null;
	}
	
	/**
	 * Is this path an ending point?
	 * @return end
	 */
	public boolean isEnd()
	{
		return endSafe;
	}
	
	public void setEnd(boolean end)
	{
		this.endSafe = end;
	}
	
	/**
	 * sets the name
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the name of this EventPath
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the losePath
	 */
	public boolean isLosePath()
	{
		return losePath;
	}

	/**
	 * @param losePath the losePath to set
	 */
	public void setLosePath(boolean losePath)
	{
		this.losePath = losePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "EventPath [name=" + name + ", islosePath=" + losePath + "]";
	}
	
}
