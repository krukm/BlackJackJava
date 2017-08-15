package myNetworkedGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;
	private ArrayList<Card> deck;

	// *******************************************************
	// Class Constructor
	// *******************************************************
	public Deck() {

		deck = new ArrayList<Card>();

		for (Suit suit : Suit.values()) {
			for (CardValue value : CardValue.values())
				deck.add(new Card(suit, value));
		}
	}

	// *******************************************************
	// Class Methods
	// *******************************************************
	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Card dealNextCard() {
		Card c = deck.get(0);
		deck.remove(0);
		return c;
	}

	public int getDeckSize() {
		return deck.size();
	}

	public String toString() {
		return Arrays.asList(deck).toString();
	}
}
