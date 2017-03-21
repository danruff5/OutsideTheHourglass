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
//		for (int i=0;i<width;++i){
//			for (int j=0;j<height;++j){
//				ret.pixels[j* ret.w +i]=img.getRGB(i, j);
//			}
//		}
                img.getRGB(0, 0, img.getWidth(), img.getHeight(), ret.pixels, 0, img.getWidth());
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
            	if ( bitmap.pixels[(yp+yoffset) * bitmap.w + xp+xoffset]!=ALPHACOLOR)
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
    
    public void colourLine(int colour, int x1, int y1, int x2, int y2) {
        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            double m = ((double)(y2 - y1) / (double)(x2 - x1));
            int start = Math.min(x1, x2);
            int end = Math.max(x1, x2);
            for (int x = start; x <= end; x++) {
                int y = y1 + (int)((double)(x - x1) * m);
                pixels[y * this.w + x] = colour;
            }
        } else {
            int start = Math.min(y1, y2);
            int end = Math.max(y1, y2);
            for (int y = start; y <= end; y++) {
                int x = ((y - y1) * (x2 - x1) + x1 * (y2 - y1)) / (y2 - y1);
                pixels[y * this.w + x] = colour;
            }
        }
    }
}
