/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import java.awt.Graphics;

/**
 *
 * @author dckra
 */
public class Leggings implements IWearable {

    @Override
    public void Effect() {
    } // Effect();

    @Override
    public int Protect() {
        return (int)(Math.random() * 10);
    } // Protect();

    @Override
    public void Draw(Graphics g) {
    } // Draw(Graphics);

    @Override
    public String getName() {
        return "Leather Leggings";
    } // getName();

    @Override
    public String getDescription() {
        return "The legs of a cow.";
    } // getDescription()
    
} // Leggings;
