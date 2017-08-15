package myNetworkedGame;

import gameNet.GameNet_CoreGame;

import java.io.Serializable;
import java.util.ArrayList;

public class MyGame extends GameNet_CoreGame implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;

	ArrayList<BJPlayer> players = new ArrayList<BJPlayer>();
	BlackJack bj = new BlackJack();
	private boolean excecutionDone = false;
	private boolean alreadyExcecuted = false;
	public int roundCount = 0;

	// *******************************************************
	// Class Constructor
	// *******************************************************
	MyGame() {
	}

	// *******************************************************
	// Game Methods
	// *******************************************************
	public void shuffleDeck() {
		bj.deck.shuffle();
	}

	public void startRound(int index) {
		// deal starting hand
		players.get(index).playerHand.add(bj.deck.dealNextCard());
		players.get(index).playerHand.add(bj.deck.dealNextCard());
	}

	// *******************************************************
	// I/O Methods
	// *******************************************************
	public Object process(Object inputs) {
		MyGameInput myGameInput = (MyGameInput) inputs;

		if (myGameInput.cmd == MyGameInput.CONNECTING && players.size() < 2) {
			players.add(new BJPlayer(myGameInput.playerName,
					players.size() + 1, false, 0));
			players.get(players.size() - 1).setPlayerNumber(players.size() - 1);
		}

		int clientIndex = getClientIndex(myGameInput.getName());

		if (clientIndex < 0) {
			System.out.println("Already have 2 players");
			return null; // Ignore input
		}

		switch (myGameInput.cmd) {
		case MyGameInput.CONNECTING:
			if (clientIndex == 0 && !excecutionDone) {
				shuffleDeck();
				startRound(0);
				excecutionDone = true;
			}
			if (clientIndex == 1 && !alreadyExcecuted) {
				startRound(1);
				alreadyExcecuted = true;
			}
			break;
		case MyGameInput.HIT:
			if (players.size() > 1) {
				if (players.get(0).playerHand.size() >= 5
						|| players.get(1).playerHand.size() >= 5) {
					break;

				} else if ((players.get(0).standStatus)
						&& (players.get(1).standStatus)) {
					break;

				} else if (players.get(0).score <= 21
						&& players.get(1).score <= 21) {

					bj.selectHit(players.get(clientIndex),
							players.get(clientIndex).playerHand);
				}
			}
			break;
		case MyGameInput.STAND:
			bj.selectStand(players.get(clientIndex),
					players.get(clientIndex).playerHand);

			break;
		case MyGameInput.DISCONNECTING:
			int index = getClientIndex(myGameInput.playerName);
			if (index >= 0)
				players.remove(index);
			break;
		case MyGameInput.NEXTROUND:
			bj.resetGame(players);
			startRound(0);
			startRound(1);
			roundCount += 1;
			break;
		default: // ignore
		}

		MyGameOutput myGameOutput = new MyGameOutput(this);
		return myGameOutput;
	}

	public String getStatus(ArrayList<BJPlayer> players) {
		return bj.getGameStatus(players);
	}

	// *******************************************************
	// Player Access Methods
	// *******************************************************
	private int getClientIndex(String name) {
		int index = -1;
		for (BJPlayer player : players) {
			if (name.equals(player.name)) {
				index = player.getPlayerNumber();
			}
		}
		return index;
	}

	public int getPlayersCurrentScore(BJPlayer player) {
		return bj.scoreHand(player.playerHand);
	}
}
