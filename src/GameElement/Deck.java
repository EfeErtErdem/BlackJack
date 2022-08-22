package GameElement;

import java.security.SecureRandom;

public class Deck {
	
	private final int LENGTH;
	private Card[] deck;
	
	
	public Deck(int LENGTH) {
		this.LENGTH = LENGTH;
		this.deck = new Card[LENGTH];
	}

	public int getLENGTH() {
		return LENGTH;
	}

	public Card[] getDeck() {
		return deck;
	}
	
	public void addCard(Card card) {
		
		for (int i = 0; i < LENGTH; i++) {
			if (deck[i] == null) {
				deck[i] = card;
				return;
			}
		}
		System.out.println("This deck is full");	
	}
	
	public Card throwCard() {
		Card throwCard = null;
		
		if (this.deck[0] == null) {
			System.out.println("This deck is empty.");
			return throwCard;
		}
		else if (deck[LENGTH - 1] != null) {
			throwCard = deck[LENGTH - 1];
			deck[LENGTH - 1] = null;
			return throwCard;
		}
		else {
			for (int i = 0; i < LENGTH; i++) {
				if(deck[i] == null) {
					throwCard = deck[i-1];
					deck[i-1] = null;
					return throwCard;
				}
			}
		}
		
		return throwCard;
	}
	
	public void shuffleDeck() {
			SecureRandom randomIndex = new SecureRandom();
			
			for (int i = 0; i < LENGTH; i++) {
				int newIndex = randomIndex.nextInt(LENGTH);
				Card temp = new Card("","");
				
				temp = deck[i];
				deck[i] = deck[newIndex];
				deck[newIndex] = temp;
			}
		
	}
	
	@Override
	public String toString() {
		
		String deckString = "[ ";
		for (Card c : deck) {
			if (c != null) {
				deckString += c.toString() + " "; 
			}
		}
		deckString += "]";
		return deckString;
		
	}
	
	public int calculateValueOfHand() {
		
		int total = 0;
		boolean aceFound = false;
		
		for (Card c : deck) {
			if (c != null) {
				if (c.isHonors()) {
					total += 10;
				}
				else if (c.isAce()) {
					total += 1;
					aceFound = true;
				}
				else {
					total += Integer.parseInt(c.getValue());
				}
			}
		}
		if(aceFound) {
			if (total + 10 <= 21) {
				total += 10;
			}
		}
		return total;
		
	}
	
	public void clearHand() {
		for (int i = 0; i < LENGTH; i++) {
			deck[i] = null;
		}
		
	}
}
