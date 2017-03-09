/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity;

import ca.cinnamon.hourglass.entity.mob.Mob;
import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.item.Boots;
import ca.cinnamon.hourglass.item.Chest;
import ca.cinnamon.hourglass.item.Helmet;
import ca.cinnamon.hourglass.item.IWeapon;
import ca.cinnamon.hourglass.item.IWearable;
import ca.cinnamon.hourglass.item.Leggings;
import ca.cinnamon.hourglass.item.PlayerInventory;
import ca.cinnamon.hourglass.item.Sword;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.map.tile.Tile;
import ca.cinnamon.hourglass.screen.Screen;
import ca.cinnamon.hourglass.sound.SoundPlayer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.glass.events.KeyEvent;

/**
 *
 * @author Daniel
 */



public class Player extends Mob {
	
	private Graphics graphicsBrush;
	private BufferedImage heartImage;
	private BufferedImage heartDeadImage;
	private int heartNum = 5; //temp num
	private int heartDeadNum = 0;
    private Keys keys;
    private boolean isInventory = false;
    public int score=0;
    private IWeapon weapon;
    private IWearable[] armour;
    private PlayerInventory inventory;
    
    public int speedLimit=150;
    public Player(Keys keys,Point spawn) {
        super(spawn);
        this.keys = keys;
        this.ATK=2;
        this.HP = 5; // same as heartNum;
        
        weapon = new Sword();
        armour = new IWearable[4];
        armour[0] = new Helmet();
        armour[1] = new Chest();
        armour[2] = new Leggings();
        armour[3] = new Boots();
        
        inventory = new PlayerInventory();
    }
    
    public void Tick() {
        if (keys.keys[KeyEvent.VK_UP].pressed) {
            // Player Move Up.
            SoundPlayer.STEP.play();
            this.moveUp(currentMap.tiles[loc.x][loc.y-1]);
        }
        if (keys.keys[KeyEvent.VK_RIGHT].pressed) {
            // Player Move Right.
            SoundPlayer.STEP.play();
            this.moveRight(currentMap.tiles[loc.x+1][loc.y]);
            //resurrectHeart();
        }
        if (keys.keys[KeyEvent.VK_DOWN].pressed) {
            // Player Move Down.
            SoundPlayer.STEP.play();
            this.moveDown(currentMap.tiles[loc.x][loc.y+1]);
        }
        if (keys.keys[KeyEvent.VK_LEFT].pressed) {
            // Player Move Left.
            SoundPlayer.STEP.play();
            this.moveLeft(currentMap.tiles[loc.x-1][loc.y]);
            //removeHeart();
        }
        //if (keys.keys[KeyEvent.VK_5].pressed) {
        	
        //}
        if (keys.keys[KeyEvent.VK_E].pressed) {
            isInventory = !isInventory;
        }
    }
    
    @Override
    public void moveUp(Tile T)
  	{
  		if (!T.collide(this))
  		{
  			currentMap.changedTile.add(new Point(this.loc));
  			this.loc.y -= 1;
  		}
  		else
  		{
  			SoundPlayer.WALL.play();
  		}
  	}

    @Override
  	public void moveDown(Tile T)
  	{
  		if (!T.collide(this))
  		{
  			currentMap.changedTile.add(new Point(this.loc));
  			this.loc.y += 1;
  		}
  		else
  		{
  			SoundPlayer.WALL.play();
  		}
  	}

    @Override
  	public void moveRight(Tile T)
  	{
  		if (!T.collide(this))
  		{
  			currentMap.changedTile.add(new Point(this.loc));
  			this.loc.x += 1;
  		}
  		else
  		{
  			SoundPlayer.WALL.play();
  		}
  	}
  	
    @Override
  	public void moveLeft(Tile T)
  	{
  		if (!T.collide(this))
  		{
  			currentMap.changedTile.add(new Point(this.loc));
  			this.loc.x -= 1;
  		}
  		else
  		{
  			SoundPlayer.WALL.play();
  		}
  	}
    
    @Override
  	public int Attack(Entity E)
  	{
  		SoundPlayer.SWORD.play();
  		return E.Hurt(weapon.Damage());
  	}
  	
    
    public void removeHeart(int hearts)
    {
    	for (int i=0;i<hearts;++i)
    	if(heartNum>0)
    	{
    		heartNum -= 1;
    		heartDeadNum += 1;
    	}
    }
    
    public void resurrectHeart(int hearts)
    {
    	for (int i=0;i<hearts;++i)
    	if(heartNum<5)
    	{
    		heartNum += 1;
    		heartDeadNum -= 1;
    	}
    }
    
    @Override
    public void Draw(Screen screen) {
    	screen.blit(sprites.add("./Pictures/wizzard.bmp"), this.loc.x*Map.tileWidth, (this.loc.y-0)*Map.tileWidth,Map.tileWidth,Map.tileHeight);
    	graphicsBrush = screen.image.getGraphics();
    	if(heartImage ==null)
    	{try{
    		heartImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Pictures\\heart.png"));
    		}
    		catch(IOException e){
    			e.printStackTrace();
    			}
    	}
    	//heartDeadImage
    	if(heartDeadImage ==null)
    	{try{
    		heartDeadImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Pictures\\heart_dead.png"));
    		}
    		catch(IOException e){
    			e.printStackTrace();
    			}
    	}
    	for(int i = 0 ; i <heartNum; ++i)
    	{
    		graphicsBrush.drawImage(heartImage, 1000 + i*60, 10, null);
    	}
    	for(int i = 0 ; i <heartDeadNum; ++i)
    	{
    		graphicsBrush.drawImage(heartDeadImage, 1000 + (i+heartNum)*60, 10, null);
    	}
    	String sScore=Integer.toString(score);
    	int scoreOffset=0;
    	for (char c : sScore.toCharArray()){
    		try{
    		screen.blit(sprites.add("./Pictures/BoxyBold - by Clint Bellanger/Double/"+(16+c-48)+".png"), scoreOffset,0,14,16);
    		}
    		catch (Exception ex){}
	    	scoreOffset+=16;
    	}
    	
        
        if (isInventory)
            inventory.DrawMenu(screen);
    }
    @Override 
    public int Hurt(int DAM){
        double armourFactor = 0;
        for (IWearable a : armour)
            armourFactor += a.Protect();
        
        DAM = (int)Math.round(DAM - (DAM * (armourFactor / 100.0)));
        
    	HP-=DAM;
    	this.removeHeart(DAM);
    	return HP;
    }
}

