package fieldGames_EV3.game;

import fieldGames_EV3.game.FieldGame;

public class TicTacToe extends FieldGame {

	public TicTacToe(int numberOfPlayers, int fieldSize) {
		this.gameField = new GameField(fieldSize, fieldSize);
		this.initPlayerFormsGeometric(numberOfPlayers);
	}
	
	@Override
	public boolean play() {
		if(this.lastPlayer == null) return true;
		
		int lFI= this.getGameField().getLastFieldIndex();
		
		int x = this.getGameField().getX(); 
		int y = this.getGameField().getY(); 
		
		String [] field = this.gameField.getFields();
		
		int moduloX = lFI % x;
		int divY = lFI / y;
		
		// check horizontal +-1
		if(moduloX != 0 && moduloX != (x-1) && field[lFI+1].equals(this.lastPlayer) && field[lFI-1].equals(this.lastPlayer))
			return false;
		// check horizontal +-2	
		if(moduloX == 0 || moduloX == 1) {
			if(field[lFI+1].equals(this.lastPlayer) && field[lFI+2].equals(this.lastPlayer))
				return false;
		} else {
			if(field[lFI-1].equals(this.lastPlayer) && field[lFI-2].equals(this.lastPlayer))
				return false;
		}
		// check vertical +-1
		if(lFI >= y && lFI < (field.length-y) && field[lFI-y].equals(this.lastPlayer) && field[lFI+y].equals(this.lastPlayer))
			return false;
		// check vertical +-2
		if(lFI <= (field.length - 2*y)) {
			if(field[lFI+y].equals(this.lastPlayer) && field[lFI+(2*y)].equals(this.lastPlayer))
				return false;
		} else if (lFI >= 2*y) {
			if(field[lFI-y].equals(this.lastPlayer) && field[lFI-(2*y)].equals(this.lastPlayer))
				return false;
		}
		// check diagonal +-1
		if(moduloX != 0 && moduloX != (x-1) && divY < (y-1) && divY > 0)
			if((field[lFI+(y+1)].equals(this.lastPlayer) && field[lFI-(y+1)].equals(this.lastPlayer)) ||
					(field[lFI+(y-1)].equals(this.lastPlayer) && field[lFI-(y-1)].equals(this.lastPlayer)))
						return false;
		// check diagonal +-2
		if(moduloX != 0 && moduloX != 1 && divY > 1) {
			if((field[lFI-(y+1)].equals(this.lastPlayer) && field[lFI-2*(y+1)].equals(this.lastPlayer))) 
				return false;
		} else {
			if((field[lFI+(y+1)].equals(this.lastPlayer) && field[lFI+2*(y+1)].equals(this.lastPlayer))) 
				return false;
		}
			
		return true;
	}
	
	// minMax
	
	// checkForBestTurn
	
	// ki (Player player)

}
