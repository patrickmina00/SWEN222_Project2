package master;

import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.rooms.Room;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import nodes.BasicNode;
import parser.GsParser;
import controllers.BusinessLogic;
import datastorage.Game;
import exceptions.GameLogicException;
/**
 * The Clock Thread is responsible for constantly updating the game state
 * as well as refresh the game display
 * 
 * @co-author Ryan Ebue
 * @co-author Patrick Mina
 * 
 */
import gameworld.items.Item;
import gameworld.items.ItemState;

/**
 * The Server is responsible for creating a server
 * Connects clients and communicates with them.
 * The Server class is a thread.
 * 
 * Inspired by Pacman Server code - djp
 * 
 * @ co-author Ryan Ebue
 * @ co-author Patrick Mina
 * 
 */
public class Server extends Thread implements BusinessLogic {
	private int uid;
	private static int port;
	private static Socket socket;
	private int broadcastClock;
	private Game game;
	private ItemManager itemManager;

	public Server(Socket socket, int uid, int broadcastClock, Game game){
		this.socket = socket;
		this.uid = uid;
		this.broadcastClock = broadcastClock;
		this.game = game;
		this.itemManager = new ItemManagerImpl();
	}

	public Server(int port, int uid){
		this.port = port;
		this.uid = uid;
	}




	public void run(){
		//This is where the communication will happen
		//Need to initialize the server and wait for client(s)...
		//Setup the input and output streams

		try {
			System.out.println("Just connected to "+ socket.getRemoteSocketAddress());

			//Setup the streams for communication with client
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());


			System.out.println("Waiting for client...");
			boolean exit = false;

			//Send client a UID
			output.writeInt(uid);
			output.flush();

			output.writeUTF(GsParser.generateExp(game.getGameState()));
			//Have a while loop that will loop "forever" until a client disconnect happens
			while(!exit){
				try{
					//check and read inputs
					if(input.available() != 0 && (!socket.isClosed() && socket.isConnected())){
						String line = input.readUTF();
						parse(line);
					}

					// get the gameState through the parser
					// broadcast the gameState to the client
					try{
						String expr = GsParser.generateExp(game.getGameState());
						output.writeUTF(expr);
					}
					catch(SocketException s){
						System.out.println("SocketException: " + s.getMessage());
						output.close();
						input.close();
						break;
					}
					//Now flush all the information to the client
					output.flush();
					Thread.sleep(broadcastClock);

				} catch(InterruptedException e) {					
				}
			}
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Socket closed? " + socket.isClosed());
		System.out.println("Socket connected? " + socket.isConnected());
	}

	@Override
	public void turnLeft() throws GameLogicException {
		switch(game.player(uid).getOrientation()){
		case EAST:
			game.player(uid).setOrientation(Orientation.NORTH);
			break;
		case NORTH:
			game.player(uid).setOrientation(Orientation.WEST);
			break;
		case SOUTH:
			game.player(uid).setOrientation(Orientation.EAST);
			break;
		case WEST:
			game.player(uid).setOrientation(Orientation.SOUTH);
			break;
		default:
			System.err.println("ERROR: Could not turnLeft");
			break;
		}
	}

	@Override
	public void turnRight() throws GameLogicException {
		switch(game.player(uid).getOrientation()){
		case EAST:
			game.player(uid).setOrientation(Orientation.SOUTH);
			break;
		case NORTH:
			game.player(uid).setOrientation(Orientation.EAST);
			break;
		case SOUTH:
			game.player(uid).setOrientation(Orientation.WEST);
			break;
		case WEST:
			game.player(uid).setOrientation(Orientation.NORTH);
			break;
		default:
			System.err.println("ERROR: Could not turnRight");
			break;
		}
	}

	@Override
	public void goForward() throws GameLogicException {
		//		System.out.println("IT IS WORKING UP");
		Room currentRoom = game.getRooms().get(game.player(uid).getRoomId());
		Room moveToRoom = currentRoom.leaveRoomWithOrientation(game.player(uid));

		if(moveToRoom != null){
			game.player(uid).setRoomId(moveToRoom.getId());
		} else {
		}
	}

	@Override
	public void dropItem(BasicNode basicNode) {
		//finds the player who is dropping the item
		for (Player p : game.getPlayers()){
			if(p.getID() == uid){

				//removes the item from player and adds it to their current room
				Item i = itemManager.getItemWithIdAndState(basicNode.getId(), basicNode.getState());
				p.removeItem(i);
				game.getRooms().get(p.getRoomId()).addItem(i);
				break;
			}
		}

	}

