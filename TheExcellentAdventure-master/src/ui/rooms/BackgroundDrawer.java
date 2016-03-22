package ui.rooms;

import java.awt.Graphics;

import assets.images.roombackground.RoomBackgroundImageLoader;
import ui.GamePanel;

public class BackgroundDrawer {

	public static void drawBackgroundVerticalStyle(Graphics g, GamePanel gamePanel){
		//BACKGROUND
		g.drawImage(RoomBackgroundImageLoader.getRoomCeilingImage(), 0, 0, gamePanel);
		g.drawImage(RoomBackgroundImageLoader.getRoomFloorImage(), 0, 370, gamePanel);
		
		//side walls
		g.drawImage(RoomBackgroundImageLoader.getRoomWallLeftImage(), 0, 0, gamePanel);
		g.drawImage(RoomBackgroundImageLoader.getRoomWallRightImage(), 430, 0, gamePanel);
		
		//back wall
		g.drawImage(RoomBackgroundImageLoader.getRoomWallImage(), 150, 150, gamePanel);
	}
	
	//TODO add horizontal assets
	public static void drawBackgroundHorizontalStyle(Graphics g, GamePanel gamePanel){
		//BACKGROUND
		g.drawImage(RoomBackgroundImageLoader.getRoomCeilingImage(), 0, 0, gamePanel);
		g.drawImage(RoomBackgroundImageLoader.getRoomFloorImage(), 0, 370, gamePanel);
		
		//side walls
		g.drawImage(RoomBackgroundImageLoader.getRoomWallLeftImage(), 0, 0, gamePanel);
		g.drawImage(RoomBackgroundImageLoader.getRoomWallRightImage(), 430, 0, gamePanel);
		
		//back wall
		g.drawImage(RoomBackgroundImageLoader.getRoomWallImage(), 150, 150, gamePanel);
	}
	
}
