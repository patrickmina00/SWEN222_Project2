package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;

import nodes.BasicNode;
import parser.GsParser;
import ui.GameFrame;
import controllers.Controller;
import datastorage.GameState;

/**
 * The Client is responsible for creating a client
 * as well as connecting it to a server
 * 
 * Inspired by Pacman Client code - djp
 * 
 * @ co-author Ryan Ebue
 * @ co-author Patrick Mina
 * 
 */

public class Client extends JFrame implements KeyListener, Controller{
	private final Socket socket;
	private int uid;
	private static DataOutputStream out;
	private static DataInputStream in;
	private GameFrame gameFrame;

	/**
	 * @param socket
	 * Constructs the client 
	 * Given a socket that it will try to connect to.
	 * 
	 */
	public Client(Socket socket){
		this.socket = socket;
	}

	/**
	 * Runs the client, connects to a socket (if open)
	 * Will continually communicate with the socket
	 * 
	 */
	public void run(){
		String serverName = "localhost";

		try
		{
			boolean exit = false;
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
			
			//Setup the streams for passing data
			OutputStream outToServer = socket.getOutputStream();
			out = new DataOutputStream(outToServer);
			InputStream inFromServer = socket.getInputStream();
			in = new DataInputStream(inFromServer);

			uid = in.readInt();
			System.out.println("I am player " + uid);

			String expr = in.readUTF();
			GameState gs = GsParser.generateGameState(expr);
			
			Controller controller = this;
			System.out.println("Client UID: " + uid);
			gameFrame = new GameFrame(gs, uid, controller);
			gameFrame.setVisible(true);


			while(!exit){
				try{
					//Read inputs from server
					expr = in.readUTF();
					//Generate a gamestate expression
					gs = GsParser.generateGameState(expr);
					//Update game board
					gameFrame.updateGameState(gs);
					gameFrame.repaint();
					out.flush();
				}
				catch(SocketException s){
					System.out.println("SocketException: " + s.getMessage());
					out.close();
					in.close();
					break;
				}
			}
			socket.close(); // release socket ... very important!
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param action
	 * sends an action downstream to the server
	 */
	public void sendAction(String action){

		try {
			out.writeUTF(action);
			out.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		sendAction("Move " + uid + " " + e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void dropItem(BasicNode basicNode) {
		sendAction("drop " + uid + " " + basicNode.getId() + " " + basicNode.getState());

	}

	@Override
	public void pickUpItem(BasicNode basicNode) {
		sendAction("pickup " + uid + " " + basicNode.getId() + " " + basicNode.getState());
	}

	@Override
	public void updateItem(BasicNode basicNode) {
		sendAction("update " + uid + " " + basicNode.getId() + " " + basicNode.getState());
	}

	@Override
	public void win() {
		sendAction("win");
	}



	@Override
	public void openPanel() {
		sendAction("openpanel");
	}

	@Override
	public void privateMessage(String s) {
		
	}
}
