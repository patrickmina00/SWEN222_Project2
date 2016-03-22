package ui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.Main;

import assets.images.background.BackgroundImageLoader;

/**
 * This is the panel for the end screen
 * that will setup buttons and the credits
 * @author Ryan Ebue
 *
 */
public class EndPanel extends JPanel{

	private boolean exit = false;
	private boolean newGame = false;
	private int startY = 200;
	private int x = 100;
	private int y = 25;
	private Font font, font1, font2, font3;


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		font = new Font( "Serif", Font.BOLD, 32);
		g.setFont(font); 
		g.drawString("You have ESCAPED!!", x, 80);
		font1 = new Font( "Serif", Font.BOLD, 22);
		g.setFont(font1); 
		g.drawString("THE END! Thanks for Playing Ballout", x, 150);
		font2 = new Font( "Serif", Font.BOLD, 16);
		g.setFont(font2);
		g.drawString("Ballout Programmers", x, startY);
		font3 = new Font( "SansSerif", Font.PLAIN, 12 );
		g.setFont(font3);
		g.drawString("Michael Winton: Game Storage", x, startY+y);
		g.drawString("Ryan Ebue: Client/Server", x, startY+y*2);
		g.drawString("Daniel Tait: Game World", x, startY+y*3);
		g.drawString("Samual Holmes: Game Design", x, startY+y*4);
		g.drawString("Patrick Mina: Client/Server", x, startY+y*5);

		
		
	}


	

}
