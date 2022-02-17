// Modified Triangle: Point-in-Triangle
// edit jsirna
import java.awt.*;
import java.awt.event.*;

public class Triangles extends Frame {
   public static void main(String[] args) {new Triangles();}

   Triangles() {
      super("Triangle: Point in Triangle");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setSize(600, 400);
      add("Center", new CvTriangles());
//      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      setVisible(true);
	  
   }
}

class CvTriangles extends Canvas {
   int maxX, maxY, minMaxXY, xCenter, yCenter;

   void initgr() {
      Dimension d = getSize();
      maxX = d.width - 1; maxY = d.height - 1;
      minMaxXY = Math.min(maxX, maxY);
      xCenter = maxX / 2; yCenter = maxY / 2;
   }

   int iX(float x) {return Math.round(x);}

   int iY(float y) {return maxY - Math.round(y);}

   public void paint(Graphics g) {
      initgr();
      float side = 0.95F * minMaxXY, sideHalf = 0.5F * side, 
            h = sideHalf * (float) Math.sqrt(3), 
            xA, yA, xB, yB, xC, yC, xA1, yA1, xB1, yB1, xC1, yC1, p, q;
      q = 0.05F; p = 1 - q;
      xA = xCenter - sideHalf; yA = yCenter - 0.5F * h;
      xB = xCenter + sideHalf; yB = yA;
      xC = xCenter; yC = yCenter + 0.5F * h;
      
      Point2D a = new Point2D(xA,yA);
      Point2D b = new Point2D(xB,yB);
      Point2D c = new Point2D(xC,yC);
      Point2D p2 = new Point2D(250,50); //x change to 250 for the point to be inside the triangle
     g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
     g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
     g.drawLine(iX(xC), iY(yC), iX(xA), iY(yA));
     
     xA1 = p * xA + q * xB; yA1 = p * yA + q * yB;
     xB1 = p * xB + q * xC; yB1 = p * yB + q * yC;
     xC1 = p * xC + q * xA; yC1 = p * yC + q * yA;
     xA = xA1; xB = xB1; xC = xC1;
     yA = yA1; yB = yB1; yC = yC1;
     
      //set crosshair point
      g.drawLine(iX(p2.x - 2), iY(p2.y - 2), iX(p2.x + 2), iY(p2.y + 2));
      g.drawLine(iX(p2.x - 2), iY(p2.y + 2), iX(p2.x + 2), iY(p2.y - 2));
      
      boolean inside = Tools2D.insideTriangle(a, b, c, p2);
      System.out.println("Is point inside triangle? "+inside);
   }
}