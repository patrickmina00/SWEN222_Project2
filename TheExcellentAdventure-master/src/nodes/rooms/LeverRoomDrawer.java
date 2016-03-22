package nodes.rooms;

import gameworld.Orientation;
import gameworld.items.ItemState;
import gameworld.items.Type;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import nodes.BasicNode;
import nodes.ItemNode;
import nodes.PlayerNode;
import nodes.RoomNode;
import controllers.Controller;
import datastorage.GameState;
import ui.GamePanel;
import ui.rooms.BackgroundDrawer;
import ui.rooms.ForegroundDrawer;
import assets.images.items.InventoryImageUtility;
import assets.images.roomforeground.RoomForegroundImageLoader;

public class LeverRoomDrawer implements Drawer{

	private boolean leversInitialised = false;
	
	ItemNode leverOne = new ItemNode(1, ItemState.FALSE, Type.ANCHORED);
	ItemNode leverTwo = new ItemNode(2, ItemState.FALSE, Type.ANCHORED);
	ItemNode leverThree = new ItemNode(3, ItemState.FALSE, Type.ANCHORED);
	
	ArrayList<BasicNode> items;
	
	ImageIcon icon = new ImageIcon(RoomForegroundImageLoader.getLeverOffFarRight());
	
	//labels
	private JLabel leverLabel1;
	private JLabel leverLabel2;
	private JLabel leverLabel3;
	
	//DroppedItems
	private JLabel keyLabel;
	private JLabel radiationLabel;
	private JLabel screwdriverLabel;
	private JLabel torchLabel;
	private JLabel playerLabel;
	
	ItemNode keyNode = null;
	ItemNode radiationNode = null;
	ItemNode screwdriverNode = null;
	ItemNode torchNode = null;
	
	boolean firstPaint = true;
	
	private Controller controller;
	
	public LeverRoomDrawer(ArrayList<BasicNode> children, Controller controller) {
		
		this.items = children;
		this.controller = controller;
		
		leverLabel1 = new JLabel();
		leverLabel2 = new JLabel();
		leverLabel3 = new JLabel();
		
		for(BasicNode bNode: children){
			
			if(bNode.getId() == 1){
				if(bNode instanceof ItemNode){
					leverOne = (ItemNode) bNode;
				}
			} else if(bNode.getId() == 2){
				if(bNode instanceof ItemNode){
					leverTwo = (ItemNode) bNode;
				}
			} else if(bNode.getId() == 3){
				if(bNode instanceof ItemNode){
					leverThree = (ItemNode) bNode;
				}
			} else if(bNode.getId() == 4){
				if(bNode instanceof ItemNode){
					torchNode = (ItemNode) bNode;
				}	
			} else if(bNode.getId() == 6){
				if(bNode instanceof ItemNode){
					radiationNode = (ItemNode) bNode;
				}
			} else if(bNode.getId() == 9){
				if(bNode instanceof ItemNode){
					screwdriverNode = (ItemNode) bNode;
				}	
			} else if(bNode.getId() == 12){
				if(bNode instanceof ItemNode){
					keyNode = (ItemNode) bNode;
				}	
			}
		}
		
		
	}

