//Jeff Waters - Student id: 2495819 - MSc Software Development

/* FruitMachineView is the view class in the model-view-controller paradigm.
 * 
 * When the GUI is initially set up, the initial card type names and the text in the top left hand quadrant are derived from the controller 
 * (which in turn gets them from the model). 
 * 
 * When changes to initial text in the GUI and to the initial engaged statuses of the buttons are made, this in response to instructions from the controller 
 * class (FruitMachine).
 * 
 * In addition, the view has action listeners, which generate action events when a button in a engaged state is pressed (only one of the the buttons is 
 * engaged and therefore capable of triggering action events when pressed at any one time). These action events are responded to by the 
 * actionPerformed method in the controller class. 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FruitMachineView {

	private JButton spinButton, newGameButton;
	private JPanel leftCard, middleCard, rightCard, topLeftPanel, bottomLeftPanel, bottomRightPanel;
	private JLabel balanceInfo, scoreChangeInfoMessage, winLoseUpdate;
	private String balanceMessage, winLoseMessage, update;
	private Font font = new Font("Times New Roman", Font.BOLD, 14);
	private ArrayList<JLabel> cards;

	FruitMachineView(FruitMachine controller) {
		initialSetup(controller);
	}

	void initialSetup(FruitMachine controller) {
		JFrame frame = createFrame();
		addPanelsToFrame(frame); // Creates four JPanels to add to the JFrame, one for each quadrant.
		createCards();
		createTopLeftQuadrantJLabels();
		setRunningBalanceText(controller.getStartingBalanceMessage());
		setCardCommentary(controller.getWelcomeMessage());
		addButtons();
		addActionListeners(controller);
		setRunningState(true); // Enables Spin button and disables New Game button. true = game not over.
		createAndInitialiseCardJLabels(controller);
		fontForCardJLabels();
		addJLabelsToCards();
		frame.setVisible(true);
	}

	// Creates a JFrame and applies a 2x2 layout (4 panels to be added, 1 per
	// quadrant):

	JFrame createFrame() {
		JFrame frame = new JFrame("Fruit Machine GUI");
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		return frame;
	}

	void addPanelsToFrame(JFrame frame) {
		createTopLeftPanel(frame);
		createTopRightPanel(frame);
		createBottomLeftPanel(frame);
		createBottomRightPanel(frame);
	}

	void createCards() {
		createLeftCardJPanel();
		createMiddleCardJPanel();
		createRightCardJPanel();
	}

	void spinButton() {
		spinButton = new JButton("spin");
		spinButton.setFont(font);
		bottomRightPanel.add(spinButton);
	}

	void newGameButton() {
		newGameButton = new JButton("new game");
		newGameButton.setFont(font);
		bottomRightPanel.add(newGameButton);
	}

	void addButtons() {
		spinButton();
		newGameButton();
	}

	void addActionListeners(FruitMachine controller) {
		spinButton.addActionListener(controller);
		newGameButton.addActionListener(controller);
	}

	/*
	 * Method below enables Spin button and disables New Game or vice versa. If true
	 * passed, game is in play (and spin button enabled/new game button disabled).
	 */

	public void setRunningState(boolean gameinProgress) {
		spinButton.setEnabled(gameinProgress);
		newGameButton.setEnabled(!gameinProgress);
	}

	void createTopLeftPanel(JFrame frame) {
		this.topLeftPanel = new JPanel();
		topLeftPanel.setBorder(new EmptyBorder(40, 40, 0, 0));
		topLeftPanel.setLayout(new BorderLayout());
		frame.add(topLeftPanel);

	}

	void createTopRightPanel(JFrame frame) {
		JPanel topRightPanel = new JPanel();
		frame.add(topRightPanel);
	}

	void createBottomLeftPanel(JFrame frame) {
		this.bottomLeftPanel = new JPanel();
		bottomLeftPanel.setBorder(new EmptyBorder(20, 40, 60, 0));
		bottomLeftPanel.setLayout(new GridLayout(0, 3, 20, 0));
		frame.add(bottomLeftPanel);
	}

	void createBottomRightPanel(JFrame frame) {
		this.bottomRightPanel = new JPanel();
		bottomRightPanel.setBorder(new EmptyBorder(40, 60, 80, 100));
		bottomRightPanel.setLayout(new GridLayout(2, 1, 0, 10));
		frame.add(bottomRightPanel);
	}

	void createLeftCardJPanel() {
		leftCard = new JPanel();
		leftCard.setBackground(Color.YELLOW);
		leftCard.setLayout(new GridBagLayout());// Has the effect of centralising the card's JLabel.
		leftCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		bottomLeftPanel.add(leftCard);
	}

	void createMiddleCardJPanel() {
		middleCard = new JPanel();
		middleCard.setBackground(Color.YELLOW);
		middleCard.setLayout(new GridBagLayout()); // Has the effect of centralising the card's JLabel.
		middleCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		bottomLeftPanel.add(middleCard);
	}

	void createRightCardJPanel() {
		rightCard = new JPanel();
		rightCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		rightCard.setBackground(Color.YELLOW);
		rightCard.setLayout(new GridBagLayout()); // Has the effect of centralising the card's JLabel.
		bottomLeftPanel.add(rightCard);
	}

	void createTopLeftQuadrantJLabels() {
		balanceInfo = new JLabel(balanceMessage);// The top message of the quadrant - balance information.
		balanceInfo.setFont(font);
		topLeftPanel.add(balanceInfo, BorderLayout.PAGE_START);
		scoreChangeInfoMessage = new JLabel(update); // The middle message of the quadrant - info about hand just dealt.
		scoreChangeInfoMessage.setFont(font);
		topLeftPanel.add(scoreChangeInfoMessage, BorderLayout.LINE_START);
		winLoseUpdate = new JLabel(winLoseMessage); // The bottom message of the quadrant - Win/lose message
		winLoseUpdate.setFont(font);
		topLeftPanel.add(winLoseUpdate, BorderLayout.PAGE_END);
	}

	void setRunningBalanceText(String balanceMessage) {
		balanceInfo.setText(balanceMessage);
	}

	void setCardCommentary(String update) {
		scoreChangeInfoMessage.setText(update);
	}

	void setWinOrLoseMessage(String winLoseMessage) {
		winLoseUpdate.setText(winLoseMessage);
	}

	private void createAndInitialiseCardJLabels(FruitMachine controller) {
		cards = new ArrayList<JLabel>();
		cards.add(new JLabel(controller.getInitialFirstCard()));
		cards.add(new JLabel(controller.getInitialSecondCard()));
		cards.add(new JLabel(controller.getInitialThirdCard()));
	}

	public void fontForCardJLabels() {
		cards.get(0).setFont(font); // Left card
		cards.get(1).setFont(font); // Middle card
		cards.get(2).setFont(font); // Right card
	}

	public void addJLabelsToCards() {
		leftCard.add(cards.get(0));
		middleCard.add(cards.get(1));
		rightCard.add(cards.get(2));
	}

	// Called from controller - assigns card types to the relevant JLabels:

	public void drawCards(String card1, String card2, String card3) {
		cards.get(0).setText(card1); // Left card
		cards.get(1).setText(card2); // Middle card
		cards.get(2).setText(card3); // Right card
	}

	JButton getNewGame() {
		return newGameButton;
	}
}
