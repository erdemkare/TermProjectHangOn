package erdem;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CreateMap 
{
	Game game;
	BufferedImage currentBackground1;
	BufferedImage currentBackground2;
	LinkedList<BufferedImage> roads;
	Images images;
	int imageChangeCount = 0;
	int offset = 0;
	public CreateMap(Game game) 
	{
		this.game = game;
		images = new Images(game);
		roads = new LinkedList<BufferedImage>(images.roads);
		currentBackground1 = roads.get(0);
	}
	public void update()
	{
		imageChangeCount++;
		//creating map
		if(game.speed==0)
		{
			game.setBackGround(currentBackground1); //if speed is 0 then set bacground as last road
		}
		else
		{
			currentBackground1 = roads.get(0); //default roads
			currentBackground2 = roads.get(1);
			
			if(game.score >= offset+5000 && game.score < offset+5100) //right start
			{
				currentBackground1 = roads.get(10);
				currentBackground2 = roads.get(11);
			}
			else if(game.score >= offset+5100 && game.score < offset+5200)
			{
				currentBackground1 = roads.get(12);
				currentBackground2 = roads.get(13);
			}
			else if(game.score >=offset+5200 && game.score < offset+5300)
			{
				currentBackground1 = roads.get(14);
				currentBackground2 = roads.get(15);
			}
			else if(game.score >=offset+5300 && game.score< offset+20000)
			{
				game.isTurningRight = true;
				game.getPlayer().x-=4.5; //going right so player will go left automatically
				currentBackground1 = roads.get(16);
				currentBackground2 = roads.get(17);
			}
			else if(game.score>=offset+20000 && game.score< offset+20100)
			{
				currentBackground1 = roads.get(15);
				currentBackground2 = roads.get(14);
			}
			else if(game.score>=offset+20100 && game.score<offset+20200)
			{
				currentBackground1 = roads.get(13);
				currentBackground2 = roads.get(12);
			}
			else if(game.score>=offset+20200 && game.score<offset+20300)
			{
				currentBackground1 = roads.get(11);
				currentBackground2 = roads.get(10);
			}
			else if(game.score>=offset+20300 && game.score < offset+25000) //right end straight start
			{
				currentBackground1 = roads.get(0);
				currentBackground2 = roads.get(1);
			} 
			else if(game.score >= offset+25000 && game.score <offset+ 25100) //straight end left start
			{
				currentBackground1 = roads.get(2);
				currentBackground2 = roads.get(3);
			}
			else if(game.score >=offset+25100 && game.score < offset+25200)
			{
				currentBackground1 = roads.get(4);
				currentBackground2 = roads.get(5);
			}
			else if(game.score >=offset+25200 && game.score < offset+25300)
			{
				currentBackground1 = roads.get(6);
				currentBackground2 = roads.get(7);
			}
			else if(game.score >=offset+25300 && game.score< offset+35400)
			{
				game.isTurningLeft = true;
				game.getPlayer().x+=4.5; //going left so player will go right automatically		
				currentBackground1 = roads.get(8);
				currentBackground2 = roads.get(9);
			}
			else if(game.score>=offset+35400 && game.score< offset+35500)
			{
				currentBackground1 = roads.get(7);
				currentBackground2 = roads.get(6);
			}
			else if(game.score>=offset+35500 && game.score<offset+35600)
			{
				currentBackground1 = roads.get(5);
				currentBackground2 = roads.get(4);
			}
			else if(game.score>=offset+35600 && game.score<offset+35700)
			{
				currentBackground1 = roads.get(3);
				currentBackground2 = roads.get(2);
			}
			else if(game.score>=offset+35700 && game.score<offset+55700) //left end straight start
			{
				currentBackground1 = roads.get(0);
				currentBackground2 = roads.get(1);
			}			
			else if(game.score >= offset+55700 && game.score <offset+ 55800) //straight end left start
			{
				currentBackground1 = roads.get(2);
				currentBackground2 = roads.get(3);
			}
			else if(game.score >=offset+55800 && game.score < offset+55900)
			{
				currentBackground1 = roads.get(4);
				currentBackground2 = roads.get(5);
			}
			else if(game.score >=offset+55900 && game.score < offset+56000)
			{
				currentBackground1 = roads.get(6);
				currentBackground2 = roads.get(7);
			}
			else if(game.score >=offset+56000 && game.score< offset+66100)
			{
				game.isTurningLeft = true;
				game.getPlayer().x+=4.5; //going left so player will go right automatically  		
				currentBackground1 = roads.get(8);
				currentBackground2 = roads.get(9);
			}
			else if(game.score>=offset+66100 && game.score< offset+66200)
			{
				currentBackground1 = roads.get(7);
				currentBackground2 = roads.get(6);
			}
			else if(game.score>=offset+66200 && game.score<offset+66300)
			{
				currentBackground1 = roads.get(5);
				currentBackground2 = roads.get(4);
			}
			else if(game.score>=offset+66300 && game.score<offset+66400)
			{
				currentBackground1 = roads.get(3);
				currentBackground2 = roads.get(2);
			}
			else if(game.score>=offset+66400 && game.score<offset+76000) //left end straight start
			{
				currentBackground1 = roads.get(0);
				currentBackground2 = roads.get(1);
			}
			if(game.score >= offset+76000 && game.score < offset+76200)
			{
				currentBackground1 = roads.get(18);
				currentBackground2 = roads.get(19);
			}
			else if(game.score >=offset+76200 && game.score < offset+76400)
			{
				currentBackground1 = roads.get(20);
				currentBackground2 = roads.get(21);
			}
			else if(game.score >=offset+76400 && game.score < offset+76600)
			{
				currentBackground1 = roads.get(22);
				currentBackground2 = roads.get(23);
			}
			else if(game.score >=offset+76600 && game.score < offset+76800)
			{
				currentBackground1 = roads.get(24);
				currentBackground2 = roads.get(25);
			}
			else if(game.score >=offset+76800 && game.score < offset+77000)
			{
				currentBackground1 = roads.get(26);
				currentBackground2 = roads.get(27);
			}
			if(game.speed<50)
			{
				if(imageChangeCount%20<10) //since in this game 60 frames changing in 1 sec this means change image 6 times in a sec
				{
					game.setBackGround(currentBackground1);
					imageChangeCount++;
				}
				else 
				{
					game.setBackGround(currentBackground2);
					imageChangeCount++;
				}
			}
			else if(game.speed>=50 && game.speed<100)
			{
				if(imageChangeCount%15<7) //since in this game 60 frames changing in 1 sec this means change image every 1/8 sec
				{
					game.setBackGround(currentBackground1);
					imageChangeCount++;
				}
				else {
					game.setBackGround(currentBackground2);
					imageChangeCount++;
				}
			}
			else if(game.speed>=100)
			{
				if(imageChangeCount%6<3) //since in this game 60 frames changing in 1 sec this means change image every 1/20 sec
				{
					game.setBackGround(currentBackground1);
					imageChangeCount++;
				}
				else {
					game.setBackGround(currentBackground2);
					imageChangeCount++;
				}
			}
		}
		//creating map done
	}
}
