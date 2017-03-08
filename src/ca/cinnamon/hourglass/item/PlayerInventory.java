/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.screen.Screen;
import java.awt.Color;

/**
 *
 * @author dckra
 */
public class PlayerInventory extends Container {
    @Override
    public void DrawMenu(Screen screen) {
        System.out.println("Draw Inventory");
        //screen.colourFill(new Color(255, 0, 0).getRGB(), 10, 10, 100, 100);
    } // DrawMenu(Graphics);
} // PlayerInventory;
