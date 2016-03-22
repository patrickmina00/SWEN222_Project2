package assets.images.roombackground;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoomBackgroundImageLoader {

	private static Image roomBackground;
	private static Image roomCeiling;
	private static Image roomFloor;
	private static Image roomWallLeft;
	private static Image roomWallRight;
	private static Image roomWall;

	static {
		try {
			roomBackground = ImageIO.read(RoomBackgroundImageLoader.class.getResource("roomBackgroundDefault.png"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			roomCeiling = ImageIO.read(RoomBackgroundImageLoader.class.getResource("ceiling.jpg"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			roomFloor = ImageIO.read(RoomBackgroundImageLoader.class.getResource("floor.jpeg"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}

		try {
			roomWall = ImageIO.read(RoomBackgroundImageLoader.class.getResource("Wall.png"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			roomWallLeft = ImageIO.read(RoomBackgroundImageLoader.class.getResource("WallLeft.png"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			roomWallRight = ImageIO.read(RoomBackgroundImageLoader.class.getResource("WallRight.png"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
	}

	public static Image getRoomBackgroundImage(){
		return roomBackground;
	}
	
	public static Image getRoomCeilingImage() {
		return roomCeiling;
	}

	public static Image getRoomFloorImage() {
		return roomFloor;
	}

	public static Image getRoomWallLeftImage() {
		return roomWallLeft;
	}

	public static Image getRoomWallRightImage() {
		return roomWallRight;
	}
	
	public static Image getRoomWallImage() {
		return roomWall;
	}

}

