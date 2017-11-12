package de.fstraub.main;

import javax.sound.sampled.*;
import java.io.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class JavaSoundRecorder {
	// record duration, in milliseconds
	static final long RECORD_TIME = 60000; // 1 minute

	// path of the wav file
	File wavFile = new File("record.wav");

	// format of audio file
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	// the line from which audio data is captured
	volatile TargetDataLine line;

	Thread recordThread;

	/**
	 * Defines an audio format
	 */
	AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	/**
	 * Captures the sound and record into a WAV file
	 */
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

					System.out.println("Start capturing...");

					AudioInputStream ais = new AudioInputStream(line);

					System.out.println("Start recording...");

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

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	void finish() {
		line.stop();
		line.close();
		System.out.println("Finished");
	}

	public JavaSoundRecorder(){
		wavFile.deleteOnExit();
	}

	public void cancel() {
		finish();
		if(wavFile.exists())
			wavFile.delete();
	}
}