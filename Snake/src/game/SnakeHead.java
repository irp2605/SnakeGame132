package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

<<<<<<< HEAD
public class SnakeHead extends Polygon implements KeyListener{
	private double ammountToMove = 0.5;
	private double ammountToRotate = 1;
=======
/**
 * This class extends Polygon and implements KeyListener. It is responsible for
 * representing the SnakeHead object. It contains methods to draw and move the
 * SnakeHead object in response to the keyboard.
 * 
 * @author Aditri Gadigi and Ishan Patel
 */

public class SnakeHead extends Polygon implements KeyListener {
	private int ammountToMove = 1;
	private int ammountToRotate = 2;
>>>>>>> branch 'master' of https://github.com/irp2605/SnakeGame132.git
	private boolean forward, left, right;

	/**
	 * Defines a SnakeHead object. It relies on the super class constructor to
	 * define the shape.
	 * 
	 * @param inShape
	 * @param inPosition
	 * @param inRotation
	 */
	public SnakeHead(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		forward = left = right = false;
	}

	/**
	 * Draw the SnakeHead object. The method loops through the list of points and
	 * separates them into a list of integer x coordinates and integer y
	 * coordinates. These points are used to draw the polygon. The brush color is
	 * selected before the polygon is drawn.
	 * 
	 * @param brush
	 */
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
		if (forward) {
			// reliant on trig to move in the direction the direction its rotated to
			double newX = (super.position.x + ammountToMove * Math.sin(Math.toRadians(super.rotation)));
			double newY = (super.position.y + ammountToMove * -Math.cos(Math.toRadians(super.rotation)));
			super.position = new Point(newX, newY);
		}
		if (left) {
			super.rotation -= ammountToRotate;
		}
		if (right) {
			super.rotation += ammountToRotate;
		}
	}

	public void levelUp(int fruitMult) {
		ammountToMove += fruitMult;
		ammountToRotate *= (1.5 * fruitMult);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// move forward
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		// rotate left true
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		// rotate right true
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// no key released for forward so user cant stop when started

		// rotate left off
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		// rotate right off
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}

	}

}
