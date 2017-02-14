package ca.cinnamon.hourglass.menu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Menu extends JFrame {

    public static enum STATE{
    	Menu,
    	Game,
    	Options
    }
    private STATE GameState = STATE.Menu;
    
    private String helpTxtPath = System.getProperty("user.dir") + "\\help.txt";
    
	public JPanel menuPanel;
	
	
	Button startButton; 
	Button optionButton;
	Button helpButton;
	

	public Menu()
	{
		 setSize(300,400);
	     setResizable(false);
	     setLocationRelativeTo(null);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create more panels for different options from the menu
        menuPanel = new JPanel();
        
        startButton = new Button("Start Game");
        optionButton = new Button("Options");
        helpButton = new Button("Help");
        
        menuPanel.add(startButton);
        menuPanel.add(optionButton);
        menuPanel.add(helpButton);
        
        add(menuPanel);
        
        //After pressing start it should switch to the game panel and set a flag to let the game start
        startButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                GameState = STATE.Game;
            }
        });
        
        optionButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	GameState = STATE.Options;
            }
        });
        
        helpButton.addActionListener(new ActionListener () {
        	public void actionPerformed(ActionEvent e) {
        		GameState = STATE.Menu;
        		HelpClicked();
        	}
        });
        
        //setSize(1000,500);
	}
	
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
	}
	
}
