package ca.cinnamon.hourglass.entity.mob;
//HELLO WORLD
import java.awt.Point;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Screen;

public class Slime extends Mob {

	public Slime() {
		this.loc=new Point(1,1);
		this.HP=2;
	}
	@Override
    public void Draw(Screen screen) {
    	screen.blit(sprites.add("./Pictures/slime.bmp"), this.loc.x*Map.tileWidth, (this.loc.y)*Map.tileWidth,50,50);
    	
    }
	

}