	public void paintRoom(Graphics g, Controller controller, GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs) {

		
		gamePanel.removeAll();
		
		if(!leversInitialised){
			this.addLevers(gamePanel);
			leversInitialised = true;
		}

		if(orientation.equals(Orientation.NORTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);
			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			
			//levers on left
			if(leverThree.getState() == ItemState.TRUE){
				
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnFarRight()));
				leverLabel3.setBounds(90, 200, 53, 74);
				
			} else {
				
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffFarRight()));
				leverLabel3.setBounds(90, 200, 53, 74);
				
			}
			
			if(leverTwo.getState() == ItemState.TRUE){
				
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnMediumRight()));
				leverLabel2.setBounds(60, 200, 53, 74);
			} else {
				
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffMediumRight()));
				leverLabel2.setBounds(60, 200, 53, 74);
			}
			
			if(leverOne.getState() == ItemState.TRUE){
				
				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnCloseRight()));
				leverLabel1.setBounds(20, 200, 53, 74);
			} else {
				
				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffCloseRight()));
				leverLabel1.setBounds(20, 200, 53, 74);
			}

	
		}
		else if(orientation.equals(Orientation.SOUTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);
			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			
			//levers on right
			if(leverOne.getState() == ItemState.TRUE){
				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnFarRight()));
				leverLabel1.setBounds(420, 200, 53, 74);
			} else {
				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffFarRight()));
				leverLabel1.setBounds(420, 200, 53, 74);
			}
			
			if(leverTwo.getState() == ItemState.TRUE){
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnMediumRight()));
				leverLabel2.setBounds(450, 200, 53, 74);
			} else {
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffMediumRight()));
				leverLabel2.setBounds(450, 200, 53, 74);
			}
			
			if(leverThree.getState() == ItemState.TRUE){
				
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnCloseRight()));
				leverLabel3.setBounds(480, 200, 53, 74);
				
			} else {
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffCloseRight()));
				leverLabel3.setBounds(480, 200, 53, 74);
			}
			

			
		}
		else if(orientation.equals(Orientation.EAST)){
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);
		
			ForegroundDrawer.drawDoorLeft(g, gamePanel);
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			
			leverLabel1.setBounds(-1000, -1000, 53, 74);
			leverLabel2.setBounds(-1000, -1000, 53, 74);
			leverLabel3.setBounds(-1000, -1000, 53, 74);
			
			//levers not in view
			
		}
		else if(orientation.equals(Orientation.WEST)){
			leverLabel3.setBounds(300, 200, 53, 74);
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);
			
			ForegroundDrawer.drawDoorLeft(g, gamePanel);
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			
			//levers in front
			if(leverOne.getState() == ItemState.TRUE){

				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnBackWall()));
				leverLabel1.setBounds(200, 200, 40, 74);
			
			} else {
				
				leverLabel1.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffBackWall()));
				leverLabel1.setBounds(200, 200, 40, 74);
			
			}
			
			if(leverTwo.getState() == ItemState.TRUE){
				
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnBackWall()));
				leverLabel2.setBounds(270, 200, 40, 74);
			} else {
				
				leverLabel2.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffBackWall()));
				leverLabel2.setBounds(270, 200, 40, 74);
			}
			
			if(leverThree.getState() == ItemState.TRUE){
				
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOnBackWall()));
				leverLabel3.setBounds(340, 200, 40, 74);
			} else {
				leverLabel3.setIcon(new ImageIcon(RoomForegroundImageLoader.getLeverOffBackWall()));
				leverLabel3.setBounds(340, 200, 40, 74);
			}
		}
		
		playerLabel = new JLabel();
		
		if(firstPaint){
			BasicNode[] nodes = new BasicNode[4];
			nodes[0] = keyNode;
			nodes[1] = radiationNode;
			nodes[2] = torchNode;
			nodes[3] = screwdriverNode;
			
			JLabel[] labels = InventoryImageUtility.setLabels(nodes);
			keyLabel = labels[0];
			radiationLabel = labels[1];
			torchLabel = labels[2];
			screwdriverLabel = labels[3];
			
			gamePanel.add(keyLabel);
			gamePanel.add(radiationLabel);
			gamePanel.add(torchLabel);
			gamePanel.add(screwdriverLabel);
			
			if(otherPlayerPresent(gs,r)){
				playerLabel.setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(14)));
				playerLabel.setBounds(100,100,100,137);
				gamePanel.add(playerLabel);
				
			}
			
			firstPaint = false;
			}
		}
		

	private void addLevers(final GamePanel gamePanel) {

		gamePanel.add(leverLabel3);
		gamePanel.add(leverLabel2);
		gamePanel.add(leverLabel1);		
	}
	
	@Override
	public void mouseClicked(Point p) {
		
		Rectangle lever1Rect = leverLabel1.getBounds();
		Rectangle lever2Rect = leverLabel2.getBounds();
		Rectangle lever3Rect = leverLabel3.getBounds();	
		
		if(lever1Rect.contains(p)){
			if(leverOne.getState() == ItemState.TRUE){
				
				ItemNode itemNode = new ItemNode(leverOne.getId(), ItemState.FALSE, leverOne.getType());
				controller.updateItem(itemNode);
				
			} else{
				
				ItemNode itemNode = new ItemNode(leverOne.getId(), ItemState.TRUE, leverOne.getType());
				controller.updateItem(itemNode);
				
			}
		}
		
		else if(lever2Rect.contains(p)){
			if(leverTwo.getState() == ItemState.TRUE){
				
				ItemNode itemNode = new ItemNode(leverTwo.getId(), ItemState.FALSE, leverTwo.getType());
				controller.updateItem(itemNode);
				
			} else{
				
				ItemNode itemNode = new ItemNode(leverTwo.getId(), ItemState.TRUE, leverTwo.getType());
				controller.updateItem(itemNode);
				
			}
		}
		
		else if(lever3Rect.contains(p)){
			if(leverThree.getState() == ItemState.TRUE){
				
				ItemNode itemNode = new ItemNode(leverThree.getId(), ItemState.FALSE, leverThree.getType());
				controller.updateItem(itemNode);
				
			} else{
				ItemNode itemNode = new ItemNode(leverThree.getId(), ItemState.TRUE, leverThree.getType());
				controller.updateItem(itemNode);
				
			}
		}
		
		checkItemClick(p);
		
	}
	
	public void checkItemClick(Point p) {
		
		Rectangle keyRect = keyLabel.getBounds();
		Rectangle radiationRect = radiationLabel.getBounds();
		Rectangle screwdriverRect = screwdriverLabel.getBounds();
		Rectangle torchRect = torchLabel.getBounds();
		
		if(keyRect.contains(p)){	
			controller.pickUpItem(keyNode);			
		}
		else if(torchRect.contains(p)){
			controller.pickUpItem(torchNode);
		}
		else if(radiationRect.contains(p)){
			controller.pickUpItem(radiationNode);
		}
		else if(screwdriverRect.contains(p)){
			controller.pickUpItem(screwdriverNode);
		}
		
	}

	@Override
	public boolean otherPlayerPresent(GameState gs, RoomNode r) {
		PlayerNode[] players = new PlayerNode[2];
		int i = 0;
		for(PlayerNode pn : gs.getPlayers()){
				players[i] = pn;
				i++;
			}
		if(players[1] == null){
			return false;
		}
		else {
			return players[0].getRoomID() == players[1].getRoomID();
		}
	}

}
