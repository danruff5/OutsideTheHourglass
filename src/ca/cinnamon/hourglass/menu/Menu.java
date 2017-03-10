package ca.cinnamon.hourglass.menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Screen;

//Every menu has a menu type to identify
//isEnabled to determine if it should execute its logic
//init for inital load of components
//draw for execution
//tick for logic updates
public abstract class Menu implements MouseListener, MouseMotionListener {
	
private List<MenuStateListener> listeners;

public MenuType ID;

public boolean isEnabled = false;

//Dictionary of components drawn on menu, !!!eventually should be a generic component!!!
public Map<String,MenuButton> ComponentMap = new HashMap<String,MenuButton>();
	
public abstract void init(MenuType id);
	
public abstract void draw(Screen screen);

public abstract void tick();

public void addListener(MenuStateListener listener) {
	if (listeners == null) {
		listeners = new ArrayList<MenuStateListener>();
	}
	listeners.add(listener);
}

public void closingMenu(){
	if (listeners != null) {
		for (MenuStateListener listener : listeners) {
			listener.closing(this);
		}
	}
}

public void stateChanged() {
	if (listeners != null) {
		for (MenuStateListener listener : listeners) {
			listener.stateChanged(this);
		}
	}
}

/*
 * 
 * EVENT HANDLING (for everything in the componentMap
 * 
 */

@Override
public void mouseClicked(MouseEvent e) {
	ComponentMap.forEach((key,value) -> {
		value.mouseClicked(e);
	});
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	ComponentMap.forEach((key,value) -> {
		value.mouseEntered(e);
	});
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	ComponentMap.forEach((key,value) -> {
		value.mouseExited(e);
	});
}

@Override
public void mousePressed(MouseEvent e) {
	ComponentMap.forEach((key,value) -> {
		value.mousePressed(e);
	});
}

@Override
public void mouseReleased(MouseEvent e) {
	ComponentMap.forEach((key,value) -> {
		value.mouseReleased(e);
	});
}

@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	ComponentMap.forEach((key,value) -> {
		value.mouseDragged(e);
	});
}

@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	ComponentMap.forEach((key,value) -> {
		value.mouseMoved(e);
	});
}
}
