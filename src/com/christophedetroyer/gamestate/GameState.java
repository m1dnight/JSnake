package com.christophedetroyer.gamestate;

import java.awt.Image;

import javax.swing.Timer;

public class GameState {
    // Snake body
    private final int x[];
    private final int y[];
    // Current snake size
    private int snake_size;
    // Movement 
    private boolean leftDirection  = false;
	private boolean rightDirection = true;
    private boolean upDirection    = false;
    private boolean downDirection  = false;
    private boolean inGame         = true;
    
    private int apple_x;
    private int apple_y;
    
    // Private variables
    private final int DOT_SIZE;
    private final int game_height;
    private final int game_width;
    
    public GameState(int height, int width, int initialSnakeSize, int gameDotSize)
    {
    	this.DOT_SIZE = gameDotSize;
    	this.game_height = height;
    	this.game_width = width;
    	// Set snake array
    	x = new int[width * height];
    	y = new int[width * height];
    	snake_size = 3;
    	
    	// Clear all the x and y coords for the snake and set the dots at the 
    	// initial position.
    	for(int i = 0; i < initialSnakeSize; i++)
    	{
    		x[i] = (initialSnakeSize * 10) - i * 10;
    		x[i] = (initialSnakeSize * 10);
    	}
    }
    
    public void UpdateApple()
    {
    	if(checkApple()) moveApple();
    }
    
    public void checkCollision() {
    	
    	// If the head x,y matches any x,y of the body we collided.
    	
        for (int z = this.getSnakeSize(); z > 0; z--)
            if ((z > 4) && (this.getSnakeXs()[0] == this.getSnakeXs()[z]) 
            && (this.getSnakeYs()[0] == this.getSnakeYs()[z]))
            	this.setInGame(false);
       
        // Make sure all our parts are in the game field.
        if (this.getSnakeYs()[0] > game_height)
        	this.setInGame(false);
        
        if (this.getSnakeYs()[0] < 0)  
        	this.setInGame(false);
        
        if (this.getSnakeXs()[0] > game_width)
        	this.setInGame(false);
        
        if (this.getSnakeXs()[0] < 0)
        	this.setInGame(false);
    }
    
    /**
     * Move our snake in the direction we were told.
     * Every position is shifted to the right in our location vector.
     * The head position will be increased with a "unit".
     */
    public void move() {

        for (int z = this.getSnakeSize(); z > 0; z--) {
        	this.getSnakeXs()[z] = this.getSnakeXs()[(z - 1)];
        	this.getSnakeYs()[z] = this.getSnakeYs()[(z - 1)];
        }

        if (this.isLeftDirection())
        	this.getSnakeXs()[0] -= DOT_SIZE;
        
        if (this.isRightDirection())
        	this.getSnakeXs()[0] += DOT_SIZE;
        
        if (this.isUpDirection())
        	this.getSnakeYs()[0] -= DOT_SIZE;
        
        if (this.isDownDirection())
        	this.getSnakeYs()[0] += DOT_SIZE;
        
    }
    /**************************************************************************/
    /**** HELPER FUNCTIONS ****************************************************/
    /**************************************************************************/
    /**
     * Checks if our snake head is positioned on top of our apple. If this is 
     * the case we increase our snake length by one and relocate the apple.
     */
    public Boolean checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            snake_size++;
            return true;
        }
        return false;
    }
    
    /**
     * Calculate a new random position for the apple
     */
    public void moveApple()
    {
    	int r = (int) (Math.random() * 29);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * 29);
        apple_y = (((r * DOT_SIZE)));
    }
    
    
    /**************************************************************************/
    /**** GETTERS AND SETTERS *************************************************/
    /**************************************************************************/
    public int[] getSnakeXs()
    {
    	return x;
    }
    public int[] getSnakeYs()
    {
    	return y;
    }
    public int getSnakeSize()
    {
    	return snake_size;
    }
    public void setSnakeSize(int newSize)
    {
    	this.snake_size = newSize;
    }
    public boolean isInGame()
    {
    	return inGame;
    }
    public void setInGame(Boolean b)
    {
    	this.inGame = b;
    }
    public int getAppleX()
    {
    	return apple_x;
    }
    public int getAppleY()
    {
    	return apple_y;
    }
    public void setAppleX(int x)
    {
    	this.apple_x = x;
    }
    public void setAppleY(int y)
    {
    	this.apple_y = y;
    }
    public boolean isLeftDirection() {
		return leftDirection;
	}
	public void setLeftDirection(boolean leftDirection) {
		this.leftDirection = leftDirection;
	}
	public boolean isRightDirection() {
		return rightDirection;
	}
	public void setRightDirection(boolean rightDirection) {
		this.rightDirection = rightDirection;
	}
	public boolean isUpDirection() {
		return upDirection;
	}
	public void setUpDirection(boolean upDirection) {
		this.upDirection = upDirection;
	}
	public boolean isDownDirection() {
		return downDirection;
	}
	public void setDownDirection(boolean downDirection) {
		this.downDirection = downDirection;
	}
    
}
