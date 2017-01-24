/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity;

import ca.cinnamon.hourglass.entity.mob.Mob;
import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.screen.Screen;
import com.sun.glass.events.KeyEvent;

/**
 *
 * @author Daniel
 */
public class Player extends Mob {
    private Keys keys;
    
    public Player(Keys keys) {
        super();
        
        this.keys = keys;
    }
    
    public void Tick() {
        if (keys.keys[KeyEvent.VK_UP].pressed) {
            // Player Move Up.
            System.out.println("Player move Up");
        }
        if (keys.keys[KeyEvent.VK_RIGHT].pressed) {
            // Player Move Right.
            System.out.println("Player move Right");
        }
        if (keys.keys[KeyEvent.VK_DOWN].pressed) {
            // Player Move Down.
            System.out.println("Player move Down");
        }
        if (keys.keys[KeyEvent.VK_LEFT].pressed) {
            // Player Move Left.
            System.out.println("Player move Left");
        }
    }
    
    public void Draw(Screen screen) {}
}
