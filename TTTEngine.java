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
 * This class represents an engine to implement the game: Tic Tac Toe.
 * It utilizes the {@code Mark} object to represent the two players in
 * the game. This class only handles the logic behind how a game of
 * Tic Tac Toe works. This class does <i>not</i> handle the interactivity
 * with the user.
 * <p>
 * The minimum size of a Tic Tac Toe game with this engine is 3 x 3,
 * while the maximum size is 10 x 10.
 * @author Joel Tengco
 *
 */
public class TTTEngine {
	/**
	 * A {@code Mark} object that belongs to player 1.
	 */
	private Mark player1;
	/**
	 * A {@code Mark} object that belongs to player 2.
	 */
	private Mark player2;
	/**
	 * Represents the board for the Tic Tac Toe game, in which {@code Mark} objects are placed on.
	 */
	private Board gameBoard;
	/**
	 * Used to essentially determine if the board is full. Hence the maximum amount of turns allowed.
	 */
	private int maxTurns;
	/**
	 * Counter for the amount of marks currently on the board.
	 */
	private int turnsMade;
	/**
	 * Determines if a win is currently possible.
	 * A win is possible if the {@link #turnsMade} counter is greater than or equal to 2x - 1 where x is the number of rows/columns in the board.
	 */
	private boolean winPossible;
	/**
	 * Boolean to hold whether or not a winner is found during the game. This boolean also holds a true value when the board becomes completely full.
	 */
	private boolean winnerFound;
	/**
	 * A reference to the {@code Mark} object that won the game.
	 */
	private Mark winner;
	
	/**
	 * Creates a standard 3 x 3 Tic Tac Toe game.
	 */
	public TTTEngine() {
		setUpEngine(3);
	}
	
	/**
	 * Creates an n x n Tic Tac Toe game where n is specified with the {@code boardSize} parameter.
	 * If {@code boardSize} is not within the range [3 - 10], then a standard 3 x 3 Tic Tac Toe game
	 * is made instead.
	 * @param boardSize The desired size of a row/column of the Tic Tac Toe game.
	 */
	public TTTEngine(int boardSize) {
		if(boardSize < 3 || boardSize > 10)
			setUpEngine(3);
		else
			setUpEngine(boardSize);
	}
	
	/**
	 * This method is used to help the constructors set up the game.
	 * @param boardSize The desired size of a row/column of the Tic Tac Toe game.
	 */
	private void setUpEngine(int boardSize) {
		gameBoard = new Board(boardSize);
		maxTurns = gameBoard.getRowSize() * gameBoard.getColumnSize();
		turnsMade = 0;
	}
	
	/**
	 * Use this method to set up the two players for the Tic Tac Toe game.
	 * The two players will be represented in the form of a {@link Mark} object.
	 * <p>
	 * This method can also be invoked in the middle of a current Tic Tac Toe
	 * game that has marks on the board already. It will properly change the
	 * marks on the board to represent the new players instead of the old. It
	 * does <i>not</i> fully reset the game. Use {@link #resetEngine()} for that
	 * matter.
	 * @param p1Name A string representing the name of player 1.
	 * @param p1Mark A character to represent player 1's marks on the board.
	 * @param p2Name A string representing the name of player 2.
	 * @param p2Mark A character to represent player 2's marks on the board.
	 */
	public void setUpPlayers(String p1Name, char p1Mark, String p2Name, char p2Mark) {
		if(player1 == null) {
			player1 = new Mark(p1Name, p1Mark);
		} else {
			Mark newPlayer1 = new Mark(p1Name, p1Mark);
			updateBoard(player1, newPlayer1);
			player1 = newPlayer1;
		}
		
		if(player2 == null) {
			player2 = new Mark(p2Name, p2Mark);
		} else {
			Mark newPlayer2 = new Mark(p2Name, p2Mark);
			updateBoard(player2, newPlayer2);
		}
	}
	
	/**
	 * This method is used only by {@linkplain #setUpPlayers(String, char, String, char)}
	 * to help properly change the old players' marks on the board to marks of the new players'.
	 * @param oldPlayer A reference to the {@code Mark} object belonging to the old player.
	 * @param newPlayer A reference to the {@code Mark} object belonging to the new player.
	 */
	private void updateBoard(Mark oldPlayer, Mark newPlayer) {
		for(int i = 0; i < gameBoard.getRowSize(); i++) {
			for(int j = 0; j < gameBoard.getColumnSize(); j++) {
				if(gameBoard.getMarkAt(i, j) == oldPlayer)
					gameBoard.setMarkAt(i, j, newPlayer);
			}
		}
	}
	
