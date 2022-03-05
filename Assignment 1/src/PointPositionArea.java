import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PointPositionArea  extends Frame{

	float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize;
   boolean ready = true;
   int centerX, centerY;
	public static void main(String[] args){
		
		
		
		System.out.println("Is Point out of Line Segment Area?\n");
//		System.out.println(isOutsideLineSegment(a,b,c));
		
		System.out.println();
		
		System.out.println("Is Point out of Line Segment Area?\\n");
//		System.out.println(isOutsideLineSegment(h,i,j));
	}
	
	PointPositionArea()
	{
		super("Check");
	      addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {System.exit(0);}
	      });
	      setSize(500, 300);
	      add("Center", new CvDefPoly());
	      //setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	      //setVisible(true);
	      repaint();
	}
	void initgr() {
      Dimension d = getSize();
      int maxX = d.width - 1, maxY = d.height - 1;
      pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
      centerX = maxX / 2; centerY = maxY / 2;
	}
	int iX(float x) {return Math.round(centerX + x / pixelSize);}
	int iY(float y) {return Math.round(centerY - y / pixelSize);}
	public void paint(Graphics g)
	{
		// this set of points should return false
		Point2D a = new Point2D(3,3);
		Point2D b = new Point2D(5,4);
		Point2D c = new Point2D(4,3);
		
		//this set of points should return false
		Point2D h = new Point2D(2,3);
		Point2D i = new Point2D(3,7);
		Point2D j = new Point2D(2,7);
		
		Point2D k = new Point2D(2,3);
		Point2D l = new Point2D(3,7);
		Point2D m = new Point2D(2,7);
		
		g.drawLine(iX(a.x),iY(b.y),iX(a.x),iY(b.y));
		
	}

	static boolean isOutsideLineSegment(Point2D x, Point2D y, Point2D p)
	{
		
		boolean onLine = false;
		
		float distanceXY = Tools2D.distance2(x, y);
		float distanceXP = Tools2D.distance2(x, p);
		float distanceYP = Tools2D.distance2(p, y);
		float crossProduct = (p.y - x.y) * (y.x - x.x) - (p.x - x.x) * (y.y - x.y);
		float dotProduct = (p.x - x.x) * (y.x - x.x) + (p.y - x.y)*(y.y - x.y);
		float epsilon = 1E-03f;
		float nEpsilon = epsilon * -1;
		
		System.out.println("Distance between points x and y: "+distanceXY);
		System.out.println("Dot Product of points x and y: "+dotProduct);
		System.out.println("Cross Product of points x and y: "+crossProduct);
		
		if ((nEpsilon < java.lang.Math.abs(crossProduct)) && (java.lang.Math.abs(crossProduct) > epsilon))
			onLine = true;
		if ((dotProduct < 0) && (dotProduct > distanceXY))
			onLine = true;
		
		if (distanceXY == (distanceXP + distanceYP))
			onLine = false;
		else
			onLine = true;
		
		return onLine;
	}
}
