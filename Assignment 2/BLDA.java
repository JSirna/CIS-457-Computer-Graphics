package pack2;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import pack1.Point2D;

public class BLDA extends Frame{

	public static void main(String[] args) {
		// TODO check whether the slope is less than or greater than 1
		// TODO apply the convergence between device coordinates and logical coordinates
		new BLDA();
	}

	BLDA() {
		super("Bresenham's Line Drawing Algorithm");
	      addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {System.exit(0);}
	      });
	      setSize(600, 400);
	      add("Center", new DrawLine());
	      setVisible(true);
	      
	      repaint();
	}
}

class DrawLine extends Canvas {
	
	public void paint(Graphics g)
	{
		int x0,y0,x1,y1;
		x0=100; x1=200;
		y0=100; y1=200;
		
		int dx, dy, p, x, y;  
		dx=x1-x0;  
	    dy=y1-y0;  
	    x=x0;  
	    y=y0;  
	    p=2*dy-dx;
	    
	    while(x<x1) {
	    	if (p>=0) {
	    		g.drawLine(x, y, x, y);
	    		y=y+1;  
	            p=p+2*dy-2*dx;
	    	}
	    	else {  
	            g.drawLine(x, y, x, y);  
	            p=p+2*dy; 
	    	}
            x=x+1;
	        
	    }
	}
}