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
		int numberOfPlayers;
		String[] gameModes = new String[] {"Player vs. Player", "Player vs. Bot"};
		// select game mode
		LCD.drawString("Select Gamemod!", 0, 0);
		textMenu = new TextMenu(gameModes);
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
			numberOfPlayers = Integer.valueOf(numberOfPlayersArray[textMenu.select()]);
			LCD.clear();			
			
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
			
		while(winner.equals("")) {		
			// iterate through every players turn
			for(PlayerEnumeration player : game.getPlayersWithToken().keySet()) {
				boolean validMove = false;
				int selectedField = -1;
			
				if(gameMode == 0 || player.toString().equals("SPIELER1")) {
					// display current gameField for selection
					textMenu = new TextMenu(game.getGameField().getFields());
					// let the player pick a valid Field
					while(!validMove) {
						selectedField = textMenu.select();
						validMove = game.getGameField().setzeSpielstein(selectedField, player.toString());
					}
				} else {
					selectedField = ((TicTacToe) game).bestMove(); 
					game.getGameField().setzeSpielstein(selectedField, player.toString());
				}
				
				// draw players token into selected field of gameField
				roboPrinter.drawFormIntoField(selectedField, game.getPlayersWithToken().get(player), game.getGameField());
				
				winner = ((TicTacToe) game).checkForWinner(player.toString());
				
				if(!winner.equals("")) break;
			}
		}
		
		roboPrinter.removePaper();
		LCD.clear();
		LCD.drawString(winner.equals("Tie") ? winner : winner + " won!", 0, 0);
		Button.ENTER.waitForPress();
	}
	
}
