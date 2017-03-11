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
import ca.cinnamon.hourglass.menu.GameMenu;
import ca.cinnamon.hourglass.menu.InventoryMenu;
import ca.cinnamon.hourglass.menu.MainMenu;
import ca.cinnamon.hourglass.menu.MainMenu.STATE;
import ca.cinnamon.hourglass.menu.MenuManager;
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
        
    public static int GAME_WIDTH = 1024 + 422;
    public static int GAME_HEIGHT = 829; //GAME_WIDTH * 9 / 16;

    //Game and Menu frame
    private static JFrame GAME;
    
    private MenuManager manager;
    
    public MainComponent() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMaximumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        int mapx=45;
        int mapy=mapx*9/16;
        
        Map.currentMap=new Map(mapx,mapy);
        Map.currentMap.testCave(4);
        
        //currentMap=Map.load();
        //currentMap.save();

        keys = new Keys(this);
        this.addKeyListener(keys);
    } // MainComponent();
    
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
        //Map.currentMap.reDraw();
        mc.start();
    } // main(String[]);
    
    public void start() {
        SoundPlayer.init();
        MusicPlayer.dungeonTrack.loop();
        running = true;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        
        manager = new MenuManager(this);
        manager.addMenu(new MainMenu(GAME_WIDTH, GAME_HEIGHT));
        GameMenu game = new GameMenu(GAME_WIDTH, GAME_HEIGHT, keys);
        manager.addMenu(game);
        manager.addMenu(new InventoryMenu(keys, game.player.inventory, GAME_WIDTH, GAME_HEIGHT));
        manager.LoadMenuAt(0);
        
        thread.start();
    } // start();
    
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        screen = new Screen(GAME_WIDTH, GAME_HEIGHT);

        long timeSinceStart = System.nanoTime() / 1000;
        long oldTimeSinceStart = 0;
        long deltaTime = 1;

        while (running) {
            // Do it
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }

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
        }
    } // run();
    
    public void render(Graphics g) { 
        screen.fill(new Color(0, 0, 0).getRGB());
        manager.drawCurrentMenu(screen);
        g.drawImage(screen.image, 0, 0, null);
    } // render(Graphics);
    
    public void tick() {
        manager.tickCurrentMenu();
    } // tick();
} // MainComponent;
