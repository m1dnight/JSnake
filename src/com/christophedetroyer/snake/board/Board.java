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
	private static final long serialVersionUID = 1L;
	// Constants
	private final int B_WIDTH     = 300; // Board width
    private final int B_HEIGHT    = 300; // Board heigth
    private final int DOT_SIZE    = 10;  // Size of a snake joint
    private final int RAND_POS    = 29;  // Position of apple
    private final int DELAY       = 140;
    private final int INIT_LENGTH = 3;
    
    // Images
    private Image ball;
    private Image apple;
    private Image head;
    
    // Game state
    private final int x[] = new int[B_WIDTH * B_HEIGHT];
    private final int y[] = new int[B_WIDTH * B_HEIGHT];
    private int snake_size;
    
    // Movement 
    private boolean leftDirection  = false;
    private boolean rightDirection = true;
    private boolean upDirection    = false;
    private boolean downDirection  = false;
    
    private boolean inGame         = true;
    
    private int apple_x;
    private int apple_y;
    
    // Private working variables
    private Timer timer;
    /**************************************************************************/
    /*** SETUP ****************************************************************/
    /**************************************************************************/
    public Board()
    {
    	this.addKeyListener(new TAdapter());
    	this.setFocusable(true); // Required for the keylistener to work.
    	
    	this.setBackground(Color.DARK_GRAY);
    	this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    	loadImages();
    	initGame();
    }
    
    /**
     * Initialize the game.
     */
    public void initGame()
    {
    	// Clear all the x and y coords for the snake and set the dots at the 
    	// initial position.
    	for(int i = 0; i < INIT_LENGTH; i++)
    	{
    		x[i] = 50 - i * 10;
            y[i] = 50;
    	}
    	snake_size = INIT_LENGTH;
    	
    	// Create new position for the apple.
    	moveApple();
    	
    	// Start the timer.
    	timer = new Timer(DELAY, this);
    	timer.start();
    	
    }
    
    public void loadImages()
    {
    	System.out.println(getClass().getResource("/apple.png"));
    	ImageIcon a = new ImageIcon(getClass().getResource("/apple.png"));
    	apple = a.getImage();
    	ImageIcon b = new ImageIcon(getClass().getResource("/head.png"));
    	head = b.getImage();
    	ImageIcon c = new ImageIcon(getClass().getResource("/body.png"));
    	ball = c.getImage();
    }
    /**************************************************************************/
    /*** OVERRIDDEN METHODS ***************************************************/
    /**************************************************************************/
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    /**
     * This method defines what happens every tick of the timer 'timer'.
     */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
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
    
    private void doDrawing(Graphics g)
    {
    	if(inGame)
    	{
    		// Draw the apple
    		g.drawImage(apple, apple_x, apple_y, this);
    		
    		// Draw the snake
    		for (int z = 0; z < snake_size; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
    		Toolkit.getDefaultToolkit().sync();
            g.dispose();
    	}
    	else
    	{
    		gameOver(g);
    	}
    }
    
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
    
    /**
     * Checks if our snake head is positioned on top of our apple. If this is 
     * the case we increase our snake length by one and relocate the apple.
     */
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            snake_size++;
            moveApple();
        }
    }
    
    /**
     * Move our snake in the direction we were told.
     * Every position is shifted to the right in our location vector.
     * The head position will be increased with a "unit".
     */
    private void move() {

        for (int z = snake_size; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection)
            x[0] -= DOT_SIZE;
        
        if (rightDirection)
            x[0] += DOT_SIZE;
        
        if (upDirection)
            y[0] -= DOT_SIZE;
        
        if (downDirection)
            y[0] += DOT_SIZE;
        
    }
    private void checkCollision() {
    	
    	// If the head x,y matches any x,y of the body we collided.
    	
        for (int z = snake_size; z > 0; z--)
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z]))
                inGame = false;
       
        // Make sure all our parts are in the game field.
        if (y[0] > B_HEIGHT)
            inGame = false;
        
        if (y[0] < 0)  
            inGame = false;
        
        if (x[0] > B_WIDTH)
            inGame = false;
        
        if (x[0] < 0)
            inGame = false;
    }
    /**************************************************************************/
    /*** KEYADAPTER TO HANDLE KEYEVENTS FROM USER *****************************/
    /**************************************************************************/
    /**
     * The KeyAdapter makes sure that impossible scenarios are ignored.
     * E.g.: snake is going up and user presses down is an invalid move.
     * @author ChristopheRosaFreddy
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
