/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map;

import java.awt.Point;

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
    public static final int tileWidth = 50;
    public static final int tileHeight = 50;
    
    public int height;
    public int width;
    
    public Map(int width,int height){
    	this.tiles=new Tile[width][height];
    	this.height=height;
    	this.width=width;
    	testMap(width,height);
    	
    }
    private void testMap(int width,int height){
        for (int i=0;i<width;++i){
        	for (int j=0;j<height;++j){	
        		this.add(new FloorTile(50*i,50*j));
        	}
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0]=new WallTile(50*i,0);
        	this.tiles[i][height-1]=new WallTile(50*i,50*(height-1));
        }
        for (int i=0;i<width;++i){
        	this.tiles[0][i]=new WallTile(0,50*i);
        	this.tiles[width-1][i]=new WallTile(50*(width-1),50*i);
        }
        this.tiles[1][1]=new StairTile(50,50);
       
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
    public Tile get(Point p){
    	return tiles[p.x][p.y];
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
