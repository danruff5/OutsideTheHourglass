/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity;

import ca.cinnamon.hourglass.entity.mob.Mob;
import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Screen;

import java.awt.Point;

import com.sun.glass.events.KeyEvent;

/**
 *
 * @author Daniel
 */
public class Player extends Mob {
    private Keys keys;
    
    public int speedLimit=150;
    public Player(Keys keys,Point spawn) {
        super(spawn);

        this.keys = keys;
        this.ATK=2;
    }
    
    public void Tick() {
        if (keys.keys[KeyEvent.VK_UP].pressed) {
            // Player Move Up.
            this.moveUp(currentMap.tiles[loc.x][loc.y-1]);
        }
        if (keys.keys[KeyEvent.VK_RIGHT].pressed) {
            // Player Move Right.
            this.moveRight(currentMap.tiles[loc.x+1][loc.y]);
        }
        if (keys.keys[KeyEvent.VK_DOWN].pressed) {
            // Player Move Down.
            this.moveDown(currentMap.tiles[loc.x][loc.y+1]);
        }
        if (keys.keys[KeyEvent.VK_LEFT].pressed) {
            // Player Move Left.
            this.moveLeft(currentMap.tiles[loc.x-1][loc.y]);
        }
    }
    @Override
    public void Draw(Screen screen) {
    	screen.blit(sprites.add("./Pictures/wizzard.bmp"), this.loc.x*Map.tileWidth, (this.loc.y-0)*Map.tileWidth,Map.tileWidth,Map.tileHeight);
    	
    }
}
