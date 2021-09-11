package erdem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject implements Entity
{
	private double velX = 0;
	private double velY = 0;
	
	private BufferedImage player;
	private Game game;
	
	Animations anim;
	public boolean isCollided = false;

	public Player(double x, double y,Images images, Game game)
	{
		super(x, y);
		this.game = game;	
		this.player = images.playerImage;
		anim = new Animations(5, images.explosionImages.get(0), images.explosionImages.get(1), images.explosionImages.get(2), images.explosionImages.get(3), images.explosionImages.get(4), images.explosionImages.get(5), images.explosionImages.get(6), images.explosionImages.get(7), images.explosionImages.get(8), images.explosionImages.get(9), images.explosionImages.get(10), images.explosionImages.get(11), images.explosionImages.get(12),images.explosionImages.get(13));
	}


	@Override
	public void update()
	{ 
		
		if(!isCollided)
		{
			x+=velX; //do this for smooth movement (called 60 times in sec)
			y+=velY; //do this for smooth movement (called 60 times in sec)			
		}
		
		if(game.score>game.offSet+5300 && game.score<game.offSet+15000) //on right road if bike out of the road explode and finish
		{
			if(x>554)
			{
				isCollided = true;
			}
			game.isTurningRight = true; //for creating enemies checking whether we are turning or not

		}
		else if(game.score>game.offSet+25300 && game.score<game.offSet+35400) //on left road if bike out of the road explode and finish
		{
			if(x<153)
			{
				isCollided = true;
			}
			game.isTurningLeft = true;
		}
		else if(game.score>game.offSet+56000 && game.score<game.offSet+66100) //on left road if bike out of the road explode and finish
		{
			if(x<153)
			{
				isCollided = true;
			}
			game.isTurningLeft = true;

		}
		else {
			game.isTurningLeft = false;
			game.isTurningRight= false;
		}
		
		
		for (int i = 0; i < game.getEnemies().size(); i++) 
		{
			LinkedList<Enemy> enemies = game.getEnemyController().getEnemies();
			if(Physics.Collision(enemies.get(i), game.getPlayer()))
			{
				System.out.println("Collision from player");
				isCollided = true;
			}			
		}
		anim.runAnimation();
		if(x<=0)
		{
			x=0;
			isCollided = true;
		}
		if(x >= 800 -40)
		{
			x=800-40;
			isCollided = true;
		}
	}

	@Override
	public void draw(Graphics g)
	{
		if(isCollided)
			anim.drawAnimation(g, ((x<100)? -10 : x-75), y, 0);
		else
			g.drawImage(player, (int)x, (int)y, null);
		
	}
	

	
	public void setPlayerImage(BufferedImage player) {
		this.player = player;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public Rectangle getBounds(int width, int height) {
		// TODO Auto-generated method stub
		return super.getBounds(width, height);
	}
	
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		int width = player.getWidth();
		int height = player.getHeight();
		return getBounds(width, height);
	}
	
}
