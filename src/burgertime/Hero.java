/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class creates the hero or the chef, which is the player of the game.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 5,
 *         2014.
 */
public class Hero extends AbstractCharacter {
	private final int INITIAL_X = 690;
	private final int INITIAL_Y = 30 * 17;
	private double width;
	private double centerY;
	private double centerX;
	private Color color;
	private double velocity;
	private ArrayList<String> environment;
	private ArrayList<Enemy> enemies;
	private String LRDirection;
	private BufferedImage img;
	boolean paused;
	private int respawnTime;

	/**
	 * Constructor for Hero.
	 * 
	 * @param e
	 * @param r
	 * @param x
	 * @param y
	 * @param ink
	 */
	public Hero(ArrayList<String> e) {
		this.width = 30;
		this.centerX = this.INITIAL_X;
		this.centerY = this.INITIAL_Y;
		this.color = Color.cyan;
		this.velocity = 30;
		this.environment = e;
		this.enemies = new ArrayList<Enemy>();
		new ArrayList<Thread>();
		this.LRDirection = "";
		this.paused = false;
		this.respawnTime = 0;
		File imgFile = new File("chef.png");
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
	 * @see burgertime.AbstractCharacter#drawOn()
	 */
	@Override
	public void drawOn(Graphics2D g) {
		// Ellipse2D.Double circle = new Ellipse2D.Double(this.centerX,
		// this.centerY, this.width, this.width);
		// g.setColor(this.color);
		// g.fill(circle);
		// g.draw(circle);
		g.drawImage(this.img, (int) this.centerX, (int) this.centerY, null);
		this.respawnTime++;
		System.out.println(this.respawnTime);

	}

	/**
	 * Returns the width of the hero.
	 * 
	 * @return width
	 */
	public double getWidth() {
		return this.width;
	}

	public void pause() {
		this.paused = true;
	}

	public void unpause() {
		this.paused = false;
	}

	@Override
	public void moveLeft() {
		if (this.respawnTime > 50) {
			if (!this.paused) {
				if (this.environment.get(((int) (HeroGetY() / 30)) + 1).charAt(
						((int) HeroGetX() / 30) - 1) == 'P'
						|| this.environment.get(((int) (HeroGetY() / 30)) + 1)
								.charAt(((int) HeroGetX() / 30) - 1) == 'X') {
					this.centerX -= this.velocity;
				}
				this.LRDirection = "Left";
			}
		}
	}

	@Override
	public void moveRight() {
		if (this.respawnTime > 50) {
			if (!this.paused) {
				if (this.environment.get(((int) (HeroGetY() / 30)) + 1).charAt(
						((int) HeroGetX() / 30) + 1) == 'P'
						|| this.environment.get(((int) (HeroGetY() / 30)) + 1)
								.charAt(((int) HeroGetX() / 30) + 1) == 'X') {
					this.centerX += this.velocity;
				}
				this.LRDirection = "Right";
			}
		}
	}

	@Override
	public void moveUp() {
		if (this.respawnTime > 50) {
			if (!this.paused) {
				if (this.environment.get((int) (HeroGetY() / 30) - 1).charAt(
						(int) HeroGetX() / 30) == 'L'
						|| this.environment.get((int) (HeroGetY() / 30) - 1)
								.charAt((int) HeroGetX() / 30) == 'X'
						|| this.environment.get((int) (HeroGetY() / 30) - 1)
								.charAt((int) HeroGetX() / 30) == 'Z') {
					this.centerY -= this.velocity;
				}
			}
		}
	}

	@Override
	public void moveDown() {
		if (this.respawnTime > 50) {
			if (!this.paused) {
				if (this.environment.get((int) (HeroGetY() / 30) + 1).charAt(
						(int) HeroGetX() / 30) == 'L'
						|| this.environment.get((int) (HeroGetY() / 30) + 1)
								.charAt((int) HeroGetX() / 30) == 'X') {
					this.centerY += this.velocity;
				}
			}
		}
	}

	/**
	 * Returns the x position of the hero.
	 * 
	 * @return x
	 */
	public double HeroGetX() {
		return this.centerX + this.width / 2;
	}

	/**
	 * Returns the y position of the hero.
	 * 
	 * @return y
	 */
	public double HeroGetY() {
		return this.centerY + this.width / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.AbstractCharacter#reSpawn()
	 */
	@Override
	public void reSpawn() {
		this.respawnTime = 0;
		this.centerX = this.INITIAL_X;
		this.centerY = this.INITIAL_Y;

	}

	@Override
	public boolean isOnLadder() {
		if (this.environment.get((int) (HeroGetY() / 30)).charAt(
				(int) HeroGetX() / 30) == 'L'
				|| this.environment.get((int) (HeroGetY() / 30)).charAt(
						(int) HeroGetX() / 30) == 'X') {
			return true;
		}
		return false;
	}

	@Override
	public boolean isOnPlatform() {
		if (this.environment.get(((int) (HeroGetY() / 30)) + 1).charAt(
				(int) HeroGetX() / 30) == 'P'
				|| this.environment.get(((int) (HeroGetY() / 30)) + 1).charAt(
						(int) HeroGetX() / 30) == 'X') {
			return true;
		}
		return false;
	}

	public String getLRDirection() {
		return this.LRDirection;
	}

	/**
	 * Initializes the enemies.
	 * 
	 * @param e
	 */
	public void setEnemies(ArrayList<Enemy> e) {
		this.enemies = e;
	}

	/**
	 * Creates individual threads for the enemies.
	 * 
	 * @param t
	 */
	public void setEnemyThreads(ArrayList<Thread> t) {

	}

	/**
	 * Sprays pepper and stuns the enemies.
	 * 
	 */
	public void sprayPepper() {
		// if (this.LRDirection == "Left") {
		// for (int k = 0; k < this.enemies.size(); k++) {
		// if ((HeroGetX() - this.enemies.get(k).getX() < 91)
		// && (HeroGetY() == this.enemies.get(k).getY())){
		// int count = 0;
		// System.out.println("Hit");
		// while(count<10000){
		// this.enemies.get(k).freeze();
		// count++;
		// }
		// }
		// }
		// }
		// if (this.LRDirection == "Right") {
		// for (int k = 0; k < this.enemies.size(); k++) {
		// if (this.enemies.get(k).getX() - HeroGetX() < 91
		// && (HeroGetY() == this.enemies.get(k).getY())) {
		// int count = 0;
		// System.out.println("Hit");
		// while(count<10000){
		// this.enemies.get(k).freeze();
		// count++;
		// }
		// }
		// }
		// }
	}

};