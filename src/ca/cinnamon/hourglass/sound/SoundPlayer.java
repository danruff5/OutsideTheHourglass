package ca.cinnamon.hourglass.sound;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author Brendan Fishback
 * Purpose: The sounds for the hour glass game
 * Adapted from: https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * Date 22-Jan-2017
 */

public enum SoundPlayer {
			STEP("Sounds/stepstone_1.wav"),
			SWORD("Sounds/sword sound.wav"),
			WALL("Sounds/metal.wav");

	
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH;
    } // Volume;

    public Volume volume = Volume.LOW;
    public Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    SoundPlayer(String fileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(fileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    /**
     * Plays the clip. If the clip is still playing it does not start.
     */
    public void play() {
        if (volume != Volume.MUTE && !clip.isRunning()) {
            // Set the clip to the start and play the clip.
            clip.setFramePosition(0);
            clip.start();
        }
    } // play ();

    /**
     * Load all of the sounds, read from disk.
     */
    public static void init() {
        values(); // calls the constructor for all the elements
    } // init ();
    
    
} // SoundEffect;