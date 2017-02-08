package ca.cinnamon.hourglass.map.tile;

import java.awt.Point;

public class FloorTile extends Tile {
	static final Point[] RandomOffsets={
        	new Point(6*WIDTH,1*HEIGHT),
        	new Point(7*WIDTH,1*HEIGHT),
        	new Point(8*WIDTH,1*HEIGHT),
        	new Point(9*WIDTH,1*HEIGHT),
        	new Point(10*WIDTH,1*HEIGHT),
        	new Point(11*WIDTH,1*HEIGHT),
        	new Point(12*WIDTH,1*HEIGHT),

        	new Point(6*WIDTH,2*HEIGHT),
        	new Point(7*WIDTH,2*HEIGHT),
        	new Point(8*WIDTH,2*HEIGHT),
        	new Point(9*WIDTH,2*HEIGHT),
        	new Point(10*WIDTH,2*HEIGHT),
        	new Point(11*WIDTH,2*HEIGHT),
        	new Point(12*WIDTH,2*HEIGHT),
        	
        	new Point(6*WIDTH,3*HEIGHT),
        	new Point(7*WIDTH,3*HEIGHT),
        	new Point(8*WIDTH,3*HEIGHT),
        	new Point(9*WIDTH,3*HEIGHT),
        	new Point(10*WIDTH,3*HEIGHT),
        	new Point(11*WIDTH,3*HEIGHT),
        	new Point(12*WIDTH,3*HEIGHT),
        	
        	new Point(6*WIDTH,0*HEIGHT),
        	new Point(7*WIDTH,0*HEIGHT),
        	new Point(8*WIDTH,0*HEIGHT),
        	new Point(9*WIDTH,0*HEIGHT),
        	new Point(10*WIDTH,0*HEIGHT),
        	new Point(11*WIDTH,0*HEIGHT),
        	new Point(12*WIDTH,0*HEIGHT),

        	new Point(11*WIDTH,4*HEIGHT),
        	new Point(12*WIDTH,4*HEIGHT),
        	new Point(11*WIDTH,5*HEIGHT),
        	new Point(12*WIDTH,5*HEIGHT),
        	
        };
	 public FloorTile(int x, int y) {
		 
		 	super(x,y);
	        img="./Pictures/sheet32x.png";
	        
	        
	        Point choice=RandomOffsets[rnd.nextInt(RandomOffsets.length)];
	        X_OFFSET=choice.x;
	        Y_OFFSET=choice.y;
	        this.isSolid=false;
	    }
	    
}
