/**
 * Program Name: MusicPlayer.java
 * Purpose: 
 * Coder: Brendan Fishback
 * Date: Feb 25, 2017
 */
package ca.cinnamon.hourglass.sound;
import java.io.File;

import javax.sound.sampled.*;

public class MusicPlayer {
	
	private Clip clip;
	
	public static MusicPlayer dungeonTrack = new MusicPlayer("./Sounds/dungeon.wav");
//	public static MusicPlayer otherTrack = new MusicPlayer("....");
	
	public MusicPlayer (String fileName) {
		try {
      File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		if(clip == null) return;
		clip.stop();
	}
	
	public void loop() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return clip.isActive();
	}
}

