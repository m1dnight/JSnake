package com.christophedetroyer.snake;

import javax.swing.JFrame;

import com.christophedetroyer.snake.board.Board;

public class Snake extends JFrame {
	/**
	 * Simple constructor that creates a new JFrame and adds a 
	 * new board.
	 */
	public Snake()
	{
		add(new Board());
		setTitle("Snake");
		pack();
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
