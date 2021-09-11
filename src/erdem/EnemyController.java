package erdem;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;




//for controlling all enemies together
public class EnemyController
{
	private LinkedList<Enemy> enemies = new LinkedList<Enemy>(); //to arrange enemies together
	Random random = new Random();
	private Game game;
	Images images;
	
	public EnemyController(Images images, Game game) 
	{
		this.images = images;
		this.game = game;
	}
	
	public void update()
	{
		for (int i = 0; i < enemies.size(); i++) 
		{
			enemies.get(i).update();			
		}
	}
	public void draw(Graphics g)
	{
		for (int i = 0; i < enemies.size(); i++) 
		{
			enemies.get(i).draw(g);			
		}
	}
	
	public void addEnemyBike(int enemyCount) //adding enemy with count
	{
		for(int i=0 ; i< enemyCount; i++)
		{
			enemies.add(new Enemy(random.nextInt(430-375)+375,340, images,this, game));
		}
	}
	public void addEnemyBike()
	{
		Enemy e;
		if(game.isTurningLeft==true)
			e = new Enemy(344,358, images,this, game);
		else if(game.isTurningRight==true)
			e = new Enemy(458,353, images,this, game);
		else
			e = new Enemy(random.nextInt(430-375)+375,340, images,this, game);
				
		enemies.add(e); //creating enemy between roads (random pos. in x-axis) but far away from player
		
	}
	public void removeEnemyBike(Enemy enemyBike)
	{
		enemies.remove(enemyBike);
	}
	public void removeAllEnemyBikes()
	{
		enemies.removeAll(enemies);
	}
	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}
	public void setEnemies(LinkedList<Enemy> enemies) {
		this.enemies = enemies;
	}
}
