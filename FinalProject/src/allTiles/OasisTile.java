package allTiles;

import javax.swing.ImageIcon;

import mapClasses.Tile;

public class OasisTile extends Tile
{
	public OasisTile()
	{
		super(BIOME.DESERT);
		
		name = "Oasis";
		
		image =  new ImageIcon(this.getClass().getResource("/oasis.png")).getImage();
	}
}
