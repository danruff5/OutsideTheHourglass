/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author dckra
 */
public class ItemPosition implements IItem {
    public int row, col;
    public IItem item;
    
    public ItemPosition(int x, int y) {
        this.row = x;
        this.col = y;
    } // ItemPosition(int, int);
    
    public ItemPosition(IItem item, int x, int y) {
        this.row = x;
        this.col = y;
        this.item = item;
    } // ItemPosition(IItem, int, int);

    @Override
    public void Draw(Screen screen, int x, int y) {
        item.Draw(screen, x, y);
    }
    public String getName() {
        return item.getName();
    }
    public String getDescription() {
        return item.getDescription();
    }
    
//    public IItem item() {
//        if (this.item instanceof ItemPosition)
//            return ((ItemPosition)item).item;
//        else
//            return this.item;
//    }

    
} // ItemPosition;

