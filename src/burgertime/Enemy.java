/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class creates the enemies that follow the chef/hero.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 8,
 *         2014.
 */
public class Enemy extends AbstractCharacter implements Runnable {

	private double diameter;
	private double centerY;
	private double centerX;
	private double initialY;
	private double initialX;
	private Color color;
	private Hero hero;
	private ArrayList<String> environment;
	private double velocity;
	BurgerTimeFrame frame;
	private char ladderPosition;
	private char xPosition;
	boolean killConfirmed;
	int runCount;
	private BufferedImage img;
	boolean frozen;
	int frozenCounter;
	boolean paused;
	private int respawnTime;

	/**
	 * Constructor for the Enemy.
	 * 
	 * @param hero
	 * @param e
	 * @param initialX
	 * @param initialY
	 */
	public Enemy(Hero hero, ArrayList<String> e, double initialX,
			double initialY) {
		this.diameter = 30;
		this.initialX = initialX;
		this.initialY = initialY;
		this.centerY = initialY;
		this.centerX = initialX;
		this.color = this.color.MAGENTA;
		this.hero = hero;
		this.environment = e;
		this.velocity = 30;
		this.ladderPosition = ' ';
		this.xPosition = ' ';
		this.killConfirmed = false;
		this.runCount = 0;
		this.frozen = false;
		this.frozenCounter = 0;
		this.paused = false;
		this.respawnTime = 0;
		File imgFile = new File("enemy.png");
		try {
			this.img = ImageIO.read(imgFile);
		} catch (IOException e1) {
			// Error reading file.
			e1.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Drawable#drawOn(java.awt.Graphics2D)
	 */
	@Override
	public void drawOn(Graphics2D g) {
		g.drawImage(this.img, (int) this.centerX, (int) this.centerY, null);

		Point2D newPosition = this.getCenterPoint();
		this.setCenterPoint(newPosition);

	}

	/**
	 * Returns the y position of the enemy.
	 * 
	 * @return y
	 */
	public double getY() {
		return this.centerY + this.diameter / 2;
	}

	/**
	 * Returns the x position of the enemy.
	 * 
	 * @return x
	 */
	public double getX() {
		return this.centerX + this.diameter / 2;
	}

	public double getUpperLeftX() {
		return this.centerX;
	}

	public double getUpperLeftY() {
		return this.centerY;
	}

	/**
	 * Sets the enemy color.
	 * 
	 * @param color
	 */
	public void setColor(Color c) {
		this.color = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.AbstractCharacter#reSpawn()
	 */
	@Override
	public void reSpawn() {
		this.respawnTime = 0;
		this.centerX = this.initialX;
		this.centerY = this.initialY;
	}

	/**
	 * this method allows the enemy to check if they have touched the hero.
	 * 
	 */
	public void killCheck() {
		double interval = 25;
		if (this.getX() >= this.hero.HeroGetX() - interval
				&& this.getX() <= this.hero.HeroGetX() + interval
				&& this.getY() == this.hero.HeroGetY()
				|| this.getX() == this.hero.HeroGetX()
				&& this.getY() >= this.hero.HeroGetY() - interval
				&& this.getY() <= this.hero.HeroGetY() + interval) {
			this.hero.reSpawn();
			this.killConfirmed = true;
		}
	}

	/**
	 * Resets the kill confirmed variable to false.
	 * 
	 */
	public void resetKillConfirmed() {
		this.killConfirmed = false;
	}

	/**
	 * Checks to see if the enemy killed the hero.
	 * 
	 * @return boolean
	 */
	public boolean getKillConfirmed() {
		return this.killConfirmed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.AbstractCharacter#isOnLadder()
	 */
	@Override
	public boolean isOnLadder() {
		if (this.environment.get((int) (getY() / 30)).charAt((int) getX() / 30) == 'L'
				|| this.environment.get((int) (getY() / 30)).charAt(
						(int) getX() / 30) == 'X'
				|| this.environment.get((int) (getY() / 30)).charAt(
						(int) getX() / 30) == 'Z') {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.AbstractCharacter#isOnPlatform()
	 */
	@Override
	public boolean isOnPlatform() {
		if (this.environment.get((int) (getY() / 30) + 1).charAt(
				(int) getX() / 30) == 'P'
				|| this.environment.get((int) (getY() / 30) + 1).charAt(
						(int) getX() / 30) == 'X') {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.AbstractCharacter#moveRight()
	 */

	/**
	 * AI for the enemy. The following-hero algorithm.
	 * 
	 */
	public void FindTarget() {
		if (this.respawnTime > 10) {
			// AI Dealing with ladder
			if (isOnLadder()) {
				if (this.ladderPosition == ' ') {
					if (getY() < this.hero.HeroGetY()) {
						this.ladderPosition = 'd';
						moveDown();
					} else if (getY() > this.hero.HeroGetY()) {
						this.ladderPosition = 'u';
						moveUp();
					}
					if (getY() == this.hero.HeroGetY()) {
						moveUp();
					}
				}
				if (this.ladderPosition == 'd') {
					moveDown();
				} else if (this.ladderPosition == 'u') {
					moveUp();
				}
			}
			if (!isOnLadder()) {
				this.ladderPosition = ' ';
			}

			// AI dealing with the X direction
			if (isOnPlatform() && this.xPosition == ' ') {
				if (getX() < this.hero.HeroGetX()) {
					moveRight();
				}
				if (getX() > this.hero.HeroGetX()) {
					moveLeft();
				} else if (getX() == this.hero.HeroGetX()
						&& getY() == this.hero.HeroGetY()) {
					if (getX() < this.hero.HeroGetX()) {
						moveRight();
					}
					if (getX() > this.hero.HeroGetX()) {
						moveLeft();
					}
				}
				if (getX() == this.hero.HeroGetX()
						&& getY() != this.hero.HeroGetY()) {
					moveLeft();
					this.xPosition = 'l';
				}
			}
			if (isOnPlatform() && this.xPosition == 'l') {
				moveLeft();
			}
			if (isOnLadder() || getX() == 30) {
				this.xPosition = ' ';
			}
		}
		this.respawnTime++;
	}

	@Override
	public void moveLeft() {
		if (this.environment.get(((int) (getY() / 30)) + 1).charAt(
				((int) getX() / 30) - 1) == 'P'
				|| this.environment.get(((int) (getY() / 30)) + 1).charAt(
						((int) getX() / 30) - 1) == 'X') {
			this.centerX -= this.velocity;
		}
	}

	@Override
	public void moveRight() {
		if (this.environment.get(((int) (getY() / 30)) + 1).charAt(
				((int) getX() / 30) + 1) == 'P'
				|| this.environment.get(((int) (getY() / 30)) + 1).charAt(
						((int) getX() / 30) + 1) == 'X') {
			this.centerX += this.velocity;
		}
	}

	@Override
	public void moveUp() {
		if (this.environment.get((int) (getY() / 30) - 1).charAt(
				(int) getX() / 30) == 'L'
				|| this.environment.get((int) (getY() / 30) - 1).charAt(
						(int) getX() / 30) == 'X'
				|| this.environment.get((int) (getY() / 30) - 1).charAt(
						(int) getX() / 30) == 'Z') {
			this.centerY -= this.velocity;
		}
	}

	@Override
	public void moveDown() {
		if (this.environment.get((int) (getY() / 30) + 1).charAt(
				(int) getX() / 30) == 'L'
				|| this.environment.get((int) (getY() / 30) + 1).charAt(
						(int) getX() / 30) == 'X') {
			this.centerY += this.velocity;
		}
	}

	/**
	 * Freezes the enemy.
	 * 
	 */
	public void freeze() {
		this.frozen = true;
	}

	/**
	 * Allows the enemy to move again.
	 * 
	 */
	public void unfreeze() {
		this.frozen = false;
	}

	public void pause() {
		this.paused = true;
	}

	public void unpause() {
		this.paused = false;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 */
	public void runHelper() {
		this.runCount++;
		if (this.runCount % 200 == 0) {
			FindTarget();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			while (true) {
				if (this.paused == false) {
					if (this.frozen == false) {
						runHelper();
						killCheck();
					}
					if (this.frozen == true) {
						this.frozenCounter++;
					}
					if (this.frozenCounter % 3000 == 0) {
						unfreeze();
					}
				}
				Thread.sleep(1);
			}
		} catch (InterruptedException exception) {
			//
		}

	}

}
