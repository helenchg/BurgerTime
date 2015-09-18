package burgertime;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/**
 * This abstract class is used to help draw the grid elements such as ladders,
 * platforms, burgers components, and so on.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 7,
 *         2014.
 */
public abstract class Environment extends JComponent {

	protected double width;
	protected double height;
	protected Point2D setPosition;

	/**
	 * Constructor for Environment. 
	 *
	 * @param point
	 */
	public Environment(Point2D point) {
		this.width = 30;
		this.height = 30;
		this.setPosition = point;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawOn(g2);
	}

	/**
	 * Returns the position of the environment element. 
	 *
	 * @return
	 */
	public Point2D getPosition() {
		return this.setPosition;
	}

	/**
	 * Draws the environment on the frame. 
	 *
	 * @param g
	 */
	public abstract void drawOn(Graphics2D g);

}
