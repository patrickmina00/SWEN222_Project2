package ui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Patrick
 *
 */
public class TitleScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	//initial state is singlePlayer mode
	private static boolean isSinglePlayer = true;
	private static boolean isMultiPlayer = false;
	private static boolean isJoining = false;
	private static boolean playerPicked = false;
	private static boolean loadGame = false;
	private String filePath;



	/**
	 * TitleScreen is responsible for showing buttons and obtaining
	 * the buttons the user has chosen. It changes the field values
	 * upon button press and passes them to main.
	 */
	public TitleScreen(){
		super("Ball Out");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 710);

		//Create A JPanel
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		//Created the buttons
		JButton singlePlayer = new JButton("Single Player");
		JButton startServer = new JButton("Start Server");
		JButton instructions = new JButton ("Instructions");
		JButton loadButton = new JButton("Load Single Player Game");
		JButton join = new JButton("Join Server");

		//Add buttons to panel
		panel.add(singlePlayer);
		panel.add(startServer);
		panel.add(loadButton);
		panel.add(join);
		panel.add(instructions);


		//Add action listener on buttons
		singlePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TitleScreen.isSinglePlayer = true;
				TitleScreen.playerPicked = true;
				System.out.println("SinglePlayer is set to: " +TitleScreen.isSinglePlayer);
				dispose();
			}
		});

		startServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TitleScreen.isMultiPlayer = true;
				TitleScreen.playerPicked = true;
				//Just to make sure;
				isSinglePlayer = false;
				isJoining = false;
	
				System.out.println("SinglePlayer is set to: " +TitleScreen.isSinglePlayer);
				System.out.println("Awaiting players!");
				dispose();
			}
		});

		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
			
			JFrame frame = new JFrame();
			 JOptionPane.showMessageDialog(frame,instructions());
			}
		});

		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Join Pressed");
				TitleScreen.playerPicked = true;
				TitleScreen.isJoining = true;
				TitleScreen.isMultiPlayer = false;
				TitleScreen.isSinglePlayer = false;
				
				//This part is further functionality. Will skip for now since prototype game
//				JFrame okFrame = new JFrame("Input URL");
//				TitleScreen.url = JOptionPane.showInputDialog(okFrame, "Connect to: ");
				dispose();
			}
		});
		
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TitleScreen.playerPicked = true;
				TitleScreen.isJoining = false;
				TitleScreen.isMultiPlayer = false;
				TitleScreen.isSinglePlayer = false;
				TitleScreen.loadGame = true;
				dispose();
			}
		});
		this.add(panel);
	}

	
	/**
	 * Returns true if a a player picked load game
	 * @return loadGame
	 */
	public boolean getIsLoading(){
		return this.loadGame;
	}

	/**
	 * Returns true if a player has picked a button!
	 * @return
	 */
	public boolean getPlayerPicked(){
		return this.playerPicked;
	}
	
	
	/**
	 * Used to start single player mode
	 * @return
	 */
	public boolean getIsSinglePlayer(){
		return this.isSinglePlayer;
	}
	
	
	/**
	 * Used to start a multiplayer game
	 * @return
	 */
	public boolean getIsMultiPlayer(){
		return this.isMultiPlayer;
	}
	
	/**
	 * Initiates a connection to localhost
	 * @return
	 */
	public boolean getIsJoining(){
		return this.isJoining;
	}
	

	/**
	 * Returns the url to connect to
	 * > Usually just input "localhost"
	 * @return
	 */
	public String getFilePath(){
		return this.filePath;
	}
	
	
	/**
	 * Sets the filepath field
	 * @param filePath
	 */
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	
	
	/**
	 * Adds the text to the joptionpane to display instructions
	 * @return
	 */
	public String instructions(){
		String y = "INSTRUCTIONS: \n"
				+ "The goal of the game is to be able to break out of the current room by solving puzzles. \n"
				+ "This involves activating levers, picking up items, using the arrow keys to rotate \n"
				+ "the world and finally being able to break out!"
				+ "\n"
				+ "\n"
				+ "MULTI-PLAYER GAME: \n"
				+ "To play multi-player: \n"
				+ "1) Press Start Server \n"
				+ "2) Run main again then on the title screen press \"join game\" to start player 1 \n" 
				+ "3) Run main again and press \"join game\" to start player 2."
				+ "\n ENJOY!";
					
		return y;
	}
	
	
	/**
	 * Prompts to select an xml save file to load from
	 * @return
	 * @throws Exception
	 */
	public String getPathToLoadFrom() throws Exception{
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("C://"));	
		//opens at C drive
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		chooser.setDialogTitle("Select a Ball Out save file");

		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
		chooser.setFileFilter(xmlfilter);

		File f = null;

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			f = chooser.getSelectedFile();
		} else { 
			throw new Exception("No File Selected");
		}

		if(f!=null) {
			return f.getAbsolutePath(); //path we want to load from
		} else {
			throw new Exception("An error ocurred while trying to find load path");
		}
	}	
}

