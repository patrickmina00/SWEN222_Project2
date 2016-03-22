package ui.rooms;

import java.awt.Graphics;

import assets.images.roomforeground.RoomForegroundImageLoader;
import ui.GamePanel;

public class ForegroundDrawer {

	
	//Door
	public static void drawDoorBack(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDoorBack(), 80, 85, gamePanel);
	}
	
	public static void drawDoorLeft(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDoorLeft(), 40, 200, gamePanel);
	}
	
	public static void drawDoorRight(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDoorRight(), 450, 200, gamePanel);
	}
	
	//Bed
	public static void drawBedBack(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDiaryBedBack(), 200, 330, gamePanel);
	}
	
	public static void drawBedLeft(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDiaryBedLeft(), 0, 330, gamePanel);
	}
	
	public static void drawBedRight(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDiaryBedRight(), 350, 330, gamePanel);
	}
	//Dark Door
	public static void drawDarkDoorBack(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDarkDoorBack(), 200, 175, gamePanel);
	}
	
	public static void drawDarkDoorLeft(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDarkDoorLeft(), 0, 140, gamePanel);
	}
	
	public static void drawDarkDoorRight(Graphics g, GamePanel gamePanel){
		g.drawImage(RoomForegroundImageLoader.getDarkDoorRight(), 450, 160, gamePanel);
	}
	//Lockers
	public static void drawLockersBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getLockers(), -115, -25, gamePanel);
		
	}
	public static void drawLockersRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getLockersRight(), 425, 210, gamePanel);
	}
	public static void drawLockersLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getLockersLeft(), -40, 210, gamePanel);
		
	}
	public static void drawChairBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getChair(), 300, 300, gamePanel);
		
	}
	public static void drawChairRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getChairRight(), 380, 240, gamePanel);
		
	}
	public static void drawChairLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getChairLeft(), -50, 200, gamePanel);
		
	}
	public static void drawTorchBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getTorch(), 315, 330, gamePanel);
		
	}
	public static void drawTorchRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getTorchRight(), 480, 400, gamePanel);
		
	}
	public static void drawTorchLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getTorchLeft(), 100, 360, gamePanel);
		
	}
	public static void drawToiletBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getToiletBack(),200, 225, gamePanel);		
	}

	public static void drawSinkBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getSinkBack(), 165, 275, gamePanel);		
	}
	public static void drawToiletLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getToiletLeft(), 65, 290, gamePanel);		
	}
	public static void drawSinkLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getSinkLeft(), 0, 300, gamePanel);
		
	}
	public static void drawToiletRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getToiletRight(), 465, 340, gamePanel);		
	}
	public static void drawSinkRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getSinkRight(), 400, 275, gamePanel);		
	}

	public static void drawRadPuddle(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getRadPuddle(), 335, 355, gamePanel);		
		
	}

	public static void drawRadBarrel(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getRadBarrel(), 120, 330, gamePanel);
	}

	public static void drawScrewDriver(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getScrewDriver(), 150, 340, gamePanel);
	}

	public static void drawRadPuddleLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getRadPuddleLeft(), 75, 350, gamePanel);
	}

	public static void drawScrewDriverLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getScrewDriverLeft(), 85, 320, gamePanel);
	}

	public static void drawRadBarrelLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getRadBarrelLeft(), 0, 345, gamePanel);	
	}


	public static void drawRadBarrelRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getRadBarrelRight(), 200, 275, gamePanel);
	}

	public static void drawScrewDriverRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getScrewDriverRight(), 220, 273, gamePanel);
	}

	public static void drawIrradiatedDoorBack(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getIrradiatedDoorBack(), 170, 140, gamePanel);
		
	}

	public static void drawIrradiatedDoorLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getIrradiatedDoorLeft(), 40, 135, gamePanel);
		
	}

	public static void drawDoorIrradiatedRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getIrradiatedDoorRight(), 445, 147, gamePanel);
		
	}

	public static void drawExit(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getExit(), 195, 237, gamePanel);
		
	}

	public static void drawExitRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getExitRight(), 435, 110, gamePanel);
		
	}

	public static void drawExitLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getExitLeft(), 10, 90, gamePanel);
		
	}

	public static void drawWiresLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getWiresLeft(), 0, 290, gamePanel);
		
	}

	public static void drawPanelLeft(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getPanelLeft(), 0, 290, gamePanel);
		
	}

	public static void drawWiresRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getWiresRight(), 430, 290, gamePanel);
		
	}

	public static void drawPanelRight(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getPanelRight(), 425, 280, gamePanel);
		
	}

	public static void drawWires(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getWiresBack(), 168, 280, gamePanel);
		
	}

	public static void drawPanel(Graphics g, GamePanel gamePanel) {
		g.drawImage(RoomForegroundImageLoader.getPanelBack(), 168, 281, gamePanel);
		
	}	
}
