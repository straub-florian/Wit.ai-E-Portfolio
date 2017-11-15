package finished_eportfolio.straub.main;

import javax.sound.sampled.*;
import java.io.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class Recorder {

	public  File wavFile = new File("record.wav");
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	private Thread recordThread;
	private volatile TargetDataLine line;

	public Recorder(){
		wavFile.deleteOnExit();
	}

	void start() {
		recordThread = new Thread(new Runnable() {
			public void run() {
				try {
					AudioFormat format = getAudioFormat();
					DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

					// checks if system supports the data line
					if (!AudioSystem.isLineSupported(info)) {
						System.out.println("Line not supported");
						System.exit(0);
					}

					line = (TargetDataLine) AudioSystem.getLine(info);
					line.open(format);
					line.start(); // start capturing

					AudioInputStream ais = new AudioInputStream(line);

					// start recording
					AudioSystem.write(ais, fileType, wavFile);

				} catch (LineUnavailableException ex) {
					ex.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		recordThread.start();

	}

	void finish() {
		line.stop();
		line.close();
		System.out.println("Finished");
	}

	public void cancel() {
		finish();
		if(wavFile.exists())
			wavFile.delete();
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}
}