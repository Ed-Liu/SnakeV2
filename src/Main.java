
/* 
 * Author: Edward Liu
 * Date: 6/6/17
 * Description: This class is the main class, where all the classes are instantiated
 * and called upon. It contains some methods to pause and resume games, and 
 * open other panels/boards. It also contains the main method to start the game
 * and the original JFrame
 */

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

public class Main
{
	private static JFrame jf;

	static MenuBoard menu;
	static Board board;
	static PauseBoard pauseBoard;
	static EndBoard endBoard;
	static HighScoreBoard hSB;
	private static boolean initialBootup = false;

	public static void main(String[] args)
	{
		jf = new JFrame();
		jf.setSize(800, 800);
		jf.setTitle("Snake");
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		createMenu();
		initialBootup = true;
	}

	public static boolean getInitial()// for the mainmenu
	{
		return initialBootup;
	}

	public static void createMenu()// creates a MenuBoard
	{

		jf.getContentPane().removeAll();
		menu = new MenuBoard();
		menu.setVisible(true);
		jf.setContentPane(menu);
		jf.invalidate();
		jf.validate();
		menu.requestFocus();
		jf.pack();

	}

	public static void createBoard()// creates a Board
	{
		jf.getContentPane().removeAll();
		board = new Board();
		board.setVisible(true);
		jf.setContentPane(board);
		jf.invalidate();
		jf.validate();
		board.requestFocus();
		jf.pack();
		board.setBackground(Color.lightGray);
	}

	public static void createPauseBoard()// creates a PauseBoard
	{
		jf.getContentPane().setVisible(false);
		pauseBoard = new PauseBoard();
		pauseBoard.setVisible(true);
		jf.setContentPane(pauseBoard);
		jf.invalidate();
		jf.validate();
		pauseBoard.requestFocus();
		jf.pack();
		pauseBoard.setBackground(Color.lightGray);

	}

	public static void resumeGame()// Because createPauseBoard doesnt actually
	                               // kill the Board, the game is simply
	                               // paused(timer stopped) so this method
	                               // resumes the game
	{
		jf.getContentPane().removeAll();
		board.setVisible(true);
		jf.setContentPane(board);
		jf.invalidate();
		jf.validate();
		board.requestFocus();
		jf.pack();
		board.resume();
	}

	public static void createEndBoard() throws IOException// creates an EndBoard
	{
		jf.getContentPane().removeAll();
		endBoard = new EndBoard();
		endBoard.setVisible(true);
		jf.setContentPane(endBoard);
		jf.invalidate();
		jf.validate();
		endBoard.requestFocus();
		jf.pack();
		endBoard.setBackground(Color.lightGray);
	}

	public static void createHighScoreBoard()// creates a HighScoreBoard
	{
		jf.getContentPane().removeAll();
		hSB = new HighScoreBoard();
		hSB.setVisible(true);
		jf.setContentPane(hSB);
		jf.invalidate();
		jf.validate();
		hSB.requestFocus();
		jf.pack();
		hSB.setBackground(Color.lightGray);
	}

	public static void createHighScoreBoard(int g, String d)// Overloaded
	                                                        // Parameters for a
	                                                        // direct highscores
	                                                        // board
	                                                       
	{
		jf.getContentPane().removeAll();
		hSB = new HighScoreBoard(g, d);
		hSB.setVisible(true);
		jf.setContentPane(hSB);
		jf.invalidate();
		jf.validate();
		hSB.requestFocus();
		jf.pack();
		hSB.setBackground(Color.lightGray);
	}

}
