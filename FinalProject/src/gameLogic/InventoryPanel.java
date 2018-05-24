package gameLogic;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InventoryPanel extends JPanel
{
	Player player;
	
	public InventoryPanel(Player player)
	{
		this.player = player;
		CreateComponents();
	}
	
	private void CreateComponents()
	{
		JLabel inv = new JLabel("Inventory");
		add(inv);
	}
}
