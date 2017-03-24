package ca.cinnamon.hourglass.menu;

import ca.cinnamon.hourglass.gui.Keys;
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
import java.awt.event.KeyEvent;

public class MainMenu extends Menu {

    public static enum STATE {
        Menu,
        Game,
        Options,
        Help,
        Exit
    }
    private static STATE GameState = STATE.Menu;

    private String helpTxtPath = System.getProperty("user.dir") + "\\help.txt";

    private BufferedImage backgroundImage;

    private Graphics graphicsBrush;
    private Keys keys;

    //temp variables for testing
    private boolean isDarkened = false;

    //Main menu button values
    private int minimumButtonDrawHeight = 200;
    private int minimumButtonSpacing = 125;
    private int btnHeight = 50;
    private int btnWidth = 150;

    public MainMenu(int screen_width, int screen_height, Keys keys) {
        ID = MenuType.Main;
        this.keys = keys;
        //Load buttons
        MenuButton btnStart = new MenuButton("btnStart",
                "Start Game",
                screen_width / 2 - btnWidth / 2,
                minimumButtonDrawHeight,
                btnWidth,
                btnHeight);
        btnStart.addListener(new ButtonListener() {
            @Override
            public void buttonPressed(MenuButton btn) {
                SetCurrentGameState(STATE.Game);
            }
        });
        ComponentMap.put("btnStart", btnStart);

        MenuButton btnOptions = new MenuButton("btnOptions",
                "Options",
                screen_width / 2 - btnWidth / 2,
                minimumButtonDrawHeight + minimumButtonSpacing,
                btnWidth,
                btnHeight);
        btnOptions.addListener(new ButtonListener() {
            @Override
            public void buttonPressed(MenuButton btn) {
                //SetCurrentGameState(STATE.Options);
            }
        });
        ComponentMap.put("btnOptions", btnOptions);

        MenuButton btnHelp = new MenuButton("btnHelp",
                "Help",
                screen_width / 2 - btnWidth / 2,
                minimumButtonDrawHeight + minimumButtonSpacing * 2,
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
                screen_width / 2 - btnWidth / 2,
                minimumButtonDrawHeight + minimumButtonSpacing * 3,
                btnWidth,
                btnHeight);
        btnExit.addListener(new ButtonListener() {
            @Override
            public void buttonPressed(MenuButton btn) {
                SetCurrentGameState(STATE.Exit);
                closingMenu();//This will run code in the statelistener if there is any closing code
            }
        });
        ComponentMap.put("btnExit", btnExit);
    }

    public void init() {
        //Put initial loading in here after changing how screen size is passed
        //ID = id;

    }

    public void draw(Screen screen) {
        int screen_width = screen.w;
        int screen_height = screen.h;
        //Set GraphicBrush if not already received from screen, SHOULD BE SET ON EACH CALL? OR ONCE?
        graphicsBrush = screen.image.getGraphics();

        if (backgroundImage == null) {
            try {
                //can do getScaledImage
                backgroundImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Pictures\\GameMenuBackground.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (GetCurrentGameState() == STATE.Menu) {
            //Draw Background Image
            graphicsBrush.drawImage(backgroundImage, 0, 0, null);

            //draw buttons
            ComponentMap.forEach((key, value) -> {
                value.draw(screen);
            });
        }
    }

    public void tick() {
        if (this.keys.keys[KeyEvent.VK_ENTER].pressed) {
            // Press enter to start the game.
            SetCurrentGameState(STATE.Game);
        }
    }

    /*===============================================================
	 * 
	 *  Utility methods
	 * 
	 ==============================================================*/
    private void HelpClicked() {
        try {
            FileReader fileReader = new FileReader(helpTxtPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            String helpText = "";
            while ((line = bufferedReader.readLine()) != null) {
                helpText += "\n" + line;
            }
            bufferedReader.close();
            JOptionPane.showMessageDialog(null, helpText, "How to Play", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public STATE GetCurrentGameState() {
        return GameState;
    }

    public void SetCurrentGameState(STATE s) {
        GameState = s;
        //Create a StateChangedEvent here and throw it back to the main
        stateChanged();
    }

}
