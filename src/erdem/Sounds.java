package erdem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sounds 
{
	int count = 0;
	public Clip clip;
	public Clip menuMusic;
	public Clip inGameMusic;
	boolean isRunning = false;
	public boolean start = false;
	Game game;
	File file;
	public Sounds(Game game) 
	{

		this.game = game;
		
		 try {
			   file = new File("motorcylcleSound.aiff");
			   clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   isRunning = true;
		 } catch (Exception e) {
			   System.err.println(e.getMessage());
		 }		 
		 
		 try{
			 file = new File("motorcylcleSound.aiff");
			 inGameMusic = AudioSystem.getClip();
			 inGameMusic.open(AudioSystem.getAudioInputStream(file));
		 } catch (Exception e) {
			   System.err.println(e.getMessage());
		 }	
	 }
		
	//volume for increasing motoSound with acceleration
	public float getVolume() 
	{
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	public void setVolume(float volume) 
	{
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
	
	public void update()
	{	
			
		//sound volume changing with speed
		if(game.speed<20)
			setVolume(0.1F);
		else if(game.speed>=20 && game.speed<50)
			setVolume(0.2F);
		else if(game.speed>=50 && game.speed<80)
			setVolume(0.3F);
		else if(game.speed>=80 && game.speed<100)
			setVolume(0.3F);
		else if(game.speed>=190 && game.speed<120)
			setVolume(0.4F);
		else if(game.speed>=120 && game.speed<150)
			setVolume(0.5F);
		else if(game.speed>=150 && game.speed<180)
			setVolume(0.6F);
		else if(game.speed>=180 && game.speed<200)
			setVolume(0.7F);
		else if(game.speed>=200 && game.speed<220)
			setVolume(0.8F);
		else if(game.speed>=220 && game.speed<250)
			setVolume(0.9F);
		else if(game.speed>=250)
			setVolume(1F);
		if(game.speed == 0)
		{
			clip.stop();
			start = true;		
		}
		else if(start)			
		{
			//clip.open(AudioSystem.getAudioInputStream(file));
			clip.loop(1000);
			start = false;
		}

	}
}
