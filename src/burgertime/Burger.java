package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class allows burgers components to be drawn and also all the functions
 * like falling from one platform to another.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 12, 2014.
 */
public class Burger implements Drawable {

	private final double WIDTH = 30;
	private final double HEIGHT = 30;
	private Color color;
	private double yPosition;
	private Hero hero;
	private ArrayList<Double> xPos;
	private ArrayList<Double> yPos;
	private double leftY;
	private double centerLeftY;
	private double centerRightY;
	private double rightY;
	private double leftX;
	private double centerLeftX;
	private double centerRightX;
	private ArrayList<Boolean> indents;
	private boolean fall;

	/**
	 * This is the constructor of the class Burger. It takes the hero, the
	 * position and color.
	 * 
	 * @param color
	 * @param position
	 * @param hero
	 */
	public Burger(Color color, Point2D position, Hero hero) {
		this.color = color;
		this.yPosition = position.getY();
		this.xPos = new ArrayList<Double>();
		this.yPos = new ArrayList<Double>();
		this.indents = new ArrayList<Boolean>();
		this.hero = hero;
		this.fall = false;

		this.xPos.add(this.leftX = position.getX());
		this.xPos.add(this.centerLeftX = this.leftX + this.WIDTH);
		this.xPos.add(this.centerRightX = this.centerLeftX + this.WIDTH);
		this.xPos.add(this.centerRightX + this.WIDTH);
		this.yPos.add(this.leftY = position.getY());
		this.yPos.add(this.centerLeftY = position.getY());
		this.yPos.add(this.centerRightY = position.getY());
		this.yPos.add(this.rightY = position.getY());

		this.indents.add(false);
		this.indents.add(false);
		this.indents.add(false);
		this.indents.add(false);

	}

	@Override
	public void drawOn(Graphics2D g) {

		for (int k = 0; k < this.xPos.size(); k++) {
			Rectangle2D.Double rect = new Rectangle2D.Double(this.xPos.get(k),
					this.yPos.get(k), this.WIDTH, this.HEIGHT);
			g.setColor(this.color);
			g.fill(rect);
			g.draw(rect);
		}

	}

	/**
	 * Moves burger to given point.
	 *
	 * @param yCoord
	 */
	public void moveTo(double yCoord) {
		this.yPosition = yCoord;
	}

	/**
	 * Returns the y coordinate of the burger.
	 *
	 * @return Y
	 */
	public Double getYPosition() {
		return this.yPosition;
	}

	/**
	 * Returns the x coordinate of the burger. 
	 *
	 * @return X
	 */
	public Double getXPosition() {
		return this.leftX;
	}

	/**
	 * Checks to see if hero is on the burger.
	 *
	 */
	public void isOnBurger() {

		double heroX = this.hero.HeroGetX() - (this.hero.getWidth() / 2);
		double heroY = this.hero.HeroGetY() - (this.hero.getWidth() / 2);

		for (int k = 0; k < this.xPos.size(); k++) {

			if (heroX == this.xPos.get(k) && heroY == this.yPos.get(k)) {
				double newY = this.yPos.get(k) + this.HEIGHT / 2;
				this.yPos.remove(k);
				this.yPos.add(k, newY);
				this.indents.remove(k);
				this.indents.add(k, true);
				this.yPosition = newY;
			}
		}
	}

	/**
	 * Checks to see if all burger components are indented.
	 *
	 * @return boolean
	 */
	public boolean isIndented() {
		for (int i = 0; i < this.indents.size(); i++) {
			if (!this.indents.get(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Drops the burger component.
	 *
	 */
	public void drop() {
		this.fall = true;
	}

	/**
	 * This method makes the burger fall
	 * 
	 */
	public void addGravity() {
		double velocity = 5;
		if (this.fall) {
			this.yPosition += velocity;
			for (int i = 0; i < this.yPos.size(); i++) {
				this.yPos.remove(i);
				this.yPos.add(i, this.yPosition);
			}
		}
	}

	/**
	 * Stops the burger from falling.
	 *
	 */
	public void stop() {
		this.fall = false;
	}

	/**
	 * Unindents all burgers. 
	 *
	 */
	public void unIndent() {

		for (int i = 0; i < this.indents.size(); i++) {
			this.indents.remove(i);
			this.indents.add(i, false);
		}
	}

	/**
	 * Returns the width of the burger.
	 *
	 * @return width
	 */
	public double getWidth() {
		return this.WIDTH;
	}

	/**
	 * Checks to see if the burger is falling. 
	 *
	 * @return boolean
	 */
	public boolean isMoving() {
		return this.fall;
	}
}
