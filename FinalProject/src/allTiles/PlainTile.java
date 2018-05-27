package allTiles;

import javax.swing.ImageIcon;

import mapClasses.Tile;

public class PlainTile extends Tile
{
	private static String[] imageOptions = {"/desert_rock_1.png", "/desert_rock_2.png", "/desert_cactus.png", "/desert_bush.png"};
	
	public PlainTile(BIOME biome)
	{
		super(biome);
		
		this.name = "desert";
		
		//pick style of desert
		int rand = (int)(Math.random() * imageOptions.length);
		
		image =  new ImageIcon(this.getClass().getResource(imageOptions[rand])).getImage();
	}
}
