package erdem;
import java.util.LinkedList;


public class Physics //handle whether we have collisions or not 
{ 
	//is enemy collided with player
	public static boolean Collision(Enemy enemy, Player player)
	{
		if(enemy.getBounds().intersects(player.getBounds()))
		{				
			return true;
		}
		return false;
	}
	public static boolean Collision(Player player, Enemy enemy)
	{
		if(player.getBounds().intersects(enemy.getBounds()))
		{				
			return true;
		}
		return false;
	}

}