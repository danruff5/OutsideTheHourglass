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
public class Sword implements IWeapon {

    @Override
    public int Damage() {
        return 1;
    } // Attack(Mob);

    @Override
    public void Draw(Graphics g) {
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
