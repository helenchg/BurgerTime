
package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Constructs a plate for the burgers to fall onto. 
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 5, 2014.
 */
public class Plate extends Environment {

	/**
	 * Constructor for plate.
	 *
	 * @param point
	 */
	public Plate(Point2D point) {
		super(point);
	}

	@Override
	public void drawOn(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(
				this.setPosition.getX(), this.setPosition.getY(), this.width,
				this.height);
		g.setColor(Color.gray);
		g.fill(rect);
		g.draw(rect);

	}
	
}