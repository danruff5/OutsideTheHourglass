package ca.cinnamon.hourglass.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import ca.cinnamon.hourglass.screen.Screen;

public class MenuButton implements MouseListener{

    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 50;
    
	private final String name;

	private String label;
	
	private Graphics graphicsBrush;
	
	private int x,y,w,h;
	private int labelx = 0,labely = 0;
	private Shape shape;
	
	public Color ButtonColour = Color.DARK_GRAY;
	public Color ButtonBorderColour = Color.BLUE;
	public Color ButtonTextColour = Color.LIGHT_GRAY;
	public Font ButtonTextFont = new Font("Arial Black", Font.BOLD, 20);
	public enum FontAlign {
		LEFT, CENTERED, RIGHT
	}
	
	private boolean pressed = false;
	
	private List<ButtonListener> listeners;



	public MenuButton(String name, String label, int x, int y) {
		this.x = x;
		this.y = y;
		this.w = BUTTON_WIDTH;
		this.h = BUTTON_HEIGHT;
		shape = new Rectangle(x,y,w,h);
		this.name = name;
		this.label = label;
	}

    public MenuButton(String name, String label, int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		shape = new Rectangle(x,y,w,h);
        this.name = name;
        this.label = label;
    }
    
	public String getLabel() {
		return label;
	}
	
	public boolean isPressed(){
		return pressed;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void draw(Screen screen) {

		if(graphicsBrush == null)
			graphicsBrush = screen.image.getGraphics();
		if(labelx == 0 || labely == 0)
		{
	    	int stringWidth = graphicsBrush.getFontMetrics().stringWidth(label);
			this.labelx = x + w/2 - stringWidth/2;
			this.labely = y + h/2+(int)ButtonTextFont.getSize()/2;
		}

		if(pressed)
		{
		    graphicsBrush.setColor(ButtonColour.darker());
		    graphicsBrush.fillRect(x, y, w, h);
		}
		else
		{
		    graphicsBrush.setColor(ButtonColour);
		    graphicsBrush.fillRect(x, y, w, h);
		}
		//make a border for the button
    	graphicsBrush.setColor(ButtonBorderColour);
    	graphicsBrush.drawRect(x, y, w, h);
    	graphicsBrush.drawRect(x+1, y+1, w-2, h-2);

    	//Button Text
    	graphicsBrush.setFont(ButtonTextFont);
    	graphicsBrush.setColor(ButtonTextColour);
    	//Draw a centered string on the button space
    	graphicsBrush.drawString(label, labelx, labely);  
		
	}
	
	public void addListener(ButtonListener listen){
		if (listeners == null) {
			listeners = new ArrayList<ButtonListener>();
		}
		listeners.add(listen);
	}
	
	public void Tick(){
		if(pressed)
		if (listeners != null) {
			for (ButtonListener listener : listeners) {
				listener.buttonPressed(this);
			}
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(shape.contains(e.getX(), e.getY()))
		{
			pressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}
	
}
