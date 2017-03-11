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
    private Graphics g;
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
        if (g == null) g = screen.image.getGraphics();
        g.setFont(font);
        g.setColor(Color.WHITE);
        
        
        boolean drawn = false;
        for (IItem item : inventory.items) {
            if (item instanceof Potion) {
                potionCount++;
                
                if (!drawn) {
                    drawn = true;
                    g.drawString(item.getName(), 50, 100);
                    g.drawString(item.getDescription(), 50, 150);
                }
            }
        }
        g.drawString("You have " + potionCount + " potions", 50, 50);
        
        g.drawString("Weapon", 50, 250);
        g.drawString(inventory.weapon.getName(), 50, 300);
        g.drawString(inventory.weapon.getDescription(), 50, 350);
        
        g.drawString("Armour", 550, 50);
        g.drawString(inventory.armour[0].getName(), 550, 100);
        g.drawString(inventory.armour[0].getDescription(), 550, 150);
        g.drawString(inventory.armour[1].getName(), 550, 200);
        g.drawString(inventory.armour[1].getDescription(), 550, 250);
        g.drawString(inventory.armour[2].getName(), 550, 300);
        g.drawString(inventory.armour[2].getDescription(), 550, 350);
        g.drawString(inventory.armour[3].getName(), 550, 400);
        g.drawString(inventory.armour[3].getDescription(), 550, 450);
        
        g.setColor(Color.GRAY);
        g.drawRect(50 - 20, 50 - 20, 500 - 20, 150 - 20);
        g.drawRect(50 - 20, 250 - 20, 500 - 20, 150 - 20);
        g.drawRect(550 - 20, 50 - 20, 650 - 20, 450 - 20);
        
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
