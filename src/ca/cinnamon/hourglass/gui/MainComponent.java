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
import ca.cinnamon.hourglass.menu.Menu;
import ca.cinnamon.hourglass.menu.Menu.STATE;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;
import ca.cinnamon.hourglass.sound.SoundPlayer;
import ca.cinnamon.hourglass.sound.MusicPlayer;
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
    
    public Player player;
    public ArrayList<Entity> entities;
    
    public static int GAME_WIDTH = 1024 + 422;
    public static int GAME_HEIGHT = 829; //GAME_WIDTH * 9 / 16;
    
    private int framesSinceLastTick = 0;
    
    //Game and Menu frame
    private static JFrame GAME;
    private static Menu MENU;
    

    public Map currentMap;
    public MainComponent() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMaximumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        int mapx=45;
        int mapy=mapx*9/16;
        currentMap=new Map(mapx,mapy);
        Map.currentMap=currentMap;
        currentMap.testCave(4);
        
        
        //currentMap=Map.load();
        //currentMap.save();

        keys = new Keys(this);
        this.addKeyListener(keys);
    }
    
    public static void main(String[] args) {
        MainComponent mc = new MainComponent();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mc);

         GAME = new JFrame();
         GAME.setContentPane(panel);
        //frame.setContentPane(panel);
         GAME.setSize(GAME_WIDTH,GAME_HEIGHT);
         GAME.setResizable(false);
         GAME.setLocationRelativeTo(null);
         GAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         GAME.setVisible(false);
         GAME.addKeyListener(mc.keys);
        

        MENU = new Menu();
        //frame.setContentPane(panel);
        MENU.setVisible(true);
        while(MENU.GetCurrentGameState() != STATE.Game)
        {
        	GAME.setVisible(false);//shitty way of doing it but hey its easy
        }
        GAME.setVisible(true);
        Map.currentMap.reDraw();
        MENU.setVisible(false);
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
        //screen = new Screen(GAME_WIDTH, GAME_HEIGHT);
        screen = new Screen(getWidth(), getHeight());

        
        player = new Player(keys,currentMap.GetRandomFloorTile());
        player.currentMap=currentMap;
        
        entities = new ArrayList<>();
        entities.add(player);
        Slime slm=new Slime(currentMap.GetRandomFloorTile());
        
        entities.add(slm);
        for (int i=0;i<10;++i){
        	entities.add(new Slime(currentMap.GetRandomFloorTile()));
        }
        entities.add(new Wolf(currentMap.GetRandomFloorTile()));
        
        long timeSinceStart = System.nanoTime() / 1000;
        long oldTimeSinceStart = 0;
        long deltaTime = 1;
        currentMap.draw(screen);
        for(int i=0;i<entities.size();++i){
        	entities.get(i).Draw(screen);
        }
       
        
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
                System.out.println("Escape");
            	
            }
        }
    }
    
    public void render(Graphics g) {
    	//Graphics2D g2=(Graphics2D)g;
        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, getWidth(), getHeight());
        
        currentMap.draw(screen);
        for(int i=0;i<entities.size();++i){
        	entities.get(i).Draw(screen);
        }
        g.drawImage(screen.image, 0, 0, null);
    }
    
    public void tick() {
    	 //player.Tick();
    	//player.Hurt(1);
        if (framesSinceLastTick > 10) {
            framesSinceLastTick = 0;

            player.Tick();
            for (Entity e : entities) {
            	if (e!=player)
            		e.Tick();
            }
            
            // TODO: This loop needs to be redone becasue of removing the entities...
            for (int i = 0; i < entities.size(); ++i) {
                for (int j = 0; j < entities.size(); ++j) {
                    if (i != j && entities.get(i).getLocation().equals(entities.get(j).getLocation())) {
                        if (entities.get(i).Attack(entities.get(j)) < 1) {
                            entities.remove(j);
                        }
                    }
                }
            }
        }
    }
}
