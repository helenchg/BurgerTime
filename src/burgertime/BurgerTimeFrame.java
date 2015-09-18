package burgertime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * This class contains the frame and all the elements and actionlisteners are
 * contained in here.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 19, 2014.
 */
public class BurgerTimeFrame extends JFrame {
	private static final int FRAMES_PER_SECOND = 30;
	private static final long REPAINT_INTERVAL_MS = 1000 / FRAMES_PER_SECOND;
	private int currentLvl;
	private BurgerTimeComponent worldComponent;
	private MenuComponent menuComponent;
	private Stats statsPanel;
	private String name;

	/**
	 * Constructor for BurgerTimeFrame.
	 *
	 */
	public BurgerTimeFrame() {
		this.currentLvl = 1;
		this.menuComponent = new MenuComponent(this);
		this.add(this.menuComponent, BorderLayout.SOUTH);
		this.statsPanel = new Stats();
		revalidate();

		this.getContentPane().setBackground(Color.black);
		this.setSize(700,700);
		this.setTitle("BurgerTime");

		// Sets up new key listener to change levels.
		JPanel changeLevelKey = new JPanel();
		InputMap input2 = changeLevelKey
				.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action2 = changeLevelKey.getActionMap();
		this.add(changeLevelKey, BorderLayout.SOUTH);

		input2.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "U");
		action2.put("U", new NewLevel("U"));

		input2.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
		action2.put("D", new NewLevel("D"));

		Runnable repainter = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVAL_MS);
						repaint();
					}
				} catch (InterruptedException exception) {
					//
				}
			}
		};
		Thread repaintThread = new Thread(repainter);
		repaintThread.start();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Switches the current level to a new level.
	 *
	 * @author millerna.
	 *         Created Feb 19, 2014.
	 */
	public class NewLevel extends AbstractAction {

		String keyString;

		/**
		 * Constructor for newLevel.
		 *
		 * @param keyString
		 */
		public NewLevel(String keyString) {
			this.keyString = keyString;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (this.keyString.equalsIgnoreCase("U")) {
				changeLevel(1);

			} else if (this.keyString.equalsIgnoreCase("D")) {
				changeLevel(-1);
			}
		}
	}

	/**
	 * Changes the current level.
	 *
	 * @param amount
	 */
	public void changeLevel(int amount) {

		if (this.currentLvl == 0) {
			newWorld();
			return;
		}

		this.currentLvl += amount;

		if (this.currentLvl <= 0) {
			this.currentLvl = 3;
		}

		String level = "level" + this.currentLvl + ".txt";

		if (this.currentLvl > 3) {
			this.currentLvl = 0;
			newMenu();
			return;
		}

		this.remove(this.worldComponent);
		this.worldComponent = new BurgerTimeComponent(this, new Level(level));
		this.add(this.worldComponent);
		this.revalidate();
	}

	/**
	 * Removes the menu and creates a new world. 
	 *
	 */
	public void newWorld() {
		this.statsPanel = new Stats();
		this.currentLvl = 1;
		String level = "level" + this.currentLvl + ".txt";

		this.worldComponent = new BurgerTimeComponent(this, new Level(level));
		this.add(this.worldComponent);
		this.remove(this.menuComponent);
		this.remove(this.menuComponent.getMenuBox());
		addStats();
		this.setSize(1430, 1000);
		this.revalidate();

	}

	/**
	 * Removes the world and creates a new menu.
	 *
	 */
	public void newMenu() {
		this.menuComponent = new MenuComponent(this, this.statsPanel);
		removeStats();
		this.remove(this.worldComponent);

		int levelNum = 0;
		this.revalidate();
	}

	/**
	 * Returns the game stats.
	 *
	 * @return
	 */
	public Stats getStats() {
		return this.statsPanel;
	}

	/**
	 * Adds the stats to the game.
	 *
	 */
	public void addStats() {
		this.add(this.statsPanel, BorderLayout.NORTH);
	}

	/**
	 * Removes the stats from the game. 
	 *
	 */
	public void removeStats() {
		this.remove(this.statsPanel);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