	/**
	 * Gets the string representing player 1's name.
	 * @return A string containing player 1's name.
	 */
	public String getP1Name() {
		return player1.getPlayerName();
	}
	
	/**
	 * Gets the string representing player 2's name.
	 * @return A string containing player 2's name.
	 */
	public String getP2Name() {
		return player2.getPlayerName();
	}
	
	/**
	 * Gets the size of the Tic Tac Toe board.
	 * Specifically the "size" of the board is the number of rows
	 * or the number of columns in the board. These two numbers
	 * are the same since Tic Tac Toe boards are generally square.
	 * @return An integer representing the number of rows in the board.
	 */
	public int getBoardSize() {
		return gameBoard.getColumnSize();
	}
	
	/**
	 * This class is used by the {@link TTTEngine} class to represent
	 * the exception that a user is attempting to mark a place on the
	 * board that is already occupied by another mark.
	 * @author Joel Tengco
	 *
	 */
	public static class PlaceOnBoardTakenException extends IllegalArgumentException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PlaceOnBoardTakenException() {
			super("That place on the board is taken already!");
		}
		
		public String toString() {
			return getMessage();
		}
	}
	
	/**
	 * Gets the graphical representation of the Tic Tac Toe board.
	 * Invoke this method when printing the current state of the
	 * game to show which places on the board are marked already, to whom
	 * they belong to, and which ones are empty.
	 * @return A string containing the graphical state of the game board, including borders and currently marked places.
	 */
	public String getBoardState() {
		return gameBoard.toString();
	}
	
	/**
	 * Advances a turn in the Tic Tac Toe game. A successful turn
	 * in the game would be a newly set mark on the board that does not
	 * attempt to replace an already occupied place on the board.
	 * @param i The row in which the desired place on the board the user wants to mark is in.
	 * @param j The column in which the desired place on the board the user wants to mark is in.
	 * @param p1Turn Boolean to indicate whether or not it is player 1's turn. True if and only if it is player 1's turn.
	 * @throws PlaceOnBoardTakenException If the place on the board specified by i and j is currently occupied by another {@code Mark} object
	 */
	public void advanceTurn(int i, int j, boolean p1Turn) throws PlaceOnBoardTakenException {
		if(gameBoard.getMarkAt(i, j) != null) {
			throw new PlaceOnBoardTakenException();
		}
		
		gameBoard.setMarkAt(i, j, p1Turn ? player1 : player2);
		turnsMade++;
		winPossible = turnsMade >= 2 * gameBoard.getRowSize() - 1;
		if(winPossible)
			checkBoardAt(i, j);
	}
	
	/**
	 * This method is mainly used to check whether a winning move has been done.
	 * It is given a specific place on the board given by the parameters {@code row} and {@code col}
	 * and with that specific place in mind, checks if it was a winning move for its respective row,
	 * column, and if is on a diagonal of the board, its diagonal(s) as well.
	 * @param row The row in which the newly marked place on the board is occupying.
	 * @param col The column in which the newly marked place on the board is occupying.
	 */
	private void checkBoardAt(int row, int col) {
		checkRowAt(row, col);
		if(winnerFound)
			return;
		
		checkColumnAt(row, col);
		if(winnerFound)
			return;
		
		if(row == col || row + col == gameBoard.getColumnSize() - 1)
			checkDiagonalAt(row, col);
		
		if(turnsMade == maxTurns)
			winnerFound = true;
	}
	
	/**
	 * This method checks whether or not the row, in which the specified place on the board is occupying,
	 * satisfies the conditions of winning the Tic Tac Toe game, where the row is <i>full</i> and only
	 * consists of <i>one</i> kind of {@code Mark}.
	 * @param row The row in which the newly marked place on the board is occupying.
	 * @param col The column in which the newly marked place on the board is occupying.
	 */
	private void checkRowAt(int row, int col) {
		Mark targetMark = gameBoard.getMarkAt(row, col);
		int colLimit = gameBoard.getColumnSize();
		
		for(int j = 0; j < colLimit; j++) {
			if(gameBoard.getMarkAt(row, j) != null && gameBoard.getMarkAt(row, j) == targetMark) {
				if(j + 1 == colLimit) {
					winnerFound = true;
					winner = targetMark;
				}	// else keep checking that row
			} else {
				break;	// row is incomplete or does not contain a winner, stop checking
			}
		}
	}
	
	/**
	 * This method checks whether or not the column, in which the specified place on the board is occupying,
	 * satisfies the conditions of winning the Tic Tac Toe game, where the column is <i>full</i> and only
	 * consists of <i>one</i> kind of {@code Mark}.
	 * @param row The row in which the newly marked place on the board is occupying.
	 * @param col The column in which the newly marked place on the board is occupying.
	 */
	private void checkColumnAt(int row, int col) {
		Mark targetMark = gameBoard.getMarkAt(row, col);
		int rowLimit = gameBoard.getRowSize();
		
		for(int i = 0; i < rowLimit; i++) {
			if(gameBoard.getMarkAt(i, col) != null && gameBoard.getMarkAt(i, col) == targetMark) {
				if(i + 1 == rowLimit) {
					winnerFound = true;
					winner = targetMark;
				}	// else keep checking that column
			} else {
				break;	// column is incomplete or does not contain a winner, stop checking
			}
		}
	}
	
	/**
	 * This method checks whether or not the diagonal, in which the specified place on the board is occupying,
	 * satisfies the conditions of winning the Tic Tac Toe game, where the diagonal is <i>full</i> and only
	 * consists of <i>one</i> kind of {@code Mark}.
	 * <p>
	 * This method is called with <i>only</i> valid row and column values because of the condition set by method
	 * {@linkplain #checkBoardAt(int, int)}, thus only diagonals on the board that can provide determine a winner
	 * are checked.
	 * @param row The row in which the newly marked place on the board is occupying.
	 * @param col The column in which the newly marked place on the board is occupying.
	 */
	private void checkDiagonalAt(int row, int col) {
		Mark targetMark = gameBoard.getMarkAt(row, col);
		int rowLimit = gameBoard.getRowSize();
		int colLimit = gameBoard.getColumnSize();
		
		if(row == col) {	// if mark is within the downwards diagonal
			for(int i = 0; i < colLimit; i++) {
				if(gameBoard.getMarkAt(i, i) != null && gameBoard.getMarkAt(i, i) == targetMark) {
					if(i + 1 == colLimit) {
						winnerFound = true;
						winner = targetMark;
						return;
					}	// else keep checking that diagonal
				} else {
					break;	// diagonal is incomplete or does not contain a winner, stop checking
				}
			}
		}
		
		if(row + col == colLimit - 1) {    // if mark is within the upwards diagonal
			for(int i = rowLimit - 1; i >= 0; i--) {
				if(gameBoard.getMarkAt(i, colLimit - i - 1) != null && gameBoard.getMarkAt(i, colLimit - i - 1) == targetMark) {
					if(i - 1 < 0) {
						winnerFound = true;
						winner = targetMark;
					}	// else keep checking that diagonal
				} else {
					break;	// diagonal is incomplete or does not contain a winner, stop checking
				}
			}
		}
	}
	
	/**
	 * Determines if the Tic Tac Toe game is done or not. Returns a true value when a winner has been found
	 * or when the board has become completely filled.
	 * @return A true value if a winner has been found or the board is full, false otherwise.
	 */
	public boolean isDone() {
		return winnerFound;
	}
	
	/**
	 * Gets the winning player's name, if any.
	 * @return Either the string representing the winning player's name <i>or</i> "No one" if no winning condition has been met.
	 */
	public String getWinnerName() {
		if(winner == null) {
			return "No one";
		} else {
			return winner.getPlayerName();
		}
	}
	
	/**
	 * Resets the current state of the game board, effectively clearing out
	 * any marks that are currently occupying any space on the board.
	 * <p>
	 * This does <i>not</i> fully reset the game. The methods {@linkplain #isDone()} and {@linkplain #getWinnerName()}
	 * are unaffected by the actions of this method. To fully reset the game, use {@link #resetEngine()}.
	 */
	public void resetBoard() {
		gameBoard.clearBoard();
		turnsMade = 0;
	}
	
	/**
	 * Resets the game engine, effectively <i>fully</i> reseting the game. It clears the game board of any marks
	 * and clears any winning conditions that have been met, if any.
	 * <p>
	 * The purpose of this method is to start a new Tic Tac Toe game, keeping the player names and their respective mark values,
	 * and essentially destroying the current one.
	 */
	public void resetEngine() {
		gameBoard.clearBoard();
		turnsMade = 0;
		winnerFound = false;
		winner = null;
	}
	
	/**
	 * This method returns a current graphical representation of the game board. Use of {@linkplain #getBoardState()} instead is recommended for clarity.
	 * Otherwise, this method is equivalent to invoking {@linkplain #getBoardState()}.
	 */
	public String toString() {
		return getBoardState();
	}
}
