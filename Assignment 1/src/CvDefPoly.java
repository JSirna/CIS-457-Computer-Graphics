//CvDefPoly modified JSirna
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CvDefPoly extends Canvas {
   Vector<Point2D> v = new Vector<Point2D>();
   float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize;
   boolean ready = true;
   int centerX, centerY;

   CvDefPoly() {
      addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent evt) {
            float xA = fx(evt.getX()), yA = fy(evt.getY());
            if (ready) {
               v.removeAllElements();
               x0 = xA; y0 = yA;
               ready = false;
            }
            float dx = xA - x0, dy = yA - y0;
            if (v.size() > 0 && 
               dx * dx + dy * dy < 20 * pixelSize * pixelSize)
               // Previously 4 instead of 20 .........................
               ready = true;
            else
               v.addElement(new Point2D(xA, yA));
            
            
            // Added December 2016:
            if(evt.getModifiers()==InputEvent.BUTTON3_MASK) {
               ready = true;
            }
            
           
            repaint();
         }
      });
   }

   void initgr() {
      Dimension d = getSize();
      int maxX = d.width - 1, maxY = d.height - 1;
      pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
      centerX = maxX / 2; centerY = maxY / 2;
   }

   int iX(float x) {return Math.round(centerX + x / pixelSize);}
   int iY(float y) {return Math.round(centerY - y / pixelSize);}
   float fx(int x) {return (x - centerX) * pixelSize;}
   float fy(int y) {return (centerY - y) * pixelSize;}

   public void paint(Graphics g) {
      initgr();
      int left = iX(-rWidth / 2), right = iX(rWidth / 2), 
          bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
      g.drawRect(left, top, right - left, bottom - top);
      int n = v.size();
      if (n == 0)
         return;
      Point2D a = (Point2D) (v.elementAt(0));
      
      char ch = 'A';
      // Show Letter around first vertex:
      g.drawString(""+(ch), iX(a.x), iY(a.y));
      for (int i = 1; i <= 1; i++) { //only two points created by user
         if (i == n && !ready)
            break;
         Point2D b = (Point2D) (v.elementAt(i % n));
         g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
         a = b;
         int dis = iX(Tools2D.distance2(a, b));
         double dis2 = java.lang.Math.sqrt((double)dis)/6;
         Point2D c = new Point2D(b.x,b.y-2);
         Point2D d = new Point2D(((int)dis2),c.y);
         
         g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
         g.drawLine(iX(b.x), iY(b.y), iX(c.x), iY(c.y)); //connect b to c
         g.drawLine(iX(c.x), iY(c.y), iX(d.x), iY(d.y)); //connect c to d
         g.drawLine(iX(d.x), iY(d.y), iX(d.x), iY(a.y)); //connect d to a
         g.drawString(""+(++ch), iX(b.x), iY(b.y));
         g.drawString(""+('C'), iX(c.x), iY(c.y));
         g.drawString(""+('D'), iX(d.x), iY(d.y));
      }
   }
}
