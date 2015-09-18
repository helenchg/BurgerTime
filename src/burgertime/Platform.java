/**
 * 
 */
package burgertime;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Constructs a horizontal platform for the components to stand on.
 * 
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller. Created Feb 5, 2014.
 */
public class Platform extends Environment {
	
	private BufferedImage img;

	/**
	 * Constructor for Platform.
	 *
	 * @param point
	 */
	public Platform(Point2D point) {
		super(point);
		File imgFile = new File("platform.png");
		try {
			this.img = ImageIO.read(imgFile);
		} catch (IOException e) {
			// Error reading file.
			e.printStackTrace();
		}
	}

	@Override
	public void drawOn(Graphics2D g) {
		
		g.drawImage(this.img, (int) this.setPosition.getX(), (int) this.setPosition.getY(), null);

	}
	
}
