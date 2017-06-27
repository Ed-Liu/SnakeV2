/* Author: Edward Liu
 * Date:6/10/17
 * Description: This class is an object used in the game.
 * It is the bomb object, spawned (randomly) only in arcade mode.
 * If the snake eats this without the invincibility effect, the snake dies.
 * The X and Y position of bombs is kept in an array in the board and in the object itself
 */
public class Bomb
{
	private int bPosX;
	private int bPosY;
	private int size;

	Bomb(int x, int y, int si)// constructor instantiated all variables
	{
		bPosX = x;
		bPosY = y;
		size = si;

	}

	public void setPosX(int x)
	{
		bPosX = x;
	}

	public void setPosY(int y)
	{
		bPosY = y;
	}

	public void setSize(int si)
	{
		size = si;
	}

	public int getPosX()
	{
		return bPosX;
	}

	public int getPosY()
	{
		return bPosY;
	}

	public int getSize()
	{
		return size;
	}
}