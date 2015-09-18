package burgertime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Three different types of power ups; add lives, add score, and add pepper. 
 *
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller.
 *         Created Feb 19, 2014.
 */
public class PowerUp extends JComponent implements Runnable {
	double width = 30;
	double height = 30;
	private Point2D position;
	private Color color;
	private ArrayList<String> environment;
	boolean exists;
	private BufferedImage img;
	private String powerUp;
	private String bonus;
	boolean paused;

	/**
	 * Constructs PowerUp class and initializes bonus, environment2, xcoor, ycoor.
	 *
	 * @param bonus
	 * @param environment2
	 * @param xcoor
	 * @param ycoor
	 */
	public PowerUp(String bonus, ArrayList<String> environment2, double xcoor,
			double ycoor) {
		this.position = new Point2D.Double(xcoor, ycoor);
		this.environment = environment2;
		this.bonus = bonus;
		this.paused = false;
		if (bonus == "Life") {
			this.powerUp = "powerUp.png";
		}
		if (bonus == "Pepper") {
			this.powerUp = "powerUp2.png";
		}
		if (bonus == "Score") {
			this.powerUp = "powerUp3.png";
		}

		File imgFile = new File(this.powerUp);
		try {
			this.img = ImageIO.read(imgFile);
		} catch (IOException e) {
			// Error reading file.
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawOn(g2);
	}
	
	public void pause(){
		this.paused = true;
	}
	
	public void unpause(){
		this.paused = false;
	}

	/**
	 * Returns the position of the power up.
	 *
	 * @return position
	 */
	public Point2D getPosition() {
		return this.position;
	}

	/**
	 * Draws the power up image on frame.
	 *
	 * @param g
	 */
	public void drawOn(Graphics2D g) {

		g.drawImage(this.img, (int) this.position.getX(),
				(int) this.position.getY(), null);
	}

	/**
	 * returns true if the power up is visible. 
	 *
	 * @return boolean
	 */
	public boolean doesExist() {
		return this.exists;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(8000);
				if(!this.paused){
					double m1 = Math.random();
					if (m1 < 0.7) {
						this.exists = false;
					} else {
						this.exists = true;
					}
				}
			}
		} catch (InterruptedException exception) {
			//
		}

	}

	/**
	 * Remove the power up from game.
	 *
	 */
	public void remove() {
		this.exists = false;
	}

	/**
	 * Returns the type of bonus to add. 
	 *
	 * @return type of bonus.
	 */
	public String getBonus() {
		return this.bonus;
	}

}