	@Override
	public void pickUpItem(BasicNode basicNode) {
		//finds the player who is picking up the item
		for (Player p : game.getPlayers()){
			if(p.getID() == uid){

				//removes the item from room and adds it to the player
				Item item = itemManager.getItemWithIdAndState(basicNode.getId(), basicNode.getState());
				game.getRooms().get(p.getRoomId()).removeItem(item);
				p.addItem(item);
				System.out.println("server: player " + p.toString());
				break;
			}
		}
	}

	@Override
	public void updateItem(BasicNode basicNode) {
		Item item = itemManager.getItemWithIdAndState(basicNode.getId(), basicNode.getState());

		int rid = -1;

		for(Room r: game.getRooms().values()){
			for(Item i: r.getItems()){
				if(i.equalsID(item)){
					rid = r.getId();
				}
			}
		}

		if(rid == -1){
			System.err.println("Could not update item");
		} else {
			Room r = game.getRooms().get(rid);
			r.updateItem(item);
		}
	}

	@Override
	public void businessWin() {
		game.setGameRunning(false);
	}
	
	
	/**
	 * Used to pass strings to messageNode
	 * @param s
	 */
	public void messaging(String s){
		game.setMessage(s);
	}

	
	/**
	 * Parser to read and execute the commands from the bytestream
	 * @param action
	 */
	public void parse(String action){
		int playerID;
		int itemID;
		String state1 = null;
		String token = null;
		Scanner scan  = new Scanner (action);
		ItemState state = ItemState.DEFAULT;
		ItemManager itemManager = new ItemManagerImpl();

		if(!scan.hasNext()){
			System.out.println("no string to scan");
			scan.close();
			return;
		}
		token = scan.next().trim();


		if(token.equalsIgnoreCase("win")){
			//call win method
			businessWin();
			scan.close();
			return;
		}
		else if(token.equalsIgnoreCase("openpanel")){
			//call open panel methods
			businessOpenPanel();
			scan.close();
			return;
		}

		else if(token.equalsIgnoreCase("message")){
			String s;
			if(scan.hasNext()){
				s = scan.nextLine().trim();
//				System.out.println(s);
				messaging(s);
			}
			scan.close();
			return;
		}


		playerID = scan.nextInt();

		if(token.equalsIgnoreCase("move")){
			System.out.println("Read:"+token+" playerID:"+ uid);
			//Call move stuff here
			int action1 = scan.nextInt();			

			if(action1 == 0) {
				System.out.println("Action received: " + action1);
			}
			else {
				System.out.println("Action received: " + action1);
				//output.writeInt(action);
			}
			if(action1 == 0) {
				System.out.println("Action received: " + action1);
			}
			else {
				System.out.println("Action received: " + action1);
			}

			try {
				if(action1 == KeyEvent.VK_RIGHT || action1 == KeyEvent.VK_KP_RIGHT) {			
					this.turnRight();
				} else if(action1 == KeyEvent.VK_LEFT || action1 == KeyEvent.VK_KP_LEFT) {
					this.turnLeft();
				} else if(action1 == KeyEvent.VK_UP) {
					this.goForward();
				} 
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			scan.close();
			return;
		}
		else{
			itemID = scan.nextInt();
			state1 = scan.next().trim();


			//ITEM STATE
			if(state1.equalsIgnoreCase("true")){
				state = ItemState.TRUE;
			}
			else if(state1.equalsIgnoreCase("false")){
				state = ItemState.FALSE;
			}
			else if(state1.equalsIgnoreCase("default")){
				state = ItemState.DEFAULT;
			}

			if(token.equalsIgnoreCase("pickup")){
				//call appropriate method here!
				System.out.println("Read:"+token+" playerID:"+ playerID + " itemID:"+itemID+ " ItemState:"+state1 );
				Item item = itemManager.getItemWithIdAndState(itemID, state);
				this.pickUpItem(item.convertToItemNode());
			}

			else if(token.equalsIgnoreCase("drop")){
				//call appropriate method here!
				System.out.println("Read:"+token+" playerID:"+ uid + " itemID:"+itemID+ " ItemState:"+state1 );
				Item item = itemManager.getItemWithIdAndState(itemID, state);
				this.dropItem(item.convertToItemNode());
			}

			else if (token.equalsIgnoreCase("update")){
				//call appropriate method here!
				System.out.println("Read:"+token+" playerID:"+ uid + " itemID:"+itemID+ " ItemState:"+state1 );
				Item item = itemManager.getItemWithIdAndState(itemID, state);
				this.updateItem(item.convertToItemNode());
			}
		}

		scan.close();
		return;
	}



	@Override
	public void businessOpenPanel() {
		
	}

}
