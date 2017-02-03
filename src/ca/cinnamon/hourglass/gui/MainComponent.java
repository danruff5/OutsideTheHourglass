/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.gui;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.entity.Player;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.map.tile.Tile;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;
import ca.cinnamon.hourglass.screen.Screen;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
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
    
    public static int GAME_WIDTH = 512;
    public static int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
    
    public Map currentMap;
    public MainComponent() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setMaximumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        int mapx=8;
        currentMap=new Map(mapx,mapx);
        
        keys = new Keys();
        this.addKeyListener(keys);
    }
    
    public static void main(String[] args) {
        MainComponent mc = new MainComponent();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mc);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(mc.keys);
        mc.start();
    }
    
    public void start() {
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
        
        player = new Player(keys);
        entities = new ArrayList<>();
        
        while (running) {
            // Do it
            
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }
            
            tick();
            
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        currentMap.draw(screen);
        
        g.drawImage(screen.image, 0, 0, null);
    }
    
    public void tick() {
        player.Tick();
        
        for(Entity e : entities)
            e.Tick();
    }
}
