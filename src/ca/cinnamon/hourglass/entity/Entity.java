/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity;

import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author Daniel
 */
public abstract class Entity {    
    public void Tick() {}
    public void Draw(Screen screen) {}
}
