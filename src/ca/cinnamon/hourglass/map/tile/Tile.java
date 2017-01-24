/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map.tile;

import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author daniel
 */
public class Tile {
    // This is a specific tile in the map.

    // The tiles heght and width -> always the same for every tile.
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;
    public static final int X_OFFSET = 0;
    public static final int Y_OFFSET = 0;

    // The position of the tile.
    protected int x;
    protected int y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw(Screen screen) {}
    public boolean collide() { return false; }
}
