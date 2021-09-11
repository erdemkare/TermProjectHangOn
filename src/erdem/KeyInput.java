package erdem;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter 
{
	Game game;
	public KeyInput(Game game)
	{
		this.game = game;
	}
	public void keyPressed(KeyEvent e)
	{
		game.keyPressed(e); //when a key pressed it will call here and that will call same method in main
	}
	public void keyReleased(KeyEvent e) //when a key pressed it will call here and that will call same method in main
	{
		game.keyReleased(e);
	}
}

