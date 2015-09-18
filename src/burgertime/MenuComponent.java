package burgertime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Start and end menu for the game.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 15, 2014.
 */
public class MenuComponent extends JComponent {

	private BurgerTimeFrame frame;
	private Box box;
	private JPanel titlePanel;

	/**
	 * Constructor for the start menu.
	 * 
	 * @param frame
	 */
	public MenuComponent(BurgerTimeFrame frame) {
		this.frame = frame;
		this.box = new Box(BoxLayout.Y_AXIS);
		this.titlePanel = new JPanel();

		Dimension expectedDimension = new Dimension(160, 115);

		JPanel menuPanel = new JPanel();

		ImageIcon titleImage = new ImageIcon("BurgerTime2.png");
		ImageIcon imageLabel = new ImageIcon("burgertimelabel.png");
		JLabel imgLabel = new JLabel(titleImage);
		this.titlePanel.setBackground(Color.black);
		this.titlePanel.setBorder(new EmptyBorder(50, 0, 100, 0));
		this.titlePanel.add(imgLabel);
		this.box.add(this.titlePanel);


		menuPanel.setPreferredSize(expectedDimension);
		menuPanel.setMaximumSize(expectedDimension);
		menuPanel.setMinimumSize(expectedDimension);
		menuPanel.setBackground(Color.red);

		this.box.add(Box.createVerticalGlue());
		this.box.add(menuPanel);
		this.box.add(Box.createVerticalGlue());

		JButton newGame = new JButton("New Game");
		newGame.setPreferredSize(new Dimension(150, 50));
		menuPanel.add(newGame);
		NewGameAction createNewGame = new NewGameAction(this.frame, this.box);
		newGame.addActionListener(createNewGame);

		JButton quitGame = new JButton("Quit");
		quitGame.setPreferredSize(new Dimension(150, 50));
		menuPanel.add(quitGame);
		QuitAction createQuitGame = new QuitAction(this.frame);
		quitGame.addActionListener(createQuitGame);

		this.frame.add(this.box, BorderLayout.NORTH);
	}

	private Stats stats;
	private ArrayList<String> highScore;

	/**
	 * Constructor for the end game menu.
	 * 
	 * @param frame
	 * @param stats
	 */
	public MenuComponent(BurgerTimeFrame frame, Stats stats) {
		this.stats = stats;
		this.frame = frame;
		this.box = new Box(BoxLayout.Y_AXIS);
		this.titlePanel = new JPanel();
		this.highScore = new ArrayList<String>();
		new ArrayList<String>();

		// Reads the highScore file.
		File file = new File("stats.txt");
		try {
			Scanner inScanner = new Scanner(file);

			while (inScanner.hasNext()) {
				this.highScore.add(inScanner.next());
			}
			inScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Add new high score to the highScore file.
		boolean lessThan = true;
		int count = 0;
		while (lessThan) {

			if (count >= this.highScore.size()) {
				lessThan = false;
				this.highScore.add(count, this.stats.getScore() + "");
			} else if (this.stats.getScore() >= Integer.parseInt(this.highScore
					.get(count))) {
				this.highScore.add(count, this.stats.getScore() + "");
				lessThan = false;
			}
			count++;
		}

		// Writes to the highScore file with new highScore.
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream("stats.txt"));
			for (String str : this.highScore) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < this.highScore.size(); i++) {
			System.out.println(this.highScore.get(i));
		}

		Dimension expectedDimension = new Dimension(160, 115);

		JPanel menuPanel = new JPanel();
		
		ImageIcon titleImage = new ImageIcon("gameOver.gif");
		
		JLabel imgLabel = new JLabel(titleImage);

		this.titlePanel.setBackground(Color.black);
		this.titlePanel.setBorder(new EmptyBorder(50, 0, 70, 0));
		this.titlePanel.add(imgLabel);
		this.box.add(this.titlePanel);

		menuPanel.setPreferredSize(expectedDimension);
		menuPanel.setMaximumSize(expectedDimension);
		menuPanel.setMinimumSize(expectedDimension);
		menuPanel.setBackground(Color.red);

		this.box.add(Box.createVerticalGlue());
		this.box.add(menuPanel);
		this.box.add(Box.createVerticalGlue());

		JButton newGame = new JButton("New Game");
		newGame.setPreferredSize(new Dimension(150, 50));
		menuPanel.add(newGame);
		NewGameAction createNewGame = new NewGameAction(this.frame, this.box);
		newGame.addActionListener(createNewGame);

		JButton quitGame = new JButton("Quit");
		quitGame.setPreferredSize(new Dimension(150, 50));
		menuPanel.add(quitGame);
		QuitAction createQuitGame = new QuitAction(this.frame);
		quitGame.addActionListener(createQuitGame);

		JPanel scorePanel = new JPanel(new GridLayout(0, 1));
		scorePanel.setBorder(new EmptyBorder(50, 0, 0, 0));
		scorePanel.setBackground(Color.black);
		JLabel highScoreLabel = new JLabel("High Scores:");
		highScoreLabel.setBorder(new EmptyBorder(0, 620, 0, 0));
		highScoreLabel.setForeground(Color.red);
		Font statsFont = new Font("Stats Font", Font.PLAIN, 30);
		highScoreLabel.setFont(statsFont);
		scorePanel.add(highScoreLabel);

		for (int i = 0; i < 5; i++) {
			JLabel stat = new JLabel(i + 1 + ". " + this.highScore.get(i));
			stat.setFont(statsFont);
			stat.setBorder(new EmptyBorder(0, 655, 0, 0));
			scorePanel.add(stat);
		}
		this.box.add(scorePanel);

		this.frame.add(this.box, BorderLayout.NORTH);
	}

	/**
	 * Quits and exits the game.
	 *
	 * @author millerna.
	 *         Created Feb 19, 2014.
	 */
	public class QuitAction implements ActionListener {

		private JFrame frame;

		/**
		 * Initializes the frame to a parameter.
		 * 
		 * @param frame
		 * @return
		 */
		public QuitAction(JFrame frame) {
			this.frame = frame;
		}

		/*
		 * Destroys the current frame.
		 * 
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			this.frame.dispose();
			System.exit(0);

		}
	}

	/**
	 * Create a new game. 
	 *
	 * @author millerna.
	 *         Created Feb 19, 2014.
	 */
	public class NewGameAction implements ActionListener {

		private BurgerTimeFrame frame;
		private Box box;

		/**
		 * Initializes the frame to a parameter.
		 * 
		 * @param frame
		 * @return
		 */
		public NewGameAction(BurgerTimeFrame frame, Box box) {
			this.frame = frame;
			this.box = box;
		}

		/*
		 * Destroys the current frame.
		 * 
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			this.frame.newWorld();
		}
	}

	/**
	 * Returns the box that the menu items are contained in.
	 *
	 * @return box.
	 */
	public Box getMenuBox() {
		return this.box;
	}
}
