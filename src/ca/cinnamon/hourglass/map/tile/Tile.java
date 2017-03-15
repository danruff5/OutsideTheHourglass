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
import ca.cinnamon.hourglass.map.Node;
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
public abstract class Tile extends Node implements java.io.Serializable{
    // The tiles heght and width -> always the same for every tile.
    protected static final int HEIGHT=Map.tileHeight ;
    protected static final int WIDTH=Map.tileWidth;
    protected int X_OFFSET = 0;
    protected int Y_OFFSET = 0;
    protected String img;
    
    // The position of the tile.
    protected int drawX;
    protected int drawY;
    protected Random rnd=new Random();
    
    public Tile(int dx, int dy)  {
    	super(dx/WIDTH,dy/HEIGHT);
        this.drawX = dx;
        this.drawY = dy;
        img=null;
    }
    
    public void draw(Bitmap fullBMP) 
    {
    	fullBMP.blit(BitmapManager.add(img), drawX, drawY,WIDTH,HEIGHT,X_OFFSET,Y_OFFSET);
    }
    public boolean collide(Entity E) { return isSolid; }
}
