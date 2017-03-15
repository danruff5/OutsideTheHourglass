/**
 * @author Cody
 * @date	3/14/2017
 * @purpose move image handling out of the logical tile objects to allow for layered tiles 
 */
package ca.cinnamon.hourglass.map.tile;

import java.awt.Point;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;

public class TileImg implements java.io.Serializable,Comparable<TileImg> {
	protected transient static BitmapManager imgs=BitmapManager.bManager;
    
    // The tiles heght and width -> always the same for every tile.
    protected static final int HEIGHT=Map.tileHeight ;
    protected static final int WIDTH=Map.tileWidth;
    protected int X_OFFSET = 0;
    protected int Y_OFFSET = 0;
    public int Z=0;
    protected String img;
    
    public TileImg(String source,int Xoff, int Yoff){
    	img=source;
    	X_OFFSET=Xoff;
    	Y_OFFSET=Yoff;
    }
    public void draw(Bitmap fullBMP, Point p) 
    {
    	fullBMP.blit(imgs.add(img), p.x*WIDTH, p.y*HEIGHT, WIDTH, HEIGHT, X_OFFSET, Y_OFFSET);
    }
	@Override
	public int compareTo(TileImg o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.Z, o.Z);
	}
}
