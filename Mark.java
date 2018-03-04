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
 * This {@code Mark} class represents a mark in a game (generally a board game).
 * It contains the name of the player to whom the mark belongs to
 * and a value of the mark as the type {@code char}.
 * @author Joel Tengco
 *
 */
public class Mark {
	/**
	 * Represents the name of the player to whom this mark belongs to.
	 */
	private String playerName;
	/**
	 * Represents the value given for this mark.
	 */
	private char markValue;
	
	/**
	 * Constructs a new Mark that belongs to <i>"unnamed_player"</i> and of value 'M'.
	 */
	public Mark() {
		playerName = "unnamed_player";
		markValue = 'M';
	}
	
	/**
	 * Constructs a new Mark that belongs to the specified player's name. The value of the mark
	 * is determined by the specified {@code char} given. If the {@code char} given is in lowercase,
	 * then it is changed to uppercase.
	 * @param playerName The name of the player to whom the mark belongs to
	 * @param value The value of the mark
	 */
	public Mark(String playerName, char value) {
		this.playerName = playerName;
		markValue = Character.toUpperCase(value);
	}
	
	/**
	 * A method to get the name of the player to whom this {@code Mark} object belongs to.
	 * @return A string containing the name of the player to whom this mark belongs to.
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Returns the value of this {@code Mark} in the form of a {@code String}.
	 * @return A string representation of the mark's value.
	 */
	public String toString() {
		return Character.toString(markValue);
	}
}
