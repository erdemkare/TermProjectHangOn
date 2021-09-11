package erdem;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MenuMusic {
	public Clip clip;
	void playMusic (String musicLocation) {
	try {
		File menuMusic = new File(musicLocation);
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(menuMusic);
		Clip clip = AudioSystem.getClip();
		if(menuMusic.exists())
		{	
			clip.open(audioInput);
			clip.open();
			clip.start();		
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
	 // stop and loop the clip
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
     }
	/*void playMusic (String musicLocation) {
		
		File menuMusic = new File(musicLocation);
		try {
			if(menuMusic.exists())
			{	
				Clip clip = AudioSystem.getClip();
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(menuMusic);
				clip.open(audioInput);
				clip.open();	
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

    // play, stop, loop the sound clip

    public void play(){
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
        }*/
}

	

	/*void playMusic (String musicLocation) {
		try {
			File menuMusic = new File(musicLocation);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(menuMusic);
			Clip clip = AudioSystem.getClip();
			if(menuMusic.exists())
			{	
				clip.open(audioInput);
				clip.open();
				clip.start();		
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
	}*/
	

