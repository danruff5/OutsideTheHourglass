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
import java.awt.event.MouseMotionListener;
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

import ca.cinnamon.hourglass.menu.MenuManager.MenuType;
import ca.cinnamon.hourglass.screen.Bitmap;
import ca.cinnamon.hourglass.screen.Screen;


public class MainMenu extends Menu implements MouseListener, MouseMotionListener {

    public static enum STATE{
    	Menu,
    	Game,
    	Options,
    	Paused,
    	Help,
    	Exit
    }
    private static STATE GameState = STATE.Menu;
    
    private String helpTxtPath = System.getProperty("user.dir") + "\\help.txt";
    
	private BufferedImage backgroundImage;
	
	private Graphics graphicsBrush;
	
	//Dictionary of components drawn on menu
	Map<String,MenuButton> ComponentMap = new HashMap<String,MenuButton>();

	//temp variables for testing
	private boolean isDarkened = false;
	
	//Main menu button values
	private int minimumButtonDrawHeight = 200;
	private int minimumButtonSpacing = 125;
	private int btnHeight = 50;
	private int btnWidth = 150;
	
	public MainMenu(int screen_width, int screen_height){
		//Load buttons
		
		MenuButton btnStart = new MenuButton("btnStart", "Start Game", screen_width/2-btnWidth/2, minimumButtonDrawHeight, btnWidth, btnHeight);
		btnStart.addListener(new ButtonListener() {
		    @Override
		    public void buttonPressed(MenuButton btn) {
		        SetCurrentGameState(STATE.Game);
		    }
		});
		ComponentMap.put("btnStart",btnStart);
		
		MenuButton btnOptions = new MenuButton("btnOptions",
				"Options",
				screen_width/2-btnWidth/2,
				minimumButtonDrawHeight + minimumButtonSpacing,
				btnWidth,
				btnHeight);
		btnOptions.addListener(new ButtonListener() {
		    @Override
		    public void buttonPressed(MenuButton btn) {
		        //SetCurrentGameState(STATE.Options);
		    }
		});
		ComponentMap.put("btnOptions",btnOptions);
		
		MenuButton btnHelp = new MenuButton("btnHelp",
				"Help",
				screen_width/2-btnWidth/2,
				minimumButtonDrawHeight + minimumButtonSpacing*2,
				btnWidth,
				btnHeight);
		btnHelp.addListener(new ButtonListener() {
		    @Override
		    public void buttonPressed(MenuButton btn) {
		        //SetCurrentGameState(STATE.Help);
		    	HelpClicked();
		    }
		});
		ComponentMap.put("btnHelp", btnHelp);
		
		MenuButton btnExit = new MenuButton("btnExit",
				"Exit Game",
				screen_width/2-btnWidth/2,
				minimumButtonDrawHeight + minimumButtonSpacing*3,
				btnWidth,
				btnHeight);
		btnExit.addListener(new ButtonListener() {
		    @Override
		    public void buttonPressed(MenuButton btn) {
		        SetCurrentGameState(STATE.Exit);
		    }
		});
		ComponentMap.put("btnExit", btnExit);
	}
	
	public void init(MenuType id){
		//Put initial loading in here after changing how screen size is passed
		ID = id;
		
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
    		
        	ComponentMap.forEach((key,value) -> {
        		value.draw(screen);
        	});
    	}
    	else if(GetCurrentGameState() == STATE.Menu)
    	{
	    	//Draw Background Image
	    	graphicsBrush.drawImage(backgroundImage, 0, 0, null);
			
	    	//draw buttons
        	ComponentMap.forEach((key,value) -> {
        		value.draw(screen);
        	});
    	}
    }
    public void tick(){
    	
    }

 	/*===============================================================
	 * 
	 *  Utility methods
	 * 
	 ==============================================================*/
	
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

