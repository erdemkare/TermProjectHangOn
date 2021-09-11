package erdem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class User 
{
	private String username, password;
	int score;
	JTextField usernameTF, passwordTF;
    static final File UserInfo = new File("UserInfo.txt"); // Data File - username password score

    private ArrayList<User> userList = new ArrayList<User>(); // username password score
	
	Scanner getInput = new Scanner(System.in);
	
	public void registerUser() // menubar register
	{
		usernameTF = new JTextField();
		passwordTF = new JPasswordField();
		Object[] message = { "Username ", usernameTF,"Password ", passwordTF};
		int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);

		score = 0;// default
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(new FileWriter(UserInfo, true));// true means append mode
		}catch (FileNotFoundException | UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		if (option == JOptionPane.OK_OPTION) 
		{
			writer.print((String)usernameTF.getText());
			writer.print(" ");
			writer.print((String)passwordTF.getText());
			writer.print(" ");
			writer.print(score);
			writer.println();
			writer.close();
		} else 
		{
		    System.out.println("Register Canceled");
		}
		
	}
	public int loginUser(Game game) 
	{
		Scanner scanner= null;
		Scanner scannerFromUser=new Scanner(System.in);

		try {
			scanner = new Scanner(UserInfo);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		usernameTF = new JTextField();
		passwordTF = new JPasswordField();
		
		Object[] message = { "Username ", usernameTF,"Password ", passwordTF};
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (option == JOptionPane.OK_OPTION) 
		{
			boolean userFound = false;
			
			while (scanner.hasNextLine()) 
			{
		        Scanner scanner2 = new Scanner(scanner.nextLine()); // scanner for words in lines
		        while (scanner2.hasNext()) 
		        {
		            String s = scanner2.next(); // s equals to username
		            
		            if(usernameTF.getText().equals(s))
		            {
		            	if (scanner2.hasNext()) 
		            	{
							s = scanner2.next();// s equals to password
						}
		            	else
		            	{
			            	userFound = false;
		            	}
		            	if (passwordTF.getText().equals(s)) 
		            	{
			            	this.username = usernameTF.getText();
			            	this.password = passwordTF.getText();
			            	score = scanner2.nextInt();
			            	
			            	JOptionPane.showMessageDialog(null, "\nWelcome " + username + "\n\nYour highscore is " + score
			            			+ "\n\nCan you beat it ?\n","Welcome", JOptionPane.PLAIN_MESSAGE);
			            	
			            	userFound = true;
			            	game.currentUser = username;
			            	game.currentPassword = password;
			            	scanner2.close();			            	
			            	return 1;
						}
		            	else
		            	{
			            	userFound = false;
		            	}
		            }
		        	else
	            	{
		            	userFound = false;
	            	}
		        }
		        if (userFound)
		        {
					break;
				}
				scanner2.close();
			}
			scannerFromUser.close();
			if (!userFound) 
			{
        		JOptionPane.showMessageDialog(null, "User not found", "Warning", JOptionPane.PLAIN_MESSAGE);
	        	System.out.println("User Not Found");
	        	return 0;
			}
		} 
		else 
		{
		    System.out.println("Login canceled");
		}
		return 0;
	}
	public int loginUser(String username, String password) // 2 parameter login for main screen login
	{

			Scanner scanner= null;
			Scanner scannerFromUser=new Scanner(System.in);
			getUsersToArray();
			try 
			{
				scanner = new Scanner(UserInfo);
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		
			boolean userFound = false;
			
			while (scanner.hasNextLine()) 
			{
		        Scanner scanner2 = new Scanner(scanner.nextLine()); // scanner for words in lines
		        while (scanner2.hasNext()) 
		        {
		            String s = scanner2.next(); // s equals to username
		            
		            if(username.equals(s))
		            {
		            	if (scanner2.hasNext()) 
		            	{
							s = scanner2.next();// s equals to password
						}
		            	else
		            	{
			            	userFound = false;
		            	}
		            	if (password.equals(s)) 
		            	{
		            		System.out.println("User Found");

		            		
		            		this.username = username;
			            	this.password = password;
			            	score = scanner2.nextInt();
			            	System.out.println(username + score);
			            	userFound = true;
			            	Game.currentUser = username;
			            	Game.currentPassword = password;
			            	scanner2.close();
			            	return 1;
						}
		            	else
		            	{
			            	userFound = false;
		            	}
		            }
		        	else
	            	{
		            	userFound = false;
	            	}
		        }
		        if (userFound)
		        {
					break;
				}
				scanner2.close();
			}
			scannerFromUser.close();
			if (!userFound) 
			{
        		JOptionPane.showMessageDialog(null, "User not found", "Warning", JOptionPane.PLAIN_MESSAGE);
	        	return 0;
			}
			
			return 0;
			
	}
	public void getUsersToArray() // gets users to arraylist for any operation
	{
		userList.clear();
	
		Scanner scanner= null;
		Scanner scannerFromUser=new Scanner(System.in);

		try 
		{
			scanner = new Scanner(UserInfo);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		while (scanner.hasNextLine()) 
		{
	        Scanner scanner2 = new Scanner(scanner.nextLine()); // scanner for words in lines
	        while (scanner2.hasNext()) 
	        {
	        	String s = scanner2.next(); // s equals to username
	            this.username = s;
	            if (scanner2.hasNext())
	            {
					this.password = scanner2.next();

				}
	            if (scanner2.hasNext())
	            {
	            	int scoreTemp = Integer.parseInt(scanner2.next());
					this.score = scoreTemp;
				}
	            
	            User newUser = new User();
		        newUser.username = this.username;
		        newUser.password = this.password;
		        newUser.score = this.score;
	            userList.add(newUser);
	            newUser = null;
	            
	        }
	      

		}
		
		
	}
	
	public void setUsersToFile() // puts users to file after any operation
	{
	
		PrintWriter writer = null;
		
		try{
			writer = new PrintWriter(new FileWriter(UserInfo, false));
		}catch (FileNotFoundException | UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		writer.print("");
		writer.close();
		writer = null;
		
		try{
			writer = new PrintWriter(new FileWriter(UserInfo, false));
		}catch (FileNotFoundException | UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		for(int i = 0; i<userList.size(); i++)
		{
			writer.print(userList.get(i).getUsername());
			writer.print(" ");
			writer.print(userList.get(i).getPassword());
			writer.print(" ");
			writer.print(userList.get(i).getScore());
			writer.println();
			
			System.out.println(userList.get(i).getUsername());
			System.out.println(userList.get(i).getPassword());
			System.out.println(userList.get(i).getScore());
			
		}
		
			userList.clear();
			writer.close();

		}
	
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public JTextField getUsernameTF() {
			return usernameTF;
		}
		public void setUsernameTF(JTextField usernameTF) {
			this.usernameTF = usernameTF;
		}
		public JTextField getPasswordTF() {
			return passwordTF;
		}
		public void setPasswordTF(JTextField passwordTF) {
			this.passwordTF = passwordTF;
		}
		public ArrayList<User> getUserList() 
		{
			return userList;
		}
		public void setUserList(ArrayList<User> userList) {
			this.userList = userList;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
	
}
