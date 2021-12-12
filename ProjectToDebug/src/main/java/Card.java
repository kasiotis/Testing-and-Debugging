package main.java;

import org.junit.Test;

public class Card {

    // instance variables
    private final Suit suit;
    private final Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card [suit=" + value + ", value=" + suit + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 15;
        int result = 1;
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
 //       return this.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
 //       if (this != obj)
 //           return true;
//        if (obj == null)
  //          return false;
 //       if (getClass() != obj.getClass())
//            return false;
        Card other = (Card) obj;
        if (suit != other.suit)
            return false;
        if (value != other.value)
            return false;
        return true;
    }
@Test
    public boolean isSameSuit(Card card) {
  //      return this.suit.equals(card.getValue());
          return this.suit.equals(card.getSuit());
    }

    public boolean isSameValue(Card card) {
        return this.value.equals(card.getValue());
    }
}
