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
    
    public int ticksSinceLastAction=0;
    public int speedLimit=150;
    public Player(Keys keys) {
        super();
        this.loc=new Point(2,2);
        this.keys = keys;
        this.ATK=2;
    }
    
    public void Tick() {
    	++ticksSinceLastAction;
        if (keys.keys[KeyEvent.VK_UP].pressed) {
            // Player Move Up.
            //System.out.println("Player move Up");
            if (ticksSinceLastAction>=speedLimit){
            	this.moveUp(currentMap.tiles[loc.x][loc.y-1]);
            	ticksSinceLastAction=0;
            }
        }
        if (keys.keys[KeyEvent.VK_RIGHT].pressed) {
            // Player Move Right.
            //System.out.println("Player move Right");
            if (ticksSinceLastAction>=speedLimit){
            	this.moveRight(currentMap.tiles[loc.x+1][loc.y]);
            	ticksSinceLastAction=0;
            }
        }
        if (keys.keys[KeyEvent.VK_DOWN].pressed) {
            // Player Move Down.
            //System.out.println("Player move Down");
            if (ticksSinceLastAction>=speedLimit){
            	this.moveDown(currentMap.tiles[loc.x][loc.y+1]);
            	ticksSinceLastAction=0;
            }
        }
        if (keys.keys[KeyEvent.VK_LEFT].pressed) {
            // Player Move Left.
            //System.out.println("Player move Left");
            if (ticksSinceLastAction>=speedLimit){
            	this.moveLeft(currentMap.tiles[loc.x-1][loc.y]);
            	ticksSinceLastAction=0;
            }
        }
    }
    @Override
    public void Draw(Screen screen) {
    	screen.blit(sprites.add("./Pictures/goblin.bmp"), this.loc.x*Map.tileWidth, (this.loc.y-1)*Map.tileWidth,50,100);
    	
    }
}
