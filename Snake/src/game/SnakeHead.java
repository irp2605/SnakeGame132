package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeHead extends Polygon implements KeyListener{
	private int ammountToMove = 1;
	private int ammountToRotate = 2;
	private boolean forward, left, right;
	
	public SnakeHead(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		forward = left = right = false;
	}
	
	
	public void Paint(Graphics brush) {
		// create arrays of correct size for x & y cords of points
		int nPoints = this.getPoints().length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		Point[] points = this.getPoints();
		// fill coordinate arrays by iterating through point array
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int) points[i].getX();
			yPoints[i] = (int) points[i].getY();
		}
		// set color
		brush.setColor(Color.GREEN);
		brush.fillPolygon(xPoints, yPoints, nPoints);
		
	}
	
	public void move() {
		if(forward) {
			//reliant on trig to move in the direction the direction its rotated to
			double newX = (super.position.x + ammountToMove * Math.sin(Math.toRadians(super.rotation)));
			double newY = (super.position.y + ammountToMove * -Math.cos(Math.toRadians(super.rotation)));
			super.position = new Point(newX, newY);
		}
		if(left) {
			super.rotation -= ammountToRotate;
		}
		if(right) {
			super.rotation += ammountToRotate;
		}
	}
	
	public void levelUp(int fruitMult) {
		ammountToMove += fruitMult;
		ammountToRotate *= (1.5*fruitMult);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// move forward
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		// rotate left true
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		// rotate right true
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// no key released for forward so user cant stop when started
		
		// rotate left off
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = false;
				}
		// rotate right off
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		
	}

}
