package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nodes.BasicNode;
import nodes.ItemNode;
import nodes.PlayerNode;
import assets.images.items.InventoryImageUtility;
import controllers.Controller;
import datastorage.GameState;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GameState gameState;
	private int uid;
	private Controller controller;
	private PlayerNode player;
	private Rectangle inv1;
	private Rectangle inv2;
	private Rectangle inv3;
	private Rectangle inv4;
	private JLabel[] labels;
	private HashMap<Integer, ImageIcon> inventoryImages;
	
	public InventoryPanel(){
		labels = new JLabel[4];
		inv1 = new Rectangle(0,0,125,125);
		inv2 = new Rectangle(0,125,125,125);
		inv3 = new Rectangle(0,250,125,125);
		inv4 = new Rectangle(0,375,125,125);
		
		
		
		this.initialiseImageIcons();
		
		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) { 
				 identifyClick(me.getPoint());
			} 
		});
	}

	private void initialiseImageIcons() {
		this.inventoryImages = new HashMap<Integer, ImageIcon>();
		
		this.inventoryImages.put(-2, new ImageIcon(InventoryImageUtility.getInventoryImage(-2)));
		this.inventoryImages.put(-1, new ImageIcon(InventoryImageUtility.getInventoryImage(-1)));
		this.inventoryImages.put(4, new ImageIcon(InventoryImageUtility.getInventoryImage(4)));
		this.inventoryImages.put(6, new ImageIcon(InventoryImageUtility.getInventoryImage(6)));
		this.inventoryImages.put(9, new ImageIcon(InventoryImageUtility.getInventoryImage(9)));
		this.inventoryImages.put(12, new ImageIcon(InventoryImageUtility.getInventoryImage(12)));
		
	}

	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    if(gameState != null){
	    	this.removeAll();
	    	g.drawImage(InventoryImageUtility.getInventoryImage(-1),0,0,null);
			int y = 0;
	    	for(JLabel jl : labels){
	    		if(!(jl == null)){
					jl.setBounds(0,y,125,125);
					jl.setVisible(true);
					this.add(jl);
					y = y + 125;
	    		}
			}
	    }
	    
	}

	public void setGame(GameState gameState, Controller controller, int uid) {
		this.gameState = gameState;
		this.uid = uid;
		this.controller = controller;
		this.setLayout(null);	// absolute position placement
		determinePlayer();
		updateInventoryLabels();
    }

	public void updateGameState(GameState gState) {
		this.gameState = gState;
		determinePlayer();
		updateInventoryLabels();
	}
	
	private void determinePlayer(){
		player = gameState.getPlayers().get(uid);
//    	for(PlayerNode pn : this.gameState.getPlayers()){
//    		if(pn.getID() == uid){
//    			player = pn;
//    			break;
//    		}
//    	}
	}
	
	private void updateInventoryLabels() {
		//int i =0;
		//for(BasicNode bn : player.getChildren()){
		//	JLabel label = new JLabel(new ImageIcon(InventoryImageLoader.getInventoryImage(bn.getId())));	
		//	labels[i] = label;
		//	i++;
    	//}
		
		for(int i = 0; i < player.getChildren().size(); i++){
			int id = player.getChildren().get(i).getId();
			JLabel label = new JLabel(inventoryImages.get(id));
			labels[i] = label;
		}
		for(int i = player.getChildren().size(); i < 4 ; i++){
			labels[i] = null;
		}
	}
	
	protected void identifyClick(Point point) {
		if(!(player.getChildren().isEmpty())){
			if (inv1.contains(point) && !(labels[0].equals(null))){controller.dropItem(player.getChildren().get(0));}
			else if (inv2.contains(point) && !(labels[1].equals(null))){controller.dropItem(player.getChildren().get(1));}
			else if (inv3.contains(point) && !(labels[2].equals(null))){controller.dropItem(player.getChildren().get(2));}
			else if (inv4.contains(point) && !(labels[3].equals(null))){controller.dropItem(player.getChildren().get(3));}
		}
	}
		
	
}
