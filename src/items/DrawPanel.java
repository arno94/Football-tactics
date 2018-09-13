package items;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

public class DrawPanel extends JLayeredPane{
	
	private ArrayList<Path> paths;
	
	public DrawPanel() {
		super();
	}
	
	public void setPaths(ArrayList<Path> paths) {
		this.paths = paths;
	}
	
	public ArrayList<Path> getPaths(){
		return paths;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);   
       
        if(paths != null) {
        	if(paths.size() > 0) {
        		for(int i = 0;i < paths.size(); i++) {       
        			paths.get(i).drawLine(g);        			
        		}
        	}
        }
	}
	

}
