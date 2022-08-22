package Game;

import GameElement.*;
import java.util.Scanner;

public class GamePlay {
	
	public static void main(String[] args) {
		
		boolean playing = true;
		Scanner input = new Scanner(System.in);
		char answer = '\0';
		
		while (true) {
			
			Game game = new Game();
			playing = true;
		
			while (playing) {
			
				game.placeBets();
				game.dealHands();
				game.show();
				game.hitOrStand();
				game.settleBets();
			
				if(game.mandatoryEndCheck()) {
					System.out.println("All the players have lost. The game has ended.");
					break;
				}
			
				do {
					System.out.println("Do you want to play another round? (Y)es/(N)o");
					answer = input.next().toUpperCase().charAt(0);
				} while (answer != 'Y' && answer != 'N');
			
				if (answer == 'N') {
					playing = false;
					System.out.println("This game has been ended by the players. The values of players are: ");
					System.out.println();
					for (Player p : game.getPlayers()) {
						System.out.println(p.getName() + ": " + p.getBank() + ".");
						System.out.println();
					}
				}
				
				game.clearHands();
			}
	
			do {
				System.out.println("Do you want to start a new game? (Y)es/(N)o");
				answer = input.next().toUpperCase().charAt(0);
			} while (answer != 'Y' && answer != 'N');
			
			if (answer == 'N') {
				System.out.println("Thanks for playing.");
				break;
			}
			
			for (int i = 0; i < 20; i++) {
				System.out.println();
			}
		}
		input.close();
	}
}
