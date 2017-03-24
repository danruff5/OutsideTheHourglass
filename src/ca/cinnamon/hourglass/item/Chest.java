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
public class Chest implements IWearable {

    @Override
    public void Effect() {
    } // Effect();

    @Override
    public int Protect() {
        return (int)(Math.random() * 10);
    } // Protect();

    @Override
    public void Draw(Screen screen, int x, int y) {
        screen.drawSquare(new Color(255, 255, 0).getRGB(), 75, x + 37, y + 37);
        DrawableString.Draw(screen, getName(), x, y);
    } // Draw(Screen);

    @Override
    public String getName() {
        return "Leather Chestpiece";
    } // getName();

    @Override
    public String getDescription() {
        return "The bottom of a cow wrapped around your body.";
    } // getDescription();
} // Chest;
