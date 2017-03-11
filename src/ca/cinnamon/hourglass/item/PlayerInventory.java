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
    public IWeapon weapon;
    public IWearable[] armour;
    
    @Override
    public void DrawMenu(Screen screen) {

    } // DrawMenu(Graphics);
} // PlayerInventory;
