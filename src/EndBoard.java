
/* 
 * Author: Edward Liu
 * Date: 6/7/17
 * Description: This class is the endboard class, where the user is brought when 
 * they lose. It  contains 3 buttons; retry, save score and mainmenu.It also
 * displays the user's score after their run.  It can save the 
 * user's highscore into the specified txt file with IO.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EndBoard extends JPanel implements ActionListener
{
	private JButton btnRetry;
	private JButton btnSaveScore;
	private JButton btnMainMenu;
	private JLabel lblScore;
	private Image endBackground;
	private String pastPlayers[] = new String[10];
	private int ranking[] = new int[10];
	private int score[] = new int[10];
	String newUser;
	private boolean flag = true;
	File click = new File("Sounds/click.wav");

	public EndBoard() throws IOException// standard constructor containing all
	                                    // the buttons and displaying score
	{

		setLayout(null);
		setPreferredSize(new Dimension(800, 800));

		ImageIcon temp = new ImageIcon("Images/endmenubackground.png");
		endBackground = temp.getImage();

		btnRetry = new JButton(new ImageIcon("Images/retry.png"));
		btnRetry.setBounds(270, 400, 260, 70);
		btnRetry.setContentAreaFilled(false);
		btnRetry.setBorder(null);
		btnRetry.addActionListener(this);
		add(btnRetry);

		btnSaveScore = new JButton(new ImageIcon("Images/savescore.png"));
		btnSaveScore.setBounds(270, 480, 260, 70);
		btnSaveScore.setContentAreaFilled(false);
		btnSaveScore.setBorder(null);
		btnSaveScore.addActionListener(this);
		add(btnSaveScore);

		btnMainMenu = new JButton(new ImageIcon("Images/mainmenu.png"));
		btnMainMenu.addActionListener(this);
		btnMainMenu.setBounds(270, 560, 260, 70);
		btnMainMenu.setBorder(null);
		btnMainMenu.setContentAreaFilled(false);
		add(btnMainMenu);

		lblScore = new JLabel("Score: " + Board.getScore());
		lblScore.setFont(new Font("Verdana", Font.BOLD, 45));
		lblScore.setForeground(new Color(99, 193, 87));
		lblScore.setBounds(280, 220, 800, 50);
		add(lblScore);
	}

	public void paintComponent(Graphics g)// paint method to paint the
	                                      // background
	{
		super.paintComponent(g);
		g.drawImage(endBackground, 0, 0, this);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnRetry)// restarts the board
		{
			playSound(click);
			Main.createBoard();
		}
		if (e.getSource() == btnSaveScore)// saves the score into the specific
		                                  // txt file (IO)
		{
			playSound(click);
			String inputName = JOptionPane// popup to ask for user name
			        .showInputDialog("Your Name(12 characters max): ");

			while (1 == 1)// trap for unwanted name cases
			{
				if (inputName != null)
				{
					if (inputName.length() <= 12 && inputName.length() != 0
					        && inputName.indexOf(" ") == -1)
					{
						flag = false;
						break;
					}
				} else// if user clicks cancel, cancel the entire save highscore
				      // operation
				{
					break;
				}
				inputName = JOptionPane.showInputDialog(
				        "Your Name(12 characters max, no spaces): ");
			}

			if (flag == false)// if passed conditions, launch into highscores
			                  // board
			{

				String gameMode;
				String difficulty = "";
				if (Board.getGameMode() == 1)
				{
					gameMode = "Arcade";
				} else
				{
					gameMode = "";
				}
				if (Board.getSleepTime() == 100)
				{
					difficulty = "E";// E for easy
				}
				if (Board.getSleepTime() == 70)
				{
					difficulty = "M";// M for medium
				}
				if (Board.getSleepTime() == 40)
				{
					difficulty = "H";// H for hard
				}
				IO.openInputFile(// opening the specific txt file (gamemode and
				                 // difficulty)
				        "Data//HighScores" + gameMode + difficulty + ".txt");
				String lineRead = "";

				for (int l = 0; l < pastPlayers.length; l++)
				{
					try
					{
						lineRead = IO.readLine();
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					pastPlayers[l] = lineRead.substring(0,
					        lineRead.indexOf(" "));
					score[l] = Integer.parseInt(
					        lineRead.substring(lineRead.indexOf(" ") + 1,
					                lineRead.lastIndexOf(" ")));
					ranking[l] = Integer.parseInt(
					        lineRead.substring(lineRead.lastIndexOf(" ") + 1));
				}

				try
				{
					IO.closeInputFile();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}

				for (int j = 0; j < pastPlayers.length; j++)
				{
					if (score[j] < Board.getScore())// this determines where the
					// user's score is inserted into the highscores
					{

						// this algortihm "moves" elements back one index (ex:e1
						// @ index 1 moves to index 2,etc)
						for (int t = pastPlayers.length - 1; t > j; t--)
						{
							pastPlayers[t] = pastPlayers[t - 1];
						}
						pastPlayers[j] = inputName;// the new user is inserted
						                           // into the array where it
						                           // should be
						for (int u = score.length - 1; u > j; u--)// same as
						                                          // above
						                                          // algorithm
						                                          // form
						                                          // pastPlayers[]
						{
							score[u] = score[u - 1];
						}
						score[j] = Board.getScore();

						break;
					}

				}
				IO.createOutputFile(
				        "Data//HighScores" + gameMode + difficulty + ".txt");
				for (int y = 0; y < pastPlayers.length; y++)// this changes the
				                                            // txt file to the
				                                            // updated scores
				{
					IO.println(
					        pastPlayers[y] + " " + score[y] + " " + ranking[y]);
				}
				IO.closeOutputFile();

				Main.createHighScoreBoard(Board.getGameMode(), difficulty);
				flag = true;
			}
		}
		if (e.getSource() == btnMainMenu)// btn to go to mainmenu
		{
			playSound(click);
			Main.createMenu();
		}
	}

	// method to play sound file
	public static void playSound(File file)
	{
		try
		{
			// get audio clip file
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));// get the audio
			                                                 // input stream
			clip.start();// playback start
		} catch (Exception e)
		{// print error if there is one
			System.err.println(e.getMessage());
		}
	}
}
