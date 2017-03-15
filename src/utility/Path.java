package utility;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.map.Node;

public class Path {
	public static Point orthoPathMap(Point start, Point target, Map m){
		List<Node> openSet=new Vector<Node>();
		List<Node> closedSet=new Vector<Node>();
		openSet.add(m.tiles[start.x][start.y]);
		m.tiles[start.x][start.y].parent=null;
		while (!closedSet.contains(m.tiles[target.x][target.y])){
			Node n=openSet.get(0);
			openSet.remove(n);
			closedSet.add(n);
			for (Node i: n.neighbors)
				if(!i.isSolid){
					if (!closedSet.contains(i)){
						if (!openSet.contains(i)){
							openSet.add(i);
							i.aScore=n.aScore+1;
							i.parent=n;
						}
						else if (i.parent.aScore>n.aScore){
							i.aScore=n.aScore+1;
							i.parent=n;
						}
					}
				}
			openSet.sort(new Comparator<Object>() {
		        public int compare(Object arg0, Object arg1) {
		            Node r1 = (Node) arg0;
		            Node r2 = (Node) arg1;
		           return Integer.compare(r1.Manhatten(target),r2.Manhatten(target));
		        }
			});
		}
		
		
		return m.tiles[target.x][target.y].GetAncestorMinus1().p;
	}
}
