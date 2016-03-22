package nodes.rooms;

import gameworld.Orientation;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

public class StartingAreaDrawer implements Drawer{

	private Controller controller;
	
	private ItemNode diary;

	//DroppedItems
	private JLabel keyLabel;
	private JLabel radiationLabel;
	private JLabel screwdriverLabel;
	private JLabel torchLabel;
	private JLabel playerLabel;
	private JLabel diaryBedLabel;
	
	ItemNode keyNode = null;
	ItemNode radiationNode = null;
	ItemNode screwdriverNode = null;
	ItemNode torchNode = null;
	
	boolean firstPaint = true;
	
	public StartingAreaDrawer(ArrayList<BasicNode> children, Controller con) {
		controller = con;
		
		for(BasicNode bNode: children){
			
			if(bNode.getId() == 0){
				if(bNode instanceof ItemNode){
					diary = (ItemNode) bNode;
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

	@Override
	public void paintRoom(Graphics g, Controller controller,
			GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs) {
		gamePanel.removeAll();
		diaryBedLabel = new JLabel();
		if(orientation.equals(Orientation.NORTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);
			
			ForegroundDrawer.drawDoorBack(g, gamePanel);
			
			diaryBedLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getDiaryBedLeft()));
			diaryBedLabel.setBounds(0, 330, 194, 179);
			gamePanel.add(diaryBedLabel);	
		}
		else if(orientation.equals(Orientation.SOUTH)){
			
			BackgroundDrawer.drawBackgroundVerticalStyle(g, gamePanel);

			diaryBedLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getDiaryBedRight()));
			diaryBedLabel.setBounds(400, 330, 225, 201);
			gamePanel.add(diaryBedLabel);
		}
		else if(orientation.equals(Orientation.EAST)){
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);
		
			ForegroundDrawer.drawDoorLeft(g, gamePanel);

		}
		else if(orientation.equals(Orientation.WEST)){
			
			BackgroundDrawer.drawBackgroundHorizontalStyle(g, gamePanel);
			
			ForegroundDrawer.drawDoorRight(g, gamePanel);
			diaryBedLabel.setIcon(new ImageIcon(RoomForegroundImageLoader.getDiaryBedBack()));
			diaryBedLabel.setBounds(200, 330, 297, 93);
			gamePanel.add(diaryBedLabel);
		}
	
		keyLabel = new JLabel();
		radiationLabel = new JLabel();
		screwdriverLabel = new JLabel();
		torchLabel = new JLabel();
		
		playerLabel = new JLabel();
		playerLabel.setIcon(new ImageIcon(InventoryImageUtility.getInventoryImage(14)));
		playerLabel.setBounds(100,100,100,137);
		
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


	@Override
	public void mouseClicked(Point p) {
		
		checkItemClick(p);
		
	}

	public void checkItemClick(Point p) {
		Rectangle diaryBedRect = diaryBedLabel.getBounds();
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
		}else if(diaryBedRect.contains(p)){
			Random rng = new Random();
			int i = rng.nextInt(4);
			if(i == 0){
				controller.privateMessage("You read an excerpt from your diary.\nToday I discovered the end room was absolutely flooded with radioactive waste.\nI'll have to take precautionary measures if I'm to get in there.\n\n");
			}else if(i == 1){
				controller.privateMessage("You read an excerpt from your diary.\nThe year is 2095 and I'm still not sure on what's going on outside.\nThe nuclear war is most likely well over by now but I don't dare step a foot outside\n\n");
			}else if(i == 2){
				controller.privateMessage("You read an excerpt from your diary.\nI'm 33 years old and it's time to grow up and be a man.\nI'm running out of food and need to get outside if I'm to survive.\nDon't forget I'll need to get into my locker if I'm to get out.\nThe code is your year of birth don't forget!\n\n");
			} else if(i == 3){
				controller.privateMessage("You read an excerpt from your diary.\nWhy was I trying to remember the pattern UP DOWN UP\n\n");
			}
		}
		
	}

}
