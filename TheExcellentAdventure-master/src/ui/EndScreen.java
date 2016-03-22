package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * This is the end screen of the game Ballout
 * This works in conjunction with the EndPanel
 * @author Ryan Ebue
 *
 */
public class EndScreen extends JFrame{

	private EndPanel endPanel = new EndPanel();

	/**
	 * Create the frame.
	 */

	public EndScreen(){

		super("Ball Out - Game Over");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 710);

		setContentPane(endPanel);
		endPanel.setFocusable(true);


	}

}
