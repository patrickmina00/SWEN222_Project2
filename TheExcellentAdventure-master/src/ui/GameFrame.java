package ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controllers.Controller;
import datastorage.GameState;
import datastorage.savemanager.GameStateWriter.GameStateWriter;
import datastorage.savemanager.GameStateWriter.GameStateWriterImpl;
import assets.images.items.InventoryImageUtility;

public class GameFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private ContentPanel contentPanel;
	private JTextArea gmTextArea;
	private GameState gameState = null;
	private GamePanel gmGamePanel;
	private InventoryPanel gmInventoryPanel;
	private EndPanel gmEndPanel;
	private boolean exit = false;

	private int playerId;
	private JLabel gmLabelInventory;
	private JScrollPane gmScrollPane;
	private JMenuBar gmMenuBar;
	/**
	 * Create the frame.
	 */
	public GameFrame(GameState gameState, int id, Controller controller) {

		super("Ball Out");

		playerId = id;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 710);
		contentPanel = new ContentPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		setContentPane(contentPanel);

		contentPanel.addKeyListener(controller);

		contentPanel.setFocusable(true);

		this.gameState = gameState;
		this.setupGameModeInterface();

		try{
			this.gmGamePanel.setGame(gameState, controller, id);
			this.gmInventoryPanel.setGame(gameState, controller, id);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

	public void updateGameState(GameState gState) {
		if(gState.isGameRunning()){
			this.gameState = gState;
			this.gmGamePanel.updateGameState(gState);
			this.gmInventoryPanel.updateGameState(gState);
			updateMessage(gState);
		}
		else{

			this.gmGamePanel.setVisible(false);
			this.gmTextArea.setVisible(false);
			this.gmLabelInventory.setVisible(false);
			this.gmScrollPane.setVisible(false);
			this.gmInventoryPanel.setVisible(false);
			this.gmMenuBar.setVisible(false);

			setupEndGamePanel();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////GAME MODE INTERFACE SETUP////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setupGameModeInterface(){

		this.setupGameModeMenu();
		this.setupGameModeGamePanel();
		this.setupGameModeInventoryPanel();
		this.setupGameModeTextFeedPanel();

	}

	/**
	 * Setup the menu for the game mode interface.
	 * 
	 * Making the drop down menu 'Game' with items; 'Save Game' and 'Quit'
	 */
	private void setupGameModeMenu(){

		gmMenuBar = new JMenuBar();
		this.setJMenuBar(gmMenuBar);

		JMenu gmGameMenu = new JMenu("Game");
		gmMenuBar.add(gmGameMenu);

		JMenuItem gmMenuItemSaveGame = new JMenuItem("Save Game");
		gmGameMenu.add(gmMenuItemSaveGame);
		gmMenuItemSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String pathToSaveTo = getPathToSaveTo();

					GameStateWriter gsWriter = new GameStateWriterImpl();

					gsWriter.writeGameStateToPath(gameState, pathToSaveTo);

					gmTextArea.append("\n"+"Game saved to: " + pathToSaveTo);

				} catch (Exception e) {
					gmTextArea.append("\n"+e.getMessage());
				}
			}
		});

		JMenuItem gmMenuItemQuit = new JMenuItem("Quit");
		gmMenuItemQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		gmGameMenu.add(gmMenuItemQuit);

	}

	/**
	 * Setup the game panel for the game mode interface
	 */
	private void setupGameModeGamePanel(){

		gmGamePanel = new GamePanel();
		gmGamePanel.setBounds(10, 10, 582, 522);
		gmGamePanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.add(gmGamePanel);

	}

	/**
	 * Setup the end panel for the end game scenario
	 */
	private void setupEndGamePanel(){

		gmEndPanel = new EndPanel();
		gmEndPanel.setBounds(10, 10, 592, 532);
		gmEndPanel.setBackground(Color.LIGHT_GRAY);
		gmEndPanel.setVisible(true);
		contentPanel.add(gmEndPanel);

		//Exit
		JButton exitButton = new JButton("Exit");
		this.add(exitButton);
		exitButton.setBounds(610, 540, 125, 35);

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exit here
				exit = true;
				runConditional();
			}
			/**Runs certain types of conditional messages depending
			 * on the button pressed
			 * 
			 */
			private void runConditional(){

				//Check for exit
				if(exit == true){
					if (JOptionPane.showConfirmDialog(null, "Are you sure you want to EXIT?", null,
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						setVisible(false);
						dispose();
					}
					else{
						exit = false;
					}
				}
			}
		});

	}

	/**
	 * Setup the inventory panel for the game mode interface
	 */
	private void setupGameModeInventoryPanel(){

		gmLabelInventory = new JLabel(new ImageIcon(InventoryImageUtility.getInventoryImage(-2)));
		gmLabelInventory.setHorizontalAlignment(SwingConstants.CENTER);
		gmLabelInventory.setBounds(610, 10, 125, 23);
		contentPanel.add(gmLabelInventory);

		gmInventoryPanel = new InventoryPanel();
		gmInventoryPanel.setBounds(610, 32, 125, 500);
		gmInventoryPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.add(gmInventoryPanel);

	}

	/**
	 * Setup the text feed panel for the game mode interface.
	 * 
	 * Making a text area set in a scroll view.
	 */
	private void setupGameModeTextFeedPanel(){

		gmTextArea = new JTextArea();
		gmTextArea.setBounds(10, 542, 580, 113);

		gmTextArea.setWrapStyleWord(true);
		gmTextArea.setEditable(false);
		gmTextArea.setLineWrap(true);

		gmScrollPane = new JScrollPane();
		gmScrollPane.setViewportView(gmTextArea);
		gmScrollPane.setBounds(10, 542, 580, 113);
		contentPanel.add(gmScrollPane);

	}

	private void updateMessage(GameState gState) {
//		System.out.println("DOES UPDATE MESSAGE GET CALLED?!?!?!?");
		String message = gState.getMessageNode().getMessage();
		if(message != null && !message.equals("null")){
			StringTokenizer tok = new StringTokenizer(message);
			if (tok.nextToken().equals(""+playerId)){
				String printMessage = "";
				while (tok.hasMoreTokens()){
					printMessage = printMessage + tok.nextToken() + " ";
				}
				printMessage = printMessage + "\n";
				
				String previousText = gmTextArea.getText();
				
				
				
				gmTextArea.setText(printMessage);
				
				gmTextArea.setForeground(Color.DARK_GRAY);
				gmTextArea.append(previousText);
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////SAVE & LOAD PROMPTS//////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * getPath is the method which opens the filechooser for the user to then locate the directory containing the save file.
	 * @throws Exception 
	 */
	public String getPathToSaveTo() throws Exception{
		//http://www.rgagnon.com/javadetails/java-0370.html
		//help from above

		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("C://"));	
		//opens at C drive
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//can only accept folders
		chooser.setAcceptAllFileFilterUsed(true);

		File f = null;

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			f = chooser.getSelectedFile();
		} else { 
			System.out.println("No Folder Selected");
		}

		if(f!=null) {
			return f.getAbsolutePath(); //path we want to load from
		} else {
			throw new Exception("An error ocurred while trying to find save path");
		}

	}

	/**
	 * Prompts to select an xml save file to load from
	 * 
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
