/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map.tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author Cody Hines
 * @date 2/1/2017
 * @purpose A tile is a square on a map grid that includes an image and whether it is solid to movement;
 * @version 1
 */
public class Tile {
    // This is a specific tile in the map.

    // The tiles heght and width -> always the same for every tile.
    public static final int HEIGHT = 50;
    public static final int WIDTH = 50;
    public int X_OFFSET = 0;
    public int Y_OFFSET = 0;
    public Bitmap img;
    public boolean isSolid=false;
    // The position of the tile.
    protected int x;
    protected int y;
    
    
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        img=null;
    }
    
    public Tile(Bitmap bmp,int x, int y) {
        this.x = x;
        this.y = y;
        img=bmp;
    }
    /*public Tile(String src,int x, int y) {
        this.x = x;
        this.y = y;
        try{
        	img = Bitmap.convert( ImageIO.read(new File(src)),WIDTH,HEIGHT);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    public void draw(Screen screen) 
    {
    	screen.blit(img, x, y,WIDTH,HEIGHT);
    }
    public boolean collide(Entity E) { return isSolid; }
}
