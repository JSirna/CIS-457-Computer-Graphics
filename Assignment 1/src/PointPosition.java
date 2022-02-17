public class PointPosition {

	public static void main(String[] args) {
		// this set of points should return true
		Point2D a = new Point2D(3,3);
		Point2D b = new Point2D(5,4);
		Point2D c = new Point2D(4,3);
		
		//this set of points should return false
		Point2D h = new Point2D(2,3);
		Point2D i = new Point2D(3,7);
		Point2D j = new Point2D(2,7);
		
		
		System.out.println("Is Point on Line Segment?\n");
		System.out.println(isOnLineSegment(a,b,c));
		
		System.out.println();
		
		System.out.println("Is Point on Line Segment?\n");
		System.out.println(isOnLineSegment(h,i,j));
	}

	static boolean isOnLineSegment(Point2D x, Point2D y, Point2D p)
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
			onLine = false;
		if ((dotProduct < 0) && (dotProduct > distanceXY))
			onLine = false;
		
		if (distanceXY == (distanceXP + distanceYP))
			onLine = true;
		else
			onLine = false;
		
		return onLine;
	}
}
