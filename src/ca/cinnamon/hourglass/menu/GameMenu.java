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
import ca.cinnamon.hourglass.screen.Screen;
import java.util.Iterator;

/**
 *
 * @author dckra
 */
public class GameMenu extends Menu {

    private Player player;
    private Keys keys;
    
    
    public GameMenu(int screen_width, int screen_height, Keys keys) {
        this.keys = keys;
        
        // init
        player = new Player(keys, Map.currentMap.GetRandomFloorTile());
        player.currentMap = Map.currentMap;
        Map.player = player;
        Map.currentMap.entities.add(player);
    } // GameMenu(int, int);

    @Override
    public void init(MenuManager.MenuType id) {
        
    } // init(MenuType);

    @Override
    public void draw(Screen screen) {
        Map.currentMap.draw(screen);
    } // draw(Screen);

    @Override
    public void tick() {
        
        

        Map.player.Tick();
        Iterator<Entity> itr = Map.currentMap.entities.iterator();
        while (itr.hasNext()) {
            Entity e = itr.next();
            if (e.getLocation().equals(Map.player.getLocation()) && !e.equals(Map.player)) {
                if (Map.player.Attack(e) < 1) {
                    itr.remove();
                    ++Map.player.score;
                }
            }
        }

        //player.Tick();
        for (Entity e : Map.currentMap.entities) {
            if (e != Map.player) {
                e.Tick();
            }
        }
        for (Entity e : Map.currentMap.entities) {
            if (e.getLocation().equals(Map.player.getLocation()) && !e.equals(Map.player)) {
                e.Attack(Map.player);
            }

        }
        itr = Map.currentMap.entities.iterator();
        while (itr.hasNext()) {
            try {
                Mob e = (Mob) itr.next();
                if (e.HP <= 0 && e != Map.player) {
                    itr.remove();
                    ++Map.player.score;
                }
            } catch (Exception ex) {
            }
        }
    } // tick();

} // GameMenu;
