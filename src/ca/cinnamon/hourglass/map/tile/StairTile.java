package ca.cinnamon.hourglass.map.tile;

import ca.cinnamon.hourglass.entity.Entity;

public class StairTile extends Tile {

	public StairTile(int x, int y) {
		super(x, y);
		img=imgs.add("./Pictures/stair.bmp");
        this.isSolid=false;
	}
	
	@Override
	public boolean collide(Entity E) {
		//IF E=PLAYER THEN MOVER DOWN FLOOR
		System.out.println("Stairs Down");
		return isSolid; 
		} 
}
