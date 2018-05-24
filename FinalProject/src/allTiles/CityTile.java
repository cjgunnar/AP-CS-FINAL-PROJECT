package allTiles;

import javax.swing.ImageIcon;

import gameLogic.Tile;

public class CityTile extends Tile
{
	public CityTile()
	{
		super("desert");
		
		name = "City";
		
		image =  new ImageIcon(this.getClass().getResource("/city_desert.png")).getImage();
	}
}
