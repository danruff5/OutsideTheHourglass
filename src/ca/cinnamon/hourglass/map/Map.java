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

import ca.cinnamon.hourglass.entity.Player;
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
    public ArrayList<Point> changedTile=new ArrayList<Point>();
    public int height;
    public int width;
    public transient Bitmap fullBMP; 
    public Map(int width,int height){
    	this.tiles=new Tile[width][height];
    	this.height=height;
    	this.width=width;
    	
    	//testMap(width,height);
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
    public void testMap(int width,int height){
        for (int i=0;i<width;++i){
        	for (int j=0;j<height;++j){	
        		this.add(new FloorTile(tileWidth*i,tileHeight*j));
        	}
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0]=new WallTile(tileWidth*i,0);
        	this.tiles[i][height-1]=new WallTile(tileWidth*i,tileHeight*(height-1));
        }
        for (int i=0;i<height;++i){
        	this.tiles[0][i]=new WallTile(0,tileHeight*i);
        	this.tiles[width-1][i]=new WallTile(tileWidth*(width-1),tileHeight*i);
        }
        this.tiles[1][1]=new StairTile(tileWidth,tileHeight);
        this.tiles[3][3]=new WallTile(tileWidth*3,tileHeight*3);
       
    }
    public void testCave(int itterations){
    	for (int i=0;i<width;++i){
        	for (int j=0;j<height;++j){	
        		this.add(new FloorTile(tileWidth*i,tileHeight*j));
        	}
        }
        for (int i=0;i<width;++i){
        	this.tiles[i][0]=new WallTile(tileWidth*i,0);
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
    }
    public void testMaze(int width,int height,int density){
    	 for (int i=0;i<width;++i){
         	for (int j=0;j<height;++j){	
         		this.add(new WallTile(tileWidth*i,tileHeight*j));
         	}
         }
    	 
         Random rnd=new Random();
         ArrayList<Rectangle> rooms=new  ArrayList<Rectangle>();
         int tries=0;
         while (rooms.size()<density&&++tries<5000000){
        	 
        	 int x1=1+rnd.nextInt(width);
        	 int x2=3+rnd.nextInt(4);
        	 int y1=1+rnd.nextInt(height);
        	 int y2=3+rnd.nextInt(4);
        	 
        	 if (x2+x1<width&&y2+y1<height){
        		 Rectangle canidate=new Rectangle(x1,y1,x2,y2);
        		 boolean collides=false;
        		 for(int i=0;i<rooms.size();i++){
        			 if (rooms.get(i).intersects(canidate)){
        				collides=true;
        				break;
        			 }
        		 }
        		 if (!collides)
        			 rooms.add(canidate);
        	 }
        	 
         }
         for (Rectangle R:rooms){
        	 for (int i=R.x;i<R.x+R.width;i++)
        		 for (int j=R.y;j<R.y+R.height;j++){
        			 this.tiles[i][j]=new FloorTile(i*this.tileWidth,j*this.tileHeight);
        		 }
         }
         
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
    public void reDraw(){
    	fullBMP=null;
    }
    public void draw(Screen screen) {
    	if (fullBMP==null){
    		fullBMP=new Bitmap(screen.w,screen.h);
	        for (int i = 0; i < width; ++i) {
	        	for (int j=0;j < height;++j)
	        	{
	        		tiles[i][j].draw(fullBMP);
	        	}
	        }
    	
    	for(int i=0;i<fullBMP.w*fullBMP.h;++i){
    		screen.pixels[i]=fullBMP.pixels[i];
    	}
    	}
    	
    	for(Point p :this.changedTile){
    		this.tiles[p.x][p.y].draw(screen);
    	}
    	changedTile.clear();
    	
    	
    }
}
