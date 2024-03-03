package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

class Snake extends Game {
	
	static int counter = 0;
	CreateElements elem = new CreateElements();
	
	class CreateElements {
		// Add snake creation here (for now its just another fruit so other methods work)
		Point[] fruitPoints = { new Point(0, 0), new Point(400, 0), new Point(400, 400), new Point(0, 400)};
		Orange tester = new Orange(fruitPoints, new Point(100,0), 0.0);
		
		// ArrayList of fruits
		private ArrayList<Fruit> fruits = new ArrayList<>();
		private Random random = new Random();
		
		// Vertical points
		Point[] wallVertPoints = {new Point(0,0), new Point(width, 0), new Point(width, 50), new Point(0, 50)};
		
		// Top wall 
		private Wall wallTop = new Wall(wallVertPoints, new Point(width/4,5), 0.0);
		// Bottom wall
		private Wall wallBottom = new Wall(wallVertPoints, new Point(width/4, height - 65), 0.0);
		
		// Horizontal points
		Point[] wallHortPoints = {new Point(0,0), new Point(50, 0), new Point(50, height), new Point(0, height)};
		
		// Left wall
		private Wall wallLeft = new Wall(wallHortPoints, new Point(5, height/4), 0.0);
		// Right wall
		private Wall wallRight = new Wall(wallHortPoints, new Point(width - 45, height/4), 0.0);
		
		// Apple apple = new Apple(fruitPoints, new Point(200,200), 0.0);

	}
	
	

	public Snake() {
		super("Snake!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();

		// Delay displaying of fruits by 3 seconds at the start
		int delay = 3000;
		// 10 second interval between generating fruits
		int period = 8000;
		new Timer(period, e -> addRandomFruit()).start();
	}

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

	public void checkFruitCollisionsAndGrowSnake() {
		Iterator<Fruit> fruitIterator = elem.fruits.iterator();
		while (fruitIterator.hasNext()) {
			Fruit fruit = fruitIterator.next();
			// is it weird to cast this
			if (elem.tester.collides((Polygon) fruit)) {  // change tester to snakeHead
				fruitIterator.remove();
				//tester.grow(); // have to create grow method for snake
				break;
			}
		}
	}
	
	public void checkWallCollisions() {
		// change tester to snakeHead
		if (elem.tester.collides(elem.wallTop) || elem.tester.collides(elem.wallBottom) || elem.tester.collides(elem.wallLeft) || elem.tester.collides(elem.wallRight)) {
			// End game if snake collides
			JOptionPane.showMessageDialog(null, "Game Over!");
			System.exit(0);
		}
	}
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// call move method here if needed

		// Draw border
		brush.setColor(Color.gray);
		elem.wallTop.paint(brush);
		elem.wallBottom.paint(brush);
		elem.wallLeft.paint(brush);
		elem.wallRight.paint(brush);
		
		// The below two lines make a random tester object to test collision methods in place of snake
		//brush.setColor(Color.orange);
		//elem.tester.paint(brush);
		
		// Periodically draw fruits
		for (Fruit fruit : elem.fruits) {
			// Pick fruit color based on fruit type
			if (fruit instanceof Apple) {
				brush.setColor(Color.red);
			} else {
				brush.setColor(Color.orange);
			}
			// Draw fruit
			fruit.paint(brush);
		}
		
		// Collision logic - done
		
		// Uncomment these two lines when snake is implemented
		//checkFruitCollisionsAndGrowSnake();
		//checkWallCollisions();

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);

	}

	public static void main(String[] args) {
		Snake a = new Snake();
		a.repaint();
	}
}