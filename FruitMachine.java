/*  FruitMachine is the controller class in the model-view-controller paradigm.
 * 
 * When the game is first loaded up, the view obtains from the controller the initial 3 card types (King, Queen and Jack) and the initial messages ('balance 
 * is 100' and 'welcome'). In turn, the controller obtains that data from the model. 
 * 
 * The controller responds to action events, caused either by the 'spin' or 'new game' button being pressed and triggering the actionPerformed method 
 * (only one of the buttons is an engaged state and therefore responsive to clicks at any given time). When an action event occurs, the controller sends
 * instructions to the model (FruitMachineModel) and the view (FruitMachineView) in order to ensure that the required actions are carried out. When it 
 * sends a request for the text on screen to be updated, it uses data  stored in the model and sends it to the view.
 * 
 * When the 'spin' button is engaged (which always is, except when the game is over), if it is clicked on, that causes the controller gets the model to 
 * randomly allocate card types to each of the three cards and to update the score based on the freshly dealt cards. The controller also checks 
 * with the model whether the game is over, and, if it is, whether there has been a winner or a loser. 
 * 
 * If the game is still ongoing, the controller gets the view to post the balance  on screen and commentary about the hand, using messages and balance
 * information retrieved from the model. If 'spin' is subsequently pressed again, the process described above is repeated. 
 * 
 * If the game is over, the controller gets the view to disengage the 'spin' button (and engage the 'new game' button), and gets the view to post 
 * information about whether the player won or lost, the balance, and the commentary on game's final hand. If 'new game' is subsequently clicked, the 
 * controller gets the model to reset the balance to 100 and to set the initial cards to King, Queen and Joker. In addition, it gets the view to 
 * display the balance, display a welcome message, clear the the win/lose message by replacing it with "", and to disengage the new game button 
 * and engage the spin button. 
 * 
 * The controller contains a main method which creates a new FruitMachine object. This causes the FruitMachine() 
 * constructor to run, which in turn creates objects of type FruitMachineModel and FruitMachineView, which in turn cause the constructors of 
 * the corresponding classes to run. 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FruitMachine implements ActionListener {

	private FruitMachineModel model;
	private FruitMachineView view;

	FruitMachine() {
		model = new FruitMachineModel();
		view = new FruitMachineView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getNewGame())
			newGameClicked();
		else
			spinClicked();
	}

	// Gets the balance message from the model and gets the view to post it.

	private void BalanceMessage() {
		String messageForView = model.getBalanceMessage();
		view.setRunningBalanceText(messageForView);
	}

	// Actions to be taken when 'New Game' is clicked:

	private void newGameClicked() {
		model.setNewGameBalance();
		BalanceMessage();
		view.drawCards(model.getStartOfGameLeftCard(), model.getStartOfGameMiddleCard(),
				model.getStartOfGameRightCard()); // Makes the cards initially king, queen and joker respectively.
		view.setCardCommentary(getWelcomeMessage());
		view.setWinOrLoseMessage(getEmptyStringToRemoveWinLoseMessagel());// To clear 'You win!' or 'You lose' messages
		view.setRunningState(true); // Enables the spin button and disables the new game button.
	}

	String scoreMessage() {
		return model.getScoreCommentaryFollowingNewHand();
	}

	private void spinClicked() {
		model.dealNewCards();
		BalanceMessage();
		view.drawCards(model.getCardType(0), model.getCardType(1), model.getCardType(2));
		view.setCardCommentary(scoreMessage());

		if (model.isGameOver()) {
			view.setRunningState(false);

			if (model.hasWon())
				view.setWinOrLoseMessage(model.getWinMessage());
			else
				view.setWinOrLoseMessage(model.getLossMessage());
		}
	}

	String getStartingBalanceMessage() {
		return model.getInitialBalanceMessage();
	}

	String getWelcomeMessage() {
		return model.getInitialWelcomeMessage();
	}

	String getEmptyStringToRemoveWinLoseMessagel() {
		return model.getEmptyStringToDeleteWinOrLoseMessage();
	}

	String getInitialFirstCard() {
		return model.getStartOfGameLeftCard();
	}

	String getInitialSecondCard() {
		return model.getStartOfGameMiddleCard();
	}

	String getInitialThirdCard() {
		return model.getStartOfGameRightCard(); // In name, 'right' refers to right hand side, not 'correct'.
	}

	public static void main(String[] args) {
		new FruitMachine();
	}

}