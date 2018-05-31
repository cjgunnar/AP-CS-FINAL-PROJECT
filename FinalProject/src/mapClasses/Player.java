package mapClasses;

import javax.swing.ImageIcon;

public class Player extends Person
{
	
	public Player()
	{
		super("Player");
		
		setGold(5);
		
		image =  new ImageIcon(this.getClass().getResource("/player.png")).getImage();
		isPlayer = true;
	}
	
}
