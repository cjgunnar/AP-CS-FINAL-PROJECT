package peopleObjects;

import javax.swing.ImageIcon;

import mapClasses.Person;

/**
 * A Bandit
 * @author Caden
 *
 */
public class Bandit extends Person
{
	public Bandit()
	{
		super("Bandit");
		
		image = new ImageIcon(this.getClass().getResource("/bandit_1.png")).getImage();
	}
}
