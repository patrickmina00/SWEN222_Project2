package main;

import gameworld.Player;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import master.Server;
import ui.GameFrame;
import ui.TitleScreen;
import client.Client;
import client.ClockThread;
import controllers.SinglePlayer;
import datastorage.Game;
import datastorage.Game.GameMode;
import datastorage.savemanager.GameStateReader.GameStateReader;
import datastorage.savemanager.GameStateReader.GameStateReaderImpl;

/**
 * This is responsible for starting a new game.
 * Single or Multiplayer
 * 
 * Inspired by Pacman Client code - djp
 * 
 * @co-author Ryan Ebue
 * @co-author Patrick Mina
 * 
 */

public class Main {
	private static final int DEFAULT_CLK_PERIOD = 1;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private static Game game;
	private static int uid = 0;

	//Getting booleans from title page
	private static boolean playerPicked = false;
	private static boolean isSinglePlayer;
	private static boolean isMultiPlayer;
	private static boolean isJoining;
	private static boolean isLoading;


	public static void main(String[] args) {
		int nclients = 2;		
		String url = "localhost";		
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 6066; // default
		boolean server = false;
		String x = null;


		TitleScreen myTitle  = new TitleScreen();
		myTitle.setVisible(true);


		//Make sure player picks a button!

		while(playerPicked !=  true){
			//keep looping here till player has picked
			playerPicked = myTitle.getPlayerPicked();
			/**
			 * Game breaks without this println - DO NOT REMOVE
			 */
			
			System.out.println("Player picked: "+playerPicked);
		}

		if(playerPicked == true ){

			isSinglePlayer = myTitle.getIsSinglePlayer();
			isMultiPlayer = myTitle.getIsMultiPlayer();
			isJoining = myTitle.getIsJoining();
			isLoading = myTitle.getIsLoading();

			//Single Player
			if(isSinglePlayer){
				x = "-single";
			}

			//Multi-player
			if(isMultiPlayer){
				x = "-server";
			}

			//Joining URL
			if(isJoining){
				x = "-connect localhost";
			}

			if(isLoading){		
				GameStateReader gameStateReader = new GameStateReaderImpl();
				try {
					String loadPath = myTitle.getPathToLoadFrom();
					if( loadPath== null || loadPath.equals("")){
						System.out.println("exited");
						System.exit(0);
					}

					Game gs = gameStateReader.readGameFromSave(loadPath);

					if(gs.getMode() == GameMode.SINGLE){
						loadedSingleUserGame(gameClock, gs);
					}
					else{
						System.out.println("Load Multiplayer game not possible!");
						System.exit(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}



		//Check what arguments are to be read

		if(x.equals("-server")){
			server = true;
		}

		else if(x.equals("-single")){
			game = new Game(Game.GameMode.SINGLE);
			try {
				singleUserGame(gameClock, game);
			} catch (IOException e) {
				e.printStackTrace();
			}							

		}

		try {
			if(server) {
				// Run in Server mode
				game = new Game(Game.GameMode.MULTIPLAYER);
				runServer(port,nclients,gameClock,broadcastClock, game);			
			} else if(url != null) {
				// Run in client mode
				runClient(url,port);
			}
		} catch(IOException ioe) {			
			System.out.println("I/O error: " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}

		System.exit(0);

	}

	/**
	 * Attempts to connect a client by opening a new socket
	 * Creates a new client and connects it with the socket
	 * 
	 * @param addr
	 * @param port
	 * @throws IOException
	 */
	private static void runClient(String addr, int port) throws IOException {
		System.out.println("Attempt to connect a client to " + addr + " port: " + port );
		Socket s = new Socket(addr,port);
		System.out.println("BALLOUT CLIENT CONNECTED TO " + addr + ":" + port);
		new Client(s).run();		
	}

	/**
	 * Attempts to run a Server that will host clients
	 * Opens a server socket and will wait for clients
	 * 
	 * @param port
	 * @param nclients
	 * @param gameClock
	 * @param broadcastClock
	 * @param game
	 */
	private static void runServer(int port, int nclients, int gameClock, int broadcastClock, Game game) {	
		System.out.println("Attempt to runServer");

		ClockThread clk = new ClockThread(gameClock,game,null);	
		// Listen for connections
		System.out.println("BALLOUT SERVER LISTENING ON PORT " + port);
		System.out.println("BALLOUT SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Server[] connections = new Server[nclients];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);		
			System.out.println("This new server socket is now waiting: " + ss);
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				//Sends out an int to associate with the thing who has connected
				registerForMultiplayer(game);
				//Starts the threads
				connections[--nclients] = new Server(s,uid++,broadcastClock,game);
				connections[nclients].start();				
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					multiUserGame(clk,game,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return;
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}

	/**
	 * Registers the necessary players needed to run a multiplayer game
	 * 
	 * @param game
	 */
	private static void registerForMultiplayer(Game game){

		//limits to having only two player - p1 @ 0, p2 @ 1
		if(uid > 0){
			return;
		}
		//1
		game.registerPlayer();
		//2
		game.registerPlayer();

	}

	/**
	 * Controls a multiplayer game, will end when clients are disconnected
	 * 
	 * 
	 * @param clk
	 * @param game
	 * @param connections
	 * @throws IOException
	 */
	private static void multiUserGame(ClockThread clk, Game game,
			Server... connections) throws IOException {
		System.out.println("Attempt a multi user game");

		clk.start(); // start the clock ticking!!!				

		// loop forever
		while(atleastOneConnection(connections)) {

			pause(3000);

			pause(3000);		
		}
	}

	/**
	 * Attempts to run a single player game
	 * 
	 * @param gameClock
	 * @param game
	 * @throws IOException
	 */
	private static void singleUserGame(int gameClock, Game game) throws IOException {
		Player player = game.registerPlayer();

		SinglePlayer singlePlayer = new SinglePlayer(game, player.getID());

		GameFrame gameFrame = new GameFrame(game.getGameState(), player.getID(), singlePlayer);
		gameFrame.setVisible(true);

		ClockThread clk = new ClockThread(gameClock, game, gameFrame);		
		clk.start(); // start the clock ticking!!!		

		while(1==1) {
			pause(3000);

			// If we get here, then we're in game over mode
			pause(3000);
		}
	}

	/**
	 * Runs a single player game from a save file
	 * 
	 * @param gameClock
	 * @param game
	 * @throws IOException
	 */
	private static void loadedSingleUserGame(int gameClock, Game game) throws IOException {	
		Player p = game.getPlayers().get(0);

		SinglePlayer singlePlayer = new SinglePlayer(game, p.getID());

		GameFrame gameFrame = new GameFrame(game.getGameState(), p.getID(), singlePlayer);
		gameFrame.setVisible(true);

		ClockThread clk = new ClockThread(gameClock, game, gameFrame);		
		clk.start(); // start the clock ticking!!!		

		while(1==1) {
			pause(3000);

			// If we get here, then we're in game over mode
			pause(3000);
			// Reset board state
		}
	}


	/**
	 * Checks for the connections - returns a boolean
	 * If 1 is alive - returns true otherwise false
	 * 
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(Server... connections) {
		for (Server s : connections) {
			if (s.isAlive()) {
				return true;
			}			
		}
		return false;
	}

	private static void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch(InterruptedException e){			
		}
	}
}
