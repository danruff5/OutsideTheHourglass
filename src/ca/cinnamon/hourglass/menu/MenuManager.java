package ca.cinnamon.hourglass.menu;

import java.util.ArrayList;
import java.util.List;

import ca.cinnamon.hourglass.screen.Screen;

public class MenuManager {

	public static enum MenuType{
		Game,
		Main,
		Inventory
	}
	
	public static List<Menu> availableMenus;

	private int currentMenu = -1;

public MenuManager(){
	//on initial load 
	
	//create a menu
	//register it with a a listener so that we can handle menu closing or state changed
};

public int CurrentMenuIndex(){
	return currentMenu;
}
public boolean LoadMenuAt(int index){
	if(index < availableMenus.size() && index > -1)
	{
		currentMenu = index;
		return true;
	}
	return false;
}

public void addMenu(Menu m){
	if(availableMenus == null)
		availableMenus = new ArrayList<Menu>();
	availableMenus.add(m);
}

public void drawCurrentMenu(Screen screen){
	//Will only draw a menu if it is enabled
	//Potentially we can draw menu's on top of eachother???
	if(currentMenu != -1)
	if(availableMenus.get(currentMenu).isEnabled)
	{
		availableMenus.get(currentMenu).draw(screen);
	}
}

public void tickCurrentMenu(){
	if(currentMenu != -1)
	if(availableMenus.get(currentMenu).isEnabled)
	{
		availableMenus.get(currentMenu).tick();
	}
}
	
	
}
