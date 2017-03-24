package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.gui.MainComponent;
import java.util.ArrayList;
import java.util.List;

import ca.cinnamon.hourglass.screen.Screen;

public class MenuManager implements MenuStateListener {
    
    private int framesSinceLastTick = 0;

    @Override
    public void stateChanged(Menu menu) {
        // menu => The menu that called the state change.
        switch (menu.ID) {
            case Main:
                LoadMenuAt(1);
                break;
            case Game:
                LoadMenuAt(2);
                break;
            case Inventory:
                LoadMenuAt(1);
                break;
            default:
                break;
        }
    } // stateChanged(Menu);

    @Override
    public void closing(Menu menu) {
    }

    public static enum MenuType {
        Game,
        Main,
        Inventory
    }

    private MainComponent mainComp;
    public static List<Menu> availableMenus;

    private int currentMenu = -1;

    public MenuManager(MainComponent mainComp) {
        this.mainComp = mainComp;
        //on initial load 

        //create a menu
        //register it with a a listener so that we can handle menu closing or state changed
    }

    public int CurrentMenuIndex() {
        return currentMenu;
    }

    public boolean LoadMenuAt(int index) {
        if (index < availableMenus.size() && index > -1) {
            for (Menu m : availableMenus)
                m.isEnabled = false;
            
            currentMenu = index;
            availableMenus.get(currentMenu).isEnabled = true;
            availableMenus.get(currentMenu).init();
            
            return true;
        }
        return false;
    }

    public void addMenu(Menu m) {
        if (availableMenus == null) {
            availableMenus = new ArrayList<Menu>();
        }
        availableMenus.add(m);
        mainComp.addMouseListener(m);
        mainComp.addMouseMotionListener(m);
        m.addListener(this);
    }

    public void drawCurrentMenu(Screen screen) {
        //Will only draw a menu if it is enabled
        //Potentially we can draw menu's on top of eachother???
        if (currentMenu != -1) {
            if (availableMenus.get(currentMenu).isEnabled) {
                availableMenus.get(currentMenu).draw(screen);
            }
        }
        framesSinceLastTick++;
    }

    public void tickCurrentMenu() {
        if (framesSinceLastTick > 10) {
            framesSinceLastTick = 0;
            if (currentMenu != -1) {
                if (availableMenus.get(currentMenu).isEnabled) {
                    availableMenus.get(currentMenu).tick();
                }
            }
        }
    }

}
