/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author daniel
 */
public class Keys implements KeyListener {
    
    public Key[] keys;
    
    public Keys() {
        keys = new Key[100];
        
        keys[KeyEvent.VK_UP] = new Key();
        keys[KeyEvent.VK_DOWN] = new Key();
        keys[KeyEvent.VK_LEFT] = new Key();
        keys[KeyEvent.VK_RIGHT] = new Key();
        
        keys[KeyEvent.VK_ESCAPE] = new Key();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_ESCAPE: 
                keys[e.getKeyCode()].pressed = true; 
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_ESCAPE: 
                keys[e.getKeyCode()].pressed = false; 
                break;
        }
    }
    
}
