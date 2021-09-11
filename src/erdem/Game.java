package erdem;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;





public class Game extends Canvas implements Runnable
{
	public static Game game;
    public static String currentUser, currentPassword; // information for current user
	private static User user = new User(); // User object
	public static final int WIDTH = 800; //Width of our frame
	public static final int HEIGHT = 600;  //height of our frame
	public static boolean running = false; //game run depends on this
	private static Thread thread;
	public static JFrame frame; //game screen
	static JFrame initialScreen ; //first opening
	static JFrame gameOverScreen ; //when you die
	static JFrame scoresScreen; //score screen
	
	private BufferedImage playerImage = null;
	private BufferedImage playerLeftImage = null;
	private BufferedImage playerRightImage = null;
	private BufferedImage backGround = null; //background image
	private BufferedImage speedImage = null;
	private BufferedImage stageImage = null;
	private BufferedImage scoreImage = null;
	private BufferedImage enemyImage1 = null;
	private BufferedImage enemyImage2 = null;
	private BufferedImage enemyImage3 = null;
	private BufferedImage enemyImage4 = null;
	private BufferedImage enemyImage5 = null;
	private BufferedImage timeImage = null;
	private BufferedImage hangOnImage = null;
	private ArrayList<BufferedImage> expolsionImages; //14 explosion image for animation
	private ArrayList<BufferedImage> roads;	
	Sounds sounds;
	BufferedImage currentBackground1; //to determine which bg's will be shown
	BufferedImage currentBackground2;
	Images images; //to access all images

	private Player player; //player object
	private EnemyController enemyController; //controlling enemies adding,removing etc.
	private LinkedList<Enemy> enemies; //all enemies
	
	private int imageChangeCount = 0;
	private boolean isAccelerating = false; //to increase speed
	private boolean isBreaking = false; //for slowing down faster
	
	public int speed = 0;
	public int speedTimer = 0; //for increasing speed;
	public int score = 0; //score += speed
	public int scoreTimer = 0; //for increasing score
	public int time = 60; //time will be shown in screen
	public int timeTimer = 0; //setting time to be decreased every 1 sec
	public int finishTimer = 1; //time to wait animation and then finishin game
	
	private boolean isEnemyAdded = false; //checking is enemy added at given sec
	public static boolean isPaused = false; //checking is game paused or not
	public static boolean isRestartRequested = false; //for restart action while playing
	public int isRestartRequesterdTimer = 0; //timer for checking is restart requested
	public int offSet = 0; //for repeating map after checkPoint
	public int stageNum = 1; //game has 5 stages
	public boolean isTurningLeft = false;
	public boolean isTurningRight = false;
	CreateMap map = null;
	public static void main(String args[])
	{
		game = new Game();

		// setting size of the component
		game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		
		frame = new JFrame("Hang On");

		// Menu items 
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem credits = new JMenuItem("Credits");
		JMenuItem login = new JMenuItem("Login");
		JMenuItem register = new JMenuItem("Register");
		JMenuItem restartGame = new JMenuItem("Restart Game");
		JMenuItem scores = new JMenuItem("Scores");
		JMenuItem pauseAndResume = new JMenuItem("Pause/Resume");
		// Menus
		JMenu file = new JMenu("Game");
		JMenu userMenu = new JMenu("User");
		JMenu help = new JMenu("Help");
		
		//Menu items added to File menu
		file.add(restartGame);
		file.add(login);
		file.add(register);
		file.add(scores);
		file.add(quit);
		file.add(pauseAndResume);

		//Menu items added to Help menu
		help.add(credits);
		
		//Menu items added user menu
		userMenu.add(login);
		userMenu.add(register);
		
		
		login.setEnabled(false);
		register.setEnabled(false);
		scores.setEnabled(false);
		
		// set menu bar and add it to the frame
	    JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar); 
	
		// Menus added to the menu bar
		menuBar.add(file);
		menuBar.add(userMenu);
		menuBar.add(help);
		
		
		
		frame.add(game);
		frame.pack(); //Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //in the middle of the screen
		frame.setVisible(false); 
		game.createInitialScreen(); //create initial screen before game starts for login or register.


