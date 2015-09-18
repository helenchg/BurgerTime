/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class creates the ladder of the game.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 5, 2014.
 */
public class Ladder extends Environment {
	
	private Color color;
	private BufferedImage img;
	
	/**
	 * Constructor for visible ladder.
	 *
	 * @param point
	 * @param file
	 */
	public Ladder(Point2D point, String file) {
		super(point);
		File imgFile = new File(file);
		try {
			this.img = ImageIO.read(imgFile);
		} catch (IOException e) {
			// Error reading file.
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for invisible ladder.
	 *
	 * @param point
	 */
	public Ladder(Point2D point) {
		super(point);

	}

	@Override
	public void drawOn(Graphics2D g) {
		if(this.color==Color.black){
			Rectangle2D.Double l = new Rectangle2D.Double(
					this.setPosition.getX(), this.setPosition.getY(), this.width,
					this.height);
			g.setColor(Color.BLACK);
			g.fill(l);
			g.draw(l);
		}
		else{
		g.drawImage(this.img, (int) this.setPosition.getX(), (int) this.setPosition.getY(), null);
		}
	}
}
