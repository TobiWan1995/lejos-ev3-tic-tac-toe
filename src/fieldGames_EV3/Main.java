 package fieldGames_EV3;

import java.util.ArrayList;
import java.util.Arrays;

import fieldGames_EV3.game.FieldGame;
import fieldGames_EV3.game.PlayerEnumeration;
import fieldGames_EV3.game.TicTacToe;
import fieldGames_EV3.graphics.ConsolePrinter;
import fieldGames_EV3.graphics.Printer;
import fieldGames_EV3.graphics.RoboterPrinter;
import plott3r_V1.Roboter;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.TextMenu;

public class Main {
	// skalar complies size of GameField in mm for every x and y
	private static final RoboterPrinter roboPrinter = new RoboterPrinter(30, new Roboter());
	private static final  ConsolePrinter consolePrinter = new ConsolePrinter();
	
	// initialize Game with desired type
	private static FieldGame game;
	
	// to Display and select field on lejosLCD 
	private static TextMenu textMenu;
	
	public static void main(String[] args) {
		playTicTacToe();
	} 
	
	public static void playTicTacToe() {
		// select game mode
		LCD.drawString("Select Gamemod!", 0, 0);
		textMenu = new TextMenu(new String[] {"Player vs. Player", "Player vs. Bot"});
		int gameMode = textMenu.select();
		LCD.clear();
		
		// create game based on game mode
		if(gameMode == 0) {
			// select Number of Players
			LCD.drawString("Select players!", 0, 0);
			String[] numberOfPlayersArray = new String[PlayerEnumeration.values().length - 1];
			for(int i = 2; i <= PlayerEnumeration.values().length; i++)
				numberOfPlayersArray[i-2] = String.valueOf(i);
			textMenu = new TextMenu(numberOfPlayersArray);
			int numberOfPlayers = Integer.valueOf(numberOfPlayersArray[textMenu.select()]);
			LCD.clear();			
			
			// initialize roboPrinter - startPosition
			roboPrinter.initializeStartPosition();
			
			// initialize game -> fieldSize is numberOfPlayers+1
			game = new TicTacToe(numberOfPlayers, numberOfPlayers + 1);
		}
		else {
			game = new TicTacToe(2, 3);
		}
		
		// initialize roboPrinter - startPosition
		roboPrinter.initializeStartPosition();
		
		// print the empty gameField
		roboPrinter.printGameField(game.getGameField());
		
		String winner = "";
			
		while(winner.isBlank()) {		
			// iterate through every players turn
			for(PlayerEnumeration player : game.getPlayersWithToken().keySet()) {
				boolean validPull = false;
				int selectedField = -1;
			
				if(gameMode == 0 || player.toString().equals("Player1")) {
					// display current gameField for selection
					textMenu = new TextMenu(game.getGameField().getFields());
					// let the player pick a valid Field
					while(!validPull) {
						selectedField = textMenu.select();
						validPull = game.getGameField().setzeSpielstein(selectedField, player.toString());
					}
				} else {
					selectedField = ((TicTacToe) game).bestMove(); 
				}
				
				// draw players token into selected field of gameField
				roboPrinter.drawFormIntoField(selectedField, game.getPlayersWithToken().get(player), game.getGameField());
				
				// print gameField on console
				consolePrinter.printGameField(game.getGameField());
				
				winner = ((TicTacToe) game).checkForWinner(player.toString());
				
				if(!winner.isBlank()) break;
			}
		}
		
		roboPrinter.removePaper();
		System.out.println(winner.equals("Tie") ? winner : winner + "won the game!");
	}
	
}
