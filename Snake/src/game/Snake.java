package game;

/*
CLASS: Snake
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/

/**
 * This class is the control center for the game. The game objects and methods 
 * are created and called here.
 * 
 * @author Aditri Gadigi and Ishan Patel
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import game.Point;

class Snake extends Game {

	static int counter = 0;
	CreateElements elem = new CreateElements();
	CheckCollisions cc = new CheckCollisions();

	/**
	 * This inner class is responsible for creating the elements of the game and is
	 * essential to organizing the code logically. This inner class generates the
	 * snake head, wall, and fruit.
	 * 
	 * @author Aditri Gadigi and Ishan Patel
	 */
	class CreateElements {

		// Snake head
		Point[] snakeHeadPoints = { new Point(0, 25), new Point(12, 0), new Point(25, 25) };
		SnakeHead snakeHead = new SnakeHead(snakeHeadPoints, new Point(150, 250), 90.0);
		private Random random = new Random();

		// Vertical points
		Point[] wallVertPoints = { new Point(0, 0), new Point(width, 0), new Point(width, 50), new Point(0, 50) };

		// Top wall
		private Wall wallTop = new Wall(wallVertPoints, new Point(width / 4, 5), 0.0);
		// Bottom wall
		private Wall wallBottom = new Wall(wallVertPoints, new Point(width / 4, height - 65), 0.0);

		// Horizontal points
		Point[] wallHortPoints = { new Point(0, 0), new Point(50, 0), new Point(50, height), new Point(0, height) };

		// Left wall
		private Wall wallLeft = new Wall(wallHortPoints, new Point(5, height / 4), 0.0);
		// Right wall
		private Wall wallRight = new Wall(wallHortPoints, new Point(width - 45, height / 4), 0.0);

		// Start fruit
		int x = 100;
		int y = 100;
		Point[] fruitPoints = { new Point(14, 5), new Point(8, 11), new Point(5, 17), new Point(23, 17),
				new Point(20, 11) };
		// Using an anonymous class to make a special starting fruit
		Apple startFruit = new Apple(fruitPoints, new Point(x,y), 0.0) {
			public void paint(Graphics brush) {
				int nPoints = 5;
				int[] xPoints = new int[nPoints];
				int[] yPoints = new int[nPoints];
				for (int i = 0; i < nPoints; i++) {
					xPoints[i] = (int) fruitPoints[i].getX();
					xPoints[i] += this.position.x;
					yPoints[i] = (int) fruitPoints[i].getY();
					yPoints[i] += this.position.y;
				}
				brush.fillPolygon(xPoints, yPoints, nPoints);
			}
		};
		ArrayList<Fruit> fruits = new ArrayList<Fruit>(Arrays.asList(startFruit));

	}

	/**
	 * Sets up the game by defining Snake. It relies on the super class constructor.
	 * It also creates a timer that is later used to periodically display Fruits.
	 */
	public Snake() {
		super("Snake!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();

		// keyListener
		this.addKeyListener(elem.snakeHead);

		// Delay displaying of fruits by 3 seconds at the start
		int delay = 3000;
		// 10 second interval between generating fruits
		int period = 8000;

		// Lambda expression
		new Timer(period, e -> addRandomFruit()).start();

	}

	/**
	 * This method generates random x and y coordinates for fruit positions and
	 * creates fruit in those positions. This allows the game to periodically
	 * display new Fruit in random positions.
	 */
	public void addRandomFruit() {
		// Generate random x and y coordinates for fruit
		int x = elem.random.nextInt(width - 200) + 100;
		int y = elem.random.nextInt(height - 200) + 100;
		int n = elem.random.nextInt(0, 3);
		Point[] fruitPoints = { new Point(0, 0), new Point(20, 0), new Point(20, 20), new Point(0, 20) };
		// Create fruit
		if (n == 0) {
			Apple apple = new Apple(fruitPoints, new Point(x, y), 0.0);
			elem.fruits.add(apple);
		} else {
			Orange orange = new Orange(fruitPoints, new Point(x, y), 0.0);
			elem.fruits.add(orange);
		}
	}

	/**
	 * This inner class is responsible for checking collisions and is essential to
	 * organizing the code logically. This inner class checks for collisions between
	 * the walls, snake, and fruit.
	 * 
	 * @author Aditri Gadigi and Ishan Patel
	 */
	private class CheckCollisions {

		/**
		 * This method checks for collisions between the snake head and the fruit. The
		 * method calls the collides method from the Polygon class to see if there is a
		 * collision. When a collision is detected, the method removes the fruit from
		 * the iterator and then increases the speed of the snake based on the type of
		 * fruit.
		 */
		public void checkFruitCollisionsAndGrowSnake() {
			Iterator<Fruit> fruitIterator = elem.fruits.iterator();
			while (fruitIterator.hasNext()) {
				Fruit fruit = fruitIterator.next();
				// is it weird to cast this
				if (elem.snakeHead.collides((Polygon) fruit)) {
					if (fruit.getClass() == Orange.class) {
						elem.snakeHead.levelUp(1);
					} else if (fruit.getClass() == Apple.class) {
						Polygon p = (Polygon) ((Apple) fruit);
						if (p.getPoints().length == 5) {
							elem.snakeHead.levelUp(2);
						} else {
							elem.snakeHead.levelUp(3);
						}
					}
					fruitIterator.remove();
					break;
				}
			}
		}

		/**
		 * This method checks for collisions between the snake head and the wall. The
		 * method calls the collides method from the Polygon class to see if there is a
		 * collision. When a collision is detected, the method displays a message that
		 * the game is over and then quits the game.
		 */
		public void checkWallCollisions() {
			// change tester to snakeHead
			if (elem.snakeHead.collides(elem.wallTop) || elem.snakeHead.collides(elem.wallBottom)
					|| elem.snakeHead.collides(elem.wallLeft) || elem.snakeHead.collides(elem.wallRight)) {
				// End game if snake collides
				JOptionPane.showMessageDialog(null, "Game Over!");
				System.exit(0);
			}
		}
	}

	/**
	 * This method acts as a control center for the game. Here, the objects are
	 * drawn and the collision and move methods are called.
	 * 
	 * @param brush
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// Draw border
		brush.setColor(Color.gray);
		elem.wallTop.paint(brush);
		elem.wallBottom.paint(brush);
		elem.wallLeft.paint(brush);
		elem.wallRight.paint(brush);

		// Snake head movement
		elem.snakeHead.move();
		brush.setColor(Color.green);
		elem.snakeHead.Paint(brush);

		// Periodically draw fruits
		for (Fruit fruit : elem.fruits) {

			// Pick fruit color based on fruit type
			if (fruit instanceof Apple) {
				Polygon p = (Polygon) ((Apple) fruit);
				if (p.getPoints().length == 5) {
					brush.setColor(Color.pink);
				} else {
					brush.setColor(Color.red);
				}
			} else if (fruit instanceof Orange) {
				brush.setColor(Color.orange);
			}
			// Draw fruit
			System.out.println(((Polygon) fruit).position.x + ", " + ((Polygon) fruit).position.y);
			fruit.paint(brush);
		}

		cc.checkFruitCollisionsAndGrowSnake();
		cc.checkWallCollisions();

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);

	}

	/**
	 * This method uses the Snake constructor to call the game.
	 */
	public static void main(String[] args) {
		Snake a = new Snake();
		a.repaint();
	}
}