/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.entity.mob;

import java.awt.Point;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.map.tile.Tile;
import ca.cinnamon.hourglass.screen.Screen;
import ca.cinnamon.hourglass.sound.SoundPlayer;

/**
 *
 * @author Daniel
 */
public abstract class Mob implements Entity
{
	public int HP = 1;
	public int ATK = 1;
	public int ATK_RANGE = 1;
	public int POINT_VALUE;
	public Point loc = new Point(0, 0);
	public Map currentMap;
	
	public Mob(Point spawn)
	{
		super();
		loc = spawn;
		currentMap = Map.currentMap;
	}

	public void Tick()
	{
	}

	public void Draw(Screen screen)
	{
	}

	public void moveUp(Tile T)
	{
		if (!T.collide(this))
		{
			currentMap.changedTile.add(new Point(this.loc));
			this.loc.y -= 1;
		}
	}

	public void moveDown(Tile T)
	{
		if (!T.collide(this))
		{
			currentMap.changedTile.add(new Point(this.loc));
			this.loc.y += 1;
		}
	}

	public void moveRight(Tile T)
	{
		if (!T.collide(this))
		{
			currentMap.changedTile.add(new Point(this.loc));
			this.loc.x += 1;
		}
	}

	public void moveLeft(Tile T)
	{
		if (!T.collide(this))
		{
			currentMap.changedTile.add(new Point(this.loc));
			this.loc.x -= 1;
		}
	}

	public int Attack(Entity E)
	{
		SoundPlayer.MONSTER.play();
		return E.Hurt(ATK);
	}

	public int Hurt(int DAM)
	{
		HP -= DAM;
		return HP;
	}
	
	public Point getLocation()
	{
		return loc;
	}
	
	public int Death()
	{
		//could also add item drop stuff here? idk
		return POINT_VALUE;
	}

}
