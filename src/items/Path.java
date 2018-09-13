package items;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

/**
 * 
 * @author Arno
 * Class for generating paths in the DrawPanel
 *
 */
public class Path{

	private GeneralPath path;
	private boolean isDashed;
	private Color color;
	
	private double startX;
	private double startY;
	
	private double endX;
	private double endY;
	
	public Path() {
	}	
	
	public void drawLine(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g.create();
		Stroke stroke;
		if(isDashed) {
			stroke = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		}
		else {
			stroke = new BasicStroke(4, BasicStroke.JOIN_BEVEL, BasicStroke.JOIN_BEVEL);
		}
		g2d.setColor(color);
        g2d.setStroke(stroke);
       
        g2d.fillOval((int)startX - 4, (int)startY - 4, 8, 8);
        
        // gen
        
        final double deltax = startX - endX;
        final double result;
        if(deltax == 0.0d) {
        	result = Math.PI / 2;
        }
        else {
        	result = Math.atan((startY - endY) / deltax) + (startX < endX ? Math.PI : 0);
        }
        
        int arrowSize = 30;
        final double angle = result;
        final double arrowAngle = Math.PI / 15.0d;
        final double x1 = arrowSize * Math.cos(angle - arrowAngle);
        final double y1 = arrowSize * Math.sin(angle - arrowAngle);
        final double x2 = arrowSize * Math.cos(angle + arrowAngle);
        final double y2 = arrowSize * Math.sin(angle + arrowAngle);
        
        final double cx = (arrowSize / 1.8f) * Math.cos(angle);
        final double cy = (arrowSize / 1.8f) * Math.sin(angle);
        
        final GeneralPath polygon = new GeneralPath();
        polygon.moveTo(endX, endY);
        polygon.lineTo(endX + x1, endY + y1);
        polygon.lineTo(endX + x2, endY + y2);
        polygon.closePath();
        g2d.fill(polygon);
        
        g2d.drawLine((int) startX, (int) startY, (int) (endX + cx), (int) (endY + cy));
        
        // gen
               
        g2d.dispose();
		
	}	
	
	public Path(GeneralPath path, Color color, double[] positions, boolean isDashed) {
		this.path = path;
		this.color = color;
		this.isDashed = isDashed;
		
		this.startX = positions[0];
		this.startY = positions[1];
		this.endX = positions[2];
		this.endY = positions[3];
	}
	
	public double getStartX() {
		return startX;
	}
	
	public double getStartY() {
		return startY;
	}
	
	public double getEndX() {
		return endX;
	}
	
	public double getEndY() {
		return endY;
	}
	
	public GeneralPath getPath() {
		return path;
	}
	
	public void setPath(GeneralPath path) {
		this.path = path;
	}
	
	public boolean isDashed() {
		return isDashed;
	}
	
	public void setDashed(boolean isDashed) {
		this.isDashed = isDashed;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}	
	
}
