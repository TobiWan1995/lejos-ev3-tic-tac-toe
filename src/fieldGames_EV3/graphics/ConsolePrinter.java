package fieldGames_EV3.graphics;

import fieldGames_EV3.game.GameField;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;


// redirect output to eclipse console -> currently ev3 console
public class ConsolePrinter extends Printer {

	@Override
	public void printGameField(GameField gameField) {
		String [] field = gameField.getFields();
		for(int i = 0; i < field.length; i++) {
			System.out.println(field[i]);
			if((i+1) % gameField.getX() == 0) System.out.println("\n");
		}
	}
	
	public void printGameSettings(int numberOfPlayers,  int fieldSize, String gameMode) {
		System.out.printf(" Players: %d \n FieldSize: %d * %d \n GameMode: %s", numberOfPlayers, fieldSize, fieldSize, gameMode);
	}

}
