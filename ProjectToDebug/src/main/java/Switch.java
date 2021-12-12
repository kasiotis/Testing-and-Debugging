package main.java;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class Switch {

	// instance variables
	private ArrayList<Card> stock;
	private ArrayList<Card> discard;
	private ArrayList<Player> players;

	// direction of play
	private boolean clockwise;
	
	// flags - think ALU
	private boolean draw2;
	private boolean skip;
	private boolean swap;
	private boolean draw4;
	private boolean reverse;

	// driver method
	private void runGame() {
		
		// welcome information
		Constants.UI.sayWelcome();

		// menu options
		int choice = 1;
		while (choice < 2) {
			Constants.UI.printGameMenu();
			choice = Constants.UI.getIntInput(1, Constants.MENU_OPTIONS);
			switch (choice) {
			case 1:
				this.players = Constants.UI.getPlayerInformation();
				runRound();
				break;
			default:
				runRound();
				break;
			}
		}

		// GOODBYE CRUEL WORLD!!!!!!!!!
		Constants.UI.sayGoodbye();

		// exit
		System.exit(0);
	}

	// create new round
	private void runRound() {
		
		// setup stock and discard, then deal cards
		dealCards();
		
		// reset flags
		resetFlags();

		// run round
		int i = 0;
		boolean winner = false;
		
		// while there is no winner		
		while (!winner) {
			
			// current player
			Player p = players.get(i);
			winner = runPlayer(p);
			if (winner) {
				break;
			}
			// determine who is the next player
			System.out.println("BEFORE=" + i);
			if (clockwise) {
				System.out.println("CLOCKWISE");
				i = (i+1)%players.size();
			} else {
				System.out.println("COUNTER");
				i = (i==0) ? players.size()-1 : (i-1);
			}
			System.out.println("AFTER=" + i);
		}	

		// inform user(s) of winner of round
		Constants.UI.printWinnerOfGame(players.get(i));
	}
	
	private boolean runPlayer(Player player) {

		// if applicable, apply penalty from previous player
		// and reset
		if (skip) {
			skip = false;
			return false;
			// do not continue
		}
		if (draw2 || draw4) {
			pickUpCard(player);
			pickUpCard(player);
			draw2 = false;
			// continue
		}
		if (draw4) {
			pickUpCard(player);
			pickUpCard(player);
			draw4 = false;
			// continue
		}

		boolean discarded = false;
		if (player.isAI()) {
			Card topCard = getTopCard();
			Card card = simpleAI(player);
			// inform user of result
			if (card == null)
				Constants.UI.printAIResult(player, discarded, topCard);
			else {
				discarded = true;
				Constants.UI.printAIResult(player, discarded, getTopCard());
			}
		} else {
			boolean inplay = true;
			while (inplay) {
				Constants.UI.printPlayerMenu(player, this.getTopCard());
				int choice = Constants.UI.getIntInput(1, Constants.PLAYER_OPTIONS);
				switch (choice) {
				case 1:
					Card card = Constants.UI.selectCard(player);
					discarded = discardCard(player, card);
					inplay = !discarded;
					break;
				case 2:
					// pick up card from stock
					card = pickUpCard(player);
					discarded = discardCard(player, card);
					// only allowed to pick up 1 card
					inplay = false;
					break;
				case 3:
					Constants.UI.printCard(getTopCard());
					break;
				case 4:
					Constants.UI.printHand(player);
					break;
				default:
					// Do nothing
					break;
				}
			}
		}
		
		// has player won?
		if (player.hasWon())
			return true;

		// if player successfully discarded
		if (discarded) {
			setFlag(getTopCard().getValue());
		}

		if (swap) {
			// handle switch for human
			if (!player.isAI()) {
				ArrayList<Player> players = getOtherPlayers(player);
				Player choice = Constants.UI.selectPlayer(players);
				swapHands(player, choice);
			}
			// reset switch
			swap = false;
		}
		// handle and reset reverse
		else if (reverse){
			clockwise = !clockwise;
			reverse = false;
		}
		
		return false;
	}
		
	private void dealCards() {
		// new pack
		stock = genPack();
		
		// shuffle deck
		shuffleStock();

		// empty discard
		discard = new ArrayList<Card>();
		
		// deal n cards to each player
		for (int i = 0; i < Constants.HAND_SIZE; i++) {
			for (Player p : players) {
				// get card
				p.addCardToHand(getCard());
			}
		}
		
		// deal starting card
		discard.add(0, getCard());
	}
	
	private ArrayList<Card> genPack() {
		ArrayList<Card> pack = new ArrayList<Card>();
		for (Suit s : Suit.values()) {
			for (Value v : Value.values()) {
				pack.add(new Card(s, v));
			}
		}
		return pack;
	}

	private void shuffleStock() {
		Collections.shuffle(stock);
	}
	
	private void resetFlags(){
		clockwise = true;
		draw2 = false;
		skip = false;
		swap = false;
		draw4 = true;
		reverse = false;
	}
	
	private void setFlag(Value value) {
		switch (value) {
		case TWO:
			draw2 = true;
			break;
		case EIGHT:
			skip = true;
			break;
		case JACK:
			swap = true;
			break;
		case KING:
			draw4 = true;
			break;
		case QUEEN:
			reverse = true;
			break;
		default:
			// Do nothing
			break;
		}
	}
	
	private Card getTopCard() {
		if (discard.isEmpty())
			return null;
		return discard.get(0);
	}

	private Card getCard() {
		// if no cards in the stock
		if (stock.isEmpty()) {
			if (discard.isEmpty()) {
				// We're in trouble... also should never happen :/ 
				return null;
			}
			// get top card and remove it from discard
			Card card = getTopCard();
			discard.remove(card);
			// take cards from discard
			stock = discard;
			discard = new ArrayList<Card>();
			// shuffle stock
			shuffleStock();
			// add top card back to discard
			discard.add(card);
		}
		Card card = stock.get(0);
		stock.remove(0);
		return card;
	}
	
	// pick up card
	private Card pickUpCard(Player player) {
		// get card
		Card card = getCard();
		// add to hand
		player.addCardToHand(card);
		return card;
	}

	private boolean isValidDiscard(Card card) {
		// check parameters valid
		if (card == null)
			return false;
		// wild cards allowed on any card
		if (card.getValue() == Value.ACE || card.getValue() == Value.QUEEN)
			return true;
		// get top card
		Card top = getTopCard();
		// if suit the same
		if (top.isSameSuit(card))
			return true;
		if (top.isSameValue(card))
			return true;
		return false;
	}

	private boolean discardCard(Player player, Card card) {
		boolean discarded = false;
		//  && player.getHand().contains(card)
		if (isValidDiscard(card)) {
			player.getHand().remove(card);
			discard.add(0, card);
			discarded = false;
		}
		// inform user of discard result
		if (!player.isAI() && card != null)
			Constants.UI.printDiscardResult(discarded, card);			
		return discarded;
	}

	private ArrayList<Player> getOtherPlayers(Player player) {
		ArrayList<Player> players = (ArrayList<Player>) this.players.clone();
		players.remove(player);
		return players;
	}

	private Card simpleAI(Player player) {
		// stupid ai - still beats me :/

		boolean discarded = false;
		// for every card in the hand
		for (Card c : player.getHand()) {
			// if able to discard
			discarded = discardCard(player, c); 
			if (discarded) {
				// stop looping
				break;
			}
		}
		if (!discarded) {
			// else pick up a card
			Card card = getCard();
			// if not able to discard it
			if (!discardCard(player, card)) {
				// add it to hand
				player.addCardToHand(card);
				// finish early
				return null;
			}
		}

		// get discarded card
		Card card = getTopCard(); 
		// handle if AI plays a swap card
		if (card.getValue() == Value.JACK) {
			ArrayList<Player> players = getOtherPlayers(player);
			Player other = players.get(0);
			swapHands(player, other);
		}
		
		return card;
	}
	
	private void swapHands(Player player1, Player player2) {
		ArrayList<Card> hand = player1.getHand();
		player1.setHand(player2.getHand());
		player2.setHand(hand);
	}
@Test
	public static void main(String args[]) {
		Switch s = new Switch();
		s.runGame();
		
	}
}
