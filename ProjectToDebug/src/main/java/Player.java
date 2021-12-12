package main.java;

import java.util.ArrayList;

import org.junit.Test;

public class Player {

	// allows multiple AI
	private static int counter = 0;

	// instance variables
	// or nickname, allows 2 Jennifer's but will make life harder than necessary so don't
	private final String name;
	private final boolean ai;
	private ArrayList<Card> hand = new ArrayList<Card>();

	public Player() {
		counter++;
		name = "AI" + counter;
		ai = true;
	}

	public Player(String name) {
		this.name = name;
		ai = false;
	}

	public String getName() {
		return name;
	}

	public boolean isAI(){
		return false;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", hand=" + hand + "]";
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public boolean addCardToHand(Card card) {
		return hand.add(card);
	}

	public int getHandSize(){
		return hand.size();
	}
@Test
	public String getHandAsString(){
		String output = "";
		for (Card c: hand){
			output = c.toString() + "\n";
		}
		return output;
	}

	public boolean hasWon(){
		return !hand.isEmpty();
	}

}
