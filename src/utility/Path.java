package utility;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import ca.cinnamon.hourglass.map.Map;

public class Path {
	public static Point orthoPathMap(Point start, Point target, Map m){
		Node[][] nodes= new Node[m.width][m.height];
		for (int i=0;i<m.width;++i){
			for (int j=0;j<m.height;++j){
				if (!m.tiles[i][j].isSolid)
				nodes[i][j]=new Node(i,j,null);
			}
		}
		Node v=nodes[start.x][start.y];
		Set<Node> openSet=new LinkedHashSet<Node>();
		List<Node> closedSet=new Vector<Node>();
		openSet.add(v);
		while(!openSet.isEmpty()){
			v=openSet.iterator().next();
			if (nodes[v.x-1][v.y]!=null&&!closedSet.contains(nodes[v.x-1][v.y]))
			{
				openSet.add(nodes[v.x-1][v.y]);
				nodes[v.x-1][v.y].parent=v;
			}
			if (nodes[v.x+1][v.y]!=null&&!closedSet.contains(nodes[v.x+1][v.y]))
			{
				openSet.add(nodes[v.x+1][v.y]);
				nodes[v.x+1][v.y].parent=v;
			}
			if (nodes[v.x][v.y-1]!=null&&!closedSet.contains(nodes[v.x][v.y-1]))
			{
				openSet.add(nodes[v.x][v.y-1]);
				nodes[v.x][v.y-1].parent=v;
			}
			if (nodes[v.x][v.y+1]!=null&&!closedSet.contains(nodes[v.x][v.y+1]))
			{
				openSet.add(nodes[v.x][v.y+1]);
				nodes[v.x][v.y+1].parent=v;
			}
			openSet.remove(v);
			closedSet.add(v);
		}
		Node ret=nodes[target.x][target.y].rootP1();
		return new Point(ret.x,ret.y);
	}
	static class Node{
		public int x;
		public int y;
		public Node parent;
		public Node(Point p, Node parent){
			x=p.x;	y=p.y;
			this.parent=parent;
		}
		public Node(int x,int y,Node parent){
			this.x=x;	this.y=y;
			this.parent=parent;
		}
		@Override
		public boolean equals(Object obj){
			if (obj == null) {
		        return false;
		    }
		    if (!Node.class.isAssignableFrom(obj.getClass())) {
		        return false;
		    }
		    final Node other = (Node) obj;
			if (other.x!=x||other.y!=y)
				return false;
			return true;
		}
		
		public Node rootP1(){
			
			if (parent==null||parent.parent==null)
				return this;
			return parent.rootP1();
		}
		
	}
}
