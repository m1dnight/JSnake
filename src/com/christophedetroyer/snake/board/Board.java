package com.christophedetroyer.snake.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	// Constants
	private final int B_WIDTH     = 300; // Board width
    private final int B_HEIGHT    = 300; // Board heigth
    private final int DOT_SIZE    = 10;  // Size of a snake joint
    private final int RAND_POS    = 29;  // Position of apple
    private final int DELAY       = 140;
    private final int INIT_LENGTH = 3;
    
    // Game state
    private final int x[] = new int[B_WIDTH * B_HEIGHT];
    private final int y[] = new int[B_WIDTH * B_HEIGHT];
    
    private int apple_x;
    private int apple_y;
    // Private working variables
    private Timer timer;
    /**************************************************************************/
    /*** SETUP ****************************************************************/
    /**************************************************************************/
    /**
     * Initialize the game.
     */
    public void initGame()
    {
    	// Clear all the x and y coords for the snake and set
    	// the dots at the initial position.
    	for(int i = 0; i < INIT_LENGTH; i++)
    	{
    		x[i] = 50 - i * 10;
            y[i] = 50;
    	}
    	
    	// Start the timer.
    	timer = new Timer(DELAY, this);
    	
    }
    /**************************************************************************/
    /*** EVENT HANDLERS *******************************************************/
    /**************************************************************************/
    /**
     * This method defines what happens every tick of the timer 'timer'.
     */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}
    /**************************************************************************/
    /*** HELPER METHODS *******************************************************/
    /**************************************************************************/
    /**
     * Calculate a new random position for the apple
     */
    private void moveApple()
    {
    	int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
    
    


}
