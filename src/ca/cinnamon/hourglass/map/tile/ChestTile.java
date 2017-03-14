package ca.cinnamon.hourglass.map.tile;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.entity.Player;
import ca.cinnamon.hourglass.item.Potion;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Bitmap;

import java.awt.Point;

public class ChestTile extends Tile {

    public ChestTile(int x, int y) {
        super(x, y);
        X_OFFSET = WIDTH * 14;
        Y_OFFSET = HEIGHT * 8;
        img = "./Pictures/sheet32x.png";
        this.isSolid = false;
    } 

    @Override
    public boolean collide(Entity E) {
        //IF E=PLAYER THEN MOVER DOWN FLOOR
        if (E == Map.player) {
            Player player = (Player)E;
            for (int i = 0; i < Math.random() * 5; i++)
                player.inventory.items.add(new Potion(player));
            
            Point choice = FloorTile.RandomOffsets[rnd.nextInt(FloorTile.RandomOffsets.length)];
            X_OFFSET = choice.x;
            Y_OFFSET = choice.y;
        }
        return isSolid;
    } 
    @Override
	public void draw(Bitmap fullBMP) 
    {
    	fullBMP.blit(imgs.add(img), x, y,WIDTH,HEIGHT,10,1);
    	fullBMP.blit(imgs.add(img), x, y,WIDTH,HEIGHT,X_OFFSET,Y_OFFSET);
    }
} // END class
