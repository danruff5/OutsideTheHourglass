/**
 * @author Cody
 * @date 1/14/2017
 * @purpose reusable code for drawing string to bitmap
 */
package utility;

import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.BitmapManager;

import java.util.HashMap;
import java.awt.Point;
public class DrawableString {
	private static HashMap<Character,Point> characters = new HashMap<Character,Point>();
	private static BitmapManager sheets=BitmapManager.bManager;
	private static String img="./Pictures/BoxyBold - by Clint Bellanger/Repacked - double.png";
	private static int width=18;
	private static int height=16;
	private DrawableString(){};
	public static void Draw(Bitmap screen,String s, int x,int y){
		if (characters.isEmpty()){
			def();
		}
		for(char c : s.toLowerCase().toCharArray()){
			screen.blit(sheets.add(img),x,y,width-1,height, characters.get(c).x, characters.get(c).y);
			x+=width-1;
		}
	}
	private static void def(){
		
		HashMap<Character,Point> ret=characters;
		
		ret.put(' ', new Point(0*width,0*height));
		ret.put('!', new Point(1*width,0*height));
		ret.put('*', new Point(2*width,0*height));
		ret.put('+', new Point(3*width,0*height));
		ret.put(',', new Point(4*width,0*height));
		ret.put('-', new Point(5*width,0*height));
		ret.put('.', new Point(6*width,0*height));
		ret.put('/', new Point(7*width,0*height));
		ret.put('0', new Point(8*width,0*height));
		
		ret.put('1', new Point(0*width,1*height));
		ret.put('2', new Point(1*width,1*height));
		ret.put('3', new Point(2*width,1*height));
		ret.put('"', new Point(3*width,1*height));
		ret.put('4', new Point(4*width,1*height));
		ret.put('5', new Point(5*width,1*height));
		ret.put('6', new Point(6*width,1*height));
		ret.put('7', new Point(7*width,1*height));
		ret.put('8', new Point(8*width,1*height));
		
		ret.put('9', new Point(0*width,2*height));
		ret.put(':', new Point(1*width,2*height));
		ret.put(';', new Point(2*width,2*height));
		ret.put('<', new Point(3*width,2*height));
		ret.put('=', new Point(4*width,2*height));
		ret.put('#', new Point(5*width,2*height));
		ret.put('>', new Point(6*width,2*height));
		ret.put('?', new Point(7*width,2*height));
		ret.put('@', new Point(8*width,2*height));
		
		ret.put('a', new Point(0*width,3*height));
		ret.put('b', new Point(1*width,3*height));
		ret.put('c', new Point(2*width,3*height));
		ret.put('d', new Point(3*width,3*height));
		ret.put('e', new Point(4*width,3*height));
		ret.put('f', new Point(5*width,3*height));
		ret.put('g', new Point(6*width,3*height));
		ret.put('$', new Point(7*width,3*height));
		ret.put('h', new Point(8*width,3*height));
		
		ret.put('i', new Point(0*width,4*height));
		ret.put('j', new Point(1*width,4*height));
		ret.put('k', new Point(2*width,4*height));
		ret.put('l', new Point(3*width,4*height));
		ret.put('m', new Point(4*width,4*height));
		ret.put('n', new Point(5*width,4*height));
		ret.put('o', new Point(6*width,4*height));
		ret.put('p', new Point(7*width,4*height));
		ret.put('q', new Point(8*width,4*height));
		
		ret.put('%', new Point(0*width,5*height));
		ret.put('r', new Point(1*width,5*height));
		ret.put('s', new Point(2*width,5*height));
		ret.put('t', new Point(3*width,5*height));
		ret.put('u', new Point(4*width,5*height));
		ret.put('v', new Point(5*width,5*height));
		ret.put('w', new Point(6*width,5*height));
		ret.put('x', new Point(7*width,5*height));
		ret.put('y', new Point(8*width,5*height));
		
		ret.put('z', new Point(0*width,6*height));
		ret.put('[', new Point(1*width,6*height));
		ret.put('&', new Point(2*width,6*height));
		ret.put('\\', new Point(3*width,6*height));
		ret.put(']', new Point(4*width,6*height));
		ret.put('^', new Point(5*width,6*height));
		ret.put('_', new Point(6*width,6*height));
		ret.put('`', new Point(7*width,6*height));
		ret.put('\'', new Point(8*width,6*height));
		
		ret.put('(', new Point(0*width,7*height));
		ret.put(')', new Point(1*width,7*height));
		ret.put('{', new Point(2*width,7*height));
		ret.put('|', new Point(3*width,7*height));
		ret.put('}', new Point(4*width,7*height));
		ret.put('~', new Point(5*width,7*height));
	}
}
