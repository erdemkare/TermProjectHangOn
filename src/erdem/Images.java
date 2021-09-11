package erdem;

import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class Images 
{
	public BufferedImage playerImage, playerImageLeft,playerImageRight, enemyImage1,enemyImage2,enemyImage3, enemyImage4, enemyImage5;
	public LinkedList<BufferedImage> explosionImages;
	public int playerImageWidth, playerImageLeftWidth,playerImageRightWidth, enemyImage1Width,enemyImage2Width,enemyImage3Width, enemyImage4Width, enemyImage5Width;
	public int playerImageHeight, playerImageLeftHeight,playerImageRightHeight, enemyImage1Height,enemyImage2Height,enemyImage3Height, enemyImage4Height, enemyImage5Height;
	public LinkedList<BufferedImage> roads;

	public Images(Game game)
	{	
		playerImage = game.getPlayerImage();
		playerImageLeft = game.getPlayerLeftImage();
		playerImageRight = game.getPlayerRightImage();
		enemyImage1 = game.getEnemyImage1();
		enemyImage2 = game.getEnemyImage2();
		enemyImage3 = game.getEnemyImage3();
		enemyImage4 = game.getEnemyImage4();
		enemyImage5 = game.getEnemyImage5();
		explosionImages = new LinkedList<BufferedImage>(game.getExpolsionImages());
		roads = new LinkedList<BufferedImage>(game.getRoads());
	}	
}
