package ca.cinnamon.hourglass.map.tile;

public class WallTile extends Tile {

	public WallTile(int x, int y) {
		super(x, y);
		img=imgs.add("./Pictures/t_brickfloorrevamp_d.bmp");
        this.isSolid=true;
	}

}
