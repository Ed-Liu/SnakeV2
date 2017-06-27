
/* 
 * Author: Edward Liu
 * Date: 6/8/17
 * Description: This class is the Highscore board class, where many buttons
 * are contained(buttons used to find specific highscores through gamemode and difficulty. 
 * It also contains 3 JTextAreas to display the data of each highscore. It is called when 
 * a new Highscore is saved or from the main menu, however if a highscore has been 
 * saved in the endBoard, it will directly open the highscores of the specific gamemode and difficulty
 * the user was playing on.
 * Has a displayHS method to display the highscores of a specific gamemode and difficulty
 * using IO.
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
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HighScoreBoard extends JPanel implements ActionListener
{
	private JButton btnMainMenu;
	private JButton btnEasy;
	private JButton btnMedium;
	private JButton btnHard;
	private JButton btnClassic;
	private JButton btnArcade;
	private JLabel lblHighScore;
	private String gameMode;
	private String difficulty;
	private Image hSBackground;
	private JTextArea scores = new JTextArea();
	private JTextArea ranks = new JTextArea();
	private JTextArea names = new JTextArea();
	File click = new File("Sounds/click.wav");

	public HighScoreBoard(int g, String d)// constructor used when user saves a
	                                      // highscore directly from endboard
	{

		setLayout(null);
		setPreferredSize(new Dimension(800, 800));

		ImageIcon temp = new ImageIcon("Images/mainmenubackground.png");
		hSBackground = temp.getImage();

		btnMainMenu = new JButton(new ImageIcon("Images/mainmenu.png"));
		btnMainMenu.addActionListener(this);
		btnMainMenu.setBounds(270, 700, 260, 70);
		btnMainMenu.setBorder(null);
		btnMainMenu.setContentAreaFilled(false);
		add(btnMainMenu);

		lblHighScore = new JLabel(new ImageIcon("Images/highscores1.png"));
		lblHighScore.setBounds(100, 10, 600, 200);
		add(lblHighScore);
		if (g == 1)
		{
			gameMode = "Arcade";
		} else
		{
			gameMode = "";
		}
		try
		{
			displayHS(gameMode, d);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public HighScoreBoard()// regular constructor from the main menu, contains
	// all the buttons to navigate through each difficulty and gamemode to find
	// the specific highscore
	{

		setLayout(null);
		setPreferredSize(new Dimension(800, 800));

		ImageIcon temp = new ImageIcon("Images/mainmenubackground.png");
		hSBackground = temp.getImage();

		btnMainMenu = new JButton(new ImageIcon("Images/mainmenu.png"));
		btnMainMenu.addActionListener(this);
		btnMainMenu.setBounds(270, 700, 260, 70);
		btnMainMenu.setBorder(null);
		btnMainMenu.setContentAreaFilled(false);
		add(btnMainMenu);

		btnEasy = new JButton(new ImageIcon("Images/easy.png"));
		btnEasy.setBounds(270, 380, 260, 70);
		btnEasy.setBorder(null);
		btnEasy.setContentAreaFilled(false);
		btnEasy.addActionListener(this);
		btnEasy.setVisible(false);
		add(btnEasy);

		btnMedium = new JButton(new ImageIcon("Images/medium.png"));
		btnMedium.setBounds(270, 460, 260, 70);
		btnMedium.setBorder(null);
		btnMedium.setContentAreaFilled(false);
		btnMedium.addActionListener(this);
		btnMedium.setVisible(false);
		add(btnMedium);

		btnHard = new JButton(new ImageIcon("Images/hard.png"));
		btnHard.setBounds(270, 540, 260, 70);
		btnHard.setBorder(null);
		btnHard.setContentAreaFilled(false);
		btnHard.addActionListener(this);
		btnHard.setVisible(false);
		add(btnHard);

		btnClassic = new JButton(new ImageIcon("Images/classic.png"));
		btnClassic.setBounds(130, 480, 260, 70);
		btnClassic.setBorder(null);
		btnClassic.setContentAreaFilled(false);
		btnClassic.addActionListener(this);
		btnClassic.setVisible(true);
		add(btnClassic);

		btnArcade = new JButton(new ImageIcon("Images/arcade.png"));
		btnArcade.setBounds(410, 480, 260, 70);
		btnArcade.setBorder(null);
		btnArcade.setContentAreaFilled(false);
		btnArcade.addActionListener(this);
		btnArcade.setVisible(true);
		add(btnArcade);

		lblHighScore = new JLabel(new ImageIcon("Images/highscores1.png"));
		lblHighScore.setBounds(100, 150, 600, 200);
		add(lblHighScore);

	}

	private void displayHS(String g, String d) throws IOException// method for
	                                                             // setting
	// the correct data into the textareas. Uses IO
	{
		IO.openInputFile("Data//HighScores" + g + d + ".txt");// opens the file
		                                                      // based on
		                                                      // gamemode(g) and
		                                                      // difficulty(d).
		String temp = "";// temp for retrieving lines from IO

		// score holder
		String s = "";
		// ranking holder
		String r = "";
		// name holder
		String n = "";
		int a;
		int b;

		temp = IO.readLine();

		while (temp != null)
		{

			a = temp.indexOf(" ");
			b = temp.lastIndexOf(" ");
			n += temp.substring(0, a) + "\n";// this is where the name is stored
			                                 // in the text file(from 0 to the
			                                 // first space)
			s += temp.substring(a + 1, b) + "\n";// score stored from after the
			                                     // first space to the second
			                                     // space
			r += temp.substring(b + 1) + "\n";// ranking is stored after the
			                                  // second space to the end of line
			temp = IO.readLine();

		}

		IO.closeInputFile();

		names.setVisible(true);

		names.setText(n); // sets the specific text onto each text area.
		names.setEditable(false);
		names.setBounds(300, 200, 300, 500);
		names.setFont(new Font("Verdana", Font.BOLD, 30));
		names.setOpaque(false);
		names.setForeground(new Color(84, 142, 81));
		repaint();
		add(names);

		scores.setVisible(true);

		scores.setText(s);
		scores.setEditable(false);
		scores.setBounds(600, 200, 200, 500);
		scores.setFont(new Font("Verdana", Font.BOLD, 30));
		scores.setOpaque(false);
		scores.setForeground(new Color(84, 142, 81));
		repaint();
		add(scores);

		ranks.setVisible(true);

		ranks.setText(r);
		ranks.setEditable(false);
		ranks.setBounds(150, 200, 200, 500);
		ranks.setFont(new Font("Verdana", Font.BOLD, 30));
		ranks.setOpaque(false);
		ranks.setForeground(new Color(84, 142, 81));
		repaint();
		add(ranks);
	}

	public void paintComponent(Graphics g)// method to draw the background
	{
		super.paintComponent(g);
		g.drawImage(hSBackground, 0, 0, this);

	}

	@Override
	public void actionPerformed(ActionEvent e)// the difficulty and gameMode
	                                          // Strings determine which txt
	                                          // file to open
	// by passing them into the displayHS Method.
	{

		if (e.getSource() == btnMainMenu)
		{
			playSound(click);
			Main.createMenu();
		}
		if (e.getSource() == btnEasy)
		{
			playSound(click);
			difficulty = "E";// sets the desired difficulty to E
			btnEasy.setVisible(false);
			btnMedium.setVisible(false);
			btnHard.setVisible(false);
			try
			{
				displayHS(gameMode, difficulty);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			lblHighScore.setBounds(100, 10, 600, 200);
		}
		if (e.getSource() == btnMedium)
		{
			playSound(click);
			difficulty = "M";// sets the desired difficulty to M
			btnEasy.setVisible(false);
			btnMedium.setVisible(false);
			btnHard.setVisible(false);
			try
			{
				displayHS(gameMode, difficulty);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			lblHighScore.setBounds(100, 10, 600, 200);
		}
		if (e.getSource() == btnHard)
		{
			playSound(click);
			difficulty = "H";// sets the desired difficulty to H
			btnEasy.setVisible(false);
			btnMedium.setVisible(false);
			btnHard.setVisible(false);
			try
			{
				displayHS(gameMode, difficulty);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			lblHighScore.setBounds(100, 10, 600, 200);
		}
		if (e.getSource() == btnClassic)
		{
			playSound(click);
			gameMode = "";// sets the desired gamemode to ""
			btnClassic.setVisible(false);
			btnArcade.setVisible(false);
			btnEasy.setVisible(true);
			btnMedium.setVisible(true);
			btnHard.setVisible(true);
		}
		if (e.getSource() == btnArcade)
		{
			playSound(click);
			gameMode = "Arcade";// sets the desired gamemode to Arcade
			btnClassic.setVisible(false);
			btnArcade.setVisible(false);
			btnEasy.setVisible(true);
			btnMedium.setVisible(true);
			btnHard.setVisible(true);
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
