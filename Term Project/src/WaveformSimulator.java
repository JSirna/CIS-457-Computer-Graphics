import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
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
	
	private ArrayList singleChannelWaveformPanels = new ArrayList();
    private AudioInfo audioInfo = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("/home/jsdev/Dropbox/CG/chord.wav");
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));
//			AudioInfo ainfo = new AudioInfo(audioInputStream);
//			System.out.println(ainfo.getNumberOfChannels());
			new WaveformSimulator(audioInputStream);
			
			
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	WaveformSimulator(AudioInputStream driver) {
		super("WaveformSimulator");
	      addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) 
	         {System.exit(0);}
	      });
	      setSize(340, 230);
	      setAudio(driver);
//	      add("Center");
	      setVisible(true);
	}
	
	public void setAudio(AudioInputStream driver) {
		singleChannelWaveformPanels = new ArrayList();
        audioInfo = new AudioInfo(driver);
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
    }
	
	public void paint(Graphics g) {
		int lineHeight = getHeight() / 2;
        g.setColor(Color.black);
        g.drawLine(0, lineHeight, (int)getWidth(), lineHeight);
//        CvBresenham.drawLine(g,0, lineHeight, (int)getWidth(), lineHeight);
        drawWaveform(g, helper.getAudio(channelIndex));
	}
}