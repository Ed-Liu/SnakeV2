
/* 
 * Author: Edward Liu
 * Date: 6/7/17
 * Description: This class is the Pause board class, where three buttons; resume, retry and main menu
 * are contained. It is called when the esc key is pressed in game, and 
 * pauses the game(stops the timer). It also uses the method the main to resume the game.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;

public class PauseBoard extends JPanel implements ActionListener
{
	// the three buttons and background with a button click noise
	private JButton btnRetry;
	private JButton btnResume;
	private JButton btnMainMenu;
	private Image pauseBackground;
	File click = new File("Sounds/click.wav");

	public PauseBoard()
	{
		ImageIcon temp = new ImageIcon("Images/mainmenubackground.png");
		pauseBackground = temp.getImage();

		setLayout(null);
		setPreferredSize(new Dimension(800, 800));

		btnRetry = new JButton(new ImageIcon("Images/retry.png"));
		btnRetry.setBounds(270, 300, 260, 70);
		btnRetry.setBorder(null);
		btnRetry.setContentAreaFilled(false);
		btnRetry.addActionListener(this);
		add(btnRetry);

		btnResume = new JButton(new ImageIcon("Images/resume.png"));
		btnResume.setBounds(270, 380, 260, 70);
		btnResume.setBorder(null);
		btnResume.setContentAreaFilled(false);
		btnResume.addActionListener(this);
		add(btnResume);

		btnMainMenu = new JButton(new ImageIcon("Images/mainmenu.png"));
		btnMainMenu.addActionListener(this);
		btnMainMenu.setBounds(270, 460, 260, 70);
		btnMainMenu.setBorder(null);
		btnMainMenu.setContentAreaFilled(false);
		add(btnMainMenu);
	}

	// paint method to paint the background
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(pauseBackground, 0, 0, this);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnRetry)
		{
			playSound(click);
			Main.createBoard();// restarts the board
		}
		if (e.getSource() == btnResume)
		{
			playSound(click);
			Main.resumeGame();// resumes from where the game was paused
		}
		if (e.getSource() == btnMainMenu)
		{
			playSound(click);
			Main.createMenu();// goes back to the main menu
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
