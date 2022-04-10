package fieldGames_EV3.game;
import fieldGames_EV3.graphics.Circle;
import fieldGames_EV3.graphics.Cross;
import fieldGames_EV3.graphics.Polygon;

public class TicTacToe extends FieldGame {

	public TicTacToe(int numberOfPlayers, int fieldSize) {
		this.gameField = new GameField(fieldSize, fieldSize);
		this.initPlayerFormMapping(numberOfPlayers);
	}
	
	@Override
	protected void initPlayerFormMapping(int numberOfPlayers) {
		try {
			PlayerEnumeration[] players = PlayerEnumeration.values();
			for(int i = 0; i < numberOfPlayers ; i++) {
				if(numberOfPlayers != 2)
					playerTokenMapping.put(players[i], (i == 0 ? new  Cross() : new Polygon(i + 2 >= 6 && i % 2 != 0 ? i + 3 : i + 2)));
				else
					playerTokenMapping.put(players[i], (i == 0 ? new Cross() : new Polygon(12)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String checkForWinner(String player) {		
		int lFI= this.getGameField().getLastFieldIndex();
		
		int x = this.getGameField().getX(); 
		int y = this.getGameField().getY(); 
		
		String [] field = this.gameField.getFields();
		
		int moduloX = lFI % x;
		int divY = lFI / y;

		// check horizontal +-1
		if(moduloX != 0 && moduloX != (x-1) && field[lFI+1].equals(player) && field[lFI-1].equals(player))
			return player;
		
		// check horizontal +-2	
		if((moduloX + 2) <= x-1) {
			if(field[lFI+1].equals(player) && field[lFI+2].equals(player))
				return player;
		} else if((moduloX - 2) >= 0) {
			if(field[lFI-1].equals(player) && field[lFI-2].equals(player))
				return player;
		}
		
		// check vertical +-1
		if(lFI >= y && lFI < (field.length-y) && field[lFI-y].equals(player) && field[lFI+y].equals(player))
			return player;
		
		// check vertical +-2
		if(lFI < field.length - 2*y) {
			if(field[lFI+y].equals(player) && field[lFI+(2*y)].equals(player))
				return player;
		} else if (lFI >= 2*y) {
			if(field[lFI-y].equals(player) && field[lFI-(2*y)].equals(player))
				return player;
		}
		
		// check diagonal +-1
		if(moduloX != 0 && moduloX != (x-1) && divY < (y-1) && divY > 0)
			if((field[lFI+(y+1)].equals(player) && field[lFI-(y+1)].equals(player)) ||
					(field[lFI+(y-1)].equals(player) && field[lFI-(y-1)].equals(player)))
						return player;
		
		// check diagonal +-2 (left to right)
		if(moduloX != 0 && moduloX != 1 && divY > 1) {
			// topleft
			if((field[lFI-(y+1)].equals(player) && field[lFI-2*(y+1)].equals(player))) 
				return player;
		} else if(moduloX != (x-1) && moduloX != (x-2) && divY < (y-2)){
			// bottomright
			if((field[lFI+(y+1)].equals(player) && field[lFI+2*(y+1)].equals(player))) 
				return player;
		}
		
		// check diagonal +-2 (right to left)
		if(moduloX != (x-1) && moduloX != (x-2) && divY > 1) {
			// topright
			if((field[lFI-(y-1)].equals(player) && field[lFI-2*(y-1)].equals(player))) 
				return player;
		} else if(moduloX != 0 && moduloX != 1 && divY < (y-2)) {
			// bottomleft
			if((field[lFI+(y-1)].equals(player) && field[lFI+2*(y-1)].equals(player))) 
				return player;
		}		
		
		// check for Tie
		for(int i = 0; i < field.length; i++) {
			if(field[i].toString().contains("FIELD")) break;
			if(i == field.length-1) return "Tie";
		}
			
		return "";
	}

	/* Tic Tac Toe AI with Minimax Algorithm
	 * The Coding Train / Daniel Shiffman
	 * https://thecodingtrain.com/CodingChallenges/154-tic-tac-toe-minimax.html
	 * https://youtu.be/I64-UTORVfU
	 * https://editor.p5js.org/codingtrain/sketches/0zyUhZdJD 
	 */	
	
	// SPIELER1 == Human ; SPIELER2 == Bot
	
	public int bestMove() {
		  // AI to make its turn
		  int bestScore = Integer.MIN_VALUE;
		  String[] field =  this.gameField.getFields();
		  int bestField = 0;
		  for (int i = 0; i < field.length; i++) {
		      // Is the spot available?
		      if (field[i].toString().contains("FIELD")) {
		        field[i] = "SPIELER2";
		        this.getGameField().setLastFieldIndex(i);
		        int score = minmax(field, 0, false, "SPIELER2");
		        field[i] = "FIELD" + i;
		        if (score > bestScore) {
		          bestScore = score;
		          bestField = i;
		        }
		    }
		  }
		  return bestField;
	}
	
	public int minmax(String[] field, int depth, boolean isMaximizing, String currentPlayer) {
		// check for winner or tie and return min, max or tie value
		String result = checkForWinner(currentPlayer);
		if(result.equals("SPIELER1")) return -10; 
		else if(result.equals("SPIELER2")) return 10;
		else if(result.equals("Tie")) return 0;
		
		if (isMaximizing) {
			int bestScore = Integer.MIN_VALUE;
			for (int i = 0; i < field.length; i++) {
			      // Is the spot available?
			      if (field[i].toString().contains("FIELD")) {
			        field[i] = "SPIELER2";
			        this.getGameField().setLastFieldIndex(i);
			        int score = minmax(field, depth + 1, false, "SPIELER2");
			        field[i] = "FIELD" + i;
			        bestScore = Math.max(score, bestScore);
			      }
			 }
		    return bestScore;
		  } else {
			int bestScore = Integer.MAX_VALUE;
		    for (int i = 0; i < field.length; i++) {
			      // Is the spot available?
			      if (field[i].toString().contains("FIELD")) {
			        field[i] = "SPIELER1";
			        this.getGameField().setLastFieldIndex(i);
			        int score = minmax(field, depth + 1, true, "SPIELER1");
			        field[i] = "FIELD" + i;
			        bestScore = Math.min(score, bestScore);
			      }
			 }
		    return bestScore;
		 }
	}
}
