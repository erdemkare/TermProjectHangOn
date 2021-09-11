package erdem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject implements Entity
{
	private double velX = 0;
	private double velY = 0;
	
	private BufferedImage enemy; //enemy image
	private Game game; //to access game
	private EnemyController enemyC; //for controlling all enemies
	Images images; //all images in game
	Random random = new Random();
	Animations anim;
	private int enemyTimer = 0; //for moving enemyBike at y axis
	private int enemyTimerX = 0; //for moving enemyBike at x axis
	
	public boolean isCollided = false;
	public Enemy(double x, double y,Images images,EnemyController c, Game game)
	{
		super(x, y);
		this.game = game;	
		this.enemy = images.enemyImage5;
		this.enemyC = c;
		this.images = images;
		anim = new Animations(5, images.explosionImages.get(0), images.explosionImages.get(1), images.explosionImages.get(2), images.explosionImages.get(3), images.explosionImages.get(4), images.explosionImages.get(5), images.explosionImages.get(6), images.explosionImages.get(7), images.explosionImages.get(8), images.explosionImages.get(9), images.explosionImages.get(10), images.explosionImages.get(11), images.explosionImages.get(12),images.explosionImages.get(13));
	}


	@Override
	public void update() 
	{
		 
		if(!isCollided && !game.isPaused)
		{
			x+=velX; //do this for smooth movement (called 60 times in sec)
			y+=velY; //do this for smooth movement (called 60 times in sec)
			
			if(game.stageNum>=3) //after stage 2 enemy will be aggressive
			{
				if(enemyTimerX%15==0)
				{	
					if(y>=335 && y<450)
					{
						if(this.x < game.getPlayer().x)
							x+=10;
						else if(this.x > game.getPlayer().x)
							x-=10;
					}
					else {
						if(this.x < game.getPlayer().x)
							x+=15;
						else if(this.x > game.getPlayer().x)
							x-=15;
					}					
				}
			}
			else //if player is in stage 1 or 2 enemy will be going randomly
			{
				if(enemyTimerX%30==0)
				{
					int rand = random.nextInt(10-(-10))+(-10);
					x+=rand;
					System.out.println(rand);
					
				}
			}
			enemyTimerX++;
			
			
			if(enemyTimer%15==0)
			{
				if(y>425 && game.getSpeed()>0) //get faster if you are close to the player
					y+=10;
				else if(y<=425 && game.getSpeed()>0)
					y+=5;
				if(game.getSpeed()==0)
					y-=5;
			}
			enemyTimer++;
			

			
			//getting bigger while its getting closer to player
			if(y<350)
				enemy = images.enemyImage5;
			else if(y>=350 && y<375)
				enemy = images.enemyImage4;
			else if(y>=375 && y<400)
				enemy = images.enemyImage3;
			else if(y>=400 && y<425)
				enemy = images.enemyImage2;
			else if(y>=425 && y<600)
			{
				enemy = images.enemyImage1;
				y++;
			}

			if(y>600)  //if enemyBike is out of screen remove it
			{
				enemyC.removeEnemyBike(this);
				game.getEnemies().remove(this);			
			}
			else if(y<325)
			{
				enemyC.removeEnemyBike(this);
				game.getEnemies().remove(this);
			}
			//System.out.println(y);
		}
		

		
		anim.runAnimation();

		if(x<= 0)
			x=0;
		if(x >= 800 -40)
			x=800-40;

	}


	@Override
	public void draw(Graphics g) 
	{
		//g.drawImage(enemy, (int)x, (int)y, null);
		
		if(Physics.Collision(this, game.getPlayer())) //if collision occurs 
		{
			anim.drawAnimation(g, x-50, y, 0);
			isCollided = true;
			System.out.println("Collision");
			
		}
		else {
			isCollided = false;
			g.drawImage(enemy, (int)x, (int)y, null);

		}
		
	}
	

	
	public void setPlayerImage(BufferedImage e) {
		this.enemy = e;
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
		int width = enemy.getWidth();
		int height = enemy.getHeight();
		return getBounds(width, height);
	}
}