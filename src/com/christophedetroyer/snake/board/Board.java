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

import com.christophedetroyer.gamestate.GameState;

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	// Constants
	private final int B_WIDTH     = 300; // Board width
    private final int B_HEIGHT    = 300; // Board heigth
    private final int DOT_SIZE    = 10;  // Size of a snake joint
    private final int RAND_POS    = 29;  // Position of apple
    private final int DELAY       = 140;
    private final int INIT_LENGTH = 5;
    
    // Images
    private Image ball;
    private Image apple;
    private Image head;
    
    // Private working variables
    private Timer timer;
    private GameState gameState;
    /**************************************************************************/
    /*** SETUP ****************************************************************/
    /**************************************************************************/
    public Board()
    {
    	this.gameState = new GameState(B_WIDTH, B_HEIGHT, INIT_LENGTH, DOT_SIZE);
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
    	gameState.setSnakeSize(INIT_LENGTH);
    	// Create new position for the apple.
    	gameState.moveApple();
    	
    	// Start the timer.
    	timer = new Timer(DELAY, this);
    	timer.start();
    	
    }
    /**
     * Load the images from class path and store them in variables.
     */
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
		if (gameState.isInGame()) 
		{
            gameState.UpdateApple();
            gameState.checkCollision();
            gameState.move();
        }
        repaint();
	}
    /**************************************************************************/
    /*** HELPER METHODS *******************************************************/
    /**************************************************************************/

    
    private void doDrawing(Graphics g)
    {
    	drawScore(g);
    	if(gameState.isInGame())
    	{
    		// Draw the apple
    		g.drawImage(apple, gameState.getAppleX(), gameState.getAppleY(), this);
    		
    		// Draw the snake
    		for (int z = 0; z < gameState.getSnakeSize(); z++) {
                if (z == 0) {
                    g.drawImage(head, gameState.getSnakeXs()[z], gameState.getSnakeYs()[z], this);
                } else {
                    g.drawImage(ball, gameState.getSnakeXs()[z], gameState.getSnakeYs()[z], this);
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
    
    private void drawScore(Graphics g)
    {
    	String scoreMsg = "Score: " + gameState.getSnakeSize();
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(scoreMsg,2,12);
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
            
            if ((key == KeyEvent.VK_LEFT) && (!gameState.isRightDirection())) {
            	System.out.println("LEFT");
                gameState.setLeftDirection(true);
                gameState.setUpDirection(false);
                gameState.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_RIGHT) && (!gameState.isLeftDirection())) {
            	System.out.println("RIGHT");
                gameState.setRightDirection(true);
                gameState.setUpDirection(false);
                gameState.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_UP) && (!gameState.isDownDirection())) {
            	System.out.println("UP");
                gameState.setUpDirection(true);
                gameState.setRightDirection(false);
                gameState.setLeftDirection(false);
            }

            if ((key == KeyEvent.VK_DOWN) && (!gameState.isUpDirection())) {
            	System.out.println("DOWN");
                gameState.setDownDirection(true);
                gameState.setRightDirection(false);
                gameState.setLeftDirection(false);
            }
        }
    }
}
