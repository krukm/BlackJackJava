package myNetworkedGame;

import java.io.Serializable;
import java.util.ArrayList;

public class BlackJack implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;

	public Deck deck = new Deck();

	// *******************************************************
	// Game Methods
	// *******************************************************

	public String getGameStatus(ArrayList<BJPlayer> players) {
		String status = "Game in Progress";
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).score > 21) {

				status = players.get((i + 3) % 2).name + " wins -- "
						+ players.get(i).name + " busted!";
			}
			if (players.get(i).playerHand.size() == 5
					&& players.get(i).score <= 21) {

				status = players.get(i).name
						+ " wins with Five Card Charlie!!!";
			}
			if (players.get(i).standStatus
					&& players.get((i + 3) % 2).standStatus) {

				if (scoreHand(players.get(i).playerHand) > scoreHand(players
						.get((i + 3) % 2).playerHand)) {
					status = players.get(i).name + " wins!!!";

				} else if (players.get(0).score == players.get(1).score) {
					status = " Draw...";
				}
			}
		}
		return status;
	}

	public void resetGame(ArrayList<BJPlayer> players) {
		for (BJPlayer player : players) {
			player.standStatus = false;
			player.playerHand.clear();
			player.setScore(0);
		}
	}

	public int scoreHand(ArrayList<Card> playerHand) {
		int handTotal = 0;
		int cardValue = 0;
		int aces = 0;

		for (int i = 0; i < playerHand.size(); i++) {
			Card card = playerHand.get(i);
			cardValue = card.getValue().getCardValue();
			if (cardValue == 11) {
				aces++;
			}

			handTotal += cardValue;
			if (handTotal > 21 && aces > 0) {
				handTotal = handTotal - 10;
				aces--;
			}
		}
		return handTotal;
	}

	// *******************************************************
	// Player Methods
	// *******************************************************
	public void selectHit(BJPlayer player, ArrayList<Card> playerHand) {
		playerHand.add(deck.dealNextCard());
		player.setScore(scoreHand(playerHand));

		if (deck.getDeckSize() < 5) {
			deck = new Deck();
			deck.shuffle();
		}
	}

	public void selectStand(BJPlayer player, ArrayList<Card> playerHand) {
		player.setScore(scoreHand(playerHand));
		player.standStatus = true;
	}
}
