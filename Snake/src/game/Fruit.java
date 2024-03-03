package game;

import java.awt.Graphics;

/**
 * Defines the functionality of Fruits (Apple and Orange).
 * 
 * @author Aditri Gadigi and Ishan Patel
 *
 */
public interface Fruit {
	/**
	 * Draw the Fruit object. The method loops through the list of points and
	 * separates them into a list of integer x coordinates and integer y
	 * coordinates. These points are used to draw the polygon. The brush color is
	 * selected before the polygon is drawn. The brush color is different for each
	 * fruit.
	 * 
	 * @param brush
	 */
	public void paint(Graphics brush);
}
