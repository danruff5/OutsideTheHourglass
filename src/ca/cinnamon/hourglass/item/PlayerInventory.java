/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.screen.Screen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dckra
 */
public class PlayerInventory extends Container {
    public List<ItemPosition> items; // Intentional hiding.
    public IWeapon weapon;
    public IWearable[] armour;
    
    public int rows = 9, cols = 4; // TODO: create proper setters to deal with inventory resizeing.
    public int size = 100;
    public int offsetx = 50, offsety = 50;

    public PlayerInventory() {
        this.items = new ArrayList<>();
    } // PlayerInventory();
    
    @Override
    public void AddItem(IItem item) {
        ItemPosition i = FindEmptyLocation(item);
        if (i == null) {
            // TODO: Throw error or do something... No room in inventory.
        } else {
            items.add(i);
        }
    } // AddItem(IItem);
    
    @Override
    public void RemoveItem(IItem item) {
        ItemPosition i = FindItem(item);
        if (i != null)
            items.remove(i);
    } // RemoveItem(IItem);
    
    private ItemPosition FindEmptyLocation(IItem item) {
        for (int col = 1; col <= this.cols; col++) {
            for (int row = 1; row <= this.rows; row++) {
                boolean itemFound = false;
                for (ItemPosition i : this.items) {
                    if (i.row == row && i.col == col) {
                        itemFound = true;
                        break;
                    }
                } // for(ItemPosition);
                
                if (!itemFound) {
                    return new ItemPosition(item, row, col);
                }
            } // for(row);
        } // for(col);
        return null;
    } // FindEmptyLocation(IItem);
    
    private ItemPosition FindItem(IItem item) {
        for (ItemPosition i : this.items)
            if (i.item == item) return i;
        return null;
    } // FindItem(IItem);
    
    public void Draw(Screen screen) {
        int white = new Color(255, 255, 255).getRGB();
        screen.drawGrid(white, this.size, this.offsetx, this.offsety, this.rows, this.cols);
        
        
        screen.drawGrid(white, 150, 1200, 50, 1, 4);
        screen.drawSquare(white, 150, 1050, 125); 
        
        for (int i = 0; i < armour.length; i++) {
            armour[i].Draw(
                    screen,
                    1200,
                    50 + i * 150
            );
        }
        weapon.Draw(screen, 1050, 125);
        
        for (ItemPosition item : this.items) {
            item.Draw(
                screen, 
                this.offsetx + (item.row - 1) * this.size, 
                this.offsety + (item.col - 1) * this.size 
            );
        }
    } // Draw(Screen);
} // PlayerInventory;