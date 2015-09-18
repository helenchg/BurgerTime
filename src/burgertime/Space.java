package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Blank blocks to be placed in between movable components.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 5, 2014.
 */
public class Space extends Environment {

	/**
	 * Constructs Space class. 
	 *
	 * @param point
	 */
	public Space(Point2D point) {
		super(point);
	}

	@Override
	public void drawOn(Graphics2D g) {
		Rectangle2D.Double emptySpace = new Rectangle2D.Double(
				this.setPosition.getX(), this.setPosition.getY(), this.width,
				this.height);
		g.setColor(Color.BLACK);
		g.fill(emptySpace);
		g.draw(emptySpace);

	}
}
