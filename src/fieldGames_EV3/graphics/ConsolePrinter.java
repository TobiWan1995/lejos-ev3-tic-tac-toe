package fieldGames_EV3.graphics;

import fieldGames_EV3.game.GameField;

public class ConsolePrinter extends Printer {

	@Override
	public void printGameField(GameField gameField) {
		String [] field = gameField.getFields();
		for(int i = 0; i < field.length; i++) {
			if((i+1) % 4 == 0) System.out.println("\n");
			System.out.println(field[i]);
		}
	}

}
