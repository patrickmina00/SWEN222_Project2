package assets.images.items;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import nodes.BasicNode;

public class InventoryImageUtility {

	private static Image image;

	public static Image getInventoryImage(int id){
			try {
				image = ImageIO.read(InventoryImageUtility.class.getResource(getName(id)));
			} catch (IOException e) {
				System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
			}
		
		return image;
	}

	private static String getName(int id) {
		switch (id){
		
		case(-2):
			return "InventoryLabelBackground.png";
		
		case(-1):
			return "InventoryPanel.png";
		
		case(4):
			return "Torch.png";
		
		case(6):
			return "RadiationSuit.png";
		
		case(9):
			return "Screwdriver.png";
		
		case(12):
			return "Key.png";
			
		case(14):
			return "Player.png";
		}
		System.err.print("Item Id does not reffer to inventory item");
		return null;
	}
	
	public static JLabel[] setLabels (BasicNode[] bn){
		JLabel[] labels = new JLabel[4];
		labels[0] = new JLabel();
		labels[1] = new JLabel();
		labels[2] = new JLabel();
		labels[3] = new JLabel();
		
		if(bn[0] != null){
			labels[0].setBounds(150,450,50,50);
			labels[0].setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(12)));
		}
		if(bn[1] != null){
			labels[1].setBounds(250,450,50,50);
			labels[1].setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(6)));
		}
		if(bn[2] != null){
			labels[2].setBounds(300,450,50,50);
			labels[2].setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(4)));
		}
		if(bn[3] != null){
			labels[3].setBounds(200,450,50,50);
			labels[3].setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(9)));
		}
		return labels;
	}
	
}

