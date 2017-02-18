package ca.cinnamon.hourglass.entity.mob;

import java.awt.Point;
import java.util.Random;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Screen;

public class Wolf extends Mob {
	public static Random rnd = new Random();
	
	public Wolf(Point spawn) {
		super(spawn);
        this.HP = 2;//health points
	}

    public void Tick() {
        //++ticksSinceLastAction;

        //if (ticksSinceLastAction > 100) {
            //1~4 options(up, left, down, right)
            int randomNum = rnd.nextInt(4) + 1;
            switch (randomNum) {
                case 1:
                    this.moveUp(currentMap.tiles[loc.x][loc.y - 1]);
                    //ticksSinceLastAction = 0;
                    break;

                case 2:
                    this.moveLeft(currentMap.tiles[loc.x - 1][loc.y]);
                    //ticksSinceLastAction = 0;
                    break;

                case 3:
                    this.moveDown(currentMap.tiles[loc.x][loc.y + 1]);
                    //ticksSinceLastAction = 0;
                    break;

                case 4:
                    this.moveRight(currentMap.tiles[loc.x + 1][loc.y]);
                    //ticksSinceLastAction = 0;
                    break;
              
            }

        //}

    }

    @Override //okay test boop
    public void Draw(Screen screen) {
        screen.blit(sprites.add("./Pictures/crawl-tiles Oct-5-2010/dc-mon/animals/warg.png"), this.loc.x * Map.tileWidth, (this.loc.y) * Map.tileWidth, 32, 32);

    }

}
