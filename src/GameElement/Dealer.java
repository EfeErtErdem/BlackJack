package GameElement;

public class Dealer extends Player{

	public Dealer(String name, int bank) {
		super(name, bank);
	}
	
	public void play(Deck d) {
		
		Deck newDeck = new Deck(25);
		newDeck.addCard(this.deck.getDeck()[0].turnCard());
		newDeck.addCard(this.deck.getDeck()[1]);
		this.deck = newDeck;
		
		
		while (this.deck.calculateValueOfHand() <= 16) {
			System.out.println("The hand is lesser than 16, drawing...");
			this.addCard(d.throwCard());
			System.out.println("The value of the hand is " + this.deck.calculateValueOfHand());
		}
		if (this.deck.calculateValueOfHand() > 21) {
			System.out.println("Dealer has busted.");
		}
		else {
			System.out.println("Dealer holds");
		}
	}
}
