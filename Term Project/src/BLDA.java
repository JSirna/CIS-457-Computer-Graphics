import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BLDA {
	
	public static void drawLine(Graphics g, int xP, int yP, int xQ, int yQ)
	{
		int x0 = xP,y0 = yP,x1 = xQ, y1 = yQ;
		g.setColor(Color.magenta);
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