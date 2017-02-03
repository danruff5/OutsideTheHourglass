package ca.cinnamon.hourglass.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
*
* @author Cody Hines
* @date 2/1/2017
* @purpose to provide a tool to share the custom bitmap objects between entities that use the same file
*/
public class BitmapManager {
	java.util.HashMap<String, Bitmap> dict= new java.util.HashMap<String, Bitmap>();
	
	public Bitmap add(String key){
		
		if (dict.containsKey(key)){
			return dict.get(key);
		}
		else{
			Bitmap bmp=null;
			try{
			BufferedImage bf=ImageIO.read(new File(key));
			bmp=Bitmap.convert( bf,bf.getWidth(),bf.getHeight());
			dict.put(key, bmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dict.put(key, bmp);
			return dict.get(key);
		}
	}
	
}
