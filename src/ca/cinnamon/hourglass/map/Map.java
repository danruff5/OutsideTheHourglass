/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map;

import ca.cinnamon.hourglass.map.tile.*;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;

/**
*
* @author Cody Hines
* @date 2/2/2017
* @purpose A map is an entity that holds the shape of floors/levels as a group of tiles 
* and provides facilities for saving, loading, and rendering itself
* TODO: Write load and save functions
*/
public class Map {
    // This is the entire map of tiles.
    public Tile[][] tiles;
    //public static final int HEIGHT = 4;
    //public static final int WIDTH = 4;
    
    public int height;
    public int width;
    
    public Map(int width,int height){
    	this.tiles=new Tile[width][height];
    	this.height=height;
    	this.width=width;
    	testMap(width,height);
    	
    }
    private void testMap(int width,int height){
    	//TESTING CODE
    	String dirtImgLoc="./Pictures/dirt_texture_by_b_a88.bmp";//"C:/Users/Cody/Pictures/dirt_texture_by_b_a88.bmp";
        String brickImgLoc="./Pictures/t_brickfloorrevamp_d.bmp";
        String stairImgLoc="./Pictures/stair.bmp";
        BitmapManager imgs=new BitmapManager();

        for (int i=0;i<width;++i){
        	for (int j=0;j<height;++j){	
        		this.add(new Tile(imgs.add(dirtImgLoc),50*i,50*j));
        	}
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0]=new Tile(imgs.add(brickImgLoc),50*i,0);
        	this.tiles[i][height-1]=new Tile(imgs.add(brickImgLoc),50*i,50*(height-1));
        }
        for (int i=0;i<width;++i){
        	this.tiles[0][i]=new Tile(imgs.add(brickImgLoc),0,50*i);
        	this.tiles[width-1][i]=new Tile(imgs.add(brickImgLoc),50*(width-1),50*i);
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0].isSolid=true;
        	this.tiles[i][height-1].isSolid=true;
        }
        for (int i=0;i<height;++i){
            this.tiles[0][i].isSolid=true;
            this.tiles[width-1][i].isSolid=true;
        }        
        this.tiles[1][1]=new Tile(imgs.add(stairImgLoc),50,50);
    }
    public void add(Tile T){
    	for (int i = 0; i <width; ++i) {
        	for (int j=0;j<height;++j){
        		if (tiles[i][j]==null){
        			tiles[i][j]=T;
        			i=width+1;
        			j=height+1;
        		}
        		
        	}
        }
    }
  
    /*public void calculateOffsets(){
    	for (int i = 0; i <width; ++i) {
        	for (int j=0;j<height;++j){
        		tiles[i][j].X_OFFSET=50*i;
        		tiles[i][j].Y_OFFSET=50*j;
        	}
        }
    }*/
    public void draw(Screen screen) {       
        screen.fill(0);
        for (int i = 0; i < width; ++i) {
        	for (int j=0;j<height;++j){
        		tiles[i][j].draw(screen);
        	}
        }
    }
}
