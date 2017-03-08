/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.screen.Screen;
import java.util.ArrayList;

/**
 *
 * @author dckra
 */
public abstract class Container {
    public ArrayList<IItem> items;
    
    public void AddItem(IItem item) {
        items.add(item);
    } // AddItem(IItem);
    
    public void RemoveItem(IItem item) {
        items.remove(item);
    } // RemoveItem(IItem);
    
    public abstract void DrawMenu(Screen screen);
} // Container;
