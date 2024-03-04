package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class extends Polygon and implements the Fruit interface. This class
 * represents the Apple object which appears in the game.
 * 
 * @author Aditri Gadigi and Ishan Patel
 */

public class Apple extends Polygon implements Fruit{

	private static Color color = Color.red;
	
	/**
	 * Defines an Apple. It relies on the super class constructor to
	 * define the shape. 
	 * 
	 * @param inShape
	 * @param inPosition
	 * @param inRotation
	 */
	public Apple(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Draw the Apple object. The method loops through the list of points and 
	 * separates them into a list of integer x coordinates and integer 
	 * y coordinates. These points are used to draw the polygon. The brush color
	 * is selected to be red before the polygon is drawn.
	 * @param brush
	 */
	public void paint(Graphics brush) {
		int nPoints = this.getPoints().length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		Point[] points = this.getPoints();
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int) points[i].getX();
			yPoints[i] = (int) points[i].getY();
		}
		brush.setColor(color);
		brush.fillPolygon(xPoints, yPoints, nPoints);
	}

}
