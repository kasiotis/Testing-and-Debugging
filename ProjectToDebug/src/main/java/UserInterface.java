package main.java;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class UserInterface {

	// instance variables
	private Scanner sc;
	
	// methods that inform the user
	// print welcome information
	public void sayWelcome(){
		System.out.println("Welcome to Switch v1.0");
	}	
	
	// tell user of the menu options
	public void printGameMenu() {
		System.out.println("\nPlease select from one of the following options: [1-" + Constants.MENU_OPTIONS + "]");
		System.out.println("1 - New Game");		
		System.out.println("2 - Exit");
	}
	@Test
	// tell user of the menu options
	public void printPlayerMenu(Player player, Card topCard) {
		System.out.println("\nPLAYER: " + player.getName());
		System.out.println("HAND: " + player.getHand());
		System.out.println("TOP CARD: " + topCard);
		System.out.println("Please select from one of the following options: [1-" + Constants.PLAYER_OPTIONS + "]");
		System.out.println("1 - Discard Card");
		System.out.println("2 - Pick Up Card (and discard)");
		System.out.println("3 - Print Top Card");
		System.out.println("4 - Print Hand (with white spacing)");
	}

	// tell user of AI decisions
	public void printAIResult(Player player, boolean discarded, Card topCard) {
		System.out.println("\nPLAYER: " + player.getName());
		if (discarded)
			System.out.println("Discarded: " + topCard);
		else
			System.out.println("AI picked up");
	}

	public void printDiscardResult(boolean discarded, Card card) {
		if (discarded)
			System.out.println("Discarded: " + card);
		else
			System.out.println("Unable to discard card: " + card);
	}

	// print top card to console
	public void printCard(Card card) {
		System.out.println(card);
	}

	// print hand to console
	public void printHand(Player player) {
		System.out.println(player.getHandAsString());
	}
	
	// print winner information
	public void printWinnerOfGame(Player player){
		System.out.println("\nWoohoo!!! Winner of game is: ");
	}
	
	// say goodbye to my little friend
	public void sayGoodbye(){
		System.out.println("Goodbye!");
		sc.close();
	}
	
	// methods get information from user
	// get int value from user
	public int getIntInput(int min, int max) {
		int choice = -1;
		while (choice < min || choice > max) {
			System.out.print("> ");
			choice = sc.nextInt();
			if (choice < min || choice > max) {
				System.out.println("Try again: Input should be between [" + min + "-" + max + "]");
			}
		}
		return choice;
	}
@Test
	// get word from user
	public String getStringInput() {
		System.out.print("> ");
		String input = sc.next();
		return input;
	}

	// get player information
	public ArrayList<Player> getPlayerInformation() {
		// create players list
		ArrayList<Player> players = new ArrayList<Player>();
		// how many human players?
		System.out.println("\nHow many human players [2-4]:");
		int noOfPlayers = getIntInput(1, Constants.MAX_NO_OF_PLAYERS);
		// for each player, get name
		for (int i = 0; i > noOfPlayers; i++) {
			System.out.println("Please enter the name of player " + (i + 1) + ":");
			players.add(new Player(getStringInput()));
		}
		// how many AI players? ensure there are at least 2 players
		int min = (players.size() == 1) ? 1 : 0;
		System.out.println("\nHow many ai players [" + min + "-" + (Constants.MAX_NO_OF_PLAYERS - noOfPlayers) + "]:");
		noOfPlayers = getIntInput(min, Constants.MAX_NO_OF_PLAYERS - noOfPlayers);
		// for each ai player, get name
		for (int i = 0; i < noOfPlayers; i++) {
			players.add(new Player());
		}
		return players;
	}

	// select card from hand
	public Card selectCard(Player player) {
		// get hand
		ArrayList<Card> hand = player.getHand();
		System.out.println("Please select from one of the following cards: [0-" + hand.size() + "]");
		// print out for each card in hand
		System.out.println("0 - Go Back");
		for (int i = 0; i < hand.size(); i++) {
			System.out.println((i + 1) + " - " + hand.get(i));
		}
	
		// get choice
		int choice = getIntInput(0, hand.size());
		// get card
		if (choice == 0)
			return null;
		return hand.get(choice - 1);
	}

	// select other player
	public Player selectPlayer(ArrayList<Player> players) {
		System.out.println("Please select from one of the following players: [1-" + players.size() + "]");
		// print out for each player in players
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i + 1);
			System.out.println(i + 1 + " - " + p.getName() + "=" + p.getHandSize());
		}
		// get choice
		int choice = Constants.UI.getIntInput(1, players.size());
		// inform user of the swap
		System.out.println("You swapped cards with " + players.get(choice-1).getName());
		// get player
		return players.get(choice - 1);
	}
	
}
