package com.main;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.area.Area;
import com.area.AreaType;
import com.ship.ShipModel;
import com.ship.ShipType;

import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.UIManager;



public class MainFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	// Boolean stuff
	private boolean isPlacing = false;
	private static boolean isShaking = false;
	private static boolean isHitting = false;
	private static int selectedShipModel = 0;
	
	// Related arrays
	private static Area compArray[][] = new Area[8][8];
	private static Area userArray[][] = new Area[8][8];
	
	private static Random ran = new Random();
	
	// Ship model related
	private static ShipModel[] modelArray = new ShipModel[10];
	private static ArrayList<Integer> compUsedModels = new ArrayList<>();
	
	// Components
	private static JLabel infoLabel;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
					// Fill area arrays' types
					for (int p = 0; p < 8; p++){
						for (int q = 0; q < 8; q++){
							userArray[p][q].setType(AreaType.BLANK);
							compArray[p][q].setType(AreaType.BLANK);
						}
					}
					
					infoLabel.setText("Computer has placed their ships, you're placing now!");
					
					updateButtons(compArray, "disable");
					
					// Computer places ship
					while (countType(compArray, AreaType.SHIP) != 14){
						
						// Choose ship model
						int sm = ran.nextInt(compArray.length) + 2;
						if (sm < 6){ // For horizontal ships
							if (checkShipModel(sm)){
								// Restart if ship type is already placed
								continue;
							}
						} else { // For vertical ships
							if (checkShipModel((sm - 4))){
								// Restart if ship type is already placed
								continue;
							}
						}
						
						// Choose random coordinates
						int rx, ry;
						if (sm < 6){
							rx = ran.nextInt((compArray.length + 1) - sm);
							ry = ran.nextInt(compArray.length);
						} else {
							rx = ran.nextInt(compArray.length);
							ry = ran.nextInt((compArray.length + 1) - (sm - 4));
						}
						
						// Are chosen coordinates not out of bounds or occupied ?
						if (isSafeToPlace(compArray, compArray[ry][rx], sm)){
							if (sm < 6) {
								for (int p = 0; p < sm; p++){
									compArray[ry][rx + p].setType(AreaType.SHIP);
								}
							} else {
								for (int p = 0; p < (sm - 4); p++){
									compArray[ry + p][rx].setType(AreaType.SHIP);
								}
							}
							compUsedModels.add(sm);
						}
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		// Adjust how the frame looks
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch ( Exception e) {
			e.printStackTrace();}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 928);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		try {
			JLabel labelComputer = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/labelComputer.png"))));
			labelComputer.setBounds(747, 7, 256, 32);
			contentPane.add(labelComputer);
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			JLabel labelUser = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/labelUser.png"))));
			labelUser.setBounds(175, 7, 256, 32);
			contentPane.add(labelUser);
		} catch (Exception e) {e.printStackTrace();}
		
		// A label that displays information related to the state of the game
		infoLabel = new JLabel("Information Label", SwingConstants.CENTER);
		infoLabel.setFont(new Font("Verdana", Font.BOLD, 32));
		infoLabel.setToolTipText("Status of the game will be displayed here");
		infoLabel.setBounds(90, 580, 1000, 48);
		contentPane.add(infoLabel);
		
		// Build shipModelArray
		for (int p = 1; p < 10; p++){
			ShipModel sm = new ShipModel();
			try { // try-catch model for IO exceptions
				
				// Add MouseListeners
				if (p == 1){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 2;
							}
						}
					});
				} else if (p == 2){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 3;
							}
						}
					});
				} else if (p == 3){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 4;
							}
						}
					});
				} else if (p == 4){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 5;
							}
						}
					});
				} else if (p == 5){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 6;
							}
						}
					});
				} else if (p == 6){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 7;
							}
						}
					});
				} else if (p == 7){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 8;
							}
						}
					});
				} else if (p == 8){
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								selectedShipModel = 9;
							}
						}
					});
				} 
				
				// First model types only should be initially visible
				if (!((p == 1) || (p == 5))){
					sm.setVisible(false);
				}
				
				// Determine shipModel bounds
				if (p < 5){
					sm.setBounds((2 + (p * 45)), 635, (32 + (p * 32)), 32);
				} else if (p != 9) {
					sm.setBounds((2 + ((p - 4) * 45)), 679, 32, (32 + ((p - 4) * 32)));}
				
				// Assign shipModel icon
				if (p != 9){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel" + (p + 1) + ".png"))));
				}
			} catch (IOException e) {}
			
			modelArray[p] = sm;
			contentPane.add(sm);
		}
		
		// Initialise user button array
		for (int a = 0; a < 8; a++){
			for (int b = 0; b < 8; b++){
				Area area = new Area("");
				area.setLocation((((64 * b) + 47)), ((64 * a) + 48));
				area.setXIndex(a);
				area.setYIndex(b);
				
				// Assign an image to the JButton
				area.changeIcon(AreaType.BLANK);
				
				// Add listeners
				area.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseEntered(MouseEvent arg0){
						
						// Render the guideline for ship placement
						if (isPlacing && isSafeToPlace(userArray, area, selectedShipModel)){
							if (selectedShipModel < 6){
								for (int p = 0; p < selectedShipModel; p++){
									// Determine the type of current ship piece
									if (p == 0){
										userArray[area.getXIndex()][area.getYIndex() + p].changeIcon(ShipType.LEFT);
									} else if (p == (selectedShipModel - 1)) {
										userArray[area.getXIndex()][area.getYIndex() + p].changeIcon(ShipType.RIGHT);
									} else {
										userArray[area.getXIndex()][area.getYIndex() + p].changeIcon(ShipType.XCENTER);
									}
								}
							}
							else {
								for (int p = 0; p < (selectedShipModel - 4); p++){
									// Determine the type of current ship piece
									if (p == 0){
											userArray[area.getXIndex() + p][area.getYIndex()].changeIcon(ShipType.UP);
										} else if (p == (selectedShipModel - 5)) {
											userArray[area.getXIndex() + p][area.getYIndex()].changeIcon(ShipType.DOWN);
										} else {
											userArray[area.getXIndex() + p][area.getYIndex()].changeIcon(ShipType.YCENTER);
										}
								}
							}
						}
					
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {

						// Erase the guideline
						if (isPlacing){
							if (isSafeToPlace(userArray, area, selectedShipModel)){
								if (selectedShipModel < 6){
									for (int p = 0; p < selectedShipModel; p++){
										userArray[area.getXIndex()][area.getYIndex() + p].changeIcon(AreaType.BLANK);
									}
								}
								else {
									for (int p = 0; p < (selectedShipModel - 4); p++){
										userArray[area.getXIndex() + p][area.getYIndex()].changeIcon(AreaType.BLANK);
									}
								}
							}
						}
					}
					
					@Override
					public void mousePressed(MouseEvent arg0){
						
						// If ship placement is successful, register AreaType
						if (isPlacing){
							if (isSafeToPlace(userArray, area, selectedShipModel)){
								isPlacing = false;
								if (selectedShipModel < 6){
									for (int p = 0; p < selectedShipModel; p++){
										userArray[area.getXIndex()][area.getYIndex() + p].setType(AreaType.SHIP);
									}
								}
								else {
									for (int p = 0; p < (selectedShipModel - 4); p++){
										userArray[area.getXIndex() + p][area.getYIndex()].setType(AreaType.SHIP);
									}
								}
								
								// Disable picked option, enable next ship models to be placed
								// Debug: System.out.println(shipModel);
								if (selectedShipModel < 6){
									modelArray[selectedShipModel - 1].setVisible(false);
									modelArray[selectedShipModel + 3].setVisible(false);
									if (selectedShipModel != 5){ // Condition so that the previous models won't re-appear
										modelArray[selectedShipModel].setVisible(true);
									}
									modelArray[selectedShipModel + 4].setVisible(true);
								} else {
									modelArray[selectedShipModel - 1].setVisible(false);
									modelArray[selectedShipModel - 5].setVisible(false);
									modelArray[selectedShipModel].setVisible(true);
									if (selectedShipModel != 9){ // Condition so that the previous models won't re-appear
										modelArray[selectedShipModel - 4].setVisible(true);
									}
								}
								
								// If it's the last ship to be placed, initiate computer
								if (selectedShipModel == 9 || selectedShipModel == 5){
									hit();
								}
							} else {
								// Shake the label to get attention
								if (infoLabel.getText().equals("You can't place a ship there!") && !isShaking){
									shakeComponent(infoLabel);
								} else {
								infoLabel.setText("You can't place a ship there!");
								}
							}
						}
					}
				});
				area.addActionListener(this);
				
				// Add to array and frame
				userArray[a][b] = area;
				contentPane.add(area);
				
			}
		}
		
		// Initialise computer button array
		for (int a = 0; a < 8; a++){
			for (int b = 0; b < 8; b++){
				Area area = new Area("");
				area.setLocation((1114 - ((64 * b) + 47)), ((64 * a) + 48));
				area.setXIndex(a); // Register indexes
				area.setYIndex(b);
				
				// Assign an image to the JButton
				area.changeIcon(AreaType.BLANK);
				
				// Add action listener
				area.addActionListener(this);
				
				// Add to array and frame
				compArray[a][b] = area;
				contentPane.add(area);
			}
		}
	}

	public void setPlacing(boolean isPlacing) {
		this.isPlacing = isPlacing;
	}
	public static void setSelectedShipModel(int selectedShipModel) {
		MainFrame.selectedShipModel = selectedShipModel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (isHitting){
			
			// Go through button array and check source and state then act accordingly
			for (int p = 0; p < userArray.length; p++){
				for (int q = 0; q < userArray.length; q++){
					if (arg0.getSource() == compArray[p][q]) {
						
						// If button is a hit or a miss
						if (compArray[p][q].getType() == AreaType.MISS || compArray[p][q].getType() == AreaType.HIT){
							// Shake the label to get attention
							if (infoLabel.getText().equals("You've hit that area already!") && !isShaking){
								shakeComponent(infoLabel);
							} else {
							infoLabel.setText("You've hit that area already!");}
						}
						
						// If button is a blank
						else if (compArray[p][q].getType() == AreaType.BLANK) {
							infoLabel.setText("You missed!");
							compArray[p][q].changeIcon(AreaType.MISS);
							compArray[p][q].setType(AreaType.MISS);
							isHitting = false;
							hit(); // Trigger SwingWorker
						}
						
						// If button is a ship
						else if (compArray[p][q].getType() == AreaType.SHIP){
							infoLabel.setText("You've hit a ship!");
							compArray[p][q].changeIcon(AreaType.HIT);
							compArray[p][q].setType(AreaType.HIT);
							
							// Winning condition
							checkWinner();
						}
					}
				}
			}
		}
	}
	
	public static void hit(){
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				// Winning condition
				checkWinner();
				
				if (!isHitting){
					Thread.sleep(1000);
					updateButtons(compArray, "disable");
					Thread.sleep(500);infoLabel.setText("Computer is deciding");
					Thread.sleep(500);infoLabel.setText("Computer is deciding.");
					Thread.sleep(500);infoLabel.setText("Computer is deciding..");
					Thread.sleep(500);infoLabel.setText("Computer is deciding...");
					int rx = ran.nextInt(8);
					int ry = ran.nextInt(8);
					
					// This code segment makes it so that the computer won't hit an already hit spot.
					while (userArray[rx][ry].getType() == AreaType.MISS || userArray[rx][ry].getType() == AreaType.HIT){
						rx = ran.nextInt(8);
						ry = ran.nextInt(8);}
					
					// Computer missed
					if (userArray[rx][ry].getType() == AreaType.BLANK){
						userArray[rx][ry].changeIcon(AreaType.MISS);
						userArray[rx][ry].setType(AreaType.MISS);
						infoLabel.setText("Computer missed!");
						isHitting = true;}
					
					// Computer hit a ship
					if (userArray[rx][ry].getType() == AreaType.SHIP){
						userArray[rx][ry].changeIcon(AreaType.HIT);
						infoLabel.setText("Computer hit one of your ships!");
						userArray[rx][ry].setType(AreaType.HIT);
						doInBackground();
						}
					
					Thread.sleep(500);
					updateButtons(compArray, "enable");
				}
				return null;
			}
		};
		
		// Start the SwingWorker
		sw.execute();
		
	}

	public static void updateButtons(JButton[][] array, String statement){
		if (statement.equals("disable")){
			for (int p = 0; p < array.length; p++){
				for (int q = 0; q < array.length; q++){
					array[p][q].setEnabled(false);
				}
			}
		}
		else {
			for (int p = 0; p < array.length; p++){
				for (int q = 0; q < array.length; q++){
					array[p][q].setEnabled(true);
				}
			}
		}
	}

	public static int countType(Area[][] array, AreaType type){
		int count = 0;
		for (int p = 0; p < array.length; p++){
			for (int q = 0; q < array.length; q++){
				if (array[p][q].getType() == type){
					count++;
				}
			}
		}
		return count;
	}

	public static boolean isSafeToPlace(Area[][] array, Area area, int shipModel){
		boolean result = true;
		if (shipModel < 6){
			for (int p = 0; p < shipModel; p++){
				if ((area.getYIndex() + p > (array.length - 1)) || array[area.getXIndex()][area.getYIndex() + p].getType() == AreaType.SHIP){
					result = false;
				}
			}
		} else {
			for (int p = 0; p < (shipModel - 4); p++){
				if ((area.getXIndex() + p > (array.length - 1)) || array[area.getXIndex() + p][area.getYIndex()].getType() == AreaType.SHIP){
					result = false;
				}
			}
		}
		return result;
	}
	
	public static void revealShips(Area[][] array){
		for (int p = 0; p < array.length; p++){
			for (int q = 0; q < array.length; q++){
				if (array[p][q].getType() == AreaType.SHIP){
					System.out.println(p + ", " + q);
					array[p][q].changeIcon(AreaType.HIT);
				}
			}
		}
	}

	public static boolean checkShipModel(int shipModel){
		boolean result = false;
		for (int p : compUsedModels) {
			if ((shipModel == p) || (shipModel == (p - 4))){
				result = true;
			}
		}
		return result;
	}
	
	public static void checkWinner(){
		if (countType(userArray, AreaType.HIT) == 14){
			JOptionPane.showMessageDialog(null, "Computer wins!");
			infoLabel.setText("Computer won!");
			updateButtons(compArray, "disable");
			updateButtons(userArray, "disable");
			isHitting = true;
		} else if (countType(compArray, AreaType.HIT) == 14){
			JOptionPane.showMessageDialog(null, "User wins!");
			infoLabel.setText("User won!");
			updateButtons(compArray, "disable");
			updateButtons(userArray, "disable");
			isHitting = true;}
	}
	

	private void shakeComponent(Component component) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				final Point point = component.getLocation();
				isShaking = true;
				for (int i = 0; i < 10; i++) {
					
					try {
						moveComponent(new Point((point.x + 5), (point.y)), component);
						Thread.sleep(15);
						moveComponent(point, component);
						Thread.sleep(15);
						moveComponent(new Point((point.x - 5), (point.y)), component);
						Thread.sleep(15);
						moveComponent(point, component);
						Thread.sleep(15);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				
				isShaking = false;
				return null;
			}
			
		};
		
		sw.execute();
	 }

	private void moveComponent(final Point p, Component component) {
		component.setLocation(p);
	 }
}

