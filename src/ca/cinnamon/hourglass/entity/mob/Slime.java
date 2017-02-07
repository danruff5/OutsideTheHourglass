package ca.cinnamon.hourglass.entity.mob;
//HELLO WORLD

import java.awt.Point;
import java.util.Random;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Screen;

public class Slime extends Mob {

    //public int ticksSinceLastAction = 0;
    public static Random rnd = new Random();

    public Slime(int x, int y) {
        super();
        this.loc = new Point(x, y);
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
        screen.blit(sprites.add("./Pictures/slime.bmp"), this.loc.x * Map.tileWidth, (this.loc.y) * Map.tileWidth, 50, 50);

    }

}
