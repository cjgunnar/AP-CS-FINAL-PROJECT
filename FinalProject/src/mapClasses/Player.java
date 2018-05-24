package mapClasses;

import javax.swing.ImageIcon;

public class Player extends Person
{
	
	public Player()
	{
		image =  new ImageIcon(this.getClass().getResource("/player.png")).getImage();
		isPlayer = true;
	}
	
}
