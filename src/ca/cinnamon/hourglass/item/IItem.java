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
public interface IItem {
    public void Draw(Screen screen, int x, int y);
    public String getName();
    public String getDescription();
} // IItem;
