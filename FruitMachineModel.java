//Jeff Waters - Student id: 2495819 - MSc Software Development 

/* FruitMachineModel is the model class in the model-view-controller paradigm. 
 * 
 * It stores the current balance and what type of card relates to each of the three cards.
 * 
 * It sets the balance to 100 prior to the start of a new game (both when the program is loaded up and when 'new game' is pressed).  It updates the 
 * balance after each 'spin'.  
 * 
 * At the start of the game, it allocates card types of King, Queen, Jack to the left, middle and right hand cards respectively. It allocates randomly 
 * chosen new cards in response to 'spins'. 
 * 
 * It has getter methods to provide the card and 'message on screen' information to the controller (which the controller - FruitMachine - uses to update 
 * the view - FruitMachineView - as the model does not communicate directly with the view). 
 * 
 * In addition, the model enables the controller to check whether the game is still ongoing and, if not, whether the game has been won or lost.. 
 */

import java.util.ArrayList;
import java.util.Random;

public class FruitMachineModel {

	private int balance;
	private ArrayList<String> typeOfCard;
	private String firstCardType, secondCardType, thirdCardType;

	FruitMachineModel() {
		setupCardTypeList();
		balance = 100; // Specification states initial balance as 100
	}

	int getBalance() {
		return balance;
	}

	// Fills typeOfCard with card names:

	void setupCardTypeList() {
		typeOfCard = new ArrayList<String>();
		typeOfCard.add("Ace");
		typeOfCard.add("King");
		typeOfCard.add("Queen");
		typeOfCard.add("Jack");
		typeOfCard.add("Joker");
	}

	/*
	 * The method below sets balance to 100. It is called after 'new game' is
	 * clicked.
	 */

	void setNewGameBalance() {
		balance = 100;
	}

	private String dealSingleCard() {
		Random rand = new Random();
		int randomIntegerBetweenZeroAndFour = rand.nextInt(5);
		return typeOfCard.get(randomIntegerBetweenZeroAndFour);
	}

	// Deals new cards and updates the balance:

	public void dealNewCards() {
		firstCardType = dealSingleCard();
		secondCardType = dealSingleCard();
		thirdCardType = dealSingleCard();
		balance += getChangeOfScore();
	}

	private int getNumberOfJokersInCurrentHand() {
		int joker = 0;
		if (firstCardType == typeOfCard.get(4))
			joker++;

		if (secondCardType == typeOfCard.get(4))
			joker++;

		if (thirdCardType == typeOfCard.get(4))
			joker++;
		return joker;
	}

	// Calculates change of score after a new hand is dealt:

	private int getChangeOfScore() {

		if (getNumberOfJokersInCurrentHand() == 1)
			return -25;
		if (getNumberOfJokersInCurrentHand() == 2)
			return -50;
		if (getNumberOfJokersInCurrentHand() == 3)
			return -75;
		if (getNumberOfJokersInCurrentHand() > 0)
			return 0;

		if (firstCardType.equals(secondCardType) && secondCardType.equals(thirdCardType))
			return 50;

		if (firstCardType.equals(secondCardType) || firstCardType.equals(thirdCardType)
				|| secondCardType.equals(thirdCardType))
			return 20;

		return 0;
	}

	boolean hasWon() {
		return (balance >= 150);
	}

	boolean isGameOver() {
		if (balance >= 150 || balance < 0)
			return true;

		return false;
	}

	public String getCardType(int number) {
		if (number == 0)
			return firstCardType;
		if (number == 1)
			return secondCardType;
		return thirdCardType;
	}

	String getStartOfGameLeftCard() {
		return typeOfCard.get(1);
	}

	String getStartOfGameMiddleCard() {
		return typeOfCard.get(2);
	}

	// In method below, 'right' in the name refers to right hand side, not right as
	// in 'correct':

	String getStartOfGameRightCard() {
		return typeOfCard.get(3);
	}

	// 'Card commentary' messages:

	String getScoreMessages() {
		if (getChangeOfScore() == -25)
			return "1 joker: you lose 25 points";
		if (getChangeOfScore() == -50)
			return "2 jokers: you lose 50 points";
		if (getChangeOfScore() == -75)
			return "3 jokers: you lose 75 points";
		if (getChangeOfScore() == 50)
			return "Three of a kind - you win 50 points";
		if (getChangeOfScore() == 20)
			return "Two of a kind - you win 20 points";
		return "Balance unchanged";
	}

	String getScoreCommentaryFollowingNewHand() {
		return getScoreMessages();
	}

	String getWinMessage() {
		return "You win!";
	}

	String getLossMessage() {
		return "You lose";
	}

	String getBalanceMessage() {
		return "balance is " + getBalance();
	}

	String getInitialBalanceMessage() {
		return "balance is 100";
	}

	String getInitialWelcomeMessage() {
		return "welcome!";
	}

	String getEmptyStringToDeleteWinOrLoseMessage() {
		return "";
	}

}