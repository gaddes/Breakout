/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout_MG_6 extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	/** Total number of bricks */
	private static int NBRICKS = NBRICKS_PER_ROW * NBRICK_ROWS;
	
	
	/* The following variables all relate to the ball - including its dimensions, 
	 * placement and velocity. */

	/** Animation delay or pause time between ball moves */
	private static final int DELAY = 5;
	
	/** Initial X and Y location of ball */
	private static final double BALL_START_X = (APPLICATION_WIDTH - (BALL_RADIUS * 2));
	private static final double BALL_START_Y = (APPLICATION_HEIGHT / 2);

	// TO BE DELETED !!!
//	private static final double BALL_START_X = 100;
//	private static final double BALL_START_Y = 100;
	
	
	/** X and Y Velocity */
	private static final double X_VEL = 1;
	private static final double Y_VEL = 1;
	
	/** Starting X and Y Velocities.
	 *  There is a -ve applied to "x" since we want the ball to travel down and left. */
	private double xVel = -X_VEL;
	private double yVel = Y_VEL;
	
	/* private instance variable */
	private GOval ball = new GOval(BALL_START_X, BALL_START_Y, (BALL_RADIUS * 2), (BALL_RADIUS * 2));
	
	// Initialise paddle
	private GRect paddle = new GRect((((APPLICATION_WIDTH + BRICK_SEP) / 2) - (PADDLE_WIDTH / 2)), (APPLICATION_HEIGHT - PADDLE_Y_OFFSET), PADDLE_WIDTH, PADDLE_HEIGHT);
	
	/** Variable to hold the brick to be deleted */
	private GObject brickDELETE; 
	
	
	/* Method: init() */
	/** Sets up the application - layout etc. */
	public void init() {
		
		// Resize canvas regardless of original size
		if(true) {
			// Increase size of canvas so that pyramid will fit
			setSize(APPLICATION_WIDTH + BRICK_SEP, APPLICATION_HEIGHT);
		}
		

		// Counter to hold remaining number of rows to be laid.
		int rowsRemaining = 0;
		
		// Initialise row counter (row No. 1)
		int rowCount = 0;
		
		// Start point (x-axis) for each individual brick
		int brickX = BRICK_SEP;
		
		/* Start point (y-axis) for each individual brick
		   The offset will increase by the size of a brick each time we move down a row */
		int brickY = BRICK_Y_OFFSET;
		
		
		// Runs until all rows have been laid
		while (rowCount < NBRICK_ROWS) {
			
			// Counter to hold number of bricks remaining in each row.
			int bricksInRow = 0;
			
			// Runs until 10No bricks have been laid
			while (bricksInRow < NBRICKS_PER_ROW) {
				
				// Create new brick
				GRect currentBrick = new GRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
				
				// Sets colour of brick, depending on row number
				if (rowCount == 0 || rowCount == 1) {
					currentBrick.setColor(Color.RED);
					currentBrick.setFillColor(Color.RED);
					currentBrick.setFilled(true);
				} else if (rowCount == 2 || rowCount == 3) {
					currentBrick.setColor(Color.ORANGE);
					currentBrick.setFillColor(Color.ORANGE);
					currentBrick.setFilled(true);
				} else if (rowCount == 4 || rowCount == 5) {
					currentBrick.setColor(Color.YELLOW);
					currentBrick.setFillColor(Color.YELLOW);
					currentBrick.setFilled(true);
				} else if (rowCount == 6 || rowCount == 7) {
					currentBrick.setColor(Color.GREEN);
					currentBrick.setFillColor(Color.GREEN);
					currentBrick.setFilled(true);
				} else if (rowCount == 8 || rowCount == 9) {
					currentBrick.setColor(Color.CYAN);
					currentBrick.setFillColor(Color.CYAN);
					currentBrick.setFilled(true);
				}
				
				// Add to canvas
				add(currentBrick);
				
				// Shift x-location of next brick to be laid
				brickX = brickX + (BRICK_WIDTH + BRICK_SEP);
						
				// Append one to number of bricks in row
				bricksInRow ++;		
			}
			
			// Reset value of brickX
			brickX = BRICK_SEP;
			
			// Shift y-location of next brick to be laid
			brickY = brickY + (BRICK_HEIGHT + BRICK_SEP); 
			
			// Append one to rowCount
			rowCount ++;
		}

		// Change colour and fill of paddle
		paddle.setColor(Color.BLACK);
		paddle.setFillColor(Color.BLACK);
		paddle.setFilled(true);
		
		// Add paddle to canvas
		add(paddle);
	}
	
	
	/** Create and place ball */
	private void setupBall() {
//		ball = new GOval(BALL_START_X, BALL_START_Y, (BALL_RADIUS * 2), (BALL_RADIUS * 2));
		ball.setFilled(true);
		add(ball);
	}
	
	
	/** When the mouse moves this method calls on the "event" */
	public void mouseMoved(MouseEvent e) {
		
		// Create variable "x" to hold the x-coordinate of the mouse position
		double x = e.getX();
		
		// Update location of paddle to current mouse position
		// Mouse position will dictate the CENTRE of paddle
		paddle.setLocation((x - (PADDLE_WIDTH / 2)), (APPLICATION_HEIGHT - PADDLE_Y_OFFSET));	
	}
	
	
	/** Determine if ball collides with either wall, update velocities
	 * and location as appropriate */
	private void checkForPaddleCollision() {
		
		// Determine if paddle has hit the right-hand wall
		// If it has, ensure the paddle goes no further
		if (paddle.getX() > ((APPLICATION_WIDTH + BRICK_SEP) - PADDLE_WIDTH)) {
			
			paddle.setLocation((APPLICATION_WIDTH - PADDLE_WIDTH), (APPLICATION_HEIGHT - PADDLE_Y_OFFSET));
		}
		
		// Determine if paddle has hit the left-hand wall
		// If it has, ensure the paddle goes no further
		if (paddle.getX() < 0) {
			
			paddle.setLocation(0, (APPLICATION_HEIGHT - PADDLE_Y_OFFSET));
		}
	}
	
	
	/** Update and move ball */
	private void moveBall() {
		// Increase yVelocity due to gravity on each cycle
		ball.move(xVel, yVel);
	}
	
	
	/** Determine if collision with roof, update velocities
	 * and location as appropriate */
	private void checkForBallCollision() {
		
		// Determine if ball has hit the ROOF
		if (ball.getY() < 0) {
			
			/* Change ball's Y velocity to bounce down, away from roof
			   (but continuing in the same x-direction) */
			xVel = xVel;
			yVel = -yVel;
			
			ball.move(xVel,  yVel);
		}
		
		// Determine if ball has hit the LEFT wall
		if (ball.getX() < 0) {
			
			/* Change ball's X velocity to bounce right, away from wall
			   (but continuing in the same y-direction */
			xVel = -xVel;
			yVel = yVel;
			
			ball.move(xVel,  yVel);
		}
		
		// Determine if ball has hit the RIGHT wall
		if (ball.getX() > ((APPLICATION_WIDTH + BRICK_SEP) - (BALL_RADIUS * 2))) {
			
			/* Change ball's X velocity to bounce left, away from wall
			   (but continuing in the same y-direction */
			xVel = -xVel;
			yVel = yVel;
			
			ball.move(xVel,  yVel);
		}
		
		// Determine if ball has hit the PADDLE
		if (ball.getY() > (APPLICATION_HEIGHT - PADDLE_Y_OFFSET - (BALL_RADIUS * 2))) {
			
			// If the ball's origin is to the right of the paddle's origin
			if (ball.getX() > paddle.getX()) {
				
				// If the ball's origin is not further than the width of the paddle
				if (ball.getX() < (paddle.getX() + PADDLE_WIDTH)) {
					
					/* Change ball's Y velocity to bounce up, away from paddle
					   (but continuing in the same x-direction) */
					xVel = xVel;
					yVel = -yVel;
					
					ball.move(xVel,  yVel);
				}
			}
		}
	}
		
	/** Determine if collision with either wall, update velocities
	 * and location as appropriate 
	 * @return Object (brick) with which ball collides*/	
	private void getCollidingBrick() {
		
		// Each of the following variables creates a GPoint to hold the location of each corner of the ball
		/** GPoint to hold the upper left "corner" of the ball */
//		GPoint upperLeft = new GPoint(ball.getX(), ball.getY());
		/** GPoint to hold the upper right "corner" of the ball */
//		GPoint upperRight = new GPoint(ball.getX() + (2 * BALL_RADIUS), ball.getY());
		/** GPoint to hold the lower left "corner" of the ball */
//		GPoint lowerLeft = new GPoint(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
		/** GPoint to hold the lower right "corner" of the ball */
//		GPoint lowerRight = new GPoint(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
		
		// Checks upperLeft corner of ball. If NOT null, then a collision has occurred.
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			
			// Variable to hold the brick at this location
//			GObject brick = getElementAt(ball.getX(), ball.getY());
			
			// Pass "brick" on to "brickDELETE". This will allow the brick to be deleted in the following method.
			brickDELETE = getElementAt(ball.getX(), ball.getY());
			

		// Checks upperRight corner of ball. If NOT null, then a collision has occurred.
		} else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY()) != null) {
		
			// Variable to hold the brick at this location
//			GObject brick = getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());
			
			// Pass "brick" on to "brickDELETE". This will allow the brick to be deleted in the following method.
			brickDELETE = getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());

		// Checks lowerLeft corner of ball. If NOT null, then a collision has occurred.
		} else if (getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS)) != null) {
			
			// Variable to hold the brick at this location
//			GObject brick = getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
			
			// Pass "brick" on to "brickDELETE". This will allow the brick to be deleted in the following method.
			brickDELETE = getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
			
		// Checks upperLeft corner of ball. If NOT null, then a collision has occurred.
		} else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS)) != null) {
			
			// Variable to hold the brick at this location
//			GObject brick = getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
			
			// Pass "brick" on to "brickDELETE". This will allow the brick to be deleted in the following method.
			brickDELETE = getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
		}
		
		// Returns an object that can be deleted in the next step