		//game.start(); //Starting our game
		class restartGameAction implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				isRestartRequested = true;
				
			};
		}
		restartGame.addActionListener(new restartGameAction());
		class pauseAndResumeAction implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				 isPaused = !isPaused;
			};
		}
		pauseAndResume.addActionListener(new pauseAndResumeAction());
		//Quit action at menuBar
		class quitAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
					System.exit(0);				
			}
		}
		quit.addActionListener(new quitAction());
		//Credits Action at menuBar
		class creditsAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(frame, "\nErdem Erdem\n\n 20170702081 \n\n");
			}
		}
		credits.addActionListener(new creditsAction());	
		
		// Login action at menuBar
		class loginAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(user.loginUser(game)==1)
				{
					game.start();
				}
			}
		}
		login.addActionListener(new loginAction());
		
		// Register action at menuBar
		class registerAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				user.registerUser();
			}
		}
		register.addActionListener(new registerAction());
		
		//Scores action at menuBar
		class scoresAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				game.createScoresScreen();
			}

		}
		scores.addActionListener(new scoresAction());
			
	}

	public void init()
	{
		requestFocus(); //play without a first click on screen
		BufferedImageLoader loader = new BufferedImageLoader();
		
		sounds = new Sounds(game);
		// load all images that used in the game
		playerImage = loader.loadImage("/player.png");
		playerLeftImage = loader.loadImage("/playerLeft.png");
		playerRightImage = loader.loadImage("/playerRight.png");
		enemyImage1 = loader.loadImage("/enemy1.png");
		enemyImage2 = loader.loadImage("/enemy2.png");
		enemyImage3 = loader.loadImage("/enemy3.png");
		enemyImage4 = loader.loadImage("/enemy4.png");
		enemyImage5 = loader.loadImage("/enemy5.png");
		expolsionImages = new ArrayList<BufferedImage>();
		for (int i = 0; i < 14; i++) 
		{
			expolsionImages.add(loader.loadImage("/Explosion/explosion"+i+".png"));
			System.out.println("/Explosion/explosion"+i+".png");
		}

		roads = new ArrayList<BufferedImage>();

		roads.add(loader.loadImage("/Roads/road1a.png"));
		roads.add(loader.loadImage("/Roads/road2b.png"));
		
		//loading left roads
		for (int i = 1; i<=4 ; i++)
		{
			for(int j = 1; j<=2; j++)
			{
				if(j%2==0) {
					roads.add(loader.loadImage("/Roads/leftRoad"+i+"a"+".png"));
				}
				else {
					roads.add(loader.loadImage("/Roads/leftRoad"+i+"b"+".png"));
				}
			}
		}
		
		//loading right roads
		for (int i = 1; i<=4 ; i++)
		{
			for(int j = 1; j<=2; j++)
			{
				if(j%2==0) {
					roads.add(loader.loadImage("/Roads/rightRoad"+i+"a"+".png"));
				}
				else {
					roads.add(loader.loadImage("/Roads/rightRoad"+i+"b"+".png"));
				}
			}
		}
		
		//loading checkPoint roads
		for (int i = 1; i<=5 ; i++)
		{
			for(int j = 1; j<=2; j++)
			{
				if(j%2==0) {
					roads.add(loader.loadImage("/CheckPointRoads/checkPointRoad"+i+"a"+".png"));
					System.out.println("/CheckPointRoads/checkPointRoad"+i+"a"+".png");
				}
				else {
					roads.add(loader.loadImage("/CheckPointRoads/checkPointRoad"+i+"b"+".png"));
					System.out.println("/CheckPointRoads/checkPointRoad"+i+"b"+".png");
				}
			}
		}
		
		currentBackground1 = roads.get(0);
		currentBackground2 = roads.get(1);
		map = new CreateMap(game);
		System.out.println(roads);
		speedImage = loader.loadImage("/speed.png");
		stageImage = loader.loadImage("/stage.png");
		backGround = roads.get(0); //initially road image is road1
		scoreImage = loader.loadImage("/score.png");
		timeImage = loader.loadImage("/time.png");
		
		images = new Images(this);
		player = new Player(WIDTH/2-27, HEIGHT-100, images, this); //returning the game
		enemyController = new EnemyController(images, this);
		enemyController.addEnemyBike();
		enemies = new LinkedList<Enemy>(enemyController.getEnemies());
		isTurningLeft = false;
		isTurningRight = false;
		
		this.addKeyListener(new KeyInput(this)); //put this otherwise key inputs will not work
		
	}
	
	public synchronized void start()
	{
		  if(running)
			  return;
		  running = true; //if it is not running run our game loop
		  thread = new Thread(this);
		  thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		running = false; //stop our game loop
		try {
			thread.join(); //Waits for this thread to die.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	@Override
	public void run()
	{
		init();// initializing the game frame	
		
		long pastTime = System.nanoTime();
		final double nanoSeconds = 1000000000 / 60;// 60 is frame count per second
		double change = 0 ;		
		while(running)
		{			
				long now = System.nanoTime();
				change += (now - pastTime) / nanoSeconds;
				pastTime = now;
				if (change >= 1) 
				{
					if(!isPaused)
						update();
					change--;
				}
				try
				{	
						if(!isPaused)
							draw();
				} catch (Exception e){}		

		}		
	}	
	public void update()
	{	
		map.update(); //updating map
		isTurningLeft = false;
		isTurningRight = false;
		player.update();
		enemyController.update(); //update of every enemy
		sounds.update();
		imageChangeCount++;
		if(stageNum==6) //finish game stages done
		{
			running = false;
			sounds.clip.stop(); //closing sound if game stops because of time
			sounds.start = true;
			user.getUsersToArray(); // get users to array for score comparison
			
			for(int i =0; i< user.getUserList().size() ; i++) // loop for all users in the arrayList
			{
				// if current user name password equals any user in the arrayList
				// and score of current user is greater than old score
				if (user.getUserList().get(i).getUsername().equals(currentUser) &&
					user.getUserList().get(i).getPassword().equals(currentPassword) && 
					user.getUserList().get(i).getScore() < score)
				{
					// set score of current user
					user.getUserList().get(i).setScore(score);
				}
			}			    	
			createGameOverScreen();// show game over screen				
		}
		else if(map.offset+77000<=score && score<map.offset+77500) //checkpoint is passed
		{
			stageNum++;
			time=60;
			offSet = score;
			map.offset = offSet;			
		}
		
		
		if(player.isCollided==false)
		{	
			//if player is not accelerating decrease speed
			if(isAccelerating==false)
			{
				if(imageChangeCount%5==0 && speed>0 && isBreaking==false) //decrease speed -5 every half sec
					speed-=5;
				if(imageChangeCount%5==0 && speed>0 && isBreaking==true) //decrease speed -15 every half sec
					speed-=15; 
				if(speed<0)
					speed = 0;
			}			
			else if(isAccelerating) //if up button is on increase speed
			{
				if(speedTimer%10==0)
				{
					//speed increasing differently depending on speed for more realistic acceleration
					if (speed<100)
						speed+=10;
					else if(speed>=100 && speed<150)
						speed+=5;
					else if(speed>=150 && speed < 200)
						speed+=2;
					else if(speed>=200 && speed <278)
						speed+=1;
					
				}
				speedTimer++;
			}
			if(scoreTimer%10==0 && speed>0) //score = score + speed at every 60 frame
			{
				score+=speed;
			}
			scoreTimer+=1;
			
			if(timeTimer%60==0) //creating real timer on  top of the screen
			{	
				time -= 1;
				if(time==0) //if time is 0 game over
				{
					running = false;
					sounds.clip.stop(); //closing sound if game stops because of time
					sounds.start = true;
					isTurningLeft=false;
					isTurningRight=false;
					user.getUsersToArray(); // get users to array for score comparison
					
					for(int i =0; i< user.getUserList().size() ; i++) // loop for all users in the arrayList
					{
						// if current sername password equals any user in the arrayList
						// and score of current user is greater than old score
						if (user.getUserList().get(i).getUsername().equals(currentUser) &&
							user.getUserList().get(i).getPassword().equals(currentPassword) && 
							user.getUserList().get(i).getScore() < score)
						{
							// set score of current user
							user.getUserList().get(i).setScore(score);
						}
					}			    	
					createGameOverScreen();// show gameover screen				
				}
			}
			timeTimer++;
			
			if(time%15==0 && !isEnemyAdded) //add enemy at every 15 sec
			{ 
				enemyController.addEnemyBike();
				enemies = enemyController.getEnemies();
				isEnemyAdded=true;
			}
			if(time%15==14)
				isEnemyAdded=false;
			
		}
		else //is collision occurs 
		{
			speed=0;
			stageNum=1;
			if(finishTimer%120==0) //wait 2 seconds for collision animation
			{
				running = false;
				isTurningLeft=false;
				isTurningRight=false;
				user.getUsersToArray(); // get users to array for score comparison
				
				for(int i =0; i< user.getUserList().size() ; i++) // loop for all users in the arrayList
				{
					// if current sername password equals any user in the arrayList
					// and score of current user is greater than old score
					if (user.getUserList().get(i).getUsername().equals(currentUser) &&
						user.getUserList().get(i).getPassword().equals(currentPassword) && 
						user.getUserList().get(i).getScore() < score)
					{
						// set score of current user
						user.getUserList().get(i).setScore(score);
					}
				}
		        user.setUsersToFile();// after all user operations on the arraylist, write all users to the file
				createGameOverScreen();// show gameover screen
			}
			finishTimer++;
		}
		if(isRestartRequesterdTimer%2==0 && isRestartRequested)
		{
			speed = 0;
			score = 0;
			time = 60;	
			stageNum=1;
			map.currentBackground1 = roads.get(0);
			isTurningLeft = false;
			isTurningRight = false;
			enemies.removeAll(enemies);
			enemyController.removeAllEnemyBikes();
			isRestartRequested = false;
		}
		isRestartRequesterdTimer++;
	}
	
	public void draw()
	{
		BufferStrategy bs = this.getBufferStrategy(); //return used buffer strategy
		if(bs == null) // previous buffer disposed, returns null
		{
			// creates 3 frames before drawing the frame
			createBufferStrategy(3); //method from canvas loading 3 images if it has time just before showing 
			
			return;
		}
		Graphics g = bs.getDrawGraphics(); //graphics contents for drawing buffers		
		g.clearRect(0, 0, 1000, 1000);	
		

		
		g.drawImage(backGround, 0, 0, WIDTH, HEIGHT, this); //drawing background
		g.setColor(Color.WHITE); //color for score speed
		g.setFont(new Font("Arial", 25, 25)); 
		
		g.drawImage(scoreImage, WIDTH-220, 10, this); 		//drawing scoreImage
		g.drawString(Integer.toString(score), WIDTH-90, 35);//drawing Score String
		g.drawImage(speedImage,WIDTH-200,50,this);			//drawing speedImage
		g.drawString(Integer.toString(speed), WIDTH-90, 72);//draw speed String
		
		g.drawImage(stageImage,10,10,this);			//drawing stageImage
		g.drawString(Integer.toString(stageNum), 125, 35);//draw stage String
		
		g.drawImage(timeImage,352,10,this);			//drawing speedImage
		g.drawString(Integer.toString(time), 387, 70);//draw time String
		player.draw(g); 
		enemyController.draw(g); //draw for every enemy	

		g.dispose(); // releases resources
		bs.show(); // shows the frame
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT && speed>0 &&!isPaused) //right clicked and speed is not 0
		{
			player.setPlayerImage(playerRightImage);
			player.setVelX(5); //move right
		}
		else if(key == KeyEvent.VK_LEFT && speed>0 &&!isPaused) //left clicked and speed is not 0
		{
			player.setPlayerImage(playerLeftImage);	
			player.setVelX(-5); //move left
		}
		else if(key == KeyEvent.VK_DOWN &&!isPaused)
		{
			isBreaking = true;
			isAccelerating = false;
		}
		else if(key == KeyEvent.VK_UP && !isPaused)
		{
			isAccelerating = true;
			isBreaking = false;
		}
		else if(key == KeyEvent.VK_P)
		{
			isPaused = true;
		}
		else if(key == KeyEvent.VK_C)
		{
			isPaused = false;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT)
		{
			player.setPlayerImage(playerImage); //if key released then player will be normal bike
			player.setVelX(0); //stop moving right
		}
		else if(key == KeyEvent.VK_LEFT)
		{
			player.setPlayerImage(playerImage);  //if key released then player will be normal bike
			player.setVelX(0); //stop moving left
		}
		else if(key == KeyEvent.VK_DOWN)
		{
			isBreaking = false;
		}
		else if(key == KeyEvent.VK_UP)
		{			
			isAccelerating = false; //set false to decrease speed
		}

	}
	
	
	
	public int getSpeed() {
		return speed;
	}
	public int getScore() {
		return score;
	}
	public BufferedImage getPlayerImage() {
		return playerImage;
	}
	public BufferedImage getPlayerLeftImage() {
		return playerLeftImage;
	}
	public BufferedImage getPlayerRightImage() {
		return playerRightImage;
	}	
	public BufferedImage getEnemyImage1() {
		return enemyImage1;
	}
	public BufferedImage getEnemyImage2() {
		return enemyImage2;
	}
	public BufferedImage getEnemyImage3() {
		return enemyImage3;
	}
	public BufferedImage getEnemyImage4() {
		return enemyImage4;
	}
	public BufferedImage getEnemyImage5() {
		return enemyImage5;
	}
	public Player getPlayer() {
		return player;
	}
	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}
	public ArrayList<BufferedImage> getExpolsionImages() {
		return expolsionImages;
	}
	public EnemyController getEnemyController() {
		return enemyController;
	}
	public void setBackGround(BufferedImage img)
	{
		backGround = img;
	}
	public ArrayList<BufferedImage> getRoads() {
		return roads;
	}

	private void createInitialScreen()
	{
		initialScreen = new JFrame("Hang On");
		initialScreen.setBounds(200,200,800,600);
		initialScreen.getContentPane().setLayout(null);
		initialScreen.setLocationRelativeTo(null);
		initialScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialScreen.setContentPane(new JLabel(new ImageIcon("Assets/HangOn.png")));
		
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem credits = new JMenuItem("Credits");
		JMenuItem login = new JMenuItem("Login");
		JMenuItem register = new JMenuItem("Register");
		JMenuItem playGame = new JMenuItem("Play Game");
		JMenuItem scores = new JMenuItem("Scores");
		JMenuItem controls = new JMenuItem("Controller");


		JMenu file = new JMenu("Game");
		JMenu userMenu = new JMenu("User");
		JMenu help = new JMenu("Help");
		
		file.add(playGame);
		file.add(scores);
		file.add(quit);
		
		userMenu.add(login);
		userMenu.add(register);
		

		help.add(credits);
		help.add(controls);
		
	    JMenuBar menuBar = new JMenuBar();
		initialScreen.setJMenuBar(menuBar); 
	
		menuBar.add(file);
		menuBar.add(userMenu);
		menuBar.add(help);

		
		class playGameAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
					if(user.loginUser(game)==1)
					{
						initialScreen.setVisible(false);
						frame.setVisible(true);
						game.start();
					}
			}
		}
		playGame.addActionListener(new playGameAction());

		class quitAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
					System.exit(0);				
			}
		}
		quit.addActionListener(new quitAction());

		class creditsAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(frame, "\nErdem Erdem\n\n 20170702081 \n\n");
			}
		}
		credits.addActionListener(new creditsAction());

		class controlsAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(frame, "\n Move - Arrow Keys\n\n Pause - P\n\n Continue - C", "Controls",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		controls.addActionListener(new controlsAction());
		
		class loginAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(user.loginUser(game)==1)
				{
					initialScreen.setVisible(false);
					frame.setVisible(true);
					game.start();	
				}
			}
		}
		login.addActionListener(new loginAction());
	
		class registerAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				//User user = new User();
				user.registerUser();
			}

		}
		register.addActionListener(new registerAction());

		class scoresAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
			System.out.println("scores");
			initialScreen.setVisible(false);
			game.createScoresScreen();
			}

		}
		scores.addActionListener(new scoresAction());
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(Color.white);
		usernameLabel.setBounds(initialScreen.getBounds().width/2 - 45, initialScreen.getBounds().height/2 + 50, 80, 20);
		
		JTextField usernameTF = new JTextField();
		usernameTF.setBounds(initialScreen.getBounds().width/2 - 100, initialScreen.getBounds().height/2 + 70, 180, 30);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.white);
		passwordLabel.setBounds(initialScreen.getBounds().width/2 - 45, initialScreen.getBounds().height/2 + 100, 80, 20);
		
		JPasswordField passwordTF = new JPasswordField();
		passwordTF.setBounds(initialScreen.getBounds().width/2 -100, initialScreen.getBounds().height/2 + 120, 180, 30);

		JButton playButton = new JButton("Play");
		playButton.setBounds(initialScreen.getBounds().width/2 - 50, initialScreen.getBounds().height/2 + 160, 80, 50);
		
		JButton musicButton = new JButton("on/off");
		musicButton.setBounds(initialScreen.getBounds().width/2 - 200, initialScreen.getBounds().height/2 + 200, 100, 40);
		
		String menuMusicPath= "menuMusic.wav";
		MenuMusic menuMusicObject = new MenuMusic();
		menuMusicObject.playMusic(menuMusicPath);
		
		
		
		
		initialScreen.setResizable(false);
		
		initialScreen.add(usernameLabel);
		initialScreen.add(usernameTF);
		initialScreen.add(passwordLabel);
		initialScreen.add(passwordTF);
		initialScreen.add(playButton);
		initialScreen.add(musicButton);
		
		

		playButton.addMouseListener(new MouseListener() 
		{
			
			@Override
			public void mouseReleased(MouseEvent e) 
			{				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				

					if(user.loginUser(usernameTF.getText(),passwordTF.getText())==1)
					{
						initialScreen.setVisible(false);
						frame.setVisible(true);
						game.start();
						menuMusicObject.stop();
						//Starts in game music
						String inGameMusicPath= "inGameMusic.wav";
						MenuMusic inGameMusicObject = new MenuMusic();
						inGameMusicObject.playMusic(inGameMusicPath);	
					}
					else
					{

					}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {				
			}
		});

		
		initialScreen.setVisible(true);
	}
	private void createGameOverScreen()
	{
		gameOverScreen = new JFrame("Hang On");
		gameOverScreen.setBounds(200,200,800,600);
		gameOverScreen.getContentPane().setLayout(null);
		gameOverScreen.setLocationRelativeTo(null);
		gameOverScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameOverScreen.setContentPane(new JLabel(new ImageIcon("Assets/HangOn.png")));
		
		JLabel scoresLabel = new JLabel("Highscores");
		scoresLabel.setForeground(Color.white);
		scoresLabel.setFont(new Font("Stencil", Font.BOLD, 24));
		scoresLabel.setBounds(initialScreen.getBounds().width/2 - 80, initialScreen.getBounds().height/2 + 50, 150, 20);

		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem credits = new JMenuItem("Credits");
		JMenuItem login = new JMenuItem("Login");
		JMenuItem register = new JMenuItem("Register");
		JMenuItem restartGame = new JMenuItem("Restart Game");
		JMenuItem scores = new JMenuItem("Scores");

		JMenu file = new JMenu("Game");
		JMenu userMenu = new JMenu("User");
		JMenu help = new JMenu("Help");
		
		file.add(restartGame);
		file.add(scores);
		file.add(quit);

		userMenu.add(login);
		userMenu.add(register);
		
		help.add(credits);
		
	    JMenuBar menuBar = new JMenuBar();
		gameOverScreen.setJMenuBar(menuBar); 
	
		menuBar.add(file);
		menuBar.add(userMenu);
		menuBar.add(help);
		
		
		restartGame.setEnabled(true);
		login.setEnabled(false);
		register.setEnabled(false);

		class restartGame implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//User user = new User();
				//user.loginUser(currentUser, currentPassword);
				
				if(user.loginUser(currentUser,currentPassword)==1)
				{
					gameOverScreen.setVisible(false);
					frame.setVisible(true);
					game.start();
				}
			}
		}
		restartGame.addActionListener(new restartGame());

		class quitAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);				
			}
		}
		quit.addActionListener(new quitAction());

		class creditsAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(frame, "\nErdem Erdem\n\n 20170702081 \n\n");
			}
		}
		credits.addActionListener(new creditsAction());
		
		class loginAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
			
			User user = new User();
			user.loginUser(game);
			
				if(user.loginUser(game)==1)
				{
					gameOverScreen.setVisible(false);
					frame.setVisible(true);					
					game.start();
				}
			}
	
		
		}
		login.addActionListener(new loginAction());
	
		class registerAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
			//User user = new User();
				user.registerUser();
			}

		}
		register.addActionListener(new registerAction());

		class scoresAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("scores");
				gameOverScreen.setVisible(false);
				game.createScoresScreen();
			}

		}
		scores.addActionListener(new scoresAction());
	
		user.getUsersToArray();
		user.getUserList().sort(Comparator.comparing(User::getScore)); // sort arraylist ascending order
		Collections.reverse(user.getUserList()); // reverse arraylist to get descending order
		
		
		JLabel first;
		if (user.getUserList().size() >= 1) 
		{
			first = new JLabel(user.getUserList().get(0).getUsername()+ " - "+ Integer.toString(user.getUserList().get(0).getScore()));
		}	
		else
		{
			first = new JLabel("- - -");

		}
			first.setForeground(Color.white);
			first.setHorizontalAlignment(SwingConstants.CENTER);
			first.setFont(new Font("Stencil", Font.BOLD, 20));
			first.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 + 90, 200, 20);

		JLabel second;
		if (user.getUserList().size() >= 2) 		
		{
			second = new JLabel(user.getUserList().get(1).getUsername()+ " - "+ Integer.toString(user.getUserList().get(1).getScore()));
		}
		else
		{
			second = new JLabel("- - -");

		}
			second.setForeground(Color.white);
			second.setHorizontalAlignment(SwingConstants.CENTER);
			second.setFont(new Font("Stencil", Font.BOLD, 16));
			second.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 + 125, 200, 20);

		
		JLabel third;
		if (user.getUserList().size() >= 3) 
		{
			 third = new JLabel(user.getUserList().get(2).getUsername()+ " - "+ Integer.toString(user.getUserList().get(2).getScore()));
		}
		else
		{
			third = new JLabel("- - -");

		}
			third.setForeground(Color.white);
			third.setHorizontalAlignment(SwingConstants.CENTER);
			third.setFont(new Font("Stencil", Font.BOLD, 12));
			third.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 +155, 200, 20);

	
		gameOverScreen.add(scoresLabel);
		gameOverScreen.add(first);
		gameOverScreen.add(second);
		gameOverScreen.add(third);
		gameOverScreen.setVisible(true);
		frame.setVisible(false);
		
        user.setUsersToFile();// after all user operations on the arraylist, write all users to the file
        speed = 0;
		stageNum=1;
    	speedTimer = 0; //for increasing speed;
    	score = 0; //score += speed
    	scoreTimer = 0; //for increasing score
    	time = 60; //time will be shown in screen
    	timeTimer = 0; //setting time to be decreased every 1 sec
    	finishTimer = 1; //time to wait animation and then finishing game
    	
    	isEnemyAdded = false; //checking is enemy added at given sec
    	isPaused = false; //checking is game paused or not
		try
		{
			thread.stop();
			thread.interrupt();			
			thread =null;
		}
		catch(Exception e)
		{
			
		}

	}
	private void createScoresScreen()
	{
		scoresScreen = new JFrame("Hang On");
		scoresScreen.setBounds(200,200,800,600);
		scoresScreen.getContentPane().setLayout(null);
		scoresScreen.setLocationRelativeTo(null);
		scoresScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoresScreen.setContentPane(new JLabel(new ImageIcon("Assets/HangOn.png")));
		JLabel scoresLabel = new JLabel("Highscores");
		scoresLabel.setForeground(Color.white);
		scoresLabel.setFont(new Font("Stencil", Font.BOLD, 24));
		scoresLabel.setBounds(initialScreen.getBounds().width/2 - 80, initialScreen.getBounds().height/2 + 50, 150, 20);

		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem credits = new JMenuItem("Credits");
		JMenuItem login = new JMenuItem("Login");
		JMenuItem register = new JMenuItem("Register");
		JMenuItem playGame = new JMenuItem("Play Game");
		JMenuItem scores = new JMenuItem("Scores");

		JMenu file = new JMenu("Game");
		JMenu userMenu = new JMenu("User");
		JMenu help = new JMenu("Help");
		
		file.add(playGame);
		file.add(scores);
		file.add(quit);

		userMenu.add(login);
		userMenu.add(register);
		
		help.add(credits);
		
	    JMenuBar menuBar = new JMenuBar();
		scoresScreen.setJMenuBar(menuBar); 
	
		menuBar.add(file);
		menuBar.add(userMenu);
		menuBar.add(help);

		scores.setEnabled(false);
		
		class playGameAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
					if(user.loginUser(game)==1)
					{
						initialScreen.setVisible(false);
						scoresScreen.setVisible(false);
						frame.setVisible(true);
						game.start();												
					}
			}
		}
		playGame.addActionListener(new playGameAction());
		
		class quitAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
					System.exit(0);				
			}
		}
		quit.addActionListener(new quitAction());

		class creditsAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(frame, "\nErdem Erdem\n\n 20170702081 \n\n");
			}
		}
		credits.addActionListener(new creditsAction());
		
		class loginAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
			
			//User user = new User();			
				if(user.loginUser(game)==1)
				{
					initialScreen.setVisible(false);
					scoresScreen.setVisible(false);
					frame.setVisible(true);
					game.start();

					
				}
			}
	
		
		}
		login.addActionListener(new loginAction());
	
		class registerAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				user.registerUser();
			}

		}
		register.addActionListener(new registerAction());

		class scoresAction implements ActionListener
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
			System.out.println("scores");
			game.createScoresScreen();
			}

		}
		scores.addActionListener(new scoresAction());
		
		user.getUsersToArray();
		user.getUserList().sort(Comparator.comparing(User::getScore));
		Collections.reverse(user.getUserList());
		
		JLabel first;
		if (user.getUserList().size() >= 1) 
		{
			first = new JLabel(user.getUserList().get(0).getUsername()+ " - "+ Integer.toString(user.getUserList().get(0).getScore()));
		}	
		else
		{
			first = new JLabel("- - -");
		}
			first.setForeground(Color.white);
			first.setHorizontalAlignment(SwingConstants.CENTER);
			first.setFont(new Font("Stencil", Font.BOLD, 18));
			first.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 + 90, 200, 20);

		JLabel second;
		if (user.getUserList().size() >= 2) 		
		{
			second = new JLabel(user.getUserList().get(1).getUsername()+ " - "+ Integer.toString(user.getUserList().get(1).getScore()));
		}
		else
		{
			second = new JLabel("- - -");
		}
			second.setForeground(Color.white);
			second.setHorizontalAlignment(SwingConstants.CENTER);
			second.setFont(new Font("Stencil", Font.BOLD, 18));
			second.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 + 120, 200, 20);

		
		JLabel third;
		if (user.getUserList().size() >= 3) 
		{
			 third = new JLabel(user.getUserList().get(2).getUsername()+ " - "+ Integer.toString(user.getUserList().get(2).getScore()));
		}
		else
		{
			third = new JLabel("- - -");
		}
			third.setForeground(Color.white);
			third.setHorizontalAlignment(SwingConstants.CENTER);
			third.setFont(new Font("Stencil", Font.BOLD, 18));
			third.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 +150, 200, 20);
			
		JLabel fourth;
		if (user.getUserList().size() >= 4) 
		{
			 fourth = new JLabel(user.getUserList().get(3).getUsername()+ " - "+ Integer.toString(user.getUserList().get(3).getScore()));
		}
		else
		{
			fourth = new JLabel("- - -");
		}
		fourth.setForeground(Color.white);
		fourth.setHorizontalAlignment(SwingConstants.CENTER);
		fourth.setFont(new Font("Stencil", Font.BOLD, 18));
		fourth.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 +180, 200, 20);
		
		JLabel fifth;
		if (user.getUserList().size() >= 5) 
		{
			 fifth = new JLabel(user.getUserList().get(4).getUsername()+ " - "+ Integer.toString(user.getUserList().get(4).getScore()));
		}
		else
		{
			fifth = new JLabel("- - -");
		}
		fifth.setForeground(Color.white);
		fifth.setHorizontalAlignment(SwingConstants.CENTER);
		fifth.setFont(new Font("Stencil", Font.BOLD, 18));
		fifth.setBounds(initialScreen.getBounds().width/2 - 115, initialScreen.getBounds().height/2 +210, 200, 20);
		
		scoresScreen.add(scoresLabel);
		scoresScreen.add(first);
		scoresScreen.add(second);
		scoresScreen.add(third);
		scoresScreen.add(fourth);
		scoresScreen.add(fifth);


		initialScreen.setVisible(false);
		scoresScreen.setVisible(true);
		
	
	}

}

