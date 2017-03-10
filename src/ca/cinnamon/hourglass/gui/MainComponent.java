/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.gui;

import ca.cinnamon.hourglass.entity.*;
import ca.cinnamon.hourglass.entity.mob.*;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.map.tile.*;
import ca.cinnamon.hourglass.menu.MainMenu;
import ca.cinnamon.hourglass.menu.MainMenu.STATE;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;

import ca.cinnamon.hourglass.sound.*;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author daniel
 */

public class MainComponent extends Canvas implements Runnable {
	
    public boolean running;
    private Screen screen;
    public Keys keys;
    
    
    //public ArrayList<Entity> entities;
    
    public static int GAME_WIDTH = 1024 + 422;
    public static int GAME_HEIGHT = 829; //GAME_WIDTH * 9 / 16;
    
    private int framesSinceLastTick = 0;
    
    //Game and Menu frame
    private static JFrame GAME;
    private static MainMenu MENU;
    

    
    public MainComponent() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMaximumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        int mapx=45;
        int mapy=mapx*9/16;
        
        Map.currentMap=new Map(mapx,mapy);
        Map.currentMap.testCave(4);
        
        MENU  = new MainMenu(GAME_WIDTH, GAME_HEIGHT);
        
        //currentMap=Map.load();
        //currentMap.save();

