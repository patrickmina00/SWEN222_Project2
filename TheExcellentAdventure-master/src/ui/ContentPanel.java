package ui;

import java.awt.Graphics;

import javax.swing.JPanel;

import assets.images.background.BackgroundImageLoader;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(BackgroundImageLoader.getBackgroundImage(), 0, 0, this);
	}

}
