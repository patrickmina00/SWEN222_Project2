package nodes.rooms;

import gameworld.Orientation;

import java.awt.Graphics;
import java.awt.Point;

import nodes.RoomNode;
import controllers.Controller;
import datastorage.GameState;
import ui.GamePanel;

public interface Drawer {

	
	public void mouseClicked(Point p);
	public void paintRoom(Graphics g, Controller controller, GamePanel gamePanel, Orientation orientation, RoomNode r, GameState gs);
	public void checkItemClick(Point p);
	public boolean otherPlayerPresent(GameState gs, RoomNode r);
}