        keys = new Keys(this);
        this.addKeyListener(keys);
        //Menu needs to get key events
        this.addMouseListener(MENU);
    }
    
    public static void main(String[] args) {
        MainComponent mc = new MainComponent();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mc);
        
        GAME = new JFrame();
        GAME.setContentPane(panel);
        GAME.pack();
       //frame.setContentPane(panel);
        GAME.setSize(GAME_WIDTH,GAME_HEIGHT);
        GAME.setResizable(false);
        GAME.setLocationRelativeTo(null);
        GAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GAME.addKeyListener(mc.keys);
        GAME.setVisible(true);
        Map.currentMap.reDraw();
        mc.start();
    }
    
    public void start() {
        SoundPlayer.init();
        MusicPlayer.dungeonTrack.loop();
        running = true;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
    
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        screen = new Screen(GAME_WIDTH, GAME_HEIGHT);
        //screen = new Screen(getWidth(), getHeight());

        Player player;    
        player = new Player(keys,Map.currentMap.GetRandomFloorTile());
        player.currentMap=Map.currentMap;
        Map.player=player;
        Map.currentMap.entities.add(player);
        
                
        long timeSinceStart = System.nanoTime() / 1000;
        long oldTimeSinceStart = 0;
        long deltaTime = 1;
        Map.currentMap.draw(screen);
       
        
        //currentMap.save();
        while (running) {
            // Do it
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }

            framesSinceLastTick++;
            //tick();

            Graphics g = bs.getDrawGraphics();
            render(g);
            g.dispose();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (bs != null) {
                bs.show();
            }

            if (keys.keys[KeyEvent.VK_ESCAPE].pressed) {
                // Pause?
            	if(MENU.GetCurrentGameState() == STATE.Paused || MENU.GetCurrentGameState() == STATE.Menu)
            		MENU.SetCurrentGameState(STATE.Game);
            	
            	if(MENU.GetCurrentGameState() == STATE.Game)
            		Map.currentMap.reDraw();
            }
        }
    }
    
    public void render(Graphics g) {
    	//Graphics2D g2=(Graphics2D)g;
        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, getWidth(), getHeight());
        if(MENU.GetCurrentGameState() == STATE.Game)
        {
        	Map.currentMap.draw(screen);
        }
        else if(MENU.GetCurrentGameState() == STATE.Exit)
        {
        	stop();
        	GAME.dispose();
        }
        else
        {
        	MENU.draw(screen);
        }
        g.drawImage(screen.image, 0, 0, null);
    }
    
    public void tick() {
	//player.Tick();
	//player.Hurt(1);
	if (framesSinceLastTick > 10) {
	   framesSinceLastTick = 0;
	   
	   
	   Map.player.Tick();
	   Iterator<Entity> itr = Map.currentMap.entities.iterator();

     while (itr.hasNext())
     {
     	Entity e=itr.next();
     	//checks if player is "attacking", and has an entity in range
       if (Map.player.State() == 1 && Map.player.HitDetection(e) &&!e.equals(Map.player))
       {
       		if (Map.player.Attack(e)<1)
       		{
       			Map.player.score += e.Death();
       			itr.remove();
       		}
       }
     }
     //Puts player back in 'idle' state
     Map.player.ResetState();
	   
	   //player.Tick();
	   for (Entity e : Map.currentMap.entities) {
	   	if (e!=Map.player)
	   		e.Tick();
	   }
	   for (Entity e: Map.currentMap.entities)
	   {
	   	if(e.getLocation().equals(Map.player.getLocation())&&!e.equals(Map.player))
	   			e.Attack(Map.player);
	   
	   }
	   itr = Map.currentMap.entities.iterator();
	   while (itr.hasNext())
	   {
	   	try{
	       	Mob e=(Mob)itr.next();
	       	if(e.HP<=0&&e!=Map.player)
	       	{
	       		itr.remove();
	       		++Map.player.score;
	       	}
	   	}
	   	catch(Exception ex){}
	   }
	   /*
	   // TODO: This loop needs to be redone becasue of removing the entities...
	   for (int i = 0; i < Map.currentMap.entities.size(); ++i) {
	       for (int j = 0; j < Map.currentMap.entities.size(); ++j) {
	           if (i != j && Map.currentMap.entities.get(i).getLocation().equals(Map.currentMap.entities.get(j).getLocation())) {
	               if (Map.currentMap.entities.get(i).Attack(Map.currentMap.entities.get(j)) < 1) {
	               	Map.currentMap.entities.remove(j);
	               	++Map.currentMap.player.score;
	               }
	           }
	       }
	   }
	    */
	}
	//player.Tick();
	//player.Hurt(1);
	if (framesSinceLastTick > 10) {
	   framesSinceLastTick = 0;
	   
	   
	   Map.player.Tick();
	   Iterator<Entity> itr = Map.currentMap.entities.iterator();
	   while (itr.hasNext())
	   {
	   	Entity e=itr.next();
	   	if(e.getLocation().equals(Map.player.getLocation())&&!e.equals(Map.player))
	   		if (Map.player.Attack(e)<1)
	   		{
	   			itr.remove();
	   			++Map.player.score;
	   		}
	   }
	   
	   //player.Tick();
	   for (Entity e : Map.currentMap.entities) {
	   	if (e!=Map.player)
	   		e.Tick();
	   }
	   for (Entity e: Map.currentMap.entities)
	   {
	   	if(e.getLocation().equals(Map.player.getLocation())&&!e.equals(Map.player))
	   			e.Attack(Map.player);
	   
	   }
	   itr = Map.currentMap.entities.iterator();
	   while (itr.hasNext())
	   {
	   	try{
	       	Mob e=(Mob)itr.next();
	       	if(e.HP<=0&&e!=Map.player)
	       	{
	       		itr.remove();
	       		++Map.player.score;
	       	}
	   	}
	   	catch(Exception ex){}
	   }
	   /*
	   // TODO: This loop needs to be redone becasue of removing the entities...
	   for (int i = 0; i < Map.currentMap.entities.size(); ++i) {
	       for (int j = 0; j < Map.currentMap.entities.size(); ++j) {
	           if (i != j && Map.currentMap.entities.get(i).getLocation().equals(Map.currentMap.entities.get(j).getLocation())) {
	               if (Map.currentMap.entities.get(i).Attack(Map.currentMap.entities.get(j)) < 1) {
	               	Map.currentMap.entities.remove(j);
	               	++Map.currentMap.player.score;
	               }
	           }
	       }
	   }
	    */
	}
	//player.Tick();
	//player.Hurt(1);
	if (framesSinceLastTick > 10) {
	   framesSinceLastTick = 0;
	   
	   
	   Map.player.Tick();
	   Iterator<Entity> itr = Map.currentMap.entities.iterator();
	   while (itr.hasNext())
	   {
	   	Entity e=itr.next();
	   	if(e.getLocation().equals(Map.player.getLocation())&&!e.equals(Map.player))
	   		if (Map.player.Attack(e)<1)
	   		{
	   			itr.remove();
	   			++Map.player.score;
	   		}
	   }
	   
	   //player.Tick();
	   for (Entity e : Map.currentMap.entities) {
	   	if (e!=Map.player)
	   		e.Tick();
	   }
	   for (Entity e: Map.currentMap.entities)
	   {
	   	if(e.getLocation().equals(Map.player.getLocation())&&!e.equals(Map.player))
	   			e.Attack(Map.player);
	   
	   }
	   itr = Map.currentMap.entities.iterator();
	   while (itr.hasNext())
	   {
	   	try{
	       	Mob e=(Mob)itr.next();
	       	if(e.HP<=0&&e!=Map.player)
	       	{
	       		itr.remove();
	       		++Map.player.score;
	       	}
	   	}
	   	catch(Exception ex){}
	   }
	   /*
	   // TODO: This loop needs to be redone becasue of removing the entities...
	   for (int i = 0; i < Map.currentMap.entities.size(); ++i) {
	       for (int j = 0; j < Map.currentMap.entities.size(); ++j) {
	           if (i != j && Map.currentMap.entities.get(i).getLocation().equals(Map.currentMap.entities.get(j).getLocation())) {
	               if (Map.currentMap.entities.get(i).Attack(Map.currentMap.entities.get(j)) < 1) {
	               	Map.currentMap.entities.remove(j);
	               	++Map.currentMap.player.score;
	               }
	           }
	       }
	   }
	    */
	}
    }
}
