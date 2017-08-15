package myNetworkedGame;

import java.io.Serializable;

public class Card implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;

	private Suit suit;
	private CardValue value;

	// *******************************************************
	// Class Constructor
	// *******************************************************
	public Card(Suit suit, CardValue value) {
		this.setSuit(suit);
		this.setValue(value);
	}

	// *******************************************************
	// Getter/ Setter Methods
	// *******************************************************
	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public CardValue getValue() {
		return value;
	}

	public void setValue(CardValue value) {
		this.value = value;
	}

	// *******************************************************
	// Access Methods
	// *******************************************************
	public String toString() {
		return value + " of " + suit;
	}

	public String imageAsset() {
		return "res/" + value + suit + ".png";
	}
}
