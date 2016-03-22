package client;

import ui.GameFrame;
import datastorage.Game;
import datastorage.GameState;


/**
 * The Clock Thread is responsible for constantly updating the game state
 * as well as refresh the game display
 * 
 * @co-author Ryan Ebue
 * @co-author Patrick Mina
 * 
 */
public class ClockThread extends Thread {
	private final int wait; //The wait time between ticks of the thread 
	private final Game game;
	private final GameFrame gameFrame;
	private GameState gState1;
	private GameState gState2;
	private boolean firstTime = true;
	/**
	 * @param wait
	 * @param game
	 * @param gameFrame
	 * Constructs the ClockThread
	 */
	public ClockThread(int wait, Game game, GameFrame gameFrame) {
		this.wait = wait;
		this.game = game;
		this.gameFrame = gameFrame;
	}

	public void run() {
		System.out.println("Run the clockThread");
		while(1 == 1) {
			// Loop forever			
			try {
				Thread.sleep(wait);

				game.clockTick();
				//Repaint and update when something different happens with the gameState
				//For single player
				if(gameFrame != null) {
					GameState gs = game.getGameState();
					gameFrame.updateGameState(gs);
					gameFrame.repaint();

				}
			} catch(InterruptedException e) {
			}			
		}
	}
}
