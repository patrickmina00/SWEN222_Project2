package nodes.rooms;

import gameworld.Orientation;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.items.Type;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import assets.images.items.InventoryImageUtility;
import assets.images.roomforeground.RoomForegroundImageLoader;
import nodes.*;
import ui.GamePanel;
import ui.rooms.BackgroundDrawer;
import ui.rooms.ForegroundDrawer;
import controllers.Controller;
import datastorage.GameState;

public class ToiletDrawer implements Drawer{
	
	private Controller controller;
	
	//DroppedItems
	private JLabel keyLabel;
	private JLabel radiationLabel;
	private JLabel screwdriverLabel;
	private JLabel torchLabel;
	private JLabel sink;
	private JLabel toilet;
	private JLabel playerLabel;
	
	ItemNode keyNode = null;
	ItemNode radiationNode = null;
	ItemNode screwdriverNode = null;
	ItemNode torchNode = null;
	
	boolean firstPaint = true;
	boolean keyInSink = true;
	boolean toiletInitialised = false;
	
	public ToiletDrawer(ArrayList<BasicNode> children, Controller controller){
		this.controller = controller;
		
		sink = new JLabel();
		toilet = new JLabel();
		
		
		for(BasicNode bNode: children){
			
			if(bNode.getId() == 4){
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
	


	@Override
	public void paintRoom(Graphics g, Controller controller, GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs){
		gamePanel.removeAll();
		toilet = new JLabel();
		sink = new JLabel();
	
		
		if(orientation.equals(Orientation.NORTH)){			
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);			
			ForegroundDrawer.drawDarkDoorRight(g, gamePanel);
			toilet.setIcon(new ImageIcon(RoomForegroundImageLoader.getToiletBack()));
			toilet.setBounds(300, 295, 100, 110);
			gamePanel.add(toilet);
			sink.setIcon(new ImageIcon(RoomForegroundImageLoader.getSinkBack()));
			sink.setBounds(165, 275, 104, 60);
			gamePanel.add(sink);
			
		}else if(orientation.equals(Orientation.EAST)){
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);			
			ForegroundDrawer.drawDarkDoorBack(g, gamePanel);
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			toilet.setIcon(new ImageIcon(RoomForegroundImageLoader.getToiletLeft()));
			toilet.setBounds(65, 290, 115, 138);			
			sink.setIcon(new ImageIcon(RoomForegroundImageLoader.getSinkLeft()));
			sink.setBounds(0, 300, 130, 146);
			gamePanel.add(sink);
			gamePanel.add(toilet);
			
		}else if(orientation.equals(Orientation.SOUTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			ForegroundDrawer.drawDarkDoorLeft(g, gamePanel);
						
		}else if(orientation.equals(Orientation.WEST)){
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorLeft(g, gamePanel);	
			sink.setIcon(new ImageIcon(RoomForegroundImageLoader.getSinkRight()));
			sink.setBounds(400, 275, 171, 180);
			gamePanel.add(toilet);
			toilet.setIcon(new ImageIcon(RoomForegroundImageLoader.getToiletRight()));
			toilet.setBounds(465, 340, 115, 161);
			gamePanel.add(sink);
		}
		
		keyLabel = new JLabel();
		radiationLabel = new JLabel();
		screwdriverLabel = new JLabel();
		torchLabel = new JLabel();
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
			
			if(keyNode != null && keyNode.getState() == ItemState.FALSE){
				gamePanel.add(keyLabel);
			}
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

	
	@Override
	public void mouseClicked(Point p) {
		checkItemClick(p);
		
	}
	
	@Override
	public void checkItemClick(Point p) {
		Rectangle keyRect = keyLabel.getBounds();
		Rectangle radiationRect = radiationLabel.getBounds();
		Rectangle screwdriverRect = screwdriverLabel.getBounds();
		Rectangle torchRect = torchLabel.getBounds();
		Rectangle toiletRect = toilet.getBounds();
		Rectangle sinkRect = sink.getBounds();
		
		if(keyRect.contains(p) && keyNode != null && keyNode.getState() == ItemState.FALSE){	
			controller.pickUpItem(keyNode);			
		}
		else if(torchRect.contains(p)){
			controller.pickUpItem(torchNode);
		}
		else if(radiationRect.contains(p)){
			controller.pickUpItem(radiationNode);
		}
		else if(screwdriverRect.contains(p)){
			controller.privateMessage("You pick up a screwdriver.\n");
			controller.pickUpItem(screwdriverNode);
		}else if((keyNode == null || keyNode.getState() == ItemState.FALSE) && sinkRect.contains(p)){			
			controller.privateMessage("You wash your hands.\n");
		}else if(sinkRect.contains(p) && keyNode != null){
			controller.privateMessage("You wash your hands. And you find a key in the sink!\n");
			controller.updateItem(new ItemNode(12, ItemState.FALSE, Type.REMOVABLE));
			controller.pickUpItem(new ItemNode(12, ItemState.FALSE, Type.REMOVABLE));
		}else if(toiletRect.contains(p)){
			controller.privateMessage("You lay a massive deuce in the toilet. Better wash your hands...\n");
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


