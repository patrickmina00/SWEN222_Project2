package nodes.rooms;

import java.util.ArrayList;

import controllers.Controller;
import nodes.BasicNode;

public class RoomDrawerManager {

	public static Drawer getRoomDrawer(int rid, ArrayList<BasicNode> children, Controller controller){
		
		switch(rid){
		
		case(0): return new StartingAreaDrawer(children, controller);
		case(1): return new LeverRoomDrawer(children, controller);
		case(2): return new GenericRoom1Drawer(children, controller);
		case(3): return new LockerRoomDrawer(children, controller);
		case(4): return new DarkRoomDrawer(children, controller);
		case(5): return new ToiletDrawer(children, controller);
		case(6): return new GenericRoom2Drawer(children, controller);
		case(7): return new IrradiatedRoomDrawer(children, controller);
		case(8): return new ExitDrawer(children, controller);
		default: System.err.println("Could not match room id!");
		
		}
		
		return null;
		
	}

}
