package ca.cinnamon.hourglass.map;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Node {
	public Set<Node>neighbors=new HashSet<Node>(); 
	public int x,y;
	public Point p;
	public boolean isSolid;
	public Node parent=null;
	public int aScore=0;
	public Node(int x, int y){
		this.x = x;
        this.y = y;
        this.p=new Point(x,y);
	}
	public int Manhatten(Point p){
		return Math.abs(x-p.x)+Math.abs(y-p.y);
	}
	public Node GetAncestorMinus1(){
		if (parent==null)
			return this;
		if (parent.parent==null)
			return this;
		return parent.GetAncestorMinus1();
					
	}
}
