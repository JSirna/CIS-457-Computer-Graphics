import java.awt.*;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CompleteRectangle extends Frame {

	public static void main(String[] args) {
		new CompleteRectangle();
	}
	
	CompleteRectangle() 
	{
      super("Draw a line from ccw");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setSize(500, 300);
      add("Center", new CvDefPoly());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      setVisible(true);
	  
   }
}
