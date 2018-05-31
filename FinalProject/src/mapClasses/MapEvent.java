package mapClasses;

import java.util.ArrayList;

public class MapEvent
{
	/** Prereqs for this MapEvent to fire */
	ArrayList<Prerequisite> prerequisites;
	/** Name of this event */
	String name;
	
	/** Start of this events branching */
	EventPath startPoint;
	
	/** Default constructor */
	public MapEvent()
	{
		this("event");
	}
	
	/**
	 * Create a MapEvent with a name
	 * @param name of this MapEvent
	 */
	public MapEvent(String name)
	{
		this.name = name;
		
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
	 * gets the start point
	 * @return start point
	 */
	public EventPath getStartPoint()
	{
		return startPoint;
	}
	
	/**
	 * sets the start point
	 * @param startPoint
	 */
	public void setStartPoint(EventPath startPoint)
	{
		this.startPoint = startPoint;
	}
	
	/**
	 * Run this event
	 * @param ep reference to EventPanel to display on
	 */
	public void runEvent(EventPanel ep)
	{
		ep.Display(this);
	}
	
	/**
	 * Interface for checking conditions
	 * @author Caden
	 *
	 */
	public interface Prerequisite
	{
		boolean checkPrerequisite(Player player, Tile tile);
	}
	
	/**
	 * Add a prereq to this event
	 * @param prereq
	 */
	public void addPrerequisite(Prerequisite prereq)
	{
		prerequisites.add(prereq);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * gets the name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
}
