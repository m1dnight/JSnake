package com.christophedetroyer.snake;

import javax.swing.JFrame;

public class Snake extends JFrame {
	/**
	 * Simple constructor that creates a new JFrame and adds a 
	 * new board.
	 */
	public Snake()
	{
		//add(new Board());
		setTitle("Snake");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
