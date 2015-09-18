/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * This abstract class has methods that get points and colors for the
 * characters.
 * 
  * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 12, 2014.
 */
public abstract class AbstractCharacter implements Drawable {
	private Color color;
	private Point2D centerPoint;

	/**
	 * Constructor for AbstractCharacter.
	 *
	 */
	public AbstractCharacter() {
		this.centerPoint = new Point2D.Double(0, 0);
	}

	/**
	 * moves the character to the specified point.
	 *
	 * @param point
	 */
	public void moveTo(Point2D point) {
		this.centerPoint = point;
	}

	/**
	 * Sets the character at the specified point.
	 *
	 * @param centerPoint
	 */
	protected void setCenterPoint(Point2D centerPoint) {
		this.centerPoint = centerPoint;
	}

	/**
	 * Returns the character's position. 
	 *
	 * @return
	 */
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	/**
	 * Returns the character's color.
	 *
	 * @param x
	 */
	public void getColor(Color x) {
		this.color = x;
	}

	/**
	 * Resets the character's position.
	 *
	 */
	public abstract void reSpawn();

	/**
	 * Checks to see if character is on ladder.
	 *
	 * @return boolean
	 */
	public abstract boolean isOnLadder();

	/**
	 * Checks to see if character is on platform.
	 *
	 * @return boolean
	 */
	public abstract boolean isOnPlatform();

	/**
	 * Moves the character right.
	 *
	 */
	public abstract void moveRight();

	/**
	 * Moves the character left.
	 *
	 */
	public abstract void moveLeft();

	/**
	 * Moves the character up.
	 *
	 */
	public abstract void moveUp();

	/**
	 * Moves the character down.
	 *
	 */
	public abstract void moveDown();
}
