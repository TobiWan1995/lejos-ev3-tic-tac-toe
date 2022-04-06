package fieldGames_EV3.game;
import fieldGames_EV3.graphics.Circle;
import fieldGames_EV3.graphics.Mark;
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
					playerTokenMapping.put(players[i], (i == 0 ? new Mark() : new Polygon(2+i)));
				else
					playerTokenMapping.put(players[i], (i == 0 ? new Mark() : new Circle()));
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
			return "";
		// check horizontal +-2	
		if(moduloX == 0 || moduloX == 1) {
			if(field[lFI+1].equals(player) && field[lFI+2].equals(player))
				return "";
		} else {
			if(field[lFI-1].equals(player) && field[lFI-2].equals(player))
				return "";
		}
		// check vertical +-1
		if(lFI >= y && lFI < (field.length-y) && field[lFI-y].equals(player) && field[lFI+y].equals(player))
			return "";
		// check vertical +-2
		if(lFI <= (field.length - 2*y)) {
			if(field[lFI+y].equals(player) && field[lFI+(2*y)].equals(player))
				return "";
		} else if (lFI >= 2*y) {
			if(field[lFI-y].equals(player) && field[lFI-(2*y)].equals(player))
				return "";
		}
		// check diagonal +-1
		if(moduloX != 0 && moduloX != (x-1) && divY < (y-1) && divY > 0)
			if((field[lFI+(y+1)].equals(player) && field[lFI-(y+1)].equals(player)) ||
					(field[lFI+(y-1)].equals(player) && field[lFI-(y-1)].equals(player)))
						return "";
		// check diagonal +-2
		if(moduloX != 0 && moduloX != 1 && divY > 1) {
			if((field[lFI-(y+1)].equals(player) && field[lFI-2*(y+1)].equals(player))) 
				return "";
		} else {
			if((field[lFI+(y+1)].equals(player) && field[lFI+2*(y+1)].equals(player))) 
				return "";
		}
		
		// check for Tie
		for(int i = 0; i < field.length; i++) {
			if(field[i].isBlank()) break;
			if(i == field.length-1) return "Tie";
		}
			
		return player;
	}

	/* Tic Tac Toe AI with Minimax Algorithm
	 * The Coding Train / Daniel Shiffman
	 * https://thecodingtrain.com/CodingChallenges/154-tic-tac-toe-minimax.html
	 * https://youtu.be/I64-UTORVfU
	 * https://editor.p5js.org/codingtrain/sketches/0zyUhZdJD 
	 */	
	
	public int bestMove() {
		  // AI to make its turn
		  int bestScore = Integer.MIN_VALUE;
		  String[] field =  this.gameField.getFields();
		  int bestField = 0;
		  for (int i = 0; i < field.length; i++) {
		      // Is the spot available?
		      if (field[i].isBlank()) {
		        field[i] = "Spieler2";
		        int score = minmax(field, 0, false, "Spieler2");
		        field[i] = "";
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
		if(result.equals("Spieler1")) return -10; 
		else if(result.equals("Spieler2")) return 10;
		else if(result.equals("Tie")) return 0;
		
		if (isMaximizing) {
			int bestScore = Integer.MIN_VALUE;
			for (int i = 0; i < field.length; i++) {
			      // Is the spot available?
			      if (field[i].isBlank()) {
			        field[i] = "Spieler2";
			        int score = minmax(field, depth + 1, false, "Spieler2");
			        field[i] = "";
			        bestScore = Math.max(score, bestScore);
			      }
			 }
		    return bestScore;
		  } else {
			int bestScore = Integer.MAX_VALUE;
		    for (int i = 0; i < field.length; i++) {
			      // Is the spot available?
			      if (field[i].isBlank()) {
			        field[i] = "Spieler1";
			        int score = minmax(field, depth + 1, true, "Spieler1");
			        field[i] = "";
			        bestScore = Math.max(score, bestScore);
			      }
			 }
		    return bestScore;
		 }
	}
}
