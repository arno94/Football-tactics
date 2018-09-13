import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import items.Item;
import items.Path;

public class Window extends WindowProperties {

	private static final long serialVersionUID = 1L;
	
	private JLayeredPane panelLastSelected;	
	
	private int panelDrawClickCount;
	
	private GeneralPath createdPath;

	public Window(int width, int height, int imagePlaceWidth, int imagePlaceHeight) {	
		super(width, height, imagePlaceWidth, imagePlaceHeight);
		
		init();
	}
	
	private void init() {
		initListeners();
	}
	
	private void initListeners() {
		
		// init right panel buttons
		addRightPanelButtons();
		
		addPanelAlwaysTopListener();
		addSelectPitchCBListener();
		addSaveButtonListener();
		addLoadButtonListener();
		addReplaceButtonListener();
		addDeleteButtonListener();
		addClearButtonListener();
		addPanelForItemPlaceListener();
		addPanelForDrawLinesListener();
		
		// left panel listeners
		addCBPanelLeftListeners();		
		
	}	
	
	private void addPanelForDrawLinesListener() {
		
		panelDrawClickCount = 0;
		drawPaths = new ArrayList<>();		
		
		panelForDrawLines.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(componentLastSelected == btnColorChoose) {
					if(drawPaths == null) {
						drawPaths = new ArrayList<>();
					}
					panelDrawClickCount++;				
					
					if(panelDrawClickCount == 1) {
						createdPath = new GeneralPath();
						
						int x = e.getX();
						int y = e.getY();
						
						for(int i = 0;i < drawPaths.size(); i++) {
							int cX = (int)drawPaths.get(i).getStartX();
							int cY = (int)drawPaths.get(i).getStartY();
							if((Math.abs(x-cX) + Math.abs(y-cY)) <= 10) {
								x = cX;
								y = cY;
								break;
							}
							cX = (int)drawPaths.get(i).getEndX();
							cY = (int)drawPaths.get(i).getEndY();
							if((Math.abs(x-cX) + Math.abs(y-cY)) <= 10) {
								x = cX;
								y = cY;
								break;
							}
						}
						
						createdPath.moveTo(x, y);
					}
					else {
						double startX = createdPath.getCurrentPoint().getX();
						double startY = createdPath.getCurrentPoint().getY();
						
						double endX = e.getX();
						double endY = e.getY();
						
						double x = endX;
						double y = endY;
						
						int i;
						for(i = 0;i < drawPaths.size(); i++) {
							int cX = (int)drawPaths.get(i).getStartX();
							int cY = (int)drawPaths.get(i).getStartY();							
							if((Math.abs(x-cX) + Math.abs(y-cY)) <= 10) {
								x = cX;
								y = cY;
								break;
							}
							cX = (int)drawPaths.get(i).getEndX();
							cY = (int)drawPaths.get(i).getEndY();							
							if((Math.abs(x-cX) + Math.abs(y-cY)) <= 10) {
								x = cX;
								y = cY;
								break;
							}							
						}	
						
						
						createdPath.lineTo(x, y);
						createdPath.closePath();						
						
						drawPaths.add(new Path(createdPath, selectedDrawColor, new double[] {startX, startY, endX, endY},isDrawLineDashed.isSelected()));
						panelDrawnLines.setPaths(drawPaths);
						panelDrawnLines.repaint();
						panelDrawClickCount = 0;
					}
					
					return;
				}
				if(componentLastSelected == buttonLineClear) {
					System.out.println("Clear selected");
					for(int i = drawPaths.size()-1;i >= 0; i--) {
						
						double Ax = drawPaths.get(i).getStartX();
						double Ay = drawPaths.get(i).getStartY();
						
						double Bx = drawPaths.get(i).getEndX();
						double By = drawPaths.get(i).getEndY();
						
						double distAB = calculateDistance(Ax, Ay, Bx, By);
						double distACCB = calculateDistance(Ax, Ay, e.getX(), e.getY()) + calculateDistance(e.getX(), e.getY(), Bx, By);						
						if(Math.abs(distAB - distACCB) <= 0.25) {
							System.out.println("Remove line");
							drawPaths.remove(i);
							panelDrawnLines.setPaths(drawPaths);
							panelDrawnLines.revalidate();
							panelDrawnLines.repaint();
							return;
						}
					}
				}
			}
		});
		
	}

	private double calculateDistance(double Ax, double Ay, double Bx, double By) {		
		
		return Math.sqrt( ((Bx - Ax)*(Bx - Ax)) + ((By - Ay) * (By - Ay)));
				
	}
	
	private void addRightPanelButtons() {
		
		selectedDrawColor = Color.BLACK;
		
		GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;        
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.weightx = 1.0;
		
		JLabel labelColorChoose = new JLabel("Sz\u00EDn kiv\u00E1laszt\u00E1sa");
		labelColorChoose.setFont(fontBold);
		labelColorChoose.setSize(panelRightData.getWidth(), panelTopButtonHeight);
	    panelRight.add(labelColorChoose, c);	    	    
	    	    
	    btnColorChoose = new JButton("  \n");
	    btnColorChoose.setFont(fontBold);
	    btnColorChoose.setSize(panelRightData.getWidth(), panelTopButtonHeight * 2);
	    btnColorChoose.setBackground(selectedDrawColor);
		btnColorChoose.setForeground(selectedDrawColor);	
		btnColorChoose.setOpaque(true);
	    
	    btnColorChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				selectedDrawColor = JColorChooser.showDialog(panelAlwaysTop, "Válassz színt", selectedDrawColor);				
				btnColorChoose.setBackground(selectedDrawColor);
				btnColorChoose.setForeground(selectedDrawColor);	
				btnColorChoose.setOpaque(true);
				setLastSelectedPanelAndComponent(panelForDrawLines, btnColorChoose);									
			}
		});
	    panelRight.add(btnColorChoose, c);
	    
	    isDrawLineDashed = new JCheckBox("Szaggatott vonal");
	    isDrawLineDashed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLastSelectedPanelAndComponent(panelForDrawLines, btnColorChoose);	
			}
		});
	    panelRight.add(isDrawLineDashed, c);
	    
	    buttonLineClear = new JButton("Vonal törlése");
	    buttonLineClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLastSelectedPanelAndComponent(panelForDrawLines, buttonLineClear);
			}
		});
	    panelRight.add(buttonLineClear, c);
	    
	    c.weighty = 1.0;
	    panelRight.add(new JPanel(), c);
		
	}
	
	private void addCBPanelLeftListeners() {
		
		for(int i = 0;i < panelLeftCb.size(); i++) {
			final int j = i;
			panelLeftCb.get(i).addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					setLastSelectedPanelAndComponent(panelForItemPlace, panelLeftItems.get(j));		
					imageToPlacePath = panelLeftItems.get(j).getIcons().get(panelLeftCb.get(j).getSelectedIndex());
				}
			});
		}
		
	}
	
	private void addLoadButtonListener() {
		buttonLoad.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLastSelectedPanelAndComponent(panelTop, buttonLoad);		
				
				File selectedFile;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("saves/"));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    selectedFile = fileChooser.getSelectedFile();
				    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				}
				else {
					return;
				}
								
				try {
					Scanner scanner = new Scanner(selectedFile);
					String ppath = scanner.nextLine();
					
					removeAllItemsFromAllPanel();
					for(int i = 0;i < pitchItems.size(); i++) {						
						if(pitchItems.get(i).getPath().matches(ppath)) {
							cbSelectPitch.setSelectedIndex(i);
							break;
						}
					}
					int number = Integer.parseInt(scanner.nextLine());
					
					// Read items
					while(scanner.hasNextLine()) {
												
						scanner.nextLine();
						if(!scanner.hasNextLine()) break;
						String ipath = scanner.nextLine();
						int x = Integer.parseInt(scanner.nextLine());
						int y = Integer.parseInt(scanner.nextLine());
												
						addNewImageToPanel(panelForItemPlace, ipath, x - (imagePlaceWidth/2), y - (imagePlaceHeight/2));
						number--;
						if(number == 0) break;
					}
					
					drawPaths = new ArrayList<>();
					// Read lines
					while(scanner.hasNextLine()) {
						scanner.nextLine();
						if(!scanner.hasNextLine()) break;
						int r = scanner.nextInt();
						int g = scanner.nextInt();
						int b = scanner.nextInt();
						System.out.println(r + " " + g + " " + b);
						int x1 = scanner.nextInt();
						int y1 = scanner.nextInt();
						int x2 = scanner.nextInt();
						int y2 = scanner.nextInt();
						System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
						boolean isDashed = scanner.nextBoolean();
						System.out.println(isDashed);
						
						Color c = new Color(r, g, b);
						GeneralPath gp = new GeneralPath();
						gp.moveTo(x1, y1);
						gp.lineTo(x2, y2);
						gp.closePath();
												
						drawPaths.add(new Path(gp, c, new double[] {x1, y1, x2, y2}, isDashed));
						panelDrawnLines.repaint();
						panelDrawnLines.setPaths(drawPaths);						
						
					}
					
					scanner.close();
				} catch (IOException e1) { 
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void addSaveButtonListener() {
		buttonSave.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLastSelectedPanelAndComponent(panelTop, buttonSave);	
				String loadPath = (String)JOptionPane.showInputDialog(getParent(), "File name", "Save file", JOptionPane.PLAIN_MESSAGE);
				if(loadPath == null) return;
				String path = "saves/" + loadPath + ".tact";	
				
				
				PrintWriter writer;
				try {
					writer = new PrintWriter(path, "UTF-8");				
					
					writer.println(pitchItems.get(cbSelectPitch.getSelectedIndex()).getPath());
					writer.println(panelForItemPlace.getComponents().length);
					writer.println();
					
					for(int i = 0;i < panelForItemPlace.getComponents().length; i++) {												
						JLabel label = (JLabel)panelForItemPlace.getComponents()[i];							
						String iconfilename = ((ImageIcon)label.getIcon()).getDescription().toString();
						
						writer.println(iconfilename);
						writer.println((label.getX() + imagePlaceWidth/2));
						writer.println((label.getY() + imagePlaceHeight/2));
						if(i+1 == panelForItemPlace.getComponents().length) break;
						writer.println();						
					}
					if(drawPaths.size() > 0)
						writer.println();
					for(int i = 0;i < drawPaths.size(); i++) {
						Color c = drawPaths.get(i).getColor();
						
						writer.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
						writer.println((int)drawPaths.get(i).getStartX() + " " + (int)drawPaths.get(i).getStartY() + " " + (int)drawPaths.get(i).getEndX() + " " + (int)drawPaths.get(i).getEndY());
						writer.println(drawPaths.get(i).isDashed());
						if(i+1 == drawPaths.size()) break;
						writer.println();	
					}
					
					writer.close();
					System.out.println("Saved");
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void addDeleteButtonListener() {		
		buttonDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				setLastSelectedPanelAndComponent(panelForItemPlace, buttonDelete);					
			}
		});
	}

	private void addReplaceButtonListener() {
		buttonReplace.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLastSelectedPanelAndComponent(panelForItemPlace, buttonReplace);
			}
		});
	}
	
	private void addClearButtonListener() {
		
		buttonClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {		
				
				setLastSelectedPanelAndComponent(panelTop, buttonClear);				
				
				removeAllItemsFromAllPanel();
				
				System.out.println("Screen cleared.");
				
			}
		});
		
	}
	
	private void addSelectPitchCBListener() {
		
		cbSelectPitch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setLastSelectedPanelAndComponent(panelTop, cbSelectPitch);
				
				String path = pitchItems.get(cbSelectPitch.getSelectedIndex()).getPath();
				int x = 0;
				int y = 0;
				int w = panelMiddleData.getWidth();
				int h = panelMiddleData.getHeight();
				
				try {
					BufferedImage io = ImageIO.read(new File(path));
					if(io.getHeight() > io.getWidth()) {
						w = w/2;
						x = w - (w/2);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				setPanelBackground(path, x, y, w, h);
				System.out.println("New pitch selected.");
			}
		});
	}
	
	private void setLastSelectedPanelAndComponent(JLayeredPane panel, Object component) {
		this.panelLastSelected = panel;
		this.componentLastSelected = component;
	}
	
	private void addPanelForItemPlaceListener() {		
		
		panelForItemPlace.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if(componentLastSelected.equals(buttonReplace)) {
					if(placedItemLastSelected != null && isMovingComponent) {
						placedItemLastSelected.setLocation(e.getX() - placedItemLastSelected.getWidth()/2, e.getY() - placedItemLastSelected.getHeight()/2);						
					}
					
					return;
				}	
			}
		});
		
		panelForItemPlace.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(componentLastSelected.equals(buttonReplace)) {
					if(placedItemLastSelected != null) {
						MouseEvent event = new MouseEvent(panelForItemPlace, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);								
						placedItemLastSelected.dispatchEvent(event);
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(componentLastSelected.equals(buttonReplace)) {
					if(placedItemLastSelected != null) {
						MouseEvent event = new MouseEvent(panelForItemPlace, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);								
						placedItemLastSelected.dispatchEvent(event);
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {				
				if(Item.class.isInstance(componentLastSelected)) {
					addNewImageToPanel(panelForItemPlace, imageToPlacePath, e.getX() - imagePlaceWidth/2, e.getY() - imagePlaceHeight/2);	
					System.out.println("New item placed.");
					return;
				}
				if(componentLastSelected.equals(buttonDelete)) {
					for(Component c : panelForItemPlace.getComponents()) {
						int x = c.getX();
						int y = c.getY();
						int w = c.getWidth();
						int h = c.getHeight();
						
						if(x <= e.getX() && x+w >= e.getX()) {
							if(y <= e.getY() && y+h >= e.getY()){
								MouseEvent event = new MouseEvent(c, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);								
								c.dispatchEvent(event);
								break;
							}
						}
					}
					return;
				}
				if(componentLastSelected.equals(buttonReplace)) {
					for(Component c : panelForItemPlace.getComponents()) {
						int x = c.getX();
						int y = c.getY();
						int w = c.getWidth();
						int h = c.getHeight();
						
						if(x <= e.getX() && x+w >= e.getX()) {
							if(y <= e.getY() && y+h >= e.getY()){
								MouseEvent event = new MouseEvent(c, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);								
								c.dispatchEvent(event);
								break;
							}
						}
					}
					return;
				}								
				
			}
		});
	}

	private void addPanelAlwaysTopListener() {
		
		panelAlwaysTop.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(componentLastSelected == null) return;	
				if(componentLastSelected.equals(btnColorChoose)) return;
				MouseEvent event = new MouseEvent(panelLastSelected, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);
				panelLastSelected.dispatchEvent(event);	
			}
		});
		
		panelAlwaysTop.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(componentLastSelected.equals(btnColorChoose)) return;
				MouseEvent event = new MouseEvent(panelLastSelected, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);
				panelLastSelected.dispatchEvent(event);	
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(componentLastSelected.equals(btnColorChoose)) return;
				MouseEvent event = new MouseEvent(panelLastSelected, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);
				panelLastSelected.dispatchEvent(event);	
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {	
				MouseEvent event = new MouseEvent(panelLastSelected, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);
				panelLastSelected.dispatchEvent(event);	
			}
		});
	}
}
