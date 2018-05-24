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
	
	public Tile(String biome)
	{
		this.biome = biome;
		
		this.biomeImage = new ImageIcon(this.getClass().getResource("/biome_desert.png")).getImage();
		
		people = new ArrayList<Person>();
		isVisible= false;
	}
	
	public void drawTile(int x, int y, Graphics g)
	{
		Image cloudLayerImage =  new ImageIcon(this.getClass().getResource("/cloudLayer.png")).getImage();
		
		if(isVisible)
		{
			//draw background
			g.drawImage(biomeImage, x, y, null);
			
			//draw picture
			g.drawImage(image, x, y, null);
		}
			
		else
			g.drawImage(cloudLayerImage, x, y, null);
		
		for(Person person : people)
		{
			if(person.isPlayer)
			{
				g.drawImage(person.getImage(), x, y, null);
			}
		}
	}
	
	public void addPerson(Person person)
	{
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
