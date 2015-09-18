/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Creates in-game stats to display in game.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 13, 2014.
 */
public class Stats extends JPanel {

	private int score;
	private int lives;
	private int peppers;
	private JLabel scoreDisplay;
	private JLabel livesDisplay;
	private JLabel peppersDisplay;

	/**
	 * Constructor for Stats class.
	 * 
	 */
	public Stats() {
		this.peppers = 5;
		this.lives = 3;
		this.score = 0;

		// Make and show panel to display game stats.
		Font statsFont = new Font("Stats Font", Font.PLAIN, 30);

		this.scoreDisplay = new JLabel();
		this.scoreDisplay.setText("Score: " + getScore());
		this.scoreDisplay.setFont(statsFont);
		add(this.scoreDisplay);

		this.livesDisplay = new JLabel();
		add(this.livesDisplay);
		this.livesDisplay.setBorder(new EmptyBorder(0, 50, 0, 50));
		this.livesDisplay.setFont(statsFont);
		this.livesDisplay.setText("Lives: " + getLives());

		this.peppersDisplay = new JLabel();
		this.peppersDisplay.setFont(statsFont);
		this.peppersDisplay.setText("Peppers: " + getPeppers());
		add(this.peppersDisplay);

		setBackground(Color.YELLOW);
		setSize(100, 500);
	}

	/**
	 * Return amount of peppers;
	 * 
	 * @return peppers
	 */
	public int getPeppers() {
		// TODO Auto-generated method stub
		return this.peppers;
	}

	/**
	 * Return amount of lives.
	 * 
	 * @return lives
	 */
	public int getLives() {
		// TODO Auto-generated method stub
		return this.lives;
	}

	/**
	 * Return remaining score.
	 * 
	 * @return score
	 */

	public int getScore() {
		return this.score;
	}

	/**
	 * Increases score by given amount
	 * 
	 * @param amount
	 *            to increment
	 */
	public void addScore(int amount) {
		this.score += amount;
		this.scoreDisplay.setText("Score: " + getScore());
	}

	/**
	 * Increases pepper by given amount
	 * 
	 * @param x
	 *            amount to increment
	 */
	public void addPepper(int x) {
		this.peppers += x;
		this.peppersDisplay.setText("Peppers: " + getPeppers());
	}

	/**
	 * Increases lives by given amount.
	 * 
	 * @param x
	 *            amount to increment
	 */
	public void addLives(int x) {
		this.lives += x;
		this.livesDisplay.setText("Lives: " + getLives());
	}

}