/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map.tile;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author Cody Hines
 * @date 2/1/2017
 * @purpose A tile is a square on a map grid that includes an image and whether it is solid to movement;
 * @version 1
 */
public abstract class Tile implements java.io.Serializable{
    // This is a specific tile in the map.
    protected transient static BitmapManager imgs=new BitmapManager();
    
    // The tiles heght and width -> always the same for every tile.
    protected static final int HEIGHT=Map.tileHeight ;
    protected static final int WIDTH=Map.tileWidth;
    protected int X_OFFSET = 0;
    protected int Y_OFFSET = 0;
    protected String img;
    public boolean isSolid;
    // The position of the tile.
    protected int x;
    protected int y;
    protected Random rnd=new Random();
    public Point loc;
    
    public Tile(int x, int y)  {
        this.x = x;
        this.y = y;
        loc=new Point(x/WIDTH,y/HEIGHT);
        img=null;
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
    
    public void draw(Bitmap fullBMP) 
    {
    	fullBMP.blit(imgs.add(img), x, y,WIDTH,HEIGHT,X_OFFSET,Y_OFFSET);
    }
    public boolean collide(Entity E) { return isSolid; }
}
