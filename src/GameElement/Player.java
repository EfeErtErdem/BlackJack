package GameElement;

public class Player {
	
	protected String name;
	protected Deck deck;
	protected int bank;
	protected int bet;
	
	public Player(String name, int bank) {
		this.name = name;
		this.bank = bank;
		
		this.bet = 0;
		this.deck = new Deck(25);	
		}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public int getBet() {
		return bet;
	}

	public String getName() {
		return name;
	}
	
	public boolean isBlackJack() {
		if (deck.calculateValueOfHand() == 21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void placeBet(int bet) {
		this.bet = bet;
	}
	
	public void win() {
		this.bank += this.bet;
		this.bet = 0;
	}
	
	public void lose() {
		this.bank -= this.bet;
		this.bet = 0;
	}
	
	public void blackjack() {
		this.bank += 1.5 * this.bet;
		this.bet = 0;
	}
	
	public int getHandValue() {
		return deck.calculateValueOfHand();
	}
	
	public void clearHand() {
		this.deck.clearHand();
	}
	
	public void addCard(Card c) {
		this.deck.addCard(c);
	}
	
	public void push() {
		this.bet = 0;
	}
}
