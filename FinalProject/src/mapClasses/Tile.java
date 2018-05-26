package mapClasses;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Tile
{
	protected Image image;
	
	private boolean isVisible;
	
	private ArrayList<Person> people;
	
	protected String name;
	protected String biome;
	
	private Image biomeImage;
	
	boolean hasPlayer;
	
	protected ArrayList<MapEvent> events;
	
	public Tile(String biome)
	{
		this.biome = biome;
		
		this.biomeImage = new ImageIcon(this.getClass().getResource("/biome_desert.png")).getImage();
		
		people = new ArrayList<Person>();
		hasPlayer = false;
		isVisible= false;
		
		events = new ArrayList<MapEvent>();
		CreateEvents();
	}
	
	private void CreateEvents()
	{
		//create events

		//create BANDITS event
		MapEvent bandits = new MapEvent("Bandits!");
		bandits.addPrerequisite(new MapEvent.Prerequisite(){

			@Override
			public boolean checkPrerequisite(Player player, ArrayList<Person> people)
			{
				//PREREQUSITES
				//TILE HAS AT LEAST ONE BANDIT

				return (containsPerson("Bandit"));

			}});

		EventPath start = new EventPath("Bandit Activity");
		start.setText("You spy a group of bandits lurking nearby.");
		bandits.setStartPoint(start);

		EventPath sneakBy = new EventPath("Sneak By", true);
		sneakBy.setText("You attempt to sneak by them. You are caught!");
		
		EventPath stealFrom = new EventPath("Steal From", true);
		stealFrom.setText("Robbing from thieves? Where's you honor? Picking on the lowlifes. You scum.\nJoking. They cut off your ear");

		EventPath talkTo = new EventPath("Talk With");
		talkTo.setText("They can't be that bad right? You walk up to a bandana-ed youngster and try to initiate a conversation.\nHe asks if you have a deathwish.");

		start.addOption(sneakBy);
		start.addOption(stealFrom);
		start.addOption(talkTo);

		EventPath yes = new EventPath("I certainly do.", true);
		yes.setText("He laughts, pulls out his revolver and shoots near your feet. You high-tail it out of there");

		EventPath no = new EventPath("Not yet", true);
		no.setText("Then you better get out of here.");

		talkTo.addOption(yes);
		talkTo.addOption(no);

		events.add(bandits);
	}
	
	/**
	 * Run events on this tile
	 * @return true if at least one event will run, false otherwise
	 */
	public boolean runEvents(EventPanel ep)
	{
		Player player = null;
		if(hasPlayer)
		{
			for(Person person : people)
			{
				if(person.isPlayer)
					player = (Player)person;
			}
		}
		
		for(MapEvent event : events)
		{
			if(event.hasPrerequisites(player, people))
			{
				event.runEvent(ep);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Draws the tile with the x, y as the upper left
	 * @param x upper left x coordinate
	 * @param y upper left y coordinate
	 * @param g Graphics to draw to
	 */
	public void drawTile(int x, int y, Graphics g)
	{
		Image cloudLayerImage =  new ImageIcon(this.getClass().getResource("/cloudLayer.png")).getImage();
		
		if(isVisible)
		{
			//draw background
			g.drawImage(biomeImage, x, y, null);
			
			//draw picture
			g.drawImage(image, x, y, null);
			
			//draw people
			for(Person person : people)
			{
				if(true) //debug
				{
					g.drawImage(person.getImage(), x, y, null);
				}
			}
		}
			
		else
		{
			g.drawImage(cloudLayerImage, x, y, null);
		}
	}
	
	/**
	 * Does this tile have someone with a certain name on it?
	 * @param name to check
	 * @return true or false
	 */
	public boolean containsPerson(String name)
	{
		for(Person person : people)
		{
			if(person.getName().equals(name)) return true;
		}
		
		return false;
	}
	
	public void addPerson(Person person)
	{
		if(person.isPlayer) hasPlayer = true;
		people.add(person);
	}
	
	public List<Person> getPeople()
	{
		return people;
	}
	
	public void removePerson(Person person)
	{
		people.remove(person);
	}
	
	public void addEvent(MapEvent event)
	{
		events.add(event);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setVisibility(boolean visibility)
	{
		isVisible = visibility;
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
	public Image getImage()
	{
		return image;
	}
}
