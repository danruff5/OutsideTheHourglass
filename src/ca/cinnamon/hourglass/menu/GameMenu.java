/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.entity.Entity;
import ca.cinnamon.hourglass.entity.Player;
import ca.cinnamon.hourglass.entity.mob.Mob;
import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.map.Map;
import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Screen;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 *
 * @author dckra
 */
public class GameMenu extends Menu
{

	public Player player;
	private Keys keys;
    private STATE GameState;
        
        
        public static enum STATE {
            inventory, game
        } // enum STATE;

	public GameMenu(int screen_width, int screen_height, Keys keys)
	{
            ID = MenuType.Game;
		this.keys = keys;

		// init
		player = new Player(keys, Map.currentMap.GetRandomFloorTile());
		player.currentMap = Map.currentMap;
		Map.player = player;
		Map.currentMap.entities.add(player);
	} // GameMenu(int, int);

	@Override
	public void init(MenuManager.MenuType id)
	{

	} // init(MenuType);

	@Override
	public void draw(Screen screen)
	{
		Map.currentMap.draw(screen);
	} // draw(Screen);

	@Override
	public void tick()
	{
            
            if (this.keys.keys[KeyEvent.VK_E].pressed) {
                SetCurrentGameState(STATE.inventory);
                return;
            }

		Map.player.Tick();
		Iterator<Entity> itr = Map.currentMap.entities.iterator();
		while (itr.hasNext())
		{
			Entity e = itr.next();
			// checks if player is "attacking", and has an entity in range
			if (Map.player.State() == 1 && Map.player.HitDetection(e) && !e.equals(Map.player))
			{
				if (Map.player.Attack(e) < 1)
				{
					Map.player.score += e.Death();
					itr.remove();
				}
			}
		}
		// Puts player back in 'idle' state
		Map.player.ResetState();

		// player.Tick();
		for (Entity e : Map.currentMap.entities)
		{
			if (e != Map.player) e.Tick();
		}
		for (Entity e : Map.currentMap.entities)
		{
			if (e.getLocation().equals(Map.player.getLocation()) && !e.equals(Map.player)) e.Attack(Map.player);

		}
		itr = Map.currentMap.entities.iterator();
		while (itr.hasNext())
		{
			try
			{
				Mob e = (Mob) itr.next();
				if (e.HP <= 0 && e != Map.player)
				{
					Map.player.score += e.Death();
					itr.remove();
				}
			}
			catch (Exception ex)
			{
			}
		}
                
                
                
	} // tick();
        
    public STATE GetCurrentGameState() {
        return this.GameState;
    }

    public void SetCurrentGameState(STATE s) {
        this.GameState = s;
        //Create a StateChangedEvent here and throw it back to the main
        stateChanged();
    }

} // GameMenu;
