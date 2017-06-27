/*Author: Edward Liu
 * Date created: 6/1/17
 * Date finished: 6/16/17
 * Description: This is the main board of the snake v2 game
 * This holds everything in the game. 
 * This class is a Jpanel, containing the actual game part, with all its features
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.GridLayout;

public class Board extends JPanel implements ActionListener, KeyListener
{
	//make all instance variables
	private static final int sizeX = 800, sizeY = 800, bodySize = 32,
	        area = (sizeX * sizeY) / (bodySize * bodySize);

	private static int sleepTime = 70;
	private int numBody = 0;
	Timer timer = new Timer(sleepTime, this);
	int snakeX[] = new int[area];
	int snakeY[] = new int[area];

	private int cherryX;
	private int cherryY;

	private int bombX[] = new int[50];
	private int bombY[] = new int[50];
	private int bombCount = 0;
	static private int gameMode = 0; // 0 = classic, 1 = arcade mode
	//all the images I need
	private Image snakeHeadRight;
	private Image snakeHeadLeft;
	private Image snakeHeadUp;
	private Image snakeHeadDown;
	private Image cherryPic;
	private Image body;
	private Image gBackground;
	private Image bomb;
	private Image goldenBomb;
	private Image goldenCherry;
	private Image skull;
	private Image explosion[] = new Image[9];//this is the images i created for the explosion/death animation

	private boolean sHR = true;//boolean flags for which head image to use
	private boolean sHL;
	private boolean sHU;
	private boolean sHD;
	private boolean firstTime;//special condition for if the game is first time run
	private boolean pause = false;
	private boolean input = false;//a flag to ensure only one input is allowed per timer tick
	
	private boolean iR = false;//flags so that when a player holds down a key, it is only pressed once
	private boolean iL = false;//so they can press another to move another direction
	private boolean iU = false;	//while still holding the first down.
	private boolean iD = false;
	private int invincibility = 0;//Counter for invincibility duration
	private boolean isInvincible = false;
	private int eatenBombIndex;
	private boolean flash = false;//for flash effect of bombs when invincibility is running out
	private int frameCount;
	private int explodeIndex = 0;
	private int cherriesEaten;
	private boolean playOnce = false;

	private static int score = 0;
	Head head = new Head(0, 0, bodySize * 2, bodySize * 2, bodySize);//initilizing the head 
	//using the constructor in head class
	
	//Sound files
	File eatCherry = new File("Sounds/eatCherry.wav");
	File eatBomb = new File("Sounds/eatBomb.wav");
	File death = new File("Sounds/death.wav");
	File bombDeath = new File("Sounds/bombDeath.wav");
	File invincibilitySound = new File("Sounds/invincibility.wav");



	public Board()//contructor for board class
	{

		addKeyListener(this);
		setFocusable(true);

		setPreferredSize(new Dimension(sizeX, sizeY));

		initializeGame();
	}

	private void loadPictures() //initializing each image needed 
	{
		ImageIcon temp = new ImageIcon("Images/headright.png");
		snakeHeadRight = temp.getImage();
		
		ImageIcon temp2 = new ImageIcon("Images/headleft.png");
		snakeHeadLeft = temp2.getImage();
		
		ImageIcon temp3 = new ImageIcon("Images/headup.png");
		snakeHeadUp = temp3.getImage();
	
		ImageIcon temp4 = new ImageIcon("Images/headdown.png");
		snakeHeadDown = temp4.getImage();
		
		ImageIcon temp5 = new ImageIcon("Images/Cherry.png");
		cherryPic = temp5.getImage();
		
		ImageIcon temp6 = new ImageIcon("Images/body.png");
		body = temp6.getImage();

		ImageIcon temp7 = new ImageIcon("Images/gamebackground.png");
		gBackground = temp7.getImage();

		ImageIcon temp8 = new ImageIcon("Images/bomb.png");
		bomb = temp8.getImage();

		ImageIcon temp9 = new ImageIcon("Images/golden bomb.png");
		goldenBomb = temp9.getImage();
		

		ImageIcon temp10 = new ImageIcon("Images/golden cherry.png");
		goldenCherry = temp10.getImage();
		
		
		ImageIcon temp11 = new ImageIcon("Images/skull.png");
		skull = temp11.getImage();

		for (int i = 0; i < 9; i++)//this is for the explosion animation when the snake dies
		{							//there are 9 frames, therfore there are 9 images 
			ImageIcon b = new ImageIcon("Images/explode" + (i + 1) + ".png");

			explosion[i] = b.getImage();

		}
	}
	//initialize game method
	private void initializeGame()
	{
		numBody = 3; //sets the starting body size to 3
		
		snakeX[0] = head.getPosX();
		snakeY[0] = head.getPosY();
		for (int i = 1; i < numBody; i++)//set the positions of the body parts behind the head
		{
			snakeX[i] = snakeX[i - 1] - bodySize;
			snakeY[i] = snakeY[i - 1];

		}
		spwnCherry(); // spawns a cherry
		if (gameMode == 1)//if arcade mode spawn a bomb too
		{
			spwnBomb();
		}
		firstTime = true;//set flag fristTime true
		loadPictures();//load all the pictures being used
		score = 0;//set score to 0
		timer.start();//start the timer to start triggering actionlistener
	}

	//method overwrite to paint my game components
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(gBackground, 0, 0,sizeX, sizeY, this);
		

		//if the game was resumed from the paused menu, ask for space input to 
		//resume game
		if (pause == true)
		{
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString("Press space to continue", 220, 40);
		}

		if(isGmOver() == false)
		{
			if (sHR == true)
				g.drawImage(snakeHeadRight, snakeX[0], snakeY[0],bodySize,bodySize, this);
			if (sHL == true)
				g.drawImage(snakeHeadLeft, snakeX[0], snakeY[0],bodySize,bodySize, this);

			if (sHU == true)
				g.drawImage(snakeHeadUp, snakeX[0], snakeY[0],bodySize,bodySize, this);

			if (sHD == true)
				g.drawImage(snakeHeadDown, snakeX[0], snakeY[0],bodySize,bodySize, this);
			
			for (int i = 1; i < numBody; i++)
			g.drawImage(body, snakeX[i], snakeY[i],bodySize,bodySize, this);
		}

		if (gameMode == 0)
		{
			g.drawImage(cherryPic, cherryX, cherryY,bodySize,bodySize, this);
		} 
		else
		{
			if ((cherriesEaten + 1) % 7 == 0 && score > 0)//for golden cherry if the num cherries eaten is just under 7
			{
				g.drawImage(goldenCherry, cherryX, cherryY, bodySize, bodySize, this);
			} else
			{
				g.drawImage(cherryPic, cherryX, cherryY, bodySize, bodySize, this);
			}
		}
		if (gameMode == 1)
		{
			if (isInvincible == true)
			{
				if (flash == false)//flash algorithm
				{
					for (int j = 0; j < bombCount; j++)
						g.drawImage(goldenBomb, bombX[j], bombY[j],bodySize,bodySize, this);
				} else
				{
					for (int j = 0; j < bombCount; j++)
						g.drawImage(bomb, bombX[j], bombY[j],bodySize,bodySize, this);
				}
			} else
			{
				for (int k = 0; k < bombCount; k++)//make sure all the other bombs stay when the snake dies on one
				{									//and the eaten one is replaced with a skull
				
					if(isGmOver() == true)
					{
						if(isBombEaten() == true)
						{
							
							if(bombX[k] == bombX[eatenBombIndex] && bombY[k] == bombY[eatenBombIndex])
							{
								g.drawImage(skull, bombX[eatenBombIndex], bombY[eatenBombIndex],bodySize,bodySize, this);
							}
							else
							{
								g.drawImage(bomb, bombX[k], bombY[k],bodySize,bodySize, this);
							}
						}
						else
						{
							g.drawImage(bomb, bombX[k], bombY[k],bodySize,bodySize, this);
						}
					}
					else
					{
						g.drawImage(bomb, bombX[k], bombY[k],bodySize,bodySize, this);
					}
				}
			}
			
		}

		if (isGmOver() == true)//draw the explosion animation on each body part when the snake dies
		{
			
			for(int i = 0; i < numBody; i++)
			{
			g.drawImage(explosion[explodeIndex], snakeX[i], snakeY[i],
			        bodySize, bodySize, this);
			}
			
		}

		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Score: " + Integer.toString(score), sizeX - 100, 20);
	}

	public static void setGameMode(int m)//method for setting the gamemode from the other mainmenu(when user selects aracde or classic
	{
		gameMode = m;
	}

	public static int getGameMode()//for the end board to set up a highscore
	{
		return gameMode;
	}

	public static void setSleepTime(int s)//to set the difficulty in the mainmenu(when user selects easy,medium or hard)
	{
		sleepTime = s;
	}

	public static int getSleepTime()//for the end board to set up a highscore
	{
		return sleepTime;
	}

	public void spwnCherry()//spawns a cherry on the board(randomly) but on a 
		//multiple of the body size, to ensure contact with the snake
	{
		Cherry c = new Cherry(
		        ((int) (Math.random() * (sizeX / bodySize)) * bodySize),
		        ((int) (Math.random() * (sizeY / bodySize)) * bodySize),
		        bodySize);

		boolean testFlag = false;
		do//makes sure a cherry doesnt spawn on the snake
		{
			for (int i = 0; i < numBody; i++)
			{
				testFlag = true;
				if (snakeX[i] == c.getPosX() && snakeY[i] == c.getPosY())
				{
					c = new Cherry(
					        ((int) (Math.random() * (sizeX / bodySize))
					                * bodySize),
					        ((int) (Math.random() * (sizeY / bodySize))
					                * bodySize),
					        bodySize);
					testFlag = false;
					break;
				}
			}
		} while (testFlag == false);
		if (c.getPosX() >= sizeX - (bodySize * 6) && c.getPosY() < (bodySize))
		{
			c.setPosY(bodySize);	//so that the cherry cant spawn behind the score
		}
		do	//this is so that the cherries cant spawn on or 1 section near a bomb
		{
			for (int i = 0; i < bombCount; i++)
			{
				testFlag = true;
				if (c.getPosX() == bombX[i] + bodySize
				        || c.getPosX() == bombX[i] - bodySize
				        || c.getPosX() == bombX[i])
				{
					if (c.getPosY() == bombY[i])
					{
						c = new Cherry(
						        ((int) (Math.random() * (sizeX / bodySize))
						                * bodySize),
						        ((int) (Math.random() * (sizeY / bodySize))
						                * bodySize),
						        bodySize);
						testFlag = false;
						break;
					}
				}
				if (c.getPosY() == bombY[i] + bodySize
				        || c.getPosY() == bombY[i] - bodySize
				        || c.getPosY() == bombY[i])
				{
					if (c.getPosX() == bombX[i])
					{
						c = new Cherry(
						        ((int) (Math.random() * (sizeX / bodySize))
						                * bodySize),
						        ((int) (Math.random() * (sizeY / bodySize))
						                * bodySize),
						        bodySize);
						testFlag = false;
						break;
					}
				}
			}
		} while (testFlag == false);
		cherryX = c.getPosX();//assigns the cherry to new the coordinates
		cherryY = c.getPosY();

	}

	public void spwnBomb()//method to spawn a new bomb similar to spwnCherry
	{
		Bomb b = new Bomb(
		        ((int) (Math.random() * (sizeX / bodySize)) * bodySize),
		        ((int) (Math.random() * (sizeY / bodySize)) * bodySize),
		        bodySize);
		boolean testFlag = false;
		do//make sure the bomb doesnt spawn on the snake
		{
			for (int i = 0; i < numBody; i++)
			{
				testFlag = true;
				if (snakeX[i] == b.getPosX() && snakeY[i] == b.getPosY())
				{
					b = new Bomb(
					        ((int) (Math.random() * (sizeX / bodySize))
					                * bodySize),
					        ((int) (Math.random() * (sizeY / bodySize))
					                * bodySize),
					        bodySize);
					testFlag = false;
					break;
				}
			}
		} while (testFlag = false);
		if (b.getPosX() >= sizeX - (bodySize * 6) && b.getPosY() < (bodySize))// so
		                                                                      // bomb
		                                                                      // doesnt
		                                                                      // spawn
		{ // behind score.
			b.setPosY(bodySize);
		}
		// conditions so that the bomb cant spawn on or 1 bodysize away from the
		// cherry
		do
		{
			testFlag = true;
			if (b.getPosX() == cherryX + bodySize
			        || b.getPosX() == cherryX - bodySize
			        || b.getPosX() == cherryX)
			{

				if (b.getPosY() == cherryY)
				{
					b = new Bomb(
					        ((int) (Math.random() * (sizeX / bodySize))
					                * bodySize),
					        ((int) (Math.random() * (sizeY / bodySize))
					                * bodySize),
					        bodySize);
					testFlag = false;
				}
			}
			if (b.getPosY() == cherryY + bodySize
			        || b.getPosY() == cherryY - bodySize
			        || b.getPosY() == cherryY)
			{
				if (b.getPosX() == cherryX)
				{
					b = new Bomb(
					        ((int) (Math.random() * (sizeX / bodySize))
					                * bodySize),
					        ((int) (Math.random() * (sizeY / bodySize))
					                * bodySize),
					        bodySize);
					testFlag = false;

				}
			}
		} while (testFlag == false);

		// conditions so that the bomb cant spawn within 10 bodySizes away from the head(only on the same x and y axis.
		do
		{
			testFlag = true;
			for (int i = 1; i <= 10; i++)
			{
				if (b.getPosX() == snakeX[0]
				        || b.getPosX() == snakeX[0] + (bodySize * i)
				        || b.getPosX() == snakeX[0] - (bodySize * i))
				{
					if (b.getPosY() == snakeY[0])
					{
						b = new Bomb(
						        ((int) (Math.random() * (sizeX / bodySize))
						                * bodySize),
						        ((int) (Math.random() * (sizeY / bodySize))
						                * bodySize),
						        bodySize);
						testFlag = false;
						break;
					}
				}
			}
			for (int j = 1; j <= 10; j++)
			{
				if (b.getPosY() == snakeY[0]
				        || b.getPosY() == snakeY[0] + (bodySize * j)
				        || b.getPosY() == snakeY[0] - (bodySize * j))
				{
					if (b.getPosX() == snakeX[0])
					{
						b = new Bomb(
						        ((int) (Math.random() * (sizeX / bodySize))
						                * bodySize),
						        ((int) (Math.random() * (sizeY / bodySize))
						                * bodySize),
						        bodySize);
						testFlag = false;
						break;
					}
				}
			}
		} while (testFlag == false);

		bombX[bombCount] = b.getPosX();//the array of bombs stores the location of each
		bombY[bombCount] = b.getPosY();//unlike the cherry, there are multiple bombs
		bombCount++;

	}

	public boolean isBombEaten()//simple method to check if a bomb has been eaten
	{
		for (int i = 0; i < bombCount; i++)
		{
			if (snakeX[0] == bombX[i] && snakeY[0] == bombY[i])
			{
				eatenBombIndex = i;
				return true;
			}
		}
		return false;
	}

	public boolean isCherryEaten()//simple method to check if a cherry has been eaten
	{
		if (snakeX[0] == cherryX && snakeY[0] == cherryY)
		{
			return true;

		}
		return false;
	}

	public boolean isGmOver()//method to check if snake is dead
	{

		if (isInvincible == false)//if the invincibility is off...
		{
			if (head.getPosX() >= sizeX || head.getPosX() < 0
			        || head.getPosY() >= sizeY || head.getPosY() < 0)//dead if ran out of bounds
				return true;

			if (gameMode == 1)
			{
				if (isBombEaten() == true)//dead if ate bomb while not invincible
					
					return true;
			}
			if (score >= 2)
			{
				for (int i = 1; i < numBody; i++)
				{
					if (head.getPosX() == snakeX[i]//dead if ran into itself
					        && head.getPosY() == snakeY[i])
						return true;
				}
			}
		} else
		{

			if (head.getPosX() >= sizeX || head.getPosX() < 0//if snake is invincible, can only die from out of bounds
			        || head.getPosY() >= sizeY || head.getPosY() < 0)
				return true;

		}
		return false;
	}

	public static int getScore()//method to return score(used in endBoard for display and highscores
	{
		return score;
	}

	public void resume()//method to display the paused game from pauseBoard
	{
		pause = true;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// never used
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		int c = e.getKeyCode();

		if (c == KeyEvent.VK_LEFT)//if left arrow key is pressed
		{
			if (firstTime == false)//only because the snake cant move left on initial start
			{
				if (iL == false)
				{
					if (head.getSpeedX() != bodySize)//so it cant move back on itself
					{
						if (input == false)
						{
							input = true;
							iL = true;//scroll up at instantiation of these flags
							iR = false;
							iU = false;
							iD = false;
							head.setSpeedX(-bodySize);//set speeds according to direction inputed
							head.setSpeedY(0);
							sHL = true;//which snake head to display based on direction
							sHR = false;
							sHU = false;
							sHD = false;

						}
					}
				}
			}
		}//from here its all the same
		if (c == KeyEvent.VK_UP)
		{
			firstTime = false;

			if (iU == false)
			{
				if (head.getSpeedY() != bodySize)
				{
					if (input == false)
					{
						input = true;
						iU = true;
						iL = false;
						iR = false;
						iD = false;

						head.setSpeedX(0);
						head.setSpeedY(-bodySize);
						sHL = false;
						sHR = false;
						sHU = true;
						sHD = false;
					}
				}

			}
		}
		if (c == KeyEvent.VK_DOWN)
		{
			firstTime = false;
			if (iD == false)
			{
				if (head.getSpeedY() != -bodySize)
				{
					if (input == false)
					{
						input = true;

						iL = false;
						iR = false;
						iU = false;
						iD = true;

						head.setSpeedX(0);
						head.setSpeedY(bodySize);
						sHL = false;
						sHR = false;
						sHU = false;
						sHD = true;
					}
				}

			}
		}
		if (c == KeyEvent.VK_RIGHT)
		{
			firstTime = false;
			if (iR == false)
			{
				if (head.getSpeedX() != -bodySize)
				{
					if (input == false)
					{
						input = true;

						iL = false;
						iR = true;
						iU = false;
						iD = false;

						head.setSpeedX(bodySize);
						head.setSpeedY(0);
						sHL = false;
						sHR = true;
						sHU = false;
						sHD = false;
					}
				}

			}
		}
		if (c == KeyEvent.VK_ESCAPE)//to pause
		{
			timer.stop();
			Main.createPauseBoard();
		}

		if (c == KeyEvent.VK_SPACE)//to resume after clicking resume in the pauseBoard
		{	//(to make a smoother transition back to gameplay)

			timer.start();
			pause = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// never used

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// if a cherry is eaten, spawn another add score and body size. If
		// arcade, add bomb.
		if (isCherryEaten() == true)
		{
			playSound(eatCherry,-20);
			spwnCherry();
			numBody += 1;
			score += 1;
			cherriesEaten++;
			if (gameMode == 1)
			{
				spwnBomb();
			}

		}
		if (gameMode == 1)
		{
			// when the player gets to 7 cherries, the player is invincible to
			// bombs and eating itself.
			if (cherriesEaten % 7 == 0 && cherriesEaten > 0)
			{
				if(playOnce == false)
				{
				playSound(invincibilitySound,-10);
				}
				isInvincible = true;
				playOnce = true;
			}
		}
		if (isInvincible == true)
			invincibility++;

		// this lasts for a period of time determined by the level difficulty;
		// the
		// harder the difficulty the less time for invincibility.

		if (invincibility == 100)
		{
			flash = false;
			invincibility = 0;
			isInvincible = false;
			cherriesEaten = 0;
			playOnce = false;


		}
		if (isInvincible == true)
		{
			if (isBombEaten() == true)//the snake can eat bombs like cherries while invincible
			{
				playSound(eatBomb,-20);
				score++;
				numBody++;
				bombCount--;
				bombX[eatenBombIndex] = 0;
				bombY[eatenBombIndex] = 0;
				for (int i = eatenBombIndex; i < bombX.length - 1; i++)//"eliminates" the eaten bomb from the array
				{														//set to 0, and move back
					bombX[i] = bombX[i + 1];
					bombY[i] = bombY[i + 1];

				}
			}
			if (invincibility > 70)//flash algorithm to control the falsh when the counter reaches 70
			{
				if (invincibility % 3 == 0)
				{
					flash = true;
				} else
				{
					flash = false;
				}
			}
		}
		if (isGmOver() == true)//when snake dies
		{
			playSound(death,0);
			head.setSpeedX(0);
			head.setSpeedY(0);
			frameCount++;
			if (frameCount % 1 == 0 && frameCount >= 1)//to control explosion animation upon death
				explodeIndex++;

			if (frameCount == 9)
			{
				timer.stop();

				try
				{
					Main.createEndBoard();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}

		if (firstTime != true)//this is the actual mouvement algorithm
		{
			if (isGmOver() != true)
			{
				for (int i = numBody - 1; i > 0; i--)//each position moves up one index to follow the snake
				{
					snakeX[i] = snakeX[(i - 1)];
					snakeY[i] = snakeY[(i - 1)];
				}

				snakeX[0] = snakeX[0] + head.getSpeedX();//snake moves on its own, after the body already moved up one index
				head.setPosX(snakeX[0]);//head moves based on speed, body will follow because the head is at index 0
				snakeY[0] = snakeY[0] + head.getSpeedY();
				head.setPosY(snakeY[0]);
				input = false;//so that the user can only input 1 direction per tick
			}
		}
		repaint();//repaint everything
	}
	// method to play sound file
		public static void playSound(File file, int v)
		{
			try
			{
				// get audio clip file
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(file));// get the audio
				FloatControl gainControl = 
					    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(v + 0.0f); // Reduce volume by v decibels.       
				clip.start();// playback start
			} catch (Exception e)
			{// print error if there is one
				System.err.println(e.getMessage());
			}
		}
}
