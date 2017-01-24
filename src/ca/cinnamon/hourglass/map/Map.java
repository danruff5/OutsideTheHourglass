/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map;

import ca.cinnamon.hourglass.map.tile.*;
import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author daniel
 */
public class Map {
    // This is the entire map of tiles.
    public Tile[] tiles;
    public static final int HEIGHT = 4;
    public static final int WIDTH = 4;
    
    public int height;
    public int width;
    
    public void draw(Screen screen) {       
        screen.fill(0);
        for (int i = 0; i < tiles.length; ++i) {
            tiles[i].draw(screen);
        }
    }
}
