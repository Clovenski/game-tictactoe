/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 * Create a program that implements the Tic Tac Toe game.
 *
 * Joel Tengco
 */
package edu.cpp.cs.cs141.prog_assgmnt_1;

/**
 * This {@code Board} class represents a board that can be used in board games.
 * It is implemented by using the {@code Mark} class to <i>mark</i> places on
 * the board. Its shape can only be in the form of a square, thus its dimensions
 * will be of n x n for any positive integer n.
 * @author Joel Tengco
 *
 */
public class Board {
	/**
	 * A 2D array of reference variables of type {@code Mark} to implement this board.
	 */
	private Mark[][] board;
	/**
	 * The desired default size of this board when its dimensions are not specified.
	 */
	private final int DEFAULT_SIZE = 8;
	/**
	 * Variable to hold the size of this board, as in integer n in an n x n board. Always positive.
	 */
	private int boardSize;
	/**
	 * This is used in printing the board, separating the columns of the board. Mainly for graphical purposes.
	 */
	private final String COLUMN_DIVIDER = " | ";
	/**
	 * This is used in printing the board, separating the rows of the board. Mainly for graphical purposes.
	 */
	private String rowDivider;
	
	/**
	 * Constructs a new {@code Board} object of size 8 x 8. 
	 */
	public Board() {
		setUpBoard(DEFAULT_SIZE);
	}
	
	/**
	 * Constructs a new {@code Board} object whose size is of the specified parameter.
	 * When given a negative value argument, the board will default to a size of 8 x 8.
	 * @param size The size of the board, as in integer n in an n x n board
	 */
	public Board(int size) {
		if(size > 0)
			setUpBoard(size);
		else
			setUpBoard(DEFAULT_SIZE);
	}
	
	/**
	 * This method is used to set up the board with the specified size.
	 * This method is mainly only to help the constructors, as well as
	 * only be called by them.
	 * @param size The size of the board, as in integer n in an n x n board
	 */
	private void setUpBoard(int size) {
		board = new Mark[size][size];
		boardSize = size;
		setRowDivider();
	}
	
	/**
	 * This method is used to initialize the {@link #rowDivider} field to
	 * a proper value with respect to the size of this board.
	 */
	private void setRowDivider() {
		rowDivider = "";
		for(int i = 0; i < boardSize; i++)
			rowDivider = rowDivider + COLUMN_DIVIDER + "-";
		rowDivider = rowDivider + COLUMN_DIVIDER + "\n";
	}
	
	/**
	 * Sets a mark on the specified place on the board. The parameters i and j correspond
	 * to a place on the board in the form of a matrix. Thus, being on the element in
	 * row i and column j, both in which starts at 0, from the top left corner of the board.
	 * @param i The row of the desired place on this board
	 * @param j The column of the desired place on this board
	 * @param mark A reference variable to the {@code Mark} object to occupy the place on the board
	 */
	public void setMarkAt(int i, int j, Mark mark) {
		board[i][j] = mark;
	}
	
	/**
	 * Returns a reference variable to the {@code Mark} object that the specified place on the board
	 * is pointing to.
	 * @param i The row of the specified place on this board
	 * @param j The column of the specified place on this board
	 * @return A reference to the {@code Mark} object that the specified place on the board is pointing to.
	 */
	public Mark getMarkAt(int i, int j) {
		return board[i][j];
	}
	
	/**
	 * Gets the number of rows in this board.
	 * @return An integer of how many rows are in this board.
	 */
	public int getRowSize() {
		return board.length;
	}
	
	/**
	 * Gets the number of columns in this board.
	 * @return An integer of how many columns are in this board.
	 */
	public int getColumnSize() {
		return board[0].length;
	}
	
	/**
	 * Clears the board of any marks occupying any place on this board.
	 */
	public void clearBoard() {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = null;
			}
		}
	}
	
	/**
	 * Returns a graphical representation of the board. Empty spaces between the borders
	 * and dividers printed are the <i>places</i> on the board in which {@code Mark} objects
	 * can occupy.
	 */
	public String toString() {
		String finalString = rowDivider;										// Example: 3 x 3 board
		for(int i = 0; i < board.length; i++) {									// | - | - | - | 
			finalString = finalString + COLUMN_DIVIDER;							// |   |   |   | 
			for(int j = 0; j < board[i].length; j++) {							// | - | - | - | 
				if(board[i][j] == null)											// |   |   |   | 
					finalString = finalString + " ";							// | - | - | - | 
				else															// |   |   |   | 
					finalString = finalString + board[i][j].toString();			// | - | - | - | 
				
				finalString = finalString + COLUMN_DIVIDER;
			}
			
			finalString = finalString + "\n" + rowDivider;
		}
		return finalString;
	}
}
