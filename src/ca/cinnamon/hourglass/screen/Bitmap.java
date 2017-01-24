/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.screen;

import java.util.Arrays;

/**
 *
 * @author daniel
 */
public class Bitmap {
    public int w, h;
    public int[] pixels;
    
    public Bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.pixels = new int[w * h];
    }
    
    public void fill(int color) {
        Arrays.fill(pixels, color);
    }
    
    public void blit(Bitmap bitmap, int x, int y) {
        int maxX = (x + bitmap.w > w)? w - x : bitmap.w;
        int maxY = (y + bitmap.h > h)? h - y : bitmap.h;
        int minX = (x < 0)? Math.abs(x): 0;
        int minY = (y < 0)? Math.abs(y): 0;
        
        for (int xp = minX; xp < maxX; ++xp) {
            for (int yp = minY; yp < maxY; ++yp) {
                pixels[(y + yp) * this.w + (x + xp)] = bitmap.pixels[yp * bitmap.w + xp];
            }
        }
    }
    
    public void colourFill(int colour, int x, int y, int w, int h) {
        for (int xp = x;  xp < x + w; ++xp) {
            for (int yp = y; yp < y + h; ++yp) {
                pixels[yp * this.w + xp] = colour;
            }
        }
    }
}
