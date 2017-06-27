/* Author: Edward Liu
 * Date:6/1/17
 * Description: This class is an object used in the game.
 * It is the head object, which is the head of the snake.
 * This object is the first body part of the snake, which dictates the movement 
 * of the entire snake. This head is also responsible for collision checks with cherry, bomb(aracade),
 * itself or the walls.
 * The X and Y position of the head is kept in an array in the board and in the object itself.
 */
public class Head
{
	private int speedX;
	private int speedY;
	private int sPosX;
	private int sPosY;
	private int size;

	Head(int sx, int sy, int x, int y, int si)// constructor to instantiate all
	                                          // variables
	{
		speedX = sx;
		speedY = sy;
		sPosX = x;
		sPosY = y;
		size = si;

	}

	public void setSpeedX(int s)
	{
		speedX = s;
	}

	public void setSpeedY(int s)
	{
		speedY = s;
	}

	public void setPosX(int x)
	{
		sPosX = x;
	}

	public void setPosY(int y)
	{
		sPosY = y;
	}

	public void setSize(int si)
	{
		size = si;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public int getSpeedY()
	{
		return speedY;
	}

	public int getPosX()
	{
		return sPosX;
	}

	public int getPosY()
	{
		return sPosY;
	}

	public int getSize()
	{
		return size;
	}

}
