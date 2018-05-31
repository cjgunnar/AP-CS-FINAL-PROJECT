package mapClasses;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusPanel extends JPanel
{
	Player player;

	JLabel location;
	JLabel health;
	JLabel thirst;
	JLabel hunger;
	JLabel gold;
	
	public StatusPanel(Player player)
	{
		this.player = player;
		CreateComponents();
	}

	private void CreateComponents()
	{
		location = new JLabel("Location");
		health = new JLabel("Health");
		thirst = new JLabel("Thirst");
		hunger = new JLabel("Hunger");
		gold = new JLabel("Gold");
		
		add(location);
		add(health);
		add(thirst);
		add(hunger);
		add(gold);
	}

	public void UpdateInfo()
	{
		String loc = player.getOccupiedTile().biome.name;
		loc = !player.getOccupiedTile().structure.name.equals("") ? loc + " " +  player.getOccupiedTile().structure.name: loc;
		location.setText(loc);
		health.setText("Health: " + player.getHealth());
		thirst.setText("Thirst: " + player.getThirst());
		hunger.setText("Hunger: " + player.getHunger());
		gold.setText("Gold: " + player.getGold());
	}

}
