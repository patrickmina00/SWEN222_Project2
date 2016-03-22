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

import assets.images.items.InventoryImageUtility;
import assets.images.roomforeground.RoomForegroundImageLoader;
import nodes.*;
import ui.GamePanel;
import ui.rooms.BackgroundDrawer;
import ui.rooms.ForegroundDrawer;
import controllers.Controller;
import datastorage.GameState;

public class IrradiatedRoomDrawer implements Drawer{

	private Controller controller;
	
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
	
	public IrradiatedRoomDrawer(ArrayList<BasicNode> children, Controller controller){
		this.controller = controller;
		
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
	public void paintRoom(Graphics g, Controller controller, GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs) {
		screwdriverLabel = new JLabel();
		if(orientation.equals(Orientation.NORTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);	
			ForegroundDrawer.drawRadPuddle(g, gamePanel);
			ForegroundDrawer.drawChairBack(g, gamePanel);
			ForegroundDrawer.drawRadBarrel(g, gamePanel);
			if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.TRUE){
				screwdriverLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getScrewDriver()));
				screwdriverLabel.setBounds(150, 340, 79, 33);
				gamePanel.add(screwdriverLabel);
			}

			
		}else if(orientation.equals(Orientation.EAST)){
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			ForegroundDrawer.drawRadPuddleLeft(g, gamePanel);
			ForegroundDrawer.drawChairLeft(g, gamePanel);			
			ForegroundDrawer.drawRadBarrelLeft(g, gamePanel);
			if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.TRUE){
				screwdriverLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getScrewDriverLeft()));
				screwdriverLabel.setBounds(85, 320, 33, 79);
				gamePanel.add(screwdriverLabel);
			}
		
	
			
			
		}else if(orientation.equals(Orientation.SOUTH)){
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			
		}else if(orientation.equals(Orientation.WEST)){
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);	
			ForegroundDrawer.drawChairRight(g, gamePanel);
			ForegroundDrawer.drawRadBarrelRight(g, gamePanel);
			if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.TRUE){
				screwdriverLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getScrewDriverRight()));
				screwdriverLabel.setBounds(220, 273, 79, 33);
				gamePanel.add(screwdriverLabel);
			}
			
		}
		
		keyLabel = new JLabel();
		radiationLabel = new JLabel();
		if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.FALSE){
			screwdriverLabel = new JLabel();
		}
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
			if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.FALSE){
				screwdriverLabel = labels[3];
			}
			gamePanel.add(keyLabel);
			gamePanel.add(radiationLabel);
			gamePanel.add(torchLabel);
			if((screwdriverNode != null) && screwdriverNode.getState() == ItemState.FALSE){
				gamePanel.add(screwdriverLabel);
			}
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
			if(screwdriverNode.getState() == ItemState.TRUE){
				controller.updateItem(new ItemNode(9, ItemState.FALSE, Type.REMOVABLE));				
				controller.pickUpItem(new ItemNode(9, ItemState.FALSE, Type.REMOVABLE));
				controller.privateMessage("You pick the screwdriver up off the barrel, a flathead, nice.\n");
			}else{
				controller.pickUpItem(new ItemNode(9, ItemState.FALSE, Type.REMOVABLE));
			}
			
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


