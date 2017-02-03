package ca.cinnamon.hourglass.map.tile;

public class FloorTile extends Tile {
	 public FloorTile(int x, int y) {
		 	super(x,y);
	        img=imgs.add("./Pictures/dirt_texture_by_b_a88.bmp");
	        this.isSolid=false;
	    }
	    
}
