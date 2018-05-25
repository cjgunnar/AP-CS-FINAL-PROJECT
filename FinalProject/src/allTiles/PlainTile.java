package allTiles;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import mapClasses.EventPath;
import mapClasses.MapEvent;
import mapClasses.Person;
import mapClasses.Player;
import mapClasses.Tile;

public class PlainTile extends Tile
{
	private static String[] imageOptions = {"/desert_rock_1.png", "/desert_rock_2.png", "/desert_cactus.png", "/desert_bush.png"};
	
	public PlainTile(String biome)
	{
		super(biome);
		
		this.name = "desert";
		
		//pick style of desert
		int rand = (int)(Math.random() * imageOptions.length);
		
		image =  new ImageIcon(this.getClass().getResource(imageOptions[rand])).getImage();
		
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
}
