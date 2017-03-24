/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.screen.Screen;
import java.awt.Color;
import utility.DrawableString;

/**
 *
 * @author dckra
 */
public class Sword implements IWeapon {

    @Override
    public int Damage() {
        return 1;
    } // Attack(Mob);

    @Override
    public void Draw(Screen screen, int x, int y) {
        screen.drawSquare(new Color(255, 0, 255).getRGB(), 75, x + 37, y + 37);
        //DrawableString.Draw(screen, getName(), x, y);
    } // Draw(Graphics);

    @Override
    public String getName() {
        return "Sword";
    } // getName();

    @Override
    public String getDescription() {
        return "Pointy blade used for stabing.";
    } // getDescription();
    
} // Sword
