package allTiles;

import javax.swing.ImageIcon;

import mapClasses.Tile;

public class CityTile extends Tile
{
	public CityTile()
	{
		super(BIOME.DESERT);
		
		name = "City";
		
		image =  new ImageIcon(this.getClass().getResource("/city_desert.png")).getImage();
	}
}
