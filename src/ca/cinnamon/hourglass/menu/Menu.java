package ca.cinnamon.hourglass.menu;

import java.util.ArrayList;
import java.util.List;

import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Screen;

//Every menu has a menu type to identify
//isEnabled to determine if it should execute its logic
//init for inital load of components
//draw for execution
//tick for logic updates
public abstract class Menu {
	
	private List<MenuStateListener> listeners;

public MenuType ID;

public boolean isEnabled = false;
	
public abstract void init(MenuType id);
	
public abstract void draw(Screen screen);

public abstract void tick();

public void addListener(MenuStateListener listener) {
	if (listeners == null) {
		listeners = new ArrayList<MenuStateListener>();
	}
	listeners.add(listener);
}

}
