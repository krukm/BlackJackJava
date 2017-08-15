package myNetworkedGame;

import java.io.Serializable;
import java.util.ArrayList;

public class BJPlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<Card> playerHand;
	String name;
	boolean standStatus = false;
	int playerNumber;
	int score;

	BJPlayer(String name, int number, boolean standStatus, int score) {
		this.name = name;
		this.playerHand = new ArrayList<Card>();
		this.playerNumber = number;
		this.standStatus = standStatus;
		this.score = score;
	}

	public void setName(String myName) {
		this.name = myName;
	}

	public String getName() {
		return name;
	}

	public void setPlayerHand(ArrayList<Card> hand) {
		playerHand = hand;
	}

	public ArrayList<Card> getPlayerHand() {
		return playerHand;
	}

	public void setPlayerNumber(int number) {
		this.playerNumber = number;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String toString() {
		return name + "--player #" + playerNumber;
	}
}