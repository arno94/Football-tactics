import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import items.DrawPanel;
import items.Item;
import items.PanelData;
import items.Path;
import items.PitchItem;

/**
 * 
 * @author Arno
 * The basic Frame. Thats what the user see.
 *
 */
public class WindowProperties extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final int WIDTH;
	protected final int HEIGHT;
	
	protected final int imagePlaceWidth;
	protected final int imagePlaceHeight;		
	
	protected final int panelTopButtonWidth = 130;
	protected final int panelTopButtonHeight = 28;	
	
	protected Font font;
	protected Font fontBold;
	
	protected PanelData panelLeftData;
	protected PanelData panelRightData;
	protected PanelData panelTopData;
	protected PanelData panelMiddleData;
	
	protected JLayeredPane panelLeft;
	protected JLayeredPane panelRight;
	protected JLayeredPane panelTop;
	protected JLayeredPane panelMiddle;
	
	protected JLayeredPane panelAlwaysTop;
	protected JLayeredPane panelBackground;
	protected JLayeredPane panelPlacedItems;
	protected JLayeredPane panelForItemPlace;
	protected JLayeredPane panelForDrawLines;
	protected DrawPanel panelDrawnLines;
	
	protected JLabel backgroundLabel;
	
	protected JComboBox cbSelectPitch;
	
	protected JButton buttonSave;
	protected JButton buttonLoad;
	protected JButton buttonReplace;
	protected JButton buttonDelete;
	protected JButton buttonClear;
	protected JButton buttonLineClear;
	protected JButton btnColorChoose;
	protected JButton buttonAnimate;
	
	private final String confPitchFilePath = "textures/conf_pitch.conf";
	private final String pitchFilePath = "textures/pitchs";		
	protected String imageToPlacePath;
	
	private Scanner scanner;
	
	protected ArrayList<Path> drawPaths;
	protected ArrayList<JComboBox> panelLeftCb;
	protected ArrayList<PitchItem> pitchItems;
	protected ArrayList<Item> panelLeftItems;
	
	protected boolean isMovingComponent;
	protected Object componentLastSelected;
	protected Component placedItemLastSelected;
	
	protected Color selectedDrawColor;	
	
	protected JCheckBox isDrawLineDashed;
	
	public WindowProperties(int width, int height, int imagePlaceWidth, int imagePlaceHeight) {
		setResizable(false);
		setTitle("Football");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		this.WIDTH = width + 5;
		this.HEIGHT = height - 15;
		this.imagePlaceWidth = imagePlaceWidth;
		this.imagePlaceHeight = imagePlaceHeight;
		
		panelLeftData = new PanelData(5, 5, 200, height - 60);		
		panelTopData = new PanelData(panelLeftData.getX() + panelLeftData.getWidth() + 5 , 5, width - 200 - 200 - 25, 32);
		panelMiddleData = new PanelData(panelLeftData.getX() + panelLeftData.getWidth() + 5, panelTopData.getY() + panelTopData.getHeight() , width - 200 - 200 - 20, height - panelTopData.getY() - panelTopData.getHeight() - 50 - 5);	
		panelMiddleData = new PanelData(210, 37, width - 210 - 210, height - panelTopData.getY() -  panelTopData.getHeight() - 50 - 5);
		panelRightData = new PanelData(panelMiddleData.getX() + panelMiddleData.getWidth() + 5, 5, 200, height - 60);		
		System.out.println(panelMiddleData.getHeight() + " " + panelMiddleData.getWidth());
		createPanels();
		initMiddleInnerPanels();
		addDefaultButtons();
		addLeftPanelButtons();
		
		setSize(WIDTH , HEIGHT);		
	    setLocationRelativeTo(null);
	    getContentPane().setLayout(null);
	}
	
	private void createPanels() {
		
		// Create left panel
		panelLeft = new JLayeredPane();
		panelLeft.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelLeft.setBounds(panelLeftData.getX(), panelLeftData.getY(), panelLeftData.getWidth(), panelLeftData.getHeight());	    
	    
	    GridBagLayout gbl_panel_left = new GridBagLayout();
	    gbl_panel_left.columnWidths = new int[]{0};
	    gbl_panel_left.rowHeights = new int[]{0};
	    gbl_panel_left.columnWeights = new double[]{Double.MIN_VALUE};
	    gbl_panel_left.rowWeights = new double[]{Double.MIN_VALUE};
	    panelLeft.setLayout(gbl_panel_left);	    
	    getContentPane().add(panelLeft);
	    
	    // Create right panel
	    panelRight = new JLayeredPane();
	    panelRight.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    panelRight.setBounds(panelRightData.getX(), panelRightData.getY(), panelRightData.getWidth(), panelRightData.getHeight());
	    
	    GridBagLayout gbl_panel_right = new GridBagLayout();
	    gbl_panel_right.columnWidths = new int[]{0};
	    gbl_panel_right.rowHeights = new int[]{0};
	    gbl_panel_right.columnWeights = new double[]{Double.MIN_VALUE};
	    gbl_panel_right.rowWeights = new double[]{Double.MIN_VALUE};
	    panelRight.setLayout(gbl_panel_right);		    	    
	    getContentPane().add(panelRight);
	    
	    // Create top panel
	    panelTop = new JLayeredPane();
	    panelTop.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    panelTop.setBounds(panelTopData.getX(), panelTopData.getY(), panelTopData.getWidth(), panelTopData.getHeight());
	    panelTop.setLayout(null);
	    getContentPane().add(panelTop);
	    
	 // Create middle panel
	    panelMiddle = new JLayeredPane();
	    panelMiddle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    panelMiddle.setBounds(panelMiddleData.getX(), panelMiddleData.getY(), panelMiddleData.getWidth(), panelMiddleData.getHeight());
	    panelMiddle.setLayout(null);	    
	    getContentPane().add(panelMiddle);	    
	}

	private void initMiddleInnerPanels() {
		
		panelAlwaysTop = new JLayeredPane();
		panelAlwaysTop.setBounds(0, 0, panelMiddleData.getWidth(), panelMiddleData.getHeight());
		panelMiddle.add(panelAlwaysTop, 1);		
		
		panelPlacedItems = new JLayeredPane();	// layer 2
		panelPlacedItems.setBounds(0, 0, panelMiddleData.getWidth() , panelMiddleData.getHeight());		
		panelMiddle.add(panelPlacedItems, 2);
		
		panelDrawnLines = new DrawPanel();	// layer 3
		panelDrawnLines.setBounds(0, 0, panelMiddleData.getWidth(), panelMiddleData.getHeight());
		panelMiddle.add(panelDrawnLines, 3);
		
		panelForItemPlace = new JLayeredPane();	// layer 4
		panelForItemPlace.setBounds(0, 0, panelMiddleData.getWidth(), panelMiddleData.getHeight());
		panelMiddle.add(panelForItemPlace, 4);
		
		panelForDrawLines = new JLayeredPane();	// layer 5
		panelForDrawLines.setBounds(0, 0, panelMiddleData.getWidth(), panelMiddleData.getHeight());
		panelMiddle.add(panelForDrawLines, 5);
		
		panelBackground = new JLayeredPane();	// layer 1 (main)
		panelBackground.setBounds(0, 0, panelMiddleData.getWidth(), panelMiddleData.getHeight());						
		panelMiddle.add(panelBackground, 6);
		backgroundLabel = new JLabel();
		panelBackground.add(backgroundLabel);		
		
	}

	private void addDefaultButtons() {				
				
		/*
		 * (no functionality, just add buttons to the panels)
		 * default buttons: 
		 * 		CB - selectPitch
		 * 		B  - save, load, delete, clear
		 */
		
		font = new Font("Tahoma", Font.PLAIN, 14);
		fontBold = new Font("Tahoma", Font.BOLD, 14);
		
		pitchItems = new ArrayList<>();
		try {
			scanner = new Scanner(new File(confPitchFilePath));
			
			while(scanner.hasNextLine()) {
				String name = scanner.nextLine();
				String path = scanner.nextLine();
				pitchItems.add(new PitchItem(name, path));
			}
						
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		String[] pitchNames = new String[pitchItems.size()];
		for(int i = 0;i < pitchItems.size(); i++) {
			pitchNames[i] = pitchItems.get(i).getName();
		}
		cbSelectPitch = new JComboBox<>();
		cbSelectPitch.setFont(font);
		cbSelectPitch.setModel(new DefaultComboBoxModel(pitchNames));
		cbSelectPitch.setBounds(0, 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(cbSelectPitch);
		
		buttonSave = new JButton("Mentés");
		buttonSave.setFont(font);
		buttonSave.setBounds(cbSelectPitch.getWidth(), 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(buttonSave);
		
		buttonLoad = new JButton("Betöltés");
		buttonLoad.setFont(font);
		buttonLoad.setBounds(buttonSave.getX() + buttonSave.getWidth(), 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(buttonLoad);
		
		buttonClear = new JButton("Pálya letörlése");
		buttonClear.setFont(font);
		buttonClear.setBounds(panelTopData.getWidth() - panelTopButtonWidth, 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(buttonClear);
		
		buttonDelete = new JButton("Elem törlése");
		buttonDelete.setFont(font);
		buttonDelete.setBounds(buttonClear.getX() - panelTopButtonWidth, 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(buttonDelete);
		
		buttonReplace = new JButton("Mozgatás");
		buttonReplace.setFont(font);
		buttonReplace.setBounds(buttonDelete.getX() - panelTopButtonWidth, 3, panelTopButtonWidth, panelTopButtonHeight);
		panelTop.add(buttonReplace);
		
		
	}	

	private void addLeftPanelButtons() {
		
		try {
			scanner = new Scanner(new File("textures/conf.txt"));
			
			GridBagConstraints c = new GridBagConstraints();
	        c.gridwidth = GridBagConstraints.REMAINDER;        
	        c.fill = GridBagConstraints.HORIZONTAL;        
	        c.weightx = 1.0;
	        
	        panelLeftItems = new ArrayList<>();
	        panelLeftCb = new ArrayList<>();
	        
	        while(scanner.hasNextLine()) {
	        	
	        	JComboBox cb = new JComboBox();	        	
	        	
	        	String name = scanner.nextLine();
	        	int num = Integer.parseInt(scanner.nextLine());
	        	ArrayList<String> paths = new ArrayList<>();
	        	ImageIcon[] ii = new ImageIcon[num];	
	        	
	        	for(int i = 0; i < num; i++) {
	        		paths.add(scanner.nextLine());
	        		ii[i] = new ImageIcon(ImageIO.read(new File(paths.get(i))).getScaledInstance(imagePlaceWidth, imagePlaceWidth, Image.SCALE_SMOOTH));
	        	}
	        	
	        	panelLeftItems.add(new Item(name, paths, imagePlaceWidth, imagePlaceHeight));
	        		        		
	        	cb.setModel(new DefaultComboBoxModel(ii));
	        	
	        	JLabel label = new JLabel(name);
	        	label.setFont(fontBold);
	        	
	        	panelLeftCb.add(cb);	        	
	        	panelLeft.add(label, c);
	        	panelLeft.add(cb, c);
	        	
	        	if(scanner.hasNextLine())
	        		scanner.nextLine();
	        }
	        c.weighty = 1.0;
	        panelLeft.add(new JPanel(), c);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}		
	
	protected void setPanelBackground(String path, int x, int y, int width, int height) {
		
		try {
			removeAllItemsFromAllPanel();
			
			ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(width, height, Image.SCALE_SMOOTH));
			backgroundLabel.setIcon(imageIcon);
			backgroundLabel.setBounds(x, y, width, height);
			
			panelBackground.repaint();
			panelBackground.revalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void addNewImageToPanel(JLayeredPane panel, String path, int x, int y) {		
		ImageIcon icon;
		try {
			
			icon = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(imagePlaceWidth, imagePlaceHeight, Image.SCALE_SMOOTH));
			icon.setDescription(path);
			JLabel label = new JLabel(icon);
			label.setBounds(x, y, imagePlaceWidth, imagePlaceHeight);			
			
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					placedItemLastSelected = null;
					isMovingComponent = false;
					System.out.println("Label moved");
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					placedItemLastSelected = label;
					isMovingComponent = true;
				}
				
				@Override
				public void mouseExited(MouseEvent e) {

				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					placedItemLastSelected = label;
					if(componentLastSelected.equals(buttonDelete)) {
						panelForItemPlace.remove(label);
						panelForItemPlace.repaint();
						panelForItemPlace.revalidate();
					}
				}
			});
			
			panel.add(label);
			System.out.println("New item placed at (" + x + "," + y + ")");
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	protected void removeAllItemsFromAllPanel() {

		/*panelBackground.removeAll();
		panelBackground.revalidate();
		panelBackground.repaint();*/
		
		panelPlacedItems.removeAll();
		panelPlacedItems.revalidate();
		panelPlacedItems.repaint();
				
		drawPaths = null;
		panelDrawnLines.setPaths(drawPaths);
		panelDrawnLines.removeAll();
		panelDrawnLines.revalidate();
		panelDrawnLines.repaint();	
		
		panelForItemPlace.removeAll();
		panelForItemPlace.revalidate();
		panelForItemPlace.repaint();
				
		panelForDrawLines.removeAll();
		panelForDrawLines.revalidate();
		panelForDrawLines.repaint();
	}

}
