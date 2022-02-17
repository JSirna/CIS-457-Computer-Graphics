import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class MinDistance extends Frame {

	public static void main(String[] args) {
		new MinDistance();
	}

	MinDistance() {
		super("Find Min Distance");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(600, 400);
		add("Center", new Points());
		setVisible(true);
	}
}

class Points extends Canvas {
	Vector<Point2D> v = new Vector<Point2D>();
	float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize;
	boolean ready = true;
	int centerX, centerY;

	Points() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				float xA = fx(evt.getX()), yA = fy(evt.getY());
				if (ready) {
					v.removeAllElements();
					x0 = xA;
					y0 = yA;
					ready = false;
				}
				float dx = xA - x0, dy = yA - y0;
				if (v.size() > 0 && dx * dx + dy * dy < 20 * pixelSize * pixelSize)
					// Previously 4 instead of 20 .........................
					ready = true;
				else
					v.addElement(new Point2D(xA, yA));

				// Added December 2016:
				if (evt.getModifiers() == InputEvent.BUTTON3_MASK) {
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
		centerX = maxX / 2;
		centerY = maxY / 2;
	}

	int iX(float x) {
		return Math.round(centerX + x / pixelSize);
	}

	int iY(float y) {
		return Math.round(centerY - y / pixelSize);
	}

	float fx(int x) {
		return (x - centerX) * pixelSize;
	}

	float fy(int y) {
		return (centerY - y) * pixelSize;
	}

	public void paint(Graphics g) {
		initgr();

		int n = v.size();
		int minDistance = 0;
		if (n == 0)
			return;
		Point2D a = (Point2D) (v.elementAt(0));
		Point2D p = new Point2D(1,1);
		Point2D p2 = (Point2D) (v.set(4, p));
		g.drawString("A", iX(a.x), iY(a.y));
		g.drawString("P", iX(p2.x), iY(p2.y));
		for (int i = 1; i <= 3; i++) {
			if (i == n && !ready)
				break;
			Point2D b = (Point2D) (v.elementAt(i % n));
			g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
			g.drawRect(iX(b.x) - 2, iY(b.y) - 2, 4, 4); // Tiny rectangle; added
			g.drawString("Distance: "+Tools2D.distance2(b, a),50,50+i*15);
			a = b;
			if (i == 1)
				g.drawString("B", iX(b.x), iY(b.y));// to test.......
			if (i == 2)
				g.drawString("C", iX(b.x), iY(b.y));// to test.......
			g.drawString("Distance p,a: "+Tools2D.distance2(p2, a),50,150+i*15);
		}
		g.drawLine(iX(a.x), iY(a.y), iX(p.x), iY(p.y));	
	}
	
}
