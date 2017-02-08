package ca.cinnamon.hourglass.map.tile;

import ca.cinnamon.hourglass.screen.Bitmap;

public class WallTile extends Tile {

	public WallTile(int x, int y) {
		super(x, y);
		this.X_OFFSET=WIDTH;
		this.Y_OFFSET=HEIGHT;
		img="./Pictures/sheet32X.png";
        this.isSolid=true;
	}
	@Override
	public void draw(Bitmap fullBMP) 
    {	
    	fullBMP.blit(imgs.add(img), x, y,WIDTH,HEIGHT,X_OFFSET,Y_OFFSET);
    }

}
