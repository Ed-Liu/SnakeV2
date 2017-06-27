/* Author: Edward Liu
 * Date:6/1/17
 * Description: This class is an object used in the game.
 * It is the cherry object, spawned randomly and worth points.
 * If the snake eats this, the snake will grow.
 * The X and Y position of the cherry is kept in an array in the board, and in the object itself
 * there is only ever one cherry at once(unlink bomb)
 */
public class Cherry
{
	private int cPosX;
	private int cPosY;
	private int size;

	Cherry(int x, int y, int si)// constructor instantiated all variables
	{
		cPosX = x;
		cPosY = y;
		size = si;

	}

	public void setPosX(int x)
	{
		cPosX = x;
	}

	public void setPosY(int y)
	{
		cPosY = y;
	}

	public void setSize(int si)
	{
		size = si;
	}

	public int getPosX()
	{
		return cPosX;
	}

	public int getPosY()
	{
		return cPosY;
	}

	public int getSize()
	{
		return size;
	}
}
