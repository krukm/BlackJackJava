package myNetworkedGame;

import gameNet.GameNet_UserInterface;
import gameNet.GamePlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyUserInterface extends JFrame implements GameNet_UserInterface,
		Serializable, ActionListener {

	// *******************************************************
	// Class Variables
	// *******************************************************
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 550;
	private static final int HEIGHT = 350;

	private MyGame myGame = null;
	private MyGameInput myGameInput = new MyGameInput();
	private GamePlayer myGamePlayer;

	private String myName = "";
	private int roundCount2 = 0;
	private boolean alreadyDone = false;
	private boolean addHitCard1 = true;
	private boolean addHitCard2 = true;
	private boolean addHitCard3 = true;
	private boolean addHitCard4 = true;
	private boolean addHitCard5 = true;
	private boolean addHitCard6 = true;

	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	private JPanel northText = new JPanel();
	private JPanel northButtons = new JPanel();
	private JPanel centerMain = new JPanel();
	private JPanel southMain = new JPanel();

	private JButton hitButton = new JButton("Hit");
	private JButton standButton = new JButton("Stand");
	private JButton nextRoundButton = new JButton("Next Round");

	private JLabel messageLabel = new JLabel("");
	private JLabel messageLabelName = new JLabel("");
	private JLabel scoreLabel1 = new JLabel("");
	private JLabel scoreLabel2 = new JLabel("");
	private JLabel cardBack1 = new JLabel(new ImageIcon("res/cardBack.png"));
	private JLabel cardBack2 = new JLabel(new ImageIcon("res/cardBack.png"));
	private JLabel player1Card1 = new JLabel();
	private JLabel player2Card1 = new JLabel();
	private JLabel player1Card2 = new JLabel();
	private JLabel player2Card2 = new JLabel();
	JLabel hitCard1;
	JLabel hitCard2;
	JLabel hitCard3;
	JLabel hitCard4;
	JLabel hitCard5;
	JLabel hitCard6;

	// *******************************************************
	// Class Constructors
	// *******************************************************
	public MyUserInterface() {
		super("Black Jack (21)");
	}

	// *******************************************************
	// I/O Methods
	// *******************************************************
	public void startUserInterface(GamePlayer player) {
		myGamePlayer = player;
		myName = myGamePlayer.getPlayerName();
		myGameInput.setName(myName);

		sendMessage(MyGameInput.CONNECTING);

		screenLayout();
	}

	public void receivedMessage(Object objectReceived) {
		MyGameOutput myGameOutput = (MyGameOutput) objectReceived;
		myGame = myGameOutput.myGame;

		int index = -1;
		for (BJPlayer player : myGame.players) {
			if (myName.equals(player.name))
				index = player.playerNumber;
		}

		if (myGame.roundCount != roundCount2) {
			resetDisplay();
			alreadyDone = false;
			addHitCard1 = true;
			addHitCard2 = true;
			addHitCard3 = true;
			addHitCard4 = true;
			addHitCard5 = true;
			addHitCard6 = true;
			roundCount2 += 1;
		}

		if (myGame.players.size() > 1 && !alreadyDone) {
			fillPlayerHands();
			if (myGame.players.size() == 2) {
				alreadyDone = true;
			}
		}

		messageLabelName.setText("* " + myName + " *");
		messageLabelName.setHorizontalAlignment(JLabel.CENTER);

		if (myGame.players.size() > 1) {

			if (myGame.players.get(0).standStatus
					&& myGame.players.get(1).standStatus) {
				endGameVisibility();
			}

			// set game status display
			String msg = myGame.getStatus(myGame.players);

			messageLabel.setText(msg);
			if (!(msg.equals("Game in Progress"))) {
				messageLabel.setForeground(Color.RED);
			} else {
				messageLabel.setForeground(Color.BLACK);
			}

			scoreLabel1.setText(myGame.players.get(0).name
					+ "'s Total: "
					+ Integer.toString(myGame
							.getPlayersCurrentScore(myGame.players.get(0))));

			scoreLabel2.setText(myGame.players.get(1).name
					+ "'s Total: "
					+ Integer.toString(myGame
							.getPlayersCurrentScore(myGame.players.get(1))));

			scoreLabel1.setVerticalAlignment(JLabel.CENTER);
			scoreLabel2.setVerticalAlignment(JLabel.CENTER);
			scoreLabel1.setForeground(Color.ORANGE);
			scoreLabel2.setForeground(Color.ORANGE);

			// add hit card display
			if (myGame.players.get(0).playerHand.size() <= 5
					&& myGame.players.get(1).playerHand.size() <= 5) {

				if ((myGame.players.get(0).standStatus)
						&& (myGame.players.get(1).standStatus)) {
					return;
				} else {
					addHitCard(myGame.players);
				}
			}

			// set visibility
			if (!(myGame.getStatus(myGame.players).equals("Game in Progress"))) {
				endGameVisibility();
			} else {
				visibilityLimitaions(index);

			}
		}
	}

	private void sendMessage(int command) {
		myGameInput.cmd = command;
		myGamePlayer.sendMessage(myGameInput);
	}

	// *******************************************************
	// Action Methods
	// *******************************************************
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hitButton) {
			sendMessage(MyGameInput.HIT);
		} else if (e.getSource() == standButton) {
			sendMessage(MyGameInput.STAND);
		} else if (e.getSource() == nextRoundButton) {
			sendMessage(MyGameInput.NEXTROUND);
		}
	}

	// *******************************************************
	// Display Methods
	// *******************************************************
	private void fillPlayerHands() {

		player1Card1.setIcon(new ImageIcon(myGame.players.get(0).playerHand
				.get(0).imageAsset()));
		player1Card2.setIcon(new ImageIcon(myGame.players.get(0).playerHand
				.get(1).imageAsset()));
		player2Card1.setIcon(new ImageIcon(myGame.players.get(1).playerHand
				.get(0).imageAsset()));
		player2Card2.setIcon(new ImageIcon(myGame.players.get(1).playerHand
				.get(1).imageAsset()));
	}

	private void visibilityLimitaions(int index) {

		switch (index) {
		case 0:
			player1Card1.setVisible(true);
			player2Card1.setVisible(false);
			cardBack1.setVisible(true);
			cardBack2.setVisible(false);
			scoreLabel1.setVisible(true);
			scoreLabel2.setVisible(false);
			nextRoundButton.setVisible(false);
			break;

		case 1:
			player1Card1.setVisible(false);
			player2Card1.setVisible(true);
			cardBack1.setVisible(false);
			cardBack2.setVisible(true);
			scoreLabel1.setVisible(false);
			scoreLabel2.setVisible(true);
			nextRoundButton.setVisible(false);
			break;
		}
	}

	private void endGameVisibility() {
		player1Card1.setVisible(true);
		player2Card1.setVisible(true);
		cardBack1.setVisible(false);
		cardBack2.setVisible(false);
		scoreLabel1.setVisible(true);
		scoreLabel2.setVisible(true);
		nextRoundButton.setVisible(true);
	}

	private void addHitCard(ArrayList<BJPlayer> players) {

		String hitCard1Source;
		String hitCard2Source;
		String hitCard3Source;
		String hitCard4Source;
		String hitCard5Source;
		String hitCard6Source;

		hitCard1 = new JLabel();
		hitCard2 = new JLabel();
		hitCard3 = new JLabel();
		hitCard4 = new JLabel();
		hitCard5 = new JLabel();
		hitCard6 = new JLabel();

		if (players.get(0).playerHand.size() == 3
				&& !(players.get(0).standStatus) && addHitCard1) {

			hitCard1Source = players.get(0).playerHand.get(2).imageAsset();
			hitCard1.setIcon(new ImageIcon(hitCard1Source));
			centerMain.add(hitCard1);
			addHitCard1 = false;
		}

		if (players.get(1).playerHand.size() == 3
				&& !(players.get(1).standStatus) && addHitCard4) {

			hitCard4Source = players.get(1).playerHand.get(2).imageAsset();
			hitCard4.setIcon(new ImageIcon(hitCard4Source));
			southMain.add(hitCard4);
			addHitCard4 = false;
		}

		if (players.get(0).playerHand.size() == 4
				&& !(players.get(0).standStatus) && addHitCard2) {

			hitCard2Source = players.get(0).playerHand.get(3).imageAsset();
			hitCard2.setIcon(new ImageIcon(hitCard2Source));
			centerMain.add(hitCard2);
			addHitCard2 = false;
		}

		if (players.get(1).playerHand.size() == 4
				&& !(players.get(1).standStatus) && addHitCard5) {

			hitCard5Source = players.get(1).playerHand.get(3).imageAsset();
			hitCard5.setIcon(new ImageIcon(hitCard5Source));
			southMain.add(hitCard5);
			addHitCard5 = false;
		}

		if (players.get(0).playerHand.size() == 5
				&& !(players.get(0).standStatus) && addHitCard3) {

			hitCard3Source = players.get(0).playerHand.get(4).imageAsset();
			hitCard3.setIcon(new ImageIcon(hitCard3Source));
			centerMain.add(hitCard3);
			addHitCard3 = false;
		}

		if (players.get(1).playerHand.size() == 5
				&& !(players.get(1).standStatus) && addHitCard6) {

			hitCard6Source = players.get(1).playerHand.get(4).imageAsset();
			hitCard6.setIcon(new ImageIcon(hitCard6Source));
			southMain.add(hitCard6);
			addHitCard6 = false;
		}

		validate();
	}

	private void resetDisplay() {
		centerMain.removeAll();
		centerMain.revalidate();
		centerMain.setBackground(new Color(0, 122, 0));
		centerMain.setLayout(new FlowLayout(FlowLayout.TRAILING));
		centerMain.add(cardBack2);
		centerMain.add(player1Card1);
		centerMain.add(player1Card2);

		southMain.removeAll();
		southMain.revalidate();
		southMain.setBackground(new Color(0, 122, 0));
		southMain.setLayout(new FlowLayout(FlowLayout.TRAILING));
		southMain.add(cardBack1);
		southMain.add(player2Card1);
		southMain.add(player2Card2);

		validate();
	}

	public void screenLayout() {
		setLayout(new BorderLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);

		addWindowListener(new Termination());
		setLocationRelativeTo(null);

		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		north.setLayout(new BorderLayout());
		north.setBackground(Color.WHITE);
		north.add(northText, BorderLayout.NORTH);
		north.add(northButtons, BorderLayout.CENTER);
		northText.setLayout(new GridLayout(0, 2));
		northText.add(messageLabelName);
		northText.add(messageLabel);
		northButtons.setBackground(new Color(0, 122, 0));
		northButtons.setLayout(new GridLayout(0, 3));
		northButtons.add(hitButton);
		northButtons.add(standButton);
		northButtons.add(nextRoundButton);

		// player1
		center.setLayout(new BorderLayout());
		center.setBackground(new Color(0, 122, 0));
		center.add(scoreLabel1, BorderLayout.NORTH);
		center.add(centerMain, BorderLayout.CENTER);
		centerMain.setBackground(new Color(0, 122, 0));
		centerMain.setLayout(new FlowLayout(FlowLayout.TRAILING));
		centerMain.add(cardBack2);
		centerMain.add(player1Card1);
		centerMain.add(player1Card2);

		// player2
		south.setLayout(new BorderLayout());
		south.setBackground(new Color(0, 122, 0));
		south.add(scoreLabel2, BorderLayout.NORTH);
		south.add(southMain, BorderLayout.CENTER);
		southMain.setBackground(new Color(0, 122, 0));
		southMain.setLayout(new FlowLayout(FlowLayout.TRAILING));
		southMain.add(cardBack1);
		southMain.add(player2Card1);
		southMain.add(player2Card2);

		hitButton.addActionListener(this);
		standButton.addActionListener(this);
		nextRoundButton.addActionListener(this);

		setVisible(true);

	}

	// *******************************************************
	// Inner Class
	// *******************************************************
	class Termination extends WindowAdapter {
		public void windowClosing(WindowEvent e) {

			sendMessage(MyGameInput.DISCONNECTING);
			myGamePlayer.doneWithGame();
			System.exit(0);
		}
	}
}
