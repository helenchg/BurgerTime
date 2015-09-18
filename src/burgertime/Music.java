/**
 * 
 */
package burgertime;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * In-game Music.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 19, 2014.
 */
public class Music implements Runnable{
	
	private Clip clip =null;

	/**
	 * Constructor for music.
	 *
	 */
	public Music(){
		String soundFile = "mariokart.wav";
		AudioInputStream audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundFile));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			this.clip.open(audioIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.clip.loop(clip.LOOP_CONTINUOUSLY);
		
	}
}