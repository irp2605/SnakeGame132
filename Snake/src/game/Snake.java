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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import game.Point;

class Snake extends Game {
	
	static int counter = 0;
	CreateElements elem = new CreateElements();
	
	class CreateElements {
		
		//snakehead
		Point[] snakeHeadPoints = { new Point(0, 25), new Point(12, 0), new Point(25, 25)};
		SnakeHead snakeHead = new SnakeHead(snakeHeadPoints, new Point(150,250), 90.0);
		
		// ArrayList of fruits
		
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
		
		// start fruit
		// Generate random x and y coordinates for fruit
		int x = 400;
				int y = 300;
				Point[] fruitPoints = { new Point(0, 0), new Point(10, 10), new Point(20, 0), new Point(20, 20), new Point(0, 20) };
				// using an anonymous class to make a special starting fruit
				
				Apple startFruit = new Apple(fruitPoints, new Point(x, y), 0) {
					public void paint(Graphics brush) {
						int nPoints = 5;
						int[] xPoints = new int[nPoints];
						int[] yPoints = new int[nPoints];
						for (int i = 0; i < nPoints; i++) {
							xPoints[i] = (int) fruitPoints[i].getX();
							yPoints[i] = (int) fruitPoints[i].getY();
						}
						brush.fillPolygon(xPoints, yPoints, nPoints);
					}
				};
		ArrayList<Fruit> fruits = new ArrayList<Fruit>(Arrays.asList(startFruit));

	}
	
	

	public Snake() {
		super("Snake!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		
		//keylistener
		this.addKeyListener(elem.snakeHead);
		
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
			if (elem.snakeHead.collides((Polygon) fruit)) {
				if(fruit.getClass() == Orange.class) {
					elem.snakeHead.levelUp(1);
				}
				else if(fruit.getClass() == Apple.class) {
					Polygon p =(Polygon)((Apple)fruit);
					if(p.getPoints().length == 5) {
						elem.snakeHead.levelUp(2);	
					}
					else {
						elem.snakeHead.levelUp(3);
					}
				}
				fruitIterator.remove();
				break;
			}
		}
	}
	
	public void checkWallCollisions() {
		// change tester to snakeHead
		if (elem.snakeHead.collides(elem.wallTop) || elem.snakeHead.collides(elem.wallBottom) 
				|| elem.snakeHead.collides(elem.wallLeft) || elem.snakeHead.collides(elem.wallRight)) {
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
		
		// Snake head movement
		elem.snakeHead.move();
		brush.setColor(Color.green);
		elem.snakeHead.Paint(brush);
		
		// Periodically draw fruits
		for (Fruit fruit : elem.fruits) {
			// Pick fruit color based on fruit type
			if (fruit instanceof Apple) {
				Polygon p =(Polygon)((Apple)fruit);
				if(p.getPoints().length == 5) {
					brush.setColor(Color.pink);
				}
				else {
					brush.setColor(Color.red);
				}
			} else if (fruit instanceof Orange){
				brush.setColor(Color.orange);
			}
			// Draw fruit
			System.out.println(((Polygon)fruit).position.x + ", " + ((Polygon)fruit).position.y);
			fruit.paint(brush);
		}
		
		// Collision logic - done
		
		// Uncomment these two lines when snake is implemented
		checkFruitCollisionsAndGrowSnake();
		checkWallCollisions();

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