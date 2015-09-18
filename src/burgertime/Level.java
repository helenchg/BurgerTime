package burgertime;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

/**
 * This class contains all the elements of a level, such as platforms, ladders,
 * powerups, etc.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 19,
 *         2014.
 */
public class Level extends JComponent {

	private final int ASPECT_RATIO = 30;
	private ArrayList<Ladder> ladders;
	private ArrayList<Platform> platforms;
	private ArrayList<Space> spaces;
	private ArrayList<String> environment;
	private ArrayList<Burger> burgers;
	private ArrayList<PowerUp> powerups;
	private Hero hero;
	private ArrayList<Enemy> enemies;
	private ArrayList<Plate> plates;
	private ArrayList<Thread> enemyThreads;
	
	/**
	 * Constructor for the level. 
	 *
	 * @param levelFile
	 */
	public Level(String levelFile) {
		this.environment = new ArrayList<String>();
		this.platforms = new ArrayList<Platform>();
		this.spaces = new ArrayList<Space>();
		this.ladders = new ArrayList<Ladder>();
		this.burgers = new ArrayList<Burger>();
		this.plates = new ArrayList<Plate>();
		this.enemies = new ArrayList<Enemy>();
		this.powerups = new ArrayList<PowerUp>();
		this.enemyThreads = new ArrayList<Thread>();

		File file = new File(levelFile);
		try {
			Scanner inScanner = new Scanner(file);

			while (inScanner.hasNext()) {
				this.environment.add(inScanner.next());
			}
			inScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.hero = new Hero(this.environment);

		convert();
	}

	/**
	 * Returns an array list of enemies. 
	 *
	 * @return enemies.
	 */
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * Converts the files into frame components. 
	 *
	 */
	public void convert() {

		System.out.println("# of rows: " + this.environment.size());
		System.out.println("# of columns: " + this.environment.get(0).length());

		for (int i = 0; i < this.environment.size(); i++) {
			for (int j = 0; j < this.environment.get(i).length(); j++) {

				if (this.environment.get(i).charAt(j) == 'L'
						|| this.environment.get(i).charAt(j) == 'X') {
					this.ladders.add(new Ladder(new Point2D.Double(
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i),
							"ladder.png"));
				}

				if (this.environment.get(i).charAt(j) == 'Z') {
					Ladder l = new Ladder(new Point2D.Double(this.ASPECT_RATIO
							* j, this.ASPECT_RATIO * i));
					this.ladders.add(l);
				}

				if (this.environment.get(i).charAt(j) == 'P'
						|| this.environment.get(i).charAt(j) == 'X') {
					this.platforms.add(new Platform(new Point2D.Double(
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i)));
				}
				if (this.environment.get(i).charAt(j) == '_') {
					this.spaces.add(new Space(new Point2D.Double(
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i)));
				}
				if (this.environment.get(i).charAt(j) == 'B') {
					this.burgers.add(new Burger(Color.orange,
							new Point2D.Double(this.ASPECT_RATIO * j,
									this.ASPECT_RATIO * i), this.hero));
				}
				if (this.environment.get(i).charAt(j) == 'G') {
					this.burgers.add(new Burger(Color.green,
							new Point2D.Double(this.ASPECT_RATIO * j,
									this.ASPECT_RATIO * i), this.hero));
				}
				if (this.environment.get(i).charAt(j) == 'R') {
					this.burgers.add(new Burger(Color.pink, new Point2D.Double(
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i),
							this.hero));
				}
				if (this.environment.get(i).charAt(j) == 'B') {
					this.burgers.add(new Burger(Color.orange,
							new Point2D.Double(this.ASPECT_RATIO * j,
									this.ASPECT_RATIO * i), this.hero));
				}
				if (this.environment.get(i).charAt(j) == 'S') {
					this.plates.add(new Plate(new Point2D.Double(
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i)));
				}
				if (this.environment.get(i).charAt(j) == 'E') {
					Enemy enemy = new Enemy(this.hero, this.environment,
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i);
					Thread e1 = new Thread(enemy);
					e1.start();
					this.enemies.add(enemy);
					this.enemyThreads.add(e1);
				}
				if (this.environment.get(i).charAt(j) == 'U') {
					PowerUp p = new PowerUp("Life", this.environment,
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i);
					Thread p1 = new Thread(p);
					p1.start();
					this.powerups.add(p);
				}
				if (this.environment.get(i).charAt(j) == 'V') {
					PowerUp p = new PowerUp("Score", this.environment,
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i);
					Thread p1 = new Thread(p);
					p1.start();
					this.powerups.add(p);
				}
				if (this.environment.get(i).charAt(j) == 'N') {
					PowerUp p = new PowerUp("Pepper", this.environment,
							this.ASPECT_RATIO * j, this.ASPECT_RATIO * i);
					Thread p1 = new Thread(p);
					p1.start();
					this.powerups.add(p);
				}
				this.hero.setEnemies(this.enemies);
				this.hero.setEnemyThreads(this.enemyThreads);
			}
		}
	}

	/**
	 * Returns an array list of ladders.
	 *
	 * @return ladders
	 */
	public ArrayList<Ladder> getLadders() {
		return this.ladders;
	}

	/**
	 * Returns an array list of platforms. 
	 *
	 * @return platforms
	 */
	public ArrayList<Platform> getPlatforms() {
		return this.platforms;
	}

	/**
	 * Returns an array list of empty spaces.
	 *
	 * @return spaces
	 */
	public ArrayList<Space> getSpaces() {
		return this.spaces;
	}

	/**
	 * Returns an array list of power ups. 
	 *
	 * @return powerups
	 */
	public ArrayList<PowerUp> getPowerUps() {
		return this.powerups;
	}

	/**
	 * Returns the hero. 
	 *
	 * @return hero
	 */
	public Hero getHero() {
		return this.hero;
	}

	/**
	 * Return an array list of enemies.
	 *
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemy() {
		return this.enemies;
	}

	/**
	 * Returns an array list of burgers. 
	 *
	 * @return burgers
	 */
	public ArrayList<Burger> getBurgers() {
		return this.burgers;
	}

	/**
	 * Returns an array list of plates
	 *
	 * @return plates
	 */
	public ArrayList<Plate> getPlates() {
		return this.plates;
	}

	/**
	 * Returns true if the player has won the game, otherwise false. 
	 *
	 * @return boolean
	 */
	public boolean hasWon() {
		for (int i = 0; i < this.burgers.size(); i++) {
			if (this.burgers.get(i).getYPosition() < this.plates.get(0)
					.getPosition().getY()) {
				return false;
			}
		}
		return true;
	}

}
