package ca.cinnamon.hourglass.entity.mob;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Screen;
import utility.Path;

public class Wolf extends Mob {
	public static Random rnd = new Random();
	
	public Wolf(Point spawn) {
		super(spawn);
        this.HP = 1;//health points
        this.POINT_VALUE = 200;
	}

    public void Tick() {
    		Point target=Map.player.getLocation();    		
    		this.loc=Path.orthoPathMap(loc, target, Map.currentMap);
    		this.loc=Path.orthoPathMap(loc, target, Map.currentMap);
    }

    @Override //okay test boop
    public void Draw(Screen screen) {
        screen.blit(sprites.add("./Pictures/crawl-tiles Oct-5-2010/dc-mon/animals/warg.png"), this.loc.x * Map.tileWidth, (this.loc.y) * Map.tileWidth, 32, 32);
    }
}
