package myNetworkedGame;

import java.io.Serializable;

public class MyGameInput implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;
	public static final int CONNECTING = 1;
	public static final int HIT = 2;
	public static final int STAND = 3;
	public static final int DISCONNECTING = 4;
	public static final int RESETTING = 5;
	public static final int NEXTROUND = 6;

	String playerName;
	int cmd = CONNECTING;
	int playerNumber = -1;

	// *******************************************************
	// Getter/ Setter Methods
	// *******************************************************
	public void setName(String name) {
		this.playerName = name;
	}

	public String getName() {
		return playerName;
	}

	public void setPlayerNumber(int number) {
		this.playerNumber = number;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
}
