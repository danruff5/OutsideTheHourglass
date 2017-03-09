package ca.cinnamon.hourglass.map.tile;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.map.Map;

public class StairTile extends Tile {

	public StairTile(int x, int y) {
		super(x, y);
		X_OFFSET=WIDTH*5;
		Y_OFFSET=HEIGHT*4;
		img="./Pictures/sheet32x.png";
        this.isSolid=false;
	}
	
	@Override
	public boolean collide(Entity E) {
		//IF E=PLAYER THEN MOVER DOWN FLOOR
		if(E==Map.player){
		Map.currentMap= new Map(Map.currentMap.width,Map.currentMap.height);
		Map.currentMap.testCave(2);
		Map.player.currentMap=Map.currentMap;
		if (Map.player!=null){
        	Map.currentMap.entities.add(Map.player);
        	Map.player.loc=Map.currentMap.GetRandomFloorTile();
		}
		System.out.println("Stairs Down");
		}
		return isSolid; 
		} 
}
