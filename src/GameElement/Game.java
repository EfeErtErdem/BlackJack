package GameElement;

import java.util.Scanner;

public class Game {
	
	private Player[] players;
	private Dealer dealer;
	private int users;
	private Scanner input = new Scanner(System.in);
	private Deck floorDeck;
	
	public Game() {
		
		System.out.println("*******************************");
		System.out.println("*Welcome to the blackjack game*");
		System.out.println("*******************************");
		
		this.floorDeck = new Deck(312);
		
		String[] shapes = {"Heart", "Spade", "Diamond", "Club"};
		String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 52; j++) {
				floorDeck.addCard(new Card(shapes[j / 13], values[j % 13]));
			}
		}
		
		floorDeck.shuffleDeck();
		
		//input checking with do while
		do {
			System.out.println("How many players are playing");
			this.users = input.nextInt();
		} while (users < 0 || users > 6);
		
		this.players = new Player[users];
		
		for (int i = 0; i < users; i++) {
			System.out.println("What is player " + (i+1) + "'s name?");
			String playerName = input.next();
			players[i] = new Player(playerName, 100);
			
		}
		dealer = new Dealer("Dealer", 1000);
	}
	
	public int getUsers() {
		return users;
	}
	
	public Player[] getPlayers() {
		return players;
	}

	public void placeBets() {
		
		int playerBet;
		
		for (int i = 0; i < users; i++) {
			if (players[i].getBank() > 0) {
				do {
				System.out.println("What is " + players[i].getName() + "'s bet? (1 - " + players[i].getBank() + ")");
				playerBet = input.nextInt();
				players[i].placeBet(playerBet);
				} while(!(playerBet > 0 && playerBet <= players[i].getBank()));
			}
		}
	}
	
	public void dealHands() {
		
		System.out.println("Dealing...");
		
		for (int i = 0; i < 2; i++) {
			for (Player p : this.players) {
				if (p.getBank() > 0) {
					p.addCard(floorDeck.throwCard());
				}
			}
		}
		dealer.addCard(floorDeck.throwCard().turnCard());
		dealer.addCard(floorDeck.throwCard());
	}
	
	public void show() {
		
		for (int i = 0;i < 10; i++) {
			System.out.println();
		}
		
		for (Player p : players) {
			if (p.getBank() > 0) {
				System.out.println(p.getName() + "'s hand:");
				System.out.println(p.deck);
				System.out.println("( " + p.getName() + "'s bet:" + p.getBet() + " |*| " + p.getName() + "'s bank: " + p.getBank() + " )");
				System.out.println("---------------------------------");
				System.out.println();
			}
		}
		System.out.println("Dealer's hand: " + dealer.deck);
	}
	
	public void checkForBlackjack() {
		
		System.out.println();
		
		if (dealer.isBlackJack()) {
			System.out.println("Dealer has blackjack!");
			for (Player p : this.players) {
				if (p.isBlackJack()) {
					p.push();
				}
				else {
					p.lose();
				}
			}
		}
		else {
			for (Player p : this.players) {
				if (p.isBlackJack()) {
					System.out.println(p.getName() + " has blackjack!");
					p.blackjack();
					p.bet = -1;
				}
			}
		}
	}
	
	public void hitOrStand() {
		
		System.out.println();
		
		for (Player p : this.players) {
			char command = '\0';
			if (p.getBet() > 0) {
				while (command != 'S' && p.deck.calculateValueOfHand() <= 21) {
					do {
						System.out.println(p.getName() + ", do you want to (H)it or (S)tay?");
						command = input.next().toUpperCase().charAt(0);
					} while(command != 'H' && command != 'S');
					if (command == 'H') {
						p.addCard(floorDeck.throwCard());
						this.show();
					}
					else {
						this.show();
					}
				}
			}
		}
	}
	
	public void settleBets() {
		
		System.out.println();
		System.out.println();
		dealer.play(floorDeck);
		System.out.println();
		System.out.println();
		
		for (Player p : this.players) {
			if (dealer.isBlackJack() && p.getBet() > 0) {	
				if (p.isBlackJack()) {
					System.out.println(p.getName() + " has blackjack and pushes.");
					p.push();
				}
				else {
					System.out.println(p.getName() + " loses.");
					p.lose();
				}
			}
			else if (dealer.deck.calculateValueOfHand() < 21 && p.getBet() > 0){
				if (p.isBlackJack()) {
					System.out.println(p.getName() + " has blackjack and wins.");
					p.blackjack();
				}
				else if (p.deck.calculateValueOfHand() > dealer.deck.calculateValueOfHand() && p.deck.calculateValueOfHand() < 21) {
					System.out.println(p.getName() + " wins.");
					p.win();
				}
				else if (p.deck.calculateValueOfHand() == dealer.deck.calculateValueOfHand() && p.deck.calculateValueOfHand() < 21) {
					System.out.println(p.getName() + " pushes");
					p.win();
				}
				else {
					System.out.println(p.getName() + " loses.");
					p.lose();
				}
			}
			else if (p.getBet() > 0){
				if (p.deck.calculateValueOfHand() < 21) {
					System.out.println(p.getName() + " wins.");
					p.win();
				}
				else if (p.isBlackJack()) {
					System.out.println(p.getName() + " has blackjack and wins.");
					p.blackjack();
				}
				else {
					System.out.println(p.getName() + " loses.");
					p.lose();
				}
			}
		}
		this.show();
	}
	
	public boolean mandatoryEndCheck() {
		
		boolean allPlayersHaveLost = true;
		
		for (Player p: this.players) {
			if (p.getBank() > 0) {
				allPlayersHaveLost = false;
			}
		}
		return allPlayersHaveLost;
	}
	
	public void clearHands() {
		
		dealer.clearHand();
		
		for (Player p: this.players) {
			p.clearHand();
		}
	}
}
