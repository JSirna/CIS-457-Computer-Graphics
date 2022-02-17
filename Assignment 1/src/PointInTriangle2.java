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

public class PointInTriangle2 extends Frame {

	public static void main(String[] args) {
		new PointInTriangle2();
	}

	PointInTriangle2() {
		super("Triangle: Point in Triangle");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(600, 400);
		add("Center", new Triangle2());
		setVisible(true);
	}
}

class Triangle2 extends Canvas {

	Vector<Point2D> v = new Vector<Point2D>();
	float x0, y0, rWidth = 10.0F, rHeight = 7.5F, pixelSize;
	boolean ready = true;
	int centerX, centerY;

	Triangle2() {
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
				if (v.size() > 0 && dx * dx + dy * dy < 4 * pixelSize * pixelSize)
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
		int left = iX(-rWidth / 2), right = iX(rWidth / 2), bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
		g.drawRect(left, top, right - left, bottom - top);
		int n = v.size();

		Point2D a = (Point2D) (v.elementAt(0));
		Point2D b = (Point2D) (v.elementAt(0));
		Point2D c = (Point2D) (v.elementAt(0));
		Point2D p = (Point2D) (v.elementAt(0));
		char ch = 'A';
		boolean isInside = false;
		if (n == 0)
	         return;
		// Show Letter around first vertex:
		g.drawString("" + ('A'), iX(a.x), iY(a.y));
		for (int i = 1; i <= n; i++) {
			if (i == n && !ready) {
				g.drawString("" + ('P'), iX(p.x), iY(p.y));
				g.drawLine(iX(p.x) - 2, iY(p.y) - 2, iX(p.x) + 2, iY(p.y) + 2);
				g.drawLine(iX(p.x) - 2, iY(p.y) + 2, iX(p.x) + 2, iY(p.y) - 2);
				if (isInside) {
					g.drawString("Point inside triangle", 50, 50);
					break;
				} else if (!isInside) {
					g.drawString("Point not inside triangle", 50, 50);
					break;
				} else if (Tools2D.intersect(a, b, c, p)) {
					g.drawString("Point on triangle", 50, 50);
					break;
				} else
					break;
			}
			b = (Point2D) (v.elementAt(i % n));
			c = (Point2D) (v.elementAt(i % n));

			g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
			a = b;
			if (i == 1)
				g.drawString("" + ('B'), iX(b.x), iY(b.y));
			else if (i == 2)
				g.drawString("" + ('C'), iX(c.x), iY(c.y));

			p = (Point2D) (v.elementAt(i % n));
			isInside = Tools2D.insideTriangle(a, b, c, p);
		}

	}
}
