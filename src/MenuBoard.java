
/* 
 * Author: Edward Liu
 * Date: 6/6/17
 * Description: This class is the menuBoard class, where the user is brought on startup.
 * It  contains many buttons, including instructions and highscores and play options(difficulty and gamemode).
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;//imported to play audio clips
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MenuBoard extends JPanel implements ActionListener
{
	// instantiation of panel objects...
	private JButton btnPlay;
	private JButton btnInstructions;
	private JButton btnHighScores;
	private JButton btnClassic;
	private JButton btnArcade;
	private JButton btnBack;
	private JButton btnEasy;
	private JButton btnMedium;
	private JButton btnHard;
	private JLabel chooseGameMode;
	private JLabel chooseDifficulty;
	private Image hTP;
	private Image iB;
	private JTextArea instructions;
	private Image background;
	private Image snakeLogo;
	private boolean flag = false;
	private boolean flag2 = false;
	File music = new File("Sounds/menuMusic.wav");
	File click = new File("Sounds/click.wav");

	public MenuBoard()
	{

		flag = true;
		flag2 = false;
		setLayout(null);
		setPreferredSize(new Dimension(800, 800));

		if (Main.getInitial() == false)// so that the music only plays once, at
		                               // launch
		{
			playMusic(music);
		}

		ImageIcon temp = new ImageIcon("Images/mainmenubackground.png");// load
		                                                                // imgs
		background = temp.getImage();

		ImageIcon temp2 = new ImageIcon("Images/snakelogo.png");
		snakeLogo = temp2.getImage();

		ImageIcon temp3 = new ImageIcon("Images/htp.png");
		hTP = temp3.getImage();

		ImageIcon temp4 = new ImageIcon("Images/instructionsbackground.png");
		iB = temp4.getImage();

		btnPlay = new JButton(new ImageIcon("Images/play.png"));
		btnPlay.setBounds(270, 380, 260, 70);
		btnPlay.setOpaque(false);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setBorder(null);
		btnPlay.addActionListener(this);
		add(btnPlay);

		instructions = new JTextArea();
		instructions.setVisible(false);
		instructions.setEditable(false);
		instructions.setFont(new Font("Verdana", Font.BOLD, 17));
		instructions.setBounds(100, 135, 700, 550);
		instructions.setOpaque(false);
		instructions.setForeground(new Color(49, 92, 47));
		add(instructions);

		btnInstructions = new JButton(new ImageIcon("Images/instructions.png"));
		btnInstructions.setBounds(270, 460, 260, 70);
		btnInstructions.setBorder(null);
		btnInstructions.setContentAreaFilled(false);
		btnInstructions.addActionListener(this);
		add(btnInstructions);

		btnHighScores = new JButton(new ImageIcon("Images/highscores.png"));
		btnHighScores.addActionListener(this);
		btnHighScores.setBounds(270, 540, 260, 70);
		btnHighScores.setBorder(null);
		btnHighScores.setContentAreaFilled(false);
		add(btnHighScores);

		btnClassic = new JButton(new ImageIcon("Images/classic.png"));
		btnClassic.setBounds(130, 480, 260, 70);
		btnClassic.addActionListener(this);
		btnClassic.setVisible(false);
		btnClassic.setBorder(null);
		btnClassic.setContentAreaFilled(false);
		add(btnClassic);

		btnArcade = new JButton(new ImageIcon("Images/arcade.png"));
		btnArcade.setBounds(410, 480, 260, 70);
		btnArcade.addActionListener(this);
		btnArcade.setVisible(false);
		btnArcade.setBorder(null);
		btnArcade.setContentAreaFilled(false);
		btnArcade.setVisible(false);
		add(btnArcade);

		chooseGameMode = new JLabel(new ImageIcon("Images/gamemode.png"));
		chooseGameMode.setBounds(100, 150, 600, 200);
		chooseGameMode.setFont(new Font("Verdana", Font.PLAIN, 45));
		chooseGameMode.setVisible(false);

		add(chooseGameMode);

		btnBack = new JButton(new ImageIcon("Images/back.png"));
		btnBack.setBounds(270, 700, 260, 70);
		btnBack.addActionListener(this);
		btnBack.setBorder(null);
		btnBack.setContentAreaFilled(false);
		btnBack.setVisible(false);
		add(btnBack);

		btnEasy = new JButton(new ImageIcon("Images/easy.png"));
		btnEasy.setBounds(270, 380, 260, 70);
		btnEasy.addActionListener(this);
		btnEasy.setBorder(null);
		btnEasy.setContentAreaFilled(false);
		btnEasy.setVisible(false);
		add(btnEasy);

		btnMedium = new JButton(new ImageIcon("Images/medium.png"));
		btnMedium.setBounds(270, 460, 260, 70);
		btnMedium.addActionListener(this);
		btnMedium.setBorder(null);
		btnMedium.setContentAreaFilled(false);
		btnMedium.setVisible(false);
		add(btnMedium);

		btnHard = new JButton(new ImageIcon("Images/hard.png"));
		btnHard.setBounds(270, 540, 260, 70);
		btnHard.addActionListener(this);
		btnHard.setBorder(null);
		btnHard.setContentAreaFilled(false);
		btnHard.setVisible(false);
		add(btnHard);

		chooseDifficulty = new JLabel(new ImageIcon("Images/difficulty.png"));
		chooseDifficulty.setBounds(100, 150, 600, 200);
		chooseDifficulty.setFont(new Font("Verdana", Font.PLAIN, 45));
		chooseDifficulty.setVisible(false);
		add(chooseDifficulty);

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		if (flag == true)// flag to control when to draw the logo
			g.drawImage(snakeLogo, 244, 50, this);
		if (flag2 == true)// flag to control when to draw instructions pictures
		{
			g.drawImage(hTP, 200, 10, 400, 100, this);
			g.drawImage(iB, 40, 105, 700, 600, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnPlay)
		{
			btnPlay.setVisible(false);
			btnInstructions.setVisible(false);
			btnHighScores.setVisible(false);
			btnClassic.setVisible(true);
			btnArcade.setVisible(true);
			chooseGameMode.setVisible(true);
			btnBack.setVisible(true);
			flag = false;// make sure the logo and instructions drawings are not
			             // drawn
			flag2 = false;
			playSound(click);
			repaint();

		}
		if (e.getSource() == btnInstructions)
		{
			IO.openInputFile("Data//Instructions.txt");// read the instructions
			                                           // from txt file with IO
			String ist;// temporary container
			String ists = "";// entire string container
			try
			{
				ist = IO.readLine();

				while (ist != null)
				{

					ists += ist + "\n";
					ist = IO.readLine();

				}

				IO.closeInputFile();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			instructions.setText(ists);// set the instructions text here
			instructions.setVisible(true);
			btnInstructions.setVisible(false);
			btnHighScores.setVisible(false);
			btnPlay.setVisible(false);
			btnBack.setVisible(true);
			flag = false;
			flag2 = true;
			playSound(click);
			repaint();

		}
		if (e.getSource() == btnHighScores)// opens HighScoreBoard
		{
			playSound(click);
			Main.createHighScoreBoard();

		}
		if (e.getSource() == btnClassic)
		{
			btnClassic.setVisible(false);
			btnArcade.setVisible(false);
			chooseGameMode.setVisible(false);
			btnEasy.setVisible(true);
			btnMedium.setVisible(true);
			btnHard.setVisible(true);
			chooseDifficulty.setVisible(true);
			Board.setGameMode(0);// sets the gamemode to classic on the board
			playSound(click);

		}
		if (e.getSource() == btnArcade)
		{

			btnClassic.setVisible(false);
			btnArcade.setVisible(false);
			chooseGameMode.setVisible(false);
			btnEasy.setVisible(true);
			btnMedium.setVisible(true);
			btnHard.setVisible(true);
			chooseDifficulty.setVisible(true);
			Board.setGameMode(1);// sets the gamemode to arcade on the board
			playSound(click);
		}
		if (e.getSource() == btnBack)// resets back to beginning of panel
		{
			btnPlay.setVisible(true);
			btnInstructions.setVisible(true);
			btnHighScores.setVisible(true);
			btnClassic.setVisible(false);
			btnArcade.setVisible(false);
			chooseGameMode.setVisible(false);
			btnEasy.setVisible(false);
			btnMedium.setVisible(false);
			btnHard.setVisible(false);
			chooseDifficulty.setVisible(false);
			btnBack.setVisible(false);
			instructions.setVisible(false);
			flag = true;
			flag2 = false;
			playSound(click);
			repaint();
		}
		// set the difficulties in the board and create the board.
		// (The gamemode AND difficulty has been set after this last set of
		// buttons)
		// (These buttons only show after gamemode selection)
		if (e.getSource() == btnEasy)
		{
			Board.setSleepTime(100);
			playSound(click);
			Main.createBoard();
		}
		if (e.getSource() == btnMedium)
		{
			Board.setSleepTime(70);
			playSound(click);
			Main.createBoard();
		}
		if (e.getSource() == btnHard)
		{
			Board.setSleepTime(40);
			playSound(click);
			Main.createBoard();
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
			FloatControl gainControl = (FloatControl) clip
			        .getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(6.0f);
			clip.start();// playback start
		} catch (Exception e)
		{// print error if there is one
			System.err.println(e.getMessage());
		}
	}

	// method to play looped music file
	public static void playMusic(File file)
	{
		try
		{
			// get audio clip file
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.loop(Clip.LOOP_CONTINUOUSLY);// loop for the music.
			FloatControl gainControl = (FloatControl) clip
			        .getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-15.0f); // Reduce volume by 15 decibels.
			clip.start();
		} catch (Exception e)
		{// print error if there is one
			System.err.println(e.getMessage());
		}
	}
}
