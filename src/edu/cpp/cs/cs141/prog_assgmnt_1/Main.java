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
 * This class is the driver for the Tic Tac Toe game. It creates a new UI object and calls its start method to start the game.
 * @author Joel Tengco
 *
 */
public class Main {

	/**
	 * Entry point for the program.
	 * @param args Not used in this program
	 */
	public static void main(String[] args) {
		UI newGame = new UI();
		newGame.start();
	}

}
