/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.gui.Keys;
import ca.cinnamon.hourglass.item.IItem;
import ca.cinnamon.hourglass.item.IUsable;
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
    private boolean actionMenu;
    private boolean moving;
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
        this.actionMenu = false;
        this.moving = false;
    } // InventoryMenu(Playinventory);

    @Override
    public void init() {
        selectedItem = new ItemPosition(1, 1);
    } // init(MenuType);

    @Override
    public void draw(Screen screen) {        
        this.inventory.Draw(screen);
        
        int o = 15;
        
        int selectColour;
        
       
        
        if (!actionMenu) {
             if (moving) {
                ((ItemPosition)selectedItem.item).col = selectedItem.col;
                ((ItemPosition)selectedItem.item).row = selectedItem.row;
                selectColour = new Color(255, 255, 0).getRGB();
            } else 
                selectColour = new Color(128, 128, 128).getRGB();
             
            if (inventorySelected) {
                selectedItem.item = this.inventory.FindItemPosition(selectedItem.row, selectedItem.col);
                screen.drawSquare(
                        selectColour, 
                        100 - o, 
                        (50 + (selectedItem.row - 1) * 100) + o / 2, 
                        (50 + (selectedItem.col - 1) * 100) + o / 2
                );
            } else {
                if (selectedItem.row == 2) {
                    selectedItem.item = this.inventory.armour[selectedItem.col - 1];
                    screen.drawSquare(
                            selectColour, 
                            150 - o, 
                            1200 + o / 2, 
                            (50 + (selectedItem.col - 1) * 150) + o / 2
                    );
                } else {
                    selectedItem.item = this.inventory.weapon;
                    screen.drawSquare(
                            selectColour, 
                            150 - o, 
                            1050 + o / 2, 
                            125 + o / 2
                    );
                }
            }
        } else 
            selectColour = new Color(255, 0, 0).getRGB();
        
        
        
        
        
        if (selectedItem.item != null) {
            DrawableString.Draw(screen, selectedItem.item.getName(), 100, 500);
            DrawableString.Draw(screen, selectedItem.item.getDescription(), 100, 525);
            
            if (actionMenu) {
                screen.drawRectGrid(new Color(255, 255, 255).getRGB(), 125, 50, 1000 - 5, 500 - 5, 1, 4);
                DrawableString.Draw(screen, "Drop", 1000 + 25 / 2, 500 + 25 / 2);
                DrawableString.Draw(screen, "Use", 1000 + 25 / 2, 550 + 25 / 2);
                DrawableString.Draw(screen, "Equip", 1000 + 25 / 2, 600 + 25 / 2);
                DrawableString.Draw(screen, "Move", 1000 + 25 / 2, 650 + 25 / 2);
                
                screen.drawRect(
                        selectColour, 
                        125 - o, 
                        50 - o, 
                        1000 - 5 + o / 2, 
                        (500 - 5 + (selectedItem.col - 1) * 50) + o / 2);
                
                if (selectedItem.item instanceof ItemPosition) {
                    if (((ItemPosition)selectedItem.item).item instanceof IUsable) {
                        
                    }
                }
            }
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
        } else if (this.keys.keys[KeyEvent.VK_ESCAPE].pressed) {
            if (actionMenu) {
                actionMenu = false;
                selectedItem.row = ((ItemPosition)selectedItem.item).row;
                selectedItem.col = ((ItemPosition)selectedItem.item).col;
            }
        } else if (this.keys.keys[KeyEvent.VK_E].pressed) {
            SetCurrentGameState(STATE.game);
        } else if (this.keys.keys[KeyEvent.VK_ENTER].pressed) {
            if (moving) {
                moving = false;
            } else if (selectedItem.item != null) {
                if (actionMenu) {
                    actionMenu = false;
                    
                    if (selectedItem.col == 4) { // Move
                        moving = true;
                        selectedItem.row = ((ItemPosition)selectedItem.item).row;
                        selectedItem.col = ((ItemPosition)selectedItem.item).col;
                    } else if (selectedItem.col == 2) { // Use
                        ItemPosition item = (ItemPosition)selectedItem.item;
                        selectedItem.row = ((ItemPosition)selectedItem.item).row;
                        selectedItem.col = ((ItemPosition)selectedItem.item).col;
                        if (item.item instanceof IUsable) {
                            ((IUsable)item.item).Use();
                            
                            this.inventory.RemoveItem((IItem)item);
                            selectedItem.item = null;
                        }
                    }
                } else {
                    actionMenu = true;
                    selectedItem.row = 1;
                    selectedItem.col = 1;
                }
            }
        }
        
        if (!actionMenu) {
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
        } else {
            // Action Menu.
            if (this.keys.keys[KeyEvent.VK_UP].pressed) {
                if (selectedItem.col >= 2) {
                    selectedItem.col--;
                } else if (selectedItem.col == 1) {
                    selectedItem.col = 4;
                }
            } else if (this.keys.keys[KeyEvent.VK_DOWN].pressed) {
                if (selectedItem.col <= 4 - 1) {
                    selectedItem.col++;
                } else if (selectedItem.col == 4) {
                    selectedItem.col = 1;
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