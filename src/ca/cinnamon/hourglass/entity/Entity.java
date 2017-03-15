/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity;

import java.awt.Point;

import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author Daniel
 */
public interface Entity {
    public void Tick();
    public void Draw(Screen screen);
    public static BitmapManager sprites=BitmapManager.bManager;
    public Map currentMap=null;
    public int Hurt(int DAM);
    public Point getLocation();
    public int Attack(Entity E);
    public int Death();
}
