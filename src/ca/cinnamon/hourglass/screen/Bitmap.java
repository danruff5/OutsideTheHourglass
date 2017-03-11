/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.screen;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author daniel
 */
public class Bitmap{
    public int w, h;
    public int[] pixels;
    public final int ALPHACOLOR=-65281;
    /**
     * @author Cody
     * @purpose created to easily convert buffered images to this data structure
     */
    public static Bitmap convert(BufferedImage img,int width, int height){
		Bitmap ret=new Bitmap(img.getWidth(), img.getHeight());
		for (int i=0;i<width;++i){
			for (int j=0;j<height;++j){
				ret.pixels[j* ret.w +i]=img.getRGB(i, j);
			}
		}
		return ret;
	}
    public Bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.pixels = new int[w * h];
    }
    
    public void fill(int colour) {        
        for (int xp = 0; xp < w; ++xp) {
            for (int yp = 0; yp < h; ++yp) {
                pixels[yp * this.w + xp] = colour;
            }
        }
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
    public void blit(Bitmap bitmap, int x, int y,int width,int height) {
        int maxX = (x + width > w)? w - x : width;
        int maxY = (y + height > h)? h - y : height;
        int minX = (x < 0)? Math.abs(x): 0;
        int minY = (y < 0)? Math.abs(y): 0;
        
        for (int xp = minX; xp < maxX; ++xp) {
            for (int yp = minY; yp < maxY; ++yp) {
            	if ( bitmap.pixels[yp * bitmap.w + xp]!=ALPHACOLOR)
            		pixels[(y + yp) * this.w + (x + xp)] = bitmap.pixels[yp * bitmap.w + xp];
            }
        }
    }
    public void blit(Bitmap bitmap, int x, int y,int width,int height,int xoffset,int yoffset) {
        int maxX = (x + width > w)? w - x : width;
        int maxY = (y + height > h)? h - y : height;
        int minX = (x < 0)? Math.abs(x): 0;
        int minY = (y < 0)? Math.abs(y): 0;
        
        for (int xp = minX; xp < maxX; ++xp) {
            for (int yp = minY; yp < maxY; ++yp) {
            	if ( bitmap.pixels[yp * bitmap.w + xp]!=ALPHACOLOR)
            		pixels[(y + yp) * this.w + (x + xp)] = bitmap.pixels[(yp+yoffset) * bitmap.w + (xp+xoffset)];
            }
        }
    }
    public void colourFill(int colour, int x, int y, int w, int h) {
        for (int xp = x; xp < x + w; ++xp) {
            for (int yp = y; yp < y + h; ++yp) {
                pixels[yp * this.w + xp] = colour;
            }
        }
    }
}
