package myNetworkedGame;

import java.io.Serializable;

public class MyGameOutput implements Serializable {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;
	MyGame myGame = null;

	// *******************************************************
	// Class Constructor
	// *******************************************************
	MyGameOutput(MyGame mygame) {
		this.myGame = mygame;
	}
}
