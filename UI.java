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

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;
import edu.cpp.cs.cs141.prog_assgmnt_1.TTTEngine.PlaceOnBoardTakenException;

/**
 * This class serves as the interface between the user and the Tic Tac Toe game engine.
 * Its main purpose is to prompt the user for input and use that input to
 * advance the game.
 * <p>
 * Creating an object of type UI by itself does not start the game. To start the game
 * please invoke the method {@link #start()}.
 * @author Joel Tengco
 *
 */
public class UI {
	/**
	 * This was used to capture the input from the user.
	 */
	private Scanner keyboard;
	/**
	 * Reference variable pointing to the Tic Tac Toe game engine.
	 */
	private TTTEngine game;
	
	/**
	 * Creates an object of type {@code UI}. Note: Use {@linkplain #start()} to start the game.
	 */
	public UI() {}
	
	/**
	 * Fully starts the Tic Tac Toe game. This method will not return until the user opts to stop playing.
	 */
	public void start() {
		keyboard = new Scanner(System.in);
		start(true);
	}
	
	/**
	 * This method provided the entry point to <i>both</i> starting the Tic Tac Toe game the first time and starting it again
	 * after a current Tic Tac Toe game ended. See {@linkplain #endGame()} for the end of the loop.
	 * @param isFirstTime True if and only if this is the first time starting a Tic Tac Toe game, false if there was a game that ended before.
	 */
	private void start(boolean isFirstTime) {
		if(isFirstTime) {
			System.out.println("Would you like to play Tic Tac Toe? Enter 1 for yes or anything else to quit.");
			System.out.print("> ");
			if(keyboard.nextLine().trim().equals("1")) {
				setUpGame();
				startGame();
			}
		} else {
			startGame();
		}
		
	}
	
	/**
	 * This method sets up the Tic Tac Toe game. It asks the user for the dimensions of the game board and
	 * the names of the two players playing.
	 */
	private void setUpGame() {
		int boardSize;
		String p1Name;
		String p2Name;
		
		System.out.println("\t\tHello and welcome to Tic Tac Toe!");
		System.out.println("How big of a board would you like? Enter an integer N from 3 - 10 for an N x N board.");
		System.out.print("Note that any type of invalid input will default the value to 3. > ");

		try {
			boardSize = Integer.parseInt(keyboard.nextLine());
			game = new TTTEngine(boardSize);
		} catch(Exception e) {
			game = new TTTEngine();
		}
		
		System.out.print("Nice, now enter the name for Player 1: ");	// prompts for the names of the players, with empty strings defaulting
		p1Name = keyboard.nextLine().toUpperCase();						// to Player 1 or Player 2 respectively
		if(p1Name.equals(""))
			p1Name = "Player 1";
		System.out.print("And now enter the name for Player 2: ");
		p2Name = keyboard.nextLine().toUpperCase();
		if(p2Name.equals(""))
			p2Name = "Player 2";
		
		System.out.println("Okay " + p1Name + " is X, and " + p2Name + " is O.");
		game.setUpPlayers(p1Name + " (P1)(X)", 'X', p2Name + " (P2)(O)", 'O');
	}
	
	/**
	 * This method is the method that directly communicates with the Tic Tac Toe game engine.
	 * It randomly chooses which player to start first and advances the game, asking for
	 * user inputs on where to mark the game board, and keeps advancing the game until it is
	 * done.
	 */
	private void startGame() {
		Random rng = new Random();
		boolean p1First = rng.nextBoolean();
		boolean nextPlayer;
		StringTokenizer tokenizer;
		int rowChosen;
		int colChosen;

		if(p1First) {
			System.out.println(game.getP1Name() + " goes first!");
		} else {
			System.out.println(game.getP2Name() + " goes first!");
		}
		nextPlayer = p1First;	// used for clarity in the following do-while loop
		
		do {
			try {
				System.out.println(game.getBoardState());
				
				if(nextPlayer) {	// true for player 1 next, false for player 2 next
					System.out.println("\t\t" + game.getP1Name() + ", it is your turn.");
				} else {
					System.out.println("\t\t" + game.getP2Name() + ", it is your turn.");
				}
				
				System.out.println("Enter two integers M and N within the range [0, " + game.getBoardSize()
									+ "), separated by a space,\n to indicate you want to mark the spot at "
									+ "row M and column N on the board.");
				System.out.print("> ");
				
				tokenizer = new StringTokenizer(keyboard.nextLine());		// puts the input in a form that makes exceptions easier to handle
				if(tokenizer.countTokens() != 2) {
					throw new IllegalArgumentException();
				} else {
					rowChosen = Integer.parseInt(tokenizer.nextToken());
					if(rowChosen < 0 || rowChosen >= game.getBoardSize())	// validates that the input
						throw new ArrayIndexOutOfBoundsException();			// is within the bounds
					colChosen = Integer.parseInt(tokenizer.nextToken());	// of the game board
					if(colChosen < 0 || colChosen >= game.getBoardSize())
						throw new ArrayIndexOutOfBoundsException();
				}
				
				game.advanceTurn(rowChosen, colChosen, nextPlayer);
				nextPlayer = !nextPlayer;
			} catch(NumberFormatException nfe) {
				System.err.println("Please enter integers!");
			} catch(PlaceOnBoardTakenException pobte) {
				System.err.println(pobte);
			} catch(IllegalArgumentException iae) {
				System.err.println("Please enter only two integers.");
			} catch(ArrayIndexOutOfBoundsException aiobe) {
				System.err.println("Please enter integers within the range [0, " + game.getBoardSize() + ").");
			} catch(Exception e) {
				System.err.println("Error. Please enter a valid input.");
			}
		} while(!game.isDone());
		
		endGame();
	}
	
	/**
	 * This method is called once a Tic Tac Toe game is done. It prints the last state of the game board and
	 * the name of the winning player, if any. It then prompts the user to choose whether or not to start another
	 * game of Tic Tac Toe.
	 */
	private void endGame() {
		System.out.println(game.getBoardState());
		System.out.println("And the winner is: " + game.getWinnerName());
		
		System.out.println("\n\tWould you like to play again? Enter 1 for yes or anything else to quit.");
		System.out.print("> ");
		if(keyboard.nextLine().trim().equals("1")) {
			game.resetEngine();
			start(false);
		} else {
			System.out.println("Thank you so much for playing!");
		}
	}
}
