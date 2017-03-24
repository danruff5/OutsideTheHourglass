/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.item.IItem;
import ca.cinnamon.hourglass.item.PlayerInventory;
import ca.cinnamon.hourglass.item.ItemPosition;
import ca.cinnamon.hourglass.item.Potion;
import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Screen;
import java.awt.Color;
import java.awt.event.KeyEvent;
import utility.DrawableString;

/**
 *
 * @author dckra
 */
public class InventoryMenu extends Menu {

    private PlayerInventory inventory;
    private ItemPosition selectedItem;
    private boolean inventorySelected;
    private Keys keys;
    
    private STATE GameState;
    
    private int screen_width, screen_height;
    
    public static enum STATE {
        inventory, game
    } // enum STATE;

    public InventoryMenu(Keys keys, PlayerInventory inventory, int screen_width, int screen_height) {
        ID = MenuType.Inventory;
        this.keys = keys;
        this.inventory = inventory;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.inventorySelected = true;
    } // InventoryMenu(Playinventory);

    @Override
    public void init() {
        selectedItem = new ItemPosition(1, 1);
    } // init(MenuType);

    @Override
    public void draw(Screen screen) {
        int potionCount = 0;        
        
        boolean drawn = false;
        for (IItem item : inventory.items) {
            if (item instanceof Potion) {
                potionCount++;
                
                if (!drawn) {
                    drawn = true;
                }
            }
        }
        
        this.inventory.Draw(screen);
        
        IItem item;
        int o = 15;
        int selectColour = new Color(255, 255, 0).getRGB();
        if (inventorySelected) {
            item = this.inventory.FindItemPosition(selectedItem.row, selectedItem.col);
            screen.drawSquare(
                    selectColour, 
                    100 - o, 
                    (50 + (selectedItem.row - 1) * 100) + o / 2, 
                    (50 + (selectedItem.col - 1) * 100) + o / 2
            );
        } else {
            if (selectedItem.row == 2) {
                item = this.inventory.armour[selectedItem.col - 1];
                screen.drawSquare(
                        selectColour, 
                        150 - o, 
                        1200 + o / 2, 
                        (50 + (selectedItem.col - 1) * 150) + o / 2
                );
            } else {
                item = this.inventory.weapon;
                screen.drawSquare(
                        selectColour, 
                        150 - o, 
                        1050 + o / 2, 
                        125 + o / 2
                );
            }
        }
        
        if (item != null) {
            DrawableString.Draw(screen, item.getName(), 100, 500);
            DrawableString.Draw(screen, item.getDescription(), 100, 525);
        }
    } // draw(Screen);

    @Override
    public void tick() {
        if (this.keys.keys[KeyEvent.VK_H].pressed) {
            for (ItemPosition i : inventory.items) {
                if (i.item instanceof Potion) {
                    ((Potion)i.item).Use();
                    inventory.items.remove(i);
                    break;
                }
            }
        } else if (this.keys.keys[KeyEvent.VK_E].pressed) {
            SetCurrentGameState(STATE.game);
        } 
        
        if (inventorySelected) {
            if (this.keys.keys[KeyEvent.VK_UP].pressed) {
                if (selectedItem.col >= 2) {
                    selectedItem.col--;
                }
            } else if (this.keys.keys[KeyEvent.VK_RIGHT].pressed) {
                if (selectedItem.row <= inventory.rows - 1) {
                    selectedItem.row++;
                } else if (selectedItem.row == inventory.rows) {
                    inventorySelected = false;
                    selectedItem.col = 1;
                    selectedItem.row = 1;
                }
            } else if (this.keys.keys[KeyEvent.VK_DOWN].pressed) {
                if (selectedItem.col <= inventory.cols - 1) {
                    selectedItem.col++;
                }
            } else if (this.keys.keys[KeyEvent.VK_LEFT].pressed) {
                if (selectedItem.row >= 2) {
                    selectedItem.row--;
                }
            }
        } else {
            if (this.keys.keys[KeyEvent.VK_UP].pressed) {
                if (selectedItem.row == 2 && selectedItem.col >= 2) {
                    selectedItem.col--;
                }
            } else if (this.keys.keys[KeyEvent.VK_RIGHT].pressed) {
                if (selectedItem.row == 1) {
                    selectedItem.row = 2;
                }
            } else if (this.keys.keys[KeyEvent.VK_DOWN].pressed) {
                if (selectedItem.row == 2 && selectedItem.col <= 3) {
                    selectedItem.col++;
                }
            } else if (this.keys.keys[KeyEvent.VK_LEFT].pressed) {
                if (selectedItem.row == 1) {
                    selectedItem.col = 1;
                    selectedItem.row = this.inventory.rows;
                    inventorySelected = true;
                } else {
                    selectedItem.row = 1;
                }
            }
        }
    } // tick();
    
    public STATE GetCurrentGameState() {
        return GameState;
    } // GetCurrentGameState();

    public void SetCurrentGameState(STATE s) {
        GameState = s;
        //Create a StateChangedEvent here and throw it back to the main
        stateChanged();
    } // SetCurrentGameState(STATE);
} // InventoryMenu;
