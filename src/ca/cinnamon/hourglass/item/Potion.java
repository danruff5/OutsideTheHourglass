/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.item;

import ca.cinnamon.hourglass.entity.Player;
import ca.cinnamon.hourglass.screen.Screen;

/**
 *
 * @author dckra
 */
public class Potion implements IUsable {
    private Player player;
    
    public Potion(Player player) {
        this.player = player;
    } // Potion(Player);
    
    @Override
    public void Use() {
        player.HP += 1;
        player.resurrectHeart(1);
    } // Use();

    @Override
    public void Draw(Screen screen) {
        
    } // Draw(Screen);

    @Override
    public String getName() {
        return "Health potion";
    } // getName();

    @Override
    public String getDescription() {
        return "This magical potion will give you life!";
    } // getDescription();
    
} // Potion;
