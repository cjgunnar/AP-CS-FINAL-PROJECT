package peopleObjects;

import javax.swing.ImageIcon;

import mapClasses.Person;

public class Bandit extends Person
{
	public Bandit()
	{
		image = new ImageIcon(this.getClass().getResource("/bandit_1.png")).getImage();
	}
}
