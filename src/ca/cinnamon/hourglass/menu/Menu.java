package ca.cinnamon.hourglass.menu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.Screen;


public class Menu implements MouseListener {

    public static enum STATE{
    	Menu,
    	Game,
    	Options,
    	Paused,
    	Help,
    	Exit
    }
    private static STATE GameState = STATE.Menu;
    class StateChanged extends EventObject 
    {
    	  public StateChanged(STATE source) {
    	    super(source);
    	  }
    }
    interface StateChangedListener extends EventListener {
    	  void changedState(StateChanged e);
    }
    
    protected void fireStateChanged(StateChanged e)
    {
    	
    }
    
    private String helpTxtPath = System.getProperty("user.dir") + "\\help.txt";
    
	private BufferedImage backgroundImage;
	
	private Graphics graphicsBrush;
	
	List<MenuButton> buttonList = new ArrayList<MenuButton>();
	
	//Dictionary of components drawn on menu
	Map<String,Rectangle> ComponentMap = new HashMap<String,Rectangle>();

	//temp variables for testing
	private boolean isDarkened = false;

	class StartButtonListener implements ButtonListener{

		@Override
		public void buttonPressed(MenuButton button) {
			// TODO Auto-generated method stub
			SetCurrentGameState(STATE.Game);
		}

		@Override
		public void buttonHovered(MenuButton clickableComponent) {
			// TODO Auto-generated method stub
		}
		
	}
	
	public Menu(int screen_width, int screen_height){
		//Load buttons
		MenuButton btnStart = new MenuButton("btnStart", "Start Game", screen_width/2-btnWidth/2, minimumButtonDrawHeight, btnWidth, btnHeight);
		btnStart.addListener(new StartButtonListener());
		buttonList.add(btnStart);
		buttonList.add(new MenuButton("btnOptions", "Options", screen_width/2-btnWidth/2, minimumButtonDrawHeight + minimumButtonSpacing, btnWidth, btnHeight));
		buttonList.add(new MenuButton("btnExit", "Exit Game", screen_width/2-btnWidth/2, minimumButtonDrawHeight + minimumButtonSpacing*2, btnWidth, btnHeight));
		
	}
	
    public void draw(Screen screen) {
    	int screen_width = screen.w;
    	int screen_height = screen.h;
    	//Set GraphicBrush if not already received from screen, SHOULD BE SET ON EACH CALL? OR ONCE?
    	graphicsBrush = screen.image.getGraphics();
    	
    	if(backgroundImage == null)
    	{
			try{
				//can do getScaledImage
			backgroundImage =ImageIO.read(new File(System.getProperty("user.dir") + "\\Pictures\\GameMenuBackground.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	if(GetCurrentGameState() == STATE.Paused)
    	{
    		if(!isDarkened)
    		{
	    		graphicsBrush.setColor(new Color(0, 0, 0, 0.8f));
	    		graphicsBrush.fillRect(0, 0, screen_width, screen_height);
	    		isDarkened = true;
    		}
    		
	    	for(int i  = 0; i < buttonList.size(); ++i)
	    	{
	    		buttonList.get(i).draw(screen);
	    	} 
    	}
    	else if(GetCurrentGameState() == STATE.Menu)
    	{
	    	//Clear previous image
	    	//graphicsBrush.clearRect(0, 0, screen_width, screen_height);
	    	
	    	//Draw Background Image
	    	graphicsBrush.drawImage(backgroundImage, 0, 0, null);
			
	    	//draw buttons
	    	for(int i  = 0; i < buttonList.size(); ++i)
	    	{
	    		buttonList.get(i).draw(screen);
	    	}
    	}
    }
    
    //Updates the menu's state and logic
    public void Tick(){
    	for(int i  = 0; i < buttonList.size(); ++i)
    	{
    		buttonList.get(i).Tick();
    	}
    }

 	/*===============================================================
	 * 
	 *  Utility methods
	 * 
	 ==============================================================*/
	private int minimumButtonDrawHeight = 200;
	private int minimumButtonSpacing = 125;
	
	private int btnHeight = 50;
	private int btnWidth = 150;
	private Color buttonBorderColour = new Color(45, 67, 102);
	Font buttonFont = new Font("TimesRoman", Font.BOLD, 20);
	
	private void HelpClicked()
	{
		try {
			FileReader fileReader = new FileReader(helpTxtPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            String helpText = "";
                while((line = bufferedReader.readLine()) != null) {
                    helpText += "\n" + line;
                }   
                bufferedReader.close();  
		    JOptionPane.showMessageDialog(null, helpText, "How to Play",JOptionPane.INFORMATION_MESSAGE);
		    
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public STATE GetCurrentGameState()
	{
		return GameState;
	}
	public void SetCurrentGameState(STATE s)
	{
		GameState = s;
		//Create a StateChangedEvent here and throw it back to the main
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

    	ComponentMap.forEach((key,value) -> {
    	Shape s = (Shape)value;
    	if(s.contains(arg0.getPoint()))
    	{
    		switch(key){
    		case "Start Game":
    			SetCurrentGameState( STATE.Game);
    			break;
    		case "Options":
    			SetCurrentGameState( STATE.Options);
    			break;
    		case "Help":
    			SetCurrentGameState( STATE.Help);
    			break;
    		case "Exit Game":
    			SetCurrentGameState( STATE.Exit);
    			break;
    		}
    	}
    	});
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
    	for(int i  = 0; i < buttonList.size(); ++i)
    	{
    		buttonList.get(i).mousePressed(arg0);
    	}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
    	for(int i  = 0; i < buttonList.size(); ++i)
    	{
    		buttonList.get(i).mouseReleased(arg0);
    	}	
    }
	
}

