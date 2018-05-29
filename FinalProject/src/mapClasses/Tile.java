package mapClasses;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Tile
{
	private boolean isVisible;
	
	private ArrayList<Person> people;
	
	protected BIOME biome;
	protected STRUCTURE structure;
	
	private Image biomeImage;
	
	boolean hasPlayer;
	
	protected ArrayList<MapEvent> events;
	
	public Tile(BIOME biome)
	{
		this.biome = biome;
		structure = STRUCTURE.NONE;
		this.biomeImage = new ImageIcon(this.getClass().getResource(biome.imageLocation)).getImage();
		
		people = new ArrayList<Person>();
		hasPlayer = false;
		isVisible = true;
		
		events = new ArrayList<MapEvent>();
		CreateEvents();
	}
	
	public Tile(BIOME biome, STRUCTURE structure)
	{
		this(biome);
		this.structure = structure;
	}
	
	/**
	 * The types of biomes
	 * @author Caden
	 *
	 */
	public enum BIOME
	{
		OCEAN("Water", "/biome_ocean.png"),
		COAST("Coast", "/biome_coast.png"),
		
		ICE_SHEET("Ice Sheet", "/biome_ice_sheet.png"),
		TUNDRA("Tundra", "/biome_tundra.png"),
		PINE_FOREST("Pine Forest", "/biome_pine_forest.png"),
		SCHRUBLAND("Schrubland", "/biome_schrubland.png"),
		GRASSLAND("Grassland", "/biome_grassland.png"),
		DECIDIOUS_FOREST("Decidious Forest", "/biome_decidious_forest.png"),
		DESERT("Desert", "/biome_desert.png"),
		SAVANNA("Savanna", "/biome_savanna.png"),
		RAINFOREST("Rainforest", "/biome_rainforest.png");
		
		public String name;
		public String imageLocation;
		
		private BIOME(String name, String imageLocation)
		{
			this.imageLocation = imageLocation;
			this.name = name;
		}
	}
	
	/**
	 * The structures that can exist on a tile
	 * @author Caden
	 *
	 */
	public enum STRUCTURE
	{
		CITY("City", "/city.png"),
		VILLAGE("Village", "/village.png"),
		NONE("", "");
		
		public String name;
		public Image image;
		
		private STRUCTURE(String name, String imageLoc)
		{
			this.name = name;
			this.image = new ImageIcon(this.getClass().getResource(imageLoc)).getImage();
		}
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
	public void drawTile(int x, int y, int size, Graphics g)
	{
		Image cloudLayerImage =  new ImageIcon(this.getClass().getResource("/cloudLayer.png")).getImage();
		
		if(isVisible)
		{
			try
			{
				//draw background
				g.drawImage(biomeImage, x, y, null);
				
				//draw strucuture
				if(structure != STRUCTURE.NONE)
					g.drawImage(structure.image, x, y, null);
			}
			catch(Exception e)
			{
				System.err.println("Tile: no image found");
			}
			
			
			//draw people
			for(Person person : people)
			{
				if(!person.isPlayer)
					g.drawImage(person.getImage(), x, y, null);
			}
			
			//draw player last (so over everything)
			if(hasPlayer)
			{
				g.drawImage(MapPanel.player.getImage(), x, y, null);
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
	
	/**
	 * Set the structure on this tile
	 * @param structure
	 */
	public void setStructure(STRUCTURE structure)
	{
		this.structure = structure;
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
		if(person.isPlayer) hasPlayer = false;
		people.remove(person);
	}
	
	public void addEvent(MapEvent event)
	{
		events.add(event);
	}
	
	public void setVisibility(boolean visibility)
	{
		isVisible = visibility;
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final int maxLen = 10;
		return "Tile [people=" + (people != null ? people.subList(0, Math.min(people.size(), maxLen)) : null)
				+ ", biome=" + biome + ", structure=" + structure + ", hasPlayer=" + hasPlayer + "]";
	}
}
