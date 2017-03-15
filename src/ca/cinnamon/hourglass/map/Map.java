/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.map;

import java.awt.Point;
import java.awt.Rectangle;
import java.beans.Transient;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.entity.Player;
import ca.cinnamon.hourglass.entity.mob.Slime;
import ca.cinnamon.hourglass.entity.mob.Wolf;
import ca.cinnamon.hourglass.map.tile.*;
import ca.cinnamon.hourglass.screen.Bitmap;
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
public class Map implements java.io.Serializable{
    // This is the entire map of tiles.
    public Tile[][] tiles;
    public static transient Map currentMap=null;
    public static transient Player player=null;
    public static final int tileWidth = 32;
    public static final int tileHeight = 32;
    public int height;
    public int width;
    public ArrayList<Entity> entities;
    public Map(int width,int height){
    	this.tiles=new Tile[width][height];
    	
    	this.height=height;
    	this.width=width;
    	entities = new ArrayList<>();
    	Map.currentMap=this;
    }
  
    public Point GetRandomFloorTile(){
    	ArrayList<Point> openTiles=new ArrayList<Point>();
    	for (int i=1;i<width;++i){
        	for (int j=1;j<height;++j){	
        		if (this.tiles[i][j].getClass().equals(FloorTile.class)){
        			openTiles.add(new Point(i,j));
        		}
        	}
        }
    	Random rnd=new Random();
    	if (!openTiles.isEmpty())
    		return openTiles.get(rnd.nextInt(openTiles.size()));
    	return null;
    }
    
    public void testCave(int itterations){
    	for (int i=0;i<width;++i){
        	for (int j=0;j<height;++j){	
        		this.add(new FloorTile(tileWidth*i,tileHeight*j));
        	}
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0]=new WallTile(tileWidth*i,0);
        	this.tiles[i][1]=new WallTile(tileWidth*i,1);
        	this.tiles[i][height-1]=new WallTile(tileWidth*i,tileHeight*(height-1));
        }
        for (int i=0;i<height;++i){
        	this.tiles[0][i]=new WallTile(0,tileHeight*i);
        	this.tiles[width-1][i]=new WallTile(tileWidth*(width-1),tileHeight*i);
        }
    	Random rnd=new Random();
    	for (int i=1;i<width-1;++i){
        	for (int j=1;j<height-1;++j){
        		if (rnd.nextInt(10)>5){
        			this.tiles[i][j]=new WallTile(i*this.tileWidth,j*this.tileWidth);
        		}
        		
        	}
        }
    	for (int foo=0;foo<itterations;++foo){
	    	for (int i=1;i<width-1;++i){
	        	for (int j=1;j<height-1;++j){
	        		int count=0;
	        		for (int k=-1;k<2;++k){
	        			for (int m=-1;m<2;++m){
	        				if (this.tiles[i+k][j+m].getClass().equals(WallTile.class)){
	        					++count;
	        				}
	        			}
	        		}
	        		if (count>4){
	        			tiles[i][j]=new WallTile(i*this.tileWidth,j*this.tileHeight);
	        		}
	        		else
	        			tiles[i][j]=new FloorTile(i*this.tileWidth,j*this.tileHeight);
	        	}
	        }
    	}
    	Point stairs=this.GetRandomFloorTile();
    	this.tiles[stairs.x][stairs.y]=new StairTile(stairs.x*tileWidth,stairs.y*tileHeight);
        
        Point chest = this.GetRandomFloorTile();
        this.tiles[chest.x][chest.y] = new ChestTile(chest.x * tileWidth, chest.y * tileHeight);
        
        for (int i = 0; i < width; ++i) {
        	for (int j=0;j < height;++j)
        	{
        		if (i>0)
        			tiles[i][j].neighbors.add(tiles[i-1][j]);
        		if (i<width-1)
        			tiles[i][j].neighbors.add(tiles[i+1][j]);
        		if (j>0)
        			tiles[i][j].neighbors.add(tiles[i][j-1]);
        		if (j<height-1)
            		tiles[i][j].neighbors.add(tiles[i][j+1]);
        	}
        }
        
    	Slime slm=new Slime(Map.currentMap.GetRandomFloorTile());
        
        entities.add(slm);
        for (int i=0;i<10;++i){
        	entities.add(new Slime(Map.currentMap.GetRandomFloorTile()));
        }
        entities.add(new Wolf(Map.currentMap.GetRandomFloorTile()));
        
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
    
    public void save(){
    	try {
            FileOutputStream fileOut =
            new FileOutputStream("./map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
         }catch(IOException i) {
            i.printStackTrace();
         }
    }
    public static Map load(){
    	Map m;
    	try {
            FileInputStream fileIn = new FileInputStream("./map.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            m = (Map) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return null;
         }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
         }
    	return m;
    }
    public void draw(Screen screen) {
       	for (int i = 0; i < width; ++i) {
        	for (int j=0;j < height;++j)
        	{
        		tiles[i][j].draw(screen);
        	}
        }
        for(int i=0;i<entities.size();++i){
        	entities.get(i).Draw(screen);
        }
  
    	
    }
}
