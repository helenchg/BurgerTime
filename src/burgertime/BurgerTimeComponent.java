package burgertime;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * All the components are drawn in this class.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 4, 2014.
 */
public class BurgerTimeComponent extends JComponent {

	private ArrayList<Platform> platforms;
	private ArrayList<Ladder> ladders;
	private ArrayList<Space> spaces;
	private ArrayList<Burger> burgers;
	private BurgerTimeFrame frame;
	private Level level;
	private ArrayList<Enemy> enemies;
	private ArrayList<Plate> plates;
	private ArrayList<PowerUp> powerups;
	private ArrayList<PepperDot> pepperDots;
	private Hero hero;
	boolean paused;

	/**
	 * Constructor for the BurgerTimeComponent.
	 *
	 * @param frame
	 * @param level
	 */
	public BurgerTimeComponent(BurgerTimeFrame frame, Level level) {
		this.frame = frame;
		this.level = level;
		this.platforms = this.level.getPlatforms();
		this.ladders = this.level.getLadders();
		this.spaces = this.level.getSpaces();
		this.burgers = this.level.getBurgers();
		this.plates = this.level.getPlates();
		this.enemies = this.level.getEnemy();
		this.powerups = this.level.getPowerUps();
		this.hero = this.level.getHero();
		this.pepperDots = new ArrayList<PepperDot>();
		this.paused = false;

		JPanel keyPanel = new JPanel();
		InputMap input = keyPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = keyPanel.getActionMap();
		this.frame.add(keyPanel);

		// Key Actions
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "SpaceBar");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "PauseButton");
		

		action.put("RightArrow", new ArrowAction("RightArrow"));
		action.put("LeftArrow", new ArrowAction("LeftArrow"));
		action.put("UpArrow", new ArrowAction("UpArrow"));
		action.put("DownArrow", new ArrowAction("DownArrow"));
		action.put("SpaceBar", new ArrowAction("SpaceBar"));
		action.put("PauseButton", new ArrowAction("PauseButton"));

	}
	
	public void pause(){
		this.paused = true;
		for(int k=0; k<this.enemies.size(); k++){
			this.enemies.get(k).pause();
		}
		for(int k=0; k<this.powerups.size(); k++){
			this.powerups.get(k).pause();
		}
		this.hero.pause();
	}
	
	public void unpause(){
		this.paused = false;
		for(int k=0; k<this.enemies.size(); k++){
			this.enemies.get(k).unpause();
		}
		for(int k=0; k<this.powerups.size(); k++){
			this.powerups.get(k).unpause();
		}
		this.hero.unpause();
	}

	@Override
	public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			for (int h = 0; h < this.plates.size(); h++) {
				this.plates.get(h).drawOn(g2);
			}
			for (int i = 0; i < this.ladders.size(); i++) {
				this.ladders.get(i).drawOn(g2);
			}
			for (int j = 0; j < this.spaces.size(); j++) {
				this.spaces.get(j).drawOn(g2);
			}
			for (int k = 0; k < this.platforms.size(); k++) {
				this.platforms.get(k).drawOn(g2);
			}
			if(this.frame.getStats().getScore()>999){
				for (int k = 0; k < this.powerups.size(); k++) {
					if (this.powerups.get(k).doesExist() == true) {
						this.powerups.get(k).drawOn(g2);
					}
				}
			}
			
			for (int l = 0; l < this.burgers.size(); l++) {
				this.burgers.get(l).drawOn(g2);
				if(!this.paused){
					this.burgers.get(l).addGravity();
					if (this.burgers.get(l).isIndented()) {
						this.frame.getStats().addScore(50);
						this.burgers.get(l).drop();
						this.burgers.get(l).unIndent();
						System.out.println(this.frame.getStats().getScore());
					}
					dropBurger(l);
					stopBurger(l);
				}
			}
			for (int k = 0; k < this.pepperDots.size(); k++) {
				this.pepperDots.get(k).drawOn(g2);
			}
			this.level.getHero().drawOn(g2);
			// all functions related to the enemy including reSpawn

			for (int m = 0; m < this.enemies.size(); m++) {
				this.enemies.get(m).drawOn(g2);
				if (this.enemies.get(m).getKillConfirmed() == true) {
					for (int i = 0; i < this.enemies.size(); i++) {
						this.enemies.get(i).reSpawn();
					}
					this.frame.getStats().addLives(-1);
					this.enemies.get(m).resetKillConfirmed();
				}
				for(int j=0; j<this.pepperDots.size(); j++){
					if(this.pepperDots.get(j).getCenterX()>this.enemies.get(m).getUpperLeftX()
							&&this.pepperDots.get(j).getCenterX()<this.enemies.get(m).getUpperLeftX()+30
							&&this.pepperDots.get(j).getCenterY()>this.enemies.get(m).getUpperLeftY()
							&&this.pepperDots.get(j).getCenterY()<this.enemies.get(m).getUpperLeftY()+30){
						this.enemies.get(m).freeze();
					}
				}
			}
			
			if(!this.paused){

			int burgerWidth = 120;
			int burgerHeight = 30;
			for (int i = 0; i < this.burgers.size(); i++) {
				for (int j = 0; j < this.enemies.size(); j++) {
					if (this.burgers.get(i).isMoving()) {
						if (BurgerTimeComponent.this.enemies.get(j).getX() >= this.burgers
								.get(i).getXPosition()
								&& BurgerTimeComponent.this.enemies.get(j).getX() <= this.burgers
								.get(i).getXPosition() + burgerWidth
								&& BurgerTimeComponent.this.enemies.get(j).getY() >= this.burgers
								.get(i).getYPosition()
								&& BurgerTimeComponent.this.enemies.get(j).getY() <= this.burgers
								.get(i).getYPosition() + burgerHeight) {
							BurgerTimeComponent.this.enemies.get(j).reSpawn();
							this.frame.getStats().addScore(100);
						}
					}
				}

			}

			// Checks if player hits powerUp and adds stats if powerUp exists.
			double heroX = this.level.getHero().HeroGetX()
					- (this.level.getHero().getWidth() / 2);
			double heroY = this.level.getHero().HeroGetY()
					- (this.level.getHero().getWidth() / 2);

			if(this.frame.getStats().getScore()>999){
				for (int j = 0; j < this.powerups.size(); j++) {
					if (this.powerups.get(j).getPosition().getX() >= heroX
							&& this.powerups.get(j).getPosition().getX() <= heroX
							&& this.powerups.get(j).getPosition().getY() == heroY) {
						if (this.powerups.get(j).doesExist()) {
							if (this.powerups.get(j).getBonus().equals("Life")) {
								this.frame.getStats().addLives(1);
							}
							if (this.powerups.get(j).getBonus().equals("Score")) {
								this.frame.getStats().addScore(250);
							}
							if (this.powerups.get(j).getBonus().equals("Pepper")) {
								this.frame.getStats().addPepper(3);
							}
							this.powerups.get(j).remove();
						}
					}
	
				}
			}

			// Ends game if lost.
			if (this.frame.getStats().getLives() < 1) {
				this.frame.newMenu();
			}

			// Switches levels if won.
			if (this.level.hasWon()) {
				this.frame.changeLevel(1);
			}
	}
	}

	/**
	 * Handles hero actions with arrow keys.
	 *
	 * @author millerna.
	 *         Created Feb 19, 2014.
	 */
	public class ArrowAction extends AbstractAction {

		private String keyString;

		/**
		 * Constructor for ArrowAction.
		 *
		 * @param keyString
		 */
		public ArrowAction(String keyString) {
			this.keyString = keyString;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.keyString.equalsIgnoreCase("LeftArrow")) {
				BurgerTimeComponent.this.level.getHero().moveLeft();

			} else if (this.keyString.equalsIgnoreCase("RightArrow")) {
				BurgerTimeComponent.this.level.getHero().moveRight();

			}
			if (this.keyString.equalsIgnoreCase("UpArrow")) {
				BurgerTimeComponent.this.level.getHero().moveUp();

			} else if (this.keyString.equalsIgnoreCase("DownArrow")) {
				BurgerTimeComponent.this.level.getHero().moveDown();

			}
			if (this.keyString.equalsIgnoreCase("SpaceBar")) {

				if(BurgerTimeComponent.this.frame.getStats().getPeppers()>0&&!BurgerTimeComponent.this.paused){
					BurgerTimeComponent.this.level.getHero().sprayPepper();
					if(BurgerTimeComponent.this.hero.getLRDirection()=="Right"){
						for(int i=0; i<50; i++){
							double randomX = Math.random();
							double randomY = Math.random();
							double xCoord = ((((int)(BurgerTimeComponent.this.hero.HeroGetX()/30)+1)*30)+(90*randomX));
							double yCoord = (((int)(BurgerTimeComponent.this.hero.HeroGetY()/30)*30)+(30*randomY));
							PepperDot p = new PepperDot(xCoord, yCoord, BurgerTimeComponent.this.pepperDots);
							Thread p1 = new Thread(p);
							p1.start();
						}
						System.out.println(BurgerTimeComponent.this.pepperDots.size());
					}
					if(BurgerTimeComponent.this.hero.getLRDirection()=="Left"){
						for(int i=0; i<50; i++){
							double randomX = Math.random();
							double randomY = Math.random();
							double xCoord = ((((int)(BurgerTimeComponent.this.hero.HeroGetX()/30))*30)-(90*randomX));
							double yCoord = (((int)(BurgerTimeComponent.this.hero.HeroGetY()/30)*30)+(30*randomY));
							PepperDot p = new PepperDot(xCoord, yCoord, BurgerTimeComponent.this.pepperDots);
							Thread p1 = new Thread(p);
							p1.start();
						}
						System.out.println(BurgerTimeComponent.this.pepperDots.size());
					}
					BurgerTimeComponent.this.frame.getStats().addPepper(-1);
				}

			}
			if (this.keyString.equalsIgnoreCase("PauseButton")) {
				if(BurgerTimeComponent.this.paused==false){
					BurgerTimeComponent.this.pause();
				}
				else{
					BurgerTimeComponent.this.unpause();
				}

			}
			for (int i = 0; i < BurgerTimeComponent.this.level.getBurgers()
					.size(); i++) {
				BurgerTimeComponent.this.level.getBurgers().get(i).isOnBurger();
			}
		}
	}

	/**
	 * Drops the given burger.
	 *
	 * @param index
	 */
	public void dropBurger(int index) {

		for (int m = 0; m < this.burgers.size(); m++) {
			if (this.burgers.get(index).getYPosition()
					+ this.burgers.get(index).getWidth() == this.burgers.get(m)
					.getYPosition()
					&& (double) this.burgers.get(index).getXPosition() == (double) this.burgers
							.get(m).getXPosition()) {
				this.burgers.get(index).unIndent();
				this.burgers.get(m).drop();
			}
			if (this.burgers.get(index).getYPosition() >= this.plates.get(0)
					.getPosition().getY()) {
				this.burgers.get(index).stop();
			}
		}
	}

	/**
	 * Stops the given burger from moving. 
	 *
	 * @param index
	 */
	public void stopBurger(int index) {

		for (int n = 0; n < this.platforms.size(); n++) {
			if (this.burgers.get(index).getYPosition()
					+ this.burgers.get(index).getWidth() == this.platforms
					.get(n).getPosition().getY()
					&& this.burgers.get(index).getXPosition() == this.platforms
							.get(n).getPosition().getX()) {
				this.burgers.get(index).stop();
			}
		}
	}
}
