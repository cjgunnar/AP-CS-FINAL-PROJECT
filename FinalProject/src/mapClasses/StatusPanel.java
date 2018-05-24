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
		
		add(location);
		add(health);
		add(thirst);
		add(hunger);
	}
	
	public void UpdateInfo()
	{
		location.setText(player.getOccupiedTile().getName());
		health.setText("Health: " + player.getHealth());
		thirst.setText("Thirst: " + player.getThirst());
		hunger.setText("Hunger: " + player.getHunger());
	}
	
}
