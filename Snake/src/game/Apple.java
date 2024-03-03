package game;

import java.awt.Graphics;

public class Apple extends Polygon implements Fruit{

	public Apple(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	public void paint(Graphics brush) {
		int nPoints = this.getPoints().length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		Point[] points = this.getPoints();
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int) points[i].getX();
			yPoints[i] = (int) points[i].getY();
		}
		brush.fillPolygon(xPoints, yPoints, nPoints);
	}

}
