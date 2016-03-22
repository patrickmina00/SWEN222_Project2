package ui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import nodes.BasicNode;
import nodes.PlayerNode;
import nodes.RoomNode;
import nodes.rooms.Drawer;
import nodes.rooms.RoomDrawerManager;
import controllers.Controller;
import datastorage.GameState;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GameState gameState;
	private int uid;
	private Controller controller;
	private Drawer roomDrawer;

	public GamePanel(){
		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) { 
				 roomDrawer.mouseClicked(me.getPoint());
			} 
		}); 
	}

	protected void paintComponent(Graphics g) {
		this.removeAll();
		super.paintComponent(g);

		if(gameState != null){

			PlayerNode p = null;

			try {
				p = gameState.player(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(p != null && p.getOrientation() != null){

				int roomId = p.getRoomID();

				RoomNode r = gameState.room(roomId);

				ArrayList<BasicNode> children = r.getChildren();

				roomDrawer = RoomDrawerManager.getRoomDrawer(roomId, children, controller);

				roomDrawer.paintRoom(g, controller, this, p.getOrientation(), r, gameState);

			}	
		}
	}

	public void setGame(GameState gameState, Controller controller, int id) {
		this.gameState = gameState;
		this.uid = id;
		this.controller = controller;
	}

	public void updateGameState(GameState gState) {
		this.gameState = gState;
	}




}
