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
	// skalar complies size of GameField in mm of every x and y
	private static final RoboterPrinter roboPrinter = new RoboterPrinter(30, new Roboter());
	private static final  ConsolePrinter consolePrinter = new ConsolePrinter();
	
	// initialize Game with desired type
	private static FieldGame game;
	
	// to Display and select field on lejosLCD 
	private static TextMenu textMenu;
	
	public static void main(String[] args) {
		// select Number of Players
		LCD.drawString("Select players!", 0, 0);
		String[] numberOfPlayersArray = new String[PlayerEnumeration.values().length - 1];
		for(int i = 2; i <= PlayerEnumeration.values().length; i++)
			numberOfPlayersArray[i-2] = String.valueOf(i);
		textMenu = new TextMenu(numberOfPlayersArray);
		int numberOfPlayers = Integer.valueOf(numberOfPlayersArray[textMenu.select()]);
		LCD.clear();
		
		// select Number of Bots
		LCD.drawString("Select bots!", 0, 0);
		String[] numberOfBotsArray = new String[numberOfPlayers];
		for(int i = 0; i < numberOfPlayers; i++)
			numberOfBotsArray[i] = String.valueOf(i);
		textMenu = new TextMenu(numberOfBotsArray);
		int numberOfBots = Integer.valueOf(numberOfBotsArray[textMenu.select()]);
		LCD.clear();

		// initialize game -> fieldSize is numberOfPlayers+1
		game = new TicTacToe(numberOfPlayers, numberOfPlayers + 1);
		
		// initialize roboPrinter - startPosition
		roboPrinter.initializeStartPosition();
		
		// print the empty gameField
		// roboPrinter.printGameField(game.getGameField());
		
		boolean play = true;
		
		while(play) {		
			// iterate through every players turn
			for(PlayerEnumeration player : game.getPlayersWithFormMapping().keySet()) {
				
				// display current gameField for selection
				textMenu = new TextMenu(game.getGameField().getFields());
				
				boolean validPull = false;
				int selectedField = -1;
				
				// let the player pic a valid Field
				while(!validPull) {
					selectedField = textMenu.select();
					validPull = game.getGameField().setzeSpielstein(selectedField, player.toString());
				}
				
				// draw players form/figure into selected field of gameField
				roboPrinter.drawFormIntoField(selectedField, game.getPlayersWithFormMapping().get(player), game.getGameField());
				
				// print gameField on console
				// consolePrinter.printGameField(game.getGameField());
				
				// set last players turn - to determine who the winner
				game.setLastPlayer(player.toString());
				
				play = game.play();
				
				if(!play) break;
			}
		}
		
		roboPrinter.removePaper();
		System.out.println(game.getLastPlayer() + "won the game!");
	} 
	
}
