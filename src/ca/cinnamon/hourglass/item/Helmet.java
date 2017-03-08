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
public class Helmet implements IWearable {

    @Override
    public void Effect() {
    } // Effect();

    @Override
    public int Protect() {
        return (int)(Math.random() * 10);
    } // Protect();

    @Override
    public void Draw(Screen screen) {
    } // Draw(Screen)

    @Override
    public String getName() {
        return "Leather Helmet";
    } // getName();

    @Override
    public String getDescription() {
        return "A hat made from a cow.";
    } // getDescription();
    
} // Helmet;
