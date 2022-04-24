import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileChooser extends JFrame {
  private JTextField filename = new JTextField(), dir = new JTextField();
  
  private JButton open = new JButton("Open");//, save = new JButton("Save");

  public FileChooser() {
    JPanel p = new JPanel();
    open.addActionListener(new OpenL());
    p.add(open);
//    save.addActionListener(new SaveL());
//    p.add(save);
    Container cp = getContentPane();
    cp.add(p, BorderLayout.SOUTH);
    dir.setEditable(false);
    filename.setEditable(false);
    p = new JPanel();
    p.setLayout(new GridLayout(2, 1));
   
    p.add(filename);
    p.add(dir);
    cp.add(p, BorderLayout.NORTH);
  }

  class OpenL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Open" dialog:
      int rVal = c.showOpenDialog(FileChooser.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        filename.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
        
        //
        String filepath = c.getSelectedFile().getPath();
        System.out.println(filepath);
        File file = new File(filepath);
        System.out.println(file.getAbsolutePath());
        try {
    		AudioInputStream chosenFile = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));
    		new WaveformSimulator(chosenFile,filepath);
    	} catch (FileNotFoundException ne) {
    		// TODO Auto-generated catch block
    		ne.printStackTrace();
    	} catch (UnsupportedAudioFileException ne) {
    		// TODO Auto-generated catch block
    		ne.printStackTrace();
    	} catch (IOException ne) {
    		// TODO Auto-generated catch block
    		ne.printStackTrace();
    	}
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
        filename.setText("You pressed cancel");
        dir.setText("");
      }
    }
  }

  public static void run(JFrame frame, int width, int height) {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);
    frame.setVisible(true);
  }
}