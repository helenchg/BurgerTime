package burgertime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class PepperDot extends JComponent implements Runnable{
	
	double xCoord;
	double yCoord;
	double radius;
	double centerX;
	double centerY;
	Color color;
	boolean visible;
	boolean paused;
	int threadCounter;
	ArrayList<PepperDot> list;
	
	public PepperDot(double x, double y, ArrayList<PepperDot> list){
		this.xCoord = x;
		this.yCoord = y;
		this.radius = 0.5;
		this.centerX = this.xCoord + this.radius;
		this.centerY = this.yCoord + this.radius;
		this.color = Color.white;
		this.visible = true;
		this.paused = false;
		this.threadCounter = 0;
		this.list = list;
		this.list.add(this);
	}
	
	public void drawOn(Graphics2D g) {
		if(this.visible){
			Ellipse2D.Double dot = new Ellipse2D.Double(this.xCoord, this.yCoord, this.radius*2, this.radius*2);
			g.setColor(this.color);
			g.fill(dot);
			g.draw(dot);
		}

	}
	
	public void suicide(){
		try{
		this.list.remove(this.list.indexOf(this));
		} catch (ArrayIndexOutOfBoundsException exception){
			
		}
	}
	
	public int getThreadCounter(){
		return this.threadCounter;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if(this.paused==false){
					if(threadCounter>500){
						suicide();
					}
					this.threadCounter+= 20;
					double random = Math.random();
					if (random>0.5) {
						invisible();
					}
					else{
						visible();
					}
				}
				Thread.sleep(20);
			}
		} catch (InterruptedException exception) {
			//
		}

	}
	
	public double getCenterX(){
		return this.centerX;
	}
	
	public double getCenterY(){
		return this.centerY;
	}
	
	public void pause(){
		this.paused = true;
	}
	
	public void unpause(){
		this.paused = false;
	}
	
	public void visible(){
		this.visible = true;
	}
	
	public void invisible(){
		this.visible = false;
	}
}