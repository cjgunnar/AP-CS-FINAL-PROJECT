package mapClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a branching path of the events
 * @author Caden
 */
public class EventPath
{
	/** The options that this path leads to */
	private ArrayList<EventPath> options;
	
	/** The name of this point in the path */
	String name;
	
	/** Context of the path and of the options */
	String text;
	
	/** Is this a dead end path? */
	boolean end;
	
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
		
		this.end = end;
		
		options = new ArrayList<EventPath>();
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
		return end;
	}
	
	/**
	 * Gets the name of this EventPath
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
}
