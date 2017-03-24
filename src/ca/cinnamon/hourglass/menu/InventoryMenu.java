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
import java.awt.event.KeyEvent;

/**
 *
 * @author dckra
 */
public class InventoryMenu extends Menu {

    private PlayerInventory inventory;
    private Keys keys;
    
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
        
        screen.drawGrid(white, 100, 50, 50, 9, 4);
        screen.drawGrid(white, 150, 1200, 50, 1, 4);
        screen.drawSquare(white, 150, 1050, 125);        
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
    } // GetCurrentGameState();

    public void SetCurrentGameState(STATE s) {
        GameState = s;
        //Create a StateChangedEvent here and throw it back to the main
        stateChanged();
    } // SetCurrentGameState(STATE);
} // InventoryMenu;
