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
import javax.swing.JOptionPane;

import assets.images.items.InventoryImageUtility;
import assets.images.roomforeground.RoomForegroundImageLoader;
import nodes.BasicNode;
import nodes.ItemNode;
import nodes.PlayerNode;
import nodes.RoomNode;
import ui.GamePanel;
import ui.rooms.BackgroundDrawer;
import ui.rooms.ForegroundDrawer;
import controllers.Controller;
import datastorage.GameState;

public class LockerRoomDrawer implements Drawer{

	
	private Controller controller;
	
	//DroppedItems
	private JLabel keyLabel;
	private JLabel radiationLabel;
	private JLabel screwdriverLabel;
	private JLabel torchLabel;
	private JLabel lockersLabel;
	private JLabel playerLabel;
	
	ItemNode keyNode = null;
	ItemNode radiationNode = null;
	ItemNode screwdriverNode = null;
	ItemNode torchNode = null;
	
	boolean firstPaint = true;
	
	public LockerRoomDrawer(ArrayList<BasicNode> children, Controller controller) {
		this.controller = controller;
		lockersLabel = new JLabel();
		
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
	public void paintRoom(Graphics g, Controller controller,GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs) {
		gamePanel.removeAll();
		lockersLabel = new JLabel();
		torchLabel = new JLabel();
		
		if(orientation.equals(Orientation.NORTH)){			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			ForegroundDrawer.drawChairLeft(g, gamePanel);
			if((torchNode != null) && torchNode.getState() == ItemState.TRUE){
				torchLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getTorchLeft()));
				torchLabel.setBounds(100, 360, 61, 57);
				gamePanel.add(torchLabel);	
			}
			
		}else if(orientation.equals(Orientation.EAST)){
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			ForegroundDrawer.drawDoorLeft(g, gamePanel);
			lockersLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getLockersRight()));
			lockersLabel.setBounds(425, 210, 200, 317);
			gamePanel.add(lockersLabel);

			
		}else if(orientation.equals(Orientation.SOUTH)){
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorLeft(g, gamePanel);
			lockersLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getLockers()));
			lockersLabel.setBounds(180, 240, 271, 151);
			gamePanel.add(lockersLabel);
			ForegroundDrawer.drawChairRight(g, gamePanel);
			if((torchNode != null) && torchNode.getState() == ItemState.TRUE){
				torchLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getTorchRight()));
				torchLabel.setBounds(100, 360, 61, 57);
				gamePanel.add(torchLabel);	
			}
			
						
		}else if(orientation.equals(Orientation.WEST)){
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);			
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			lockersLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getLockersLeft()));
			lockersLabel.setBounds(-40, 210, 200, 317);
			gamePanel.add(lockersLabel);
			ForegroundDrawer.drawLockersLeft(g, gamePanel);
			ForegroundDrawer.drawChairBack(g, gamePanel);
			if((torchNode != null) && torchNode.getState() == ItemState.TRUE){
				torchLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getTorch()));
				torchLabel.setBounds(315, 330, 61, 57);
				gamePanel.add(torchLabel);	
			}
		}
		playerLabel = new JLabel();
		keyLabel = new JLabel();
		radiationLabel = new JLabel();
		screwdriverLabel = new JLabel();
		if((torchNode != null) && torchNode.getState() == ItemState.FALSE){
			torchLabel = new JLabel();
		}
		
		if(firstPaint){
			BasicNode[] nodes = new BasicNode[4];
			nodes[0] = keyNode;
			nodes[1] = radiationNode;
			nodes[2] = torchNode;
			nodes[3] = screwdriverNode;
			
			JLabel[] labels = InventoryImageUtility.setLabels(nodes);
			keyLabel = labels[0];
			radiationLabel = labels[1];
			if(torchNode != null && torchNode.getState() == ItemState.FALSE){
				torchLabel = labels[2];				
			}
			screwdriverLabel = labels[3];
			
			
			gamePanel.add(keyLabel);
			if(radiationNode != null && radiationNode.getState() == ItemState.FALSE){
				gamePanel.add(radiationLabel);
			}
			if(torchNode != null && torchNode.getState() == ItemState.FALSE){
				gamePanel.add(torchLabel);
			}
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
		Rectangle lockersRect = lockersLabel.getBounds();
		System.out.println(torchRect);
		
		if(keyRect.contains(p)){	
			controller.pickUpItem(keyNode);			
		}
		else if(torchRect.contains(p)){
			if(torchNode.getState() == ItemState.TRUE){
				controller.updateItem(new ItemNode(4, ItemState.FALSE, Type.REMOVABLE));				
				controller.pickUpItem(new ItemNode(4, ItemState.FALSE, Type.REMOVABLE));
				controller.privateMessage("You pick the torch up off the chair, it seems to have a lot of battery left.\n");
			}else{
				controller.pickUpItem(new ItemNode(4, ItemState.FALSE, Type.REMOVABLE));
			}
		}
		else if(radiationRect.contains(p) && radiationNode.getState() == ItemState.FALSE){
			controller.pickUpItem(radiationNode);
		}
		else if(screwdriverRect.contains(p)){
			controller.pickUpItem(screwdriverNode);
		}else if(lockersRect.contains(p)){
			if(radiationNode != null && radiationNode.getState() == ItemState.TRUE){
				int lockersCode = Integer.parseInt(JOptionPane.showInputDialog("You locker seems to be the only functioning locker left.\nWhat is your code again?", null));
				if(lockersCode != 2062){
					controller.privateMessage("Bzzzzzz! Wrong!");
				}else if(lockersCode == 2062){
					controller.updateItem(new ItemNode(6, ItemState.FALSE, Type.REMOVABLE));
					System.out.println(radiationNode.getState());
					controller.pickUpItem(new ItemNode(6, ItemState.FALSE, Type.REMOVABLE));
					controller.privateMessage("The lockers pops open and reveals a radiation suit.\nYou put it on.");
				}
			}else{
				controller.privateMessage("There's nothing left in your locker.\n");
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


