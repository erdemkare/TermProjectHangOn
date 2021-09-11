package erdem;

import java.awt.Graphics;
import java.awt.Rectangle;

//common things for entities
public interface Entity
{
	public void update();
	public void draw(Graphics g);
	public Rectangle getBounds(); //getting bounds for every entity for collision
	
	public double getX();
	public double getY();
}
