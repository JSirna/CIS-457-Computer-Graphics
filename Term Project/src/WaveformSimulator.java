import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaveformSimulator extends Frame{
	
	private ArrayList<SingleWaveformPanel> singleChannelWaveformPanels = new ArrayList<SingleWaveformPanel>();
    private AudioInfo audioInfo = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileChooser.run(new FileChooser(), 250, 110);
	}
	
	WaveformSimulator(AudioInputStream driver, String filename) {
		super("Waveform Simulator - " + filename);
	      addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) 
	         {System.exit(0);}
	      });
	      setSize(340, 230);
	      setLayout(new GridLayout(0,1));
	      setAudio(driver);
//	      add("Center");
	      setVisible(true);
	      validate();
	      repaint();
	      try {
			driver.close(); //don't leave the stream  open
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setAudio(AudioInputStream driver) {
		singleChannelWaveformPanels = new ArrayList<SingleWaveformPanel>();
        audioInfo = new AudioInfo(driver);
        System.out.println("Number of Channels in the stream: "+audioInfo.getNumberOfChannels());
        for (int t=0; t<audioInfo.getNumberOfChannels(); t++){
            SingleWaveformPanel waveformPanel
                    = new SingleWaveformPanel(audioInfo, t);
            singleChannelWaveformPanels.add(waveformPanel);
            add(createChannelDisplay(waveformPanel, t));
        }
		//return singleChannelWaveformPanels;
	}
	
	public Component createChannelDisplay(SingleWaveformPanel wvpanel, int index ) {
		JPanel panel = new JPanel(new BorderLayout());
        panel.add(wvpanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Channel " + ++index);
        panel.add(label, BorderLayout.NORTH);

        return panel;
		
	}

}
class SingleWaveformPanel extends Canvas {
	
	AudioInfo helper = null;
	int channelIndex;
	
	public SingleWaveformPanel(AudioInfo helper, int channelIndex) {
        this.helper = helper;
        this.channelIndex = channelIndex;
        setBackground(Color.white);
    }

	
	protected void drawWaveform(Graphics g, int[] samples) {
        if (samples == null) {
            return;
        }

        int oldX = 0;
        int oldY = (int) (getHeight() / 2);
        int xIndex = 0;

        int increment = helper.getIncrement(helper.getXScaleFactor(getWidth()));
        g.setColor(Color.magenta);

        int t = 0;

        for (t = 0; t < increment; t += increment) {
        	CvBresenham.drawLine(g, oldX, oldY, xIndex, oldY);
//            g.drawLine(oldX, oldY, xIndex, oldY);
            xIndex++;
            oldX = xIndex;
        }

        for (; t < samples.length; t += increment) {
            double scaleFactor = helper.getYScaleFactor(getHeight());
            double scaledSample = samples[t] * scaleFactor;
            int y = (int) ((getHeight() / 2) - (scaledSample));
//            g.drawLine(oldX, oldY, xIndex, y);
            CvBresenham.drawLine(g, oldX, oldY, xIndex, y);

            xIndex++;
            oldX = xIndex;
            oldY = y;
        }
        System.out.println("Number of samples in audio file: "+ samples.length);
    }
	
	public void paint(Graphics g) {
		int lineHeight = getHeight() / 2;
//		super.paint(g);
        g.setColor(Color.black);
        g.drawLine(0, lineHeight, (int)getWidth(), lineHeight);
//        CvBresenham.drawLine(g,0, lineHeight, (int)getWidth(), lineHeight);
        drawWaveform(g, helper.getAudio(channelIndex));
	}
}