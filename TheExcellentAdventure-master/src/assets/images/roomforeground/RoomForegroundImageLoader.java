package assets.images.roomforeground;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoomForegroundImageLoader {

	private static Image diaryBedBack;
	private static Image diaryBedLeft;
	private static Image diaryBedRight;
	private static Image doorBack;
	private static Image doorLeft;
	private static Image doorRight;
	private static Image leverOffBackWall;
	private static Image leverOffCloseLeft;
	private static Image leverOffCloseRight;
	private static Image leverOffMediumLeft;
	private static Image leverOffMediumRight;
	private static Image leverOffFarLeft;
	private static Image leverOffFarRight;	
	private static Image leverOnBackWall;
	private static Image leverOnCloseLeft;
	private static Image leverOnCloseRight;
	private static Image leverOnMediumLeft;
	private static Image leverOnMediumRight;
	private static Image leverOnFarLeft;
	private static Image leverOnFarRight;
	private static Image darkDoorBack;
	private static Image darkDoorLeft;
	private static Image darkDoorRight;
	private static Image lockers;
	private static Image lockersLeft;
	private static Image lockersRight;
	private static Image torch;
	private static Image torchLeft;
	private static Image torchRight;
	//private static Image torchItem;
	private static Image chair;
	private static Image chairLeft;
	private static Image chairRight;
	private static Image toiletBack;	
	private static Image toiletLeft;
	private static Image toiletRight;
	private static Image sinkBack;
	private static Image sinkLeft;
	private static Image sinkRight;
	private static Image radBarrel;
	private static Image radBarrelLeft;
	private static Image radBarrelRight;
	private static Image radPuddle;
	private static Image radPuddleLeft;
	private static Image screwdriver;
	private static Image screwdriverLeft;
	private static Image screwdriverRight;
	private static Image screwdriverItem;
	private static Image radDoor;
	private static Image radDoorLeft;
	private static Image radDoorRight;
	private static Image exit;
	private static Image exitRight;
	private static Image exitLeft;
	private static Image wiresBack;
	private static Image wiresLeft;
	private static Image wiresRight;
	private static Image panelBack;
	private static Image panelLeft;
	private static Image panelRight;
	
	

	static {
		try {
			diaryBedBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("DiaryBed.png"));
			diaryBedLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("DiaryBedLeft.png"));
			diaryBedRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("DiaryBedRight.png"));
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			doorBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("Door.png"));
			doorLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("DoorLeft.png"));
			doorRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("DoorRight.png"));
			darkDoorBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("DarkDoor.png"));
			darkDoorLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("DarkDoorLeft.png"));
			darkDoorRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("DarkDoorRight.png"));
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			leverOffBackWall = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffBackWall.png"));
			
			leverOffCloseLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffCloseLeft.png"));
			leverOffCloseRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffCloseRight.png"));
			
			leverOffMediumLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffMediumLeft.png"));
			leverOffMediumRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffMediumRight.png"));
			
			leverOffFarLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffFarLeft.png"));
			leverOffFarRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOffFarRight.png"));
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}
		
		try {
			leverOnBackWall = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnBackWall.png"));
			
			leverOnCloseLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnCloseLeft.png"));
			leverOnCloseRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnCloseRight.png"));
			
			leverOnMediumLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnMediumLeft.png"));
			leverOnMediumRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnMediumRight.png"));
			
			leverOnFarLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnFarLeft.png"));
			leverOnFarRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LeverOnFarRight.png"));
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}try {
			lockers = ImageIO.read(RoomForegroundImageLoader.class.getResource("Lockers.png"));
			lockersLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("LockersLeft.png"));
			lockersRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("LockersRight.png"));
			torch = ImageIO.read(RoomForegroundImageLoader.class.getResource("Torch.png"));
			torchLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("TorchLeft.png"));
			torchRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("TorchRight.png"));
			//torchItem = ImageIO.read(RoomForegroundImageLoader.class.getResource("TorchItem.png"));
			chair = ImageIO.read(RoomForegroundImageLoader.class.getResource("Chair.png"));
			chairLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("ChairLeft.png"));
			chairRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("ChairRight.png"));
		
			
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}try {
			toiletBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("Toilet.png"));
			toiletLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("ToiletLeft.png"));
			toiletRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("ToiletRight.png"));
			sinkRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("sinkRight.png"));
			sinkLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("sinkLeft.png"));
			sinkBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("sink.png"));
		
			
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}try {
			radBarrel = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadBarrel.png"));
			radBarrelLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadBarrelLeft.png"));
			radBarrelRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadBarrelRight.png"));
			radDoor = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadDoor.png"));
			radDoorLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadDoorLeft.png"));
			radDoorRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadDoorRight.png"));
			radPuddle = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadPuddle.png"));
			radPuddleLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("RadPuddleLeft.png"));
			screwdriver = ImageIO.read(RoomForegroundImageLoader.class.getResource("Screwdriver.png"));
			screwdriverLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("ScrewdriverLeft.png"));
			screwdriverRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("ScrewdriverRight.png"));
			screwdriverItem = ImageIO.read(RoomForegroundImageLoader.class.getResource("ScrewdriverItem.png"));			
			
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}try {
			exit = ImageIO.read(RoomForegroundImageLoader.class.getResource("ExitDoor.png"));	
			exitLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("ExitDoorLeft.png"));
			exitRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("ExitDoorRight.png"));
			panelBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("Panel.png"));	
			panelLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("PanelLeft.png"));
			panelRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("PanelRight.png"));
			wiresBack = ImageIO.read(RoomForegroundImageLoader.class.getResource("Wires.png"));	
			wiresLeft = ImageIO.read(RoomForegroundImageLoader.class.getResource("WiresLeft.png"));
			wiresRight = ImageIO.read(RoomForegroundImageLoader.class.getResource("WiresRight.png"));
			
		} catch (IOException e) {
			System.err.println("Foreground loading encountered an error:" + e.getLocalizedMessage());
		}
	}

	public static Image getDiaryBedBack() {
		return diaryBedBack;
	}
	public static Image getScrewdriverItem(){
		return screwdriverItem;
	}

	public static Image getDiaryBedLeft() {
		return diaryBedLeft;
	}

	public static Image getDiaryBedRight() {
		return diaryBedRight;
	}

	public static Image getDoorBack() {
		return doorBack;
	}

	public static Image getDoorLeft() {
		return doorLeft;
	}

	public static Image getDoorRight() {
		return doorRight;
	}
	//public static Image getTorchItem(){
	//	return torchItem;
	//}

	public static Image getLeverOffBackWall() {
		return leverOffBackWall;
	}

	public static Image getLeverOffCloseLeft() {
		return leverOffCloseLeft;
	}

	public static Image getLeverOffCloseRight() {
		return leverOffCloseRight;
	}

	public static Image getLeverOffMediumLeft() {
		return leverOffMediumLeft;
	}

	public static Image getLeverOffMediumRight() {
		return leverOffMediumRight;
	}

	public static Image getLeverOffFarLeft() {
		return leverOffFarLeft;
	}

	public static Image getLeverOffFarRight() {
		return leverOffFarRight;
	}

	public static Image getLeverOnBackWall() {
		return leverOnBackWall;
	}

	public static Image getLeverOnCloseLeft() {
		return leverOnCloseLeft;
	}

	public static Image getLeverOnCloseRight() {
		return leverOnCloseRight;
	}

	public static Image getLeverOnMediumLeft() {
		return leverOnMediumLeft;
	}

	public static Image getLeverOnMediumRight() {
		return leverOnMediumRight;
	}

	public static Image getLeverOnFarLeft() {
		return leverOnFarLeft;
	}

	public static Image getLeverOnFarRight() {
		return leverOnFarRight;
	}

	public static Image getDarkDoorBack() {
		return darkDoorBack;
	}

	public static Image getDarkDoorLeft() {
		return darkDoorLeft;
	}

	public static Image getDarkDoorRight() {
		return darkDoorRight;
	}

	public static Image getLockers() {
		return lockers;
	}

	public static Image getLockersRight() {
		return lockersRight;
	}

	public static Image getLockersLeft() {
		return lockersLeft;
	}

	public static Image getChair() {
		return chair;
	}

	public static Image getChairRight() {
		return chairRight;
	}

	public static Image getChairLeft() {
		return chairLeft;
	}

	public static Image getTorch() {
		return torch;
	}

	public static Image getTorchRight() {
		return torchRight;
	}

	public static Image getTorchLeft() {
		return torchLeft;
	}

	public static Image getToiletBack() {
		return toiletBack;
	}

	public static Image getToiletLeft() {
		return toiletLeft;
	}

	public static Image getToiletRight() {
		return toiletRight;
	}

	public static Image getSinkBack() {
		return sinkBack;
	}

	public static Image getSinkLeft() {
		return sinkLeft;
	}

	public static Image getSinkRight() {
		return sinkRight;
	}

	public static Image getRadPuddle() {
		return radPuddle;
	}

	public static Image getRadBarrel() {
		return radBarrel;
	}

	public static Image getScrewDriver() {
		return screwdriver;
	}

	public static Image getRadPuddleLeft() {
		return radPuddleLeft;
	}

	public static Image getScrewDriverLeft() {
		return screwdriverLeft;
	}

	public static Image getRadBarrelLeft() {
		return radBarrelLeft;
	}

	public static Image getRadBarrelRight() {
		return radBarrelRight;
	}

	public static Image getScrewDriverRight() {
		return screwdriverRight;
	}

	public static Image getIrradiatedDoorBack() {
		return radDoor;
	}

	public static Image getIrradiatedDoorLeft() {
		return radDoorLeft;
	}

	public static Image getIrradiatedDoorRight() {
		return radDoorRight;
	}
	public static Image getExit() {
		return exit;
	}
	public static Image getExitRight() {
		return exitRight;
	}
	public static Image getExitLeft() {
		return exitLeft;
	}
	public static Image getWiresLeft() {
		return wiresLeft;
	}
	public static Image getPanelLeft() {
		return panelLeft;
	}
	public static Image getWiresRight() {
		return wiresRight;
	}
	public static Image getPanelRight() {
		return panelRight;
	}
	public static Image getWiresBack() {
		return wiresBack;
	}
	public static Image getPanelBack() {
		return panelBack;
	}

}

