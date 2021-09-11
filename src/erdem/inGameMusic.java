package erdem;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class inGameMusic {
	void playMusic (String inGameMusicPath) {
		try {
			File inGameMusic = new File(inGameMusicPath);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(inGameMusic);
			Clip clip2 = AudioSystem.getClip();
			if(inGameMusic.exists())
			{
				clip2.open(audioInput);
				clip2.open();
				clip2.start();		
			}
			else
			{
				System.out.println("Can't find the file");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