//		return brickDELETE;
		
		// Check that object is NOT null
		if (brickDELETE != null) {
		
			// Check that the object being collided with is NOT the paddle
			if (brickDELETE != paddle) {

				/* Change ball's Y velocity to bounce away from brick
				   (but continuing in the same x-direction) */
//				yVel = -yVel;
				
				// Is this necessary?
//				ball.move(xVel,  yVel);
				
				
				// Delete colliding object
				remove(brickDELETE);
				
				// Reduce number of bricks remaining by one
				NBRICKS --;
			}
		}
	}

	
	private void removeBrick() {
	

		
		// Continue to run loop until there are no more bricks remaining

//		if (NBRICKS > 0) {
//		if(true) {
		
			// Variable GObject to hold object with which ball collides
//			GObject collidingObject = getCollidingBrick();
			
			// !!! "brickDELETE" has replaced "collidingObject" in the following loops
			
			// Check that object is NOT null
			if (brickDELETE != null) {
			
				// Check that the object being collided with is NOT the paddle
				if (brickDELETE != paddle) {

					/* Change ball's Y velocity to bounce away from brick
					   (but continuing in the same x-direction) */
//					yVel = -yVel;
					
					// Is this necessary?
//					ball.move(xVel,  yVel);
					
					
					// Delete colliding object
					remove(brickDELETE);
					
					// Reduce number of bricks remaining by one
					NBRICKS --;
				}
			}
//		}
	}
	

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		init();
		setupBall();
		addMouseListeners();
		
//		while (NBRICKS > 0) {
		while(true) {
			moveBall();
			checkForBallCollision();
			getCollidingBrick();
//			removeBrick();
			pause(DELAY);
		}
		
		// Add ENDGAME code here "congratulations!" etc..
		
		
	}
}