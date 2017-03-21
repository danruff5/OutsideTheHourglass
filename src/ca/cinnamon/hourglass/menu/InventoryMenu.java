/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.item.IItem;
import ca.cinnamon.hourglass.item.PlayerInventory;
import ca.cinnamon.hourglass.item.Potion;
import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author dckra
 */
public class InventoryMenu extends Menu {

    private PlayerInventory inventory;
    private Keys keys;
    
    private Font font = new Font("Arial Black", Font.BOLD, 20);
    //private Graphics g;
    private STATE GameState;
    
    private int screen_width, screen_height;
    
    public static enum STATE {
        inventory, game
    } // enum STATE;

    public InventoryMenu(Keys keys, PlayerInventory inventory, int screen_width, int screen_height) {
        ID = MenuType.Inventory;
        this.keys = keys;
        this.inventory = inventory;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
    } // InventoryMenu(Playinventory);

    @Override
    public void init(MenuManager.MenuType id) {
    } // init(MenuType);

    @Override
    public void draw(Screen screen) {
        int potionCount = 0;        
        
        boolean drawn = false;
        for (IItem item : inventory.items) {
            if (item instanceof Potion) {
                potionCount++;
                
                if (!drawn) {
                    drawn = true;
                }
            }
        }
        
        int white = new Color(255, 255, 255).getRGB();
        int startx = 50, starty = 50;
        int size = 50;
        int cols = 10, rows = 10;
        
        for (int x = startx; x <= startx + (size * cols); x += size)
            screen.colourLine(white, x, starty, x + 1, starty + size * rows);
        
        for (int y = starty; y <= starty + (size * rows); y += size)
            screen.colourLine(white, startx, y, startx + size * cols, y);
    } // draw(Screen);

    @Override
    public void tick() {
        if (this.keys.keys[KeyEvent.VK_H].pressed) {
            for (IItem item : inventory.items) {
                if (item instanceof Potion) {
                    ((Potion)item).Use();
                    inventory.items.remove(item);
                    break;
                }
            }
        } else if (this.keys.keys[KeyEvent.VK_E].pressed) {
            SetCurrentGameState(STATE.game);
        }
    } // tick();
    
     public STATE GetCurrentGameState() {
        return GameState;
    }

    public void SetCurrentGameState(STATE s) {
        GameState = s;
        //Create a StateChangedEvent here and throw it back to the main
        stateChanged();
    }
} // InventoryMenu;
