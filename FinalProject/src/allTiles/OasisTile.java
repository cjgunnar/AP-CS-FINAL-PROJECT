package allTiles;

import javax.swing.ImageIcon;

import gameLogic.Tile;

public class OasisTile extends Tile
{
	public OasisTile()
	{
		super("desert");
		
		name = "Oasis";
		
		image =  new ImageIcon(this.getClass().getResource("/oasis.png")).getImage();
	}
}
