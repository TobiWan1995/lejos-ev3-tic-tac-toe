/**
 * 
 */
package fieldGames_EV3.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;

import fieldGames_EV3.graphics.Circle;
import fieldGames_EV3.graphics.EV3PrintableForm;
import fieldGames_EV3.graphics.Mark;
import fieldGames_EV3.graphics.Polygon;

public abstract class FieldGame {
	
	protected GameField gameField;
	protected EnumMap<PlayerEnumeration, EV3PrintableForm> playerFormMapping = new EnumMap<>(PlayerEnumeration.class);
	protected String lastPlayer = null;
	
	// for basic fieldGames like TicTacToe or FourWins
	protected void initPlayerFormsGeometric(int numberOfPlayers) {
		try {
			PlayerEnumeration[] players = PlayerEnumeration.values();
			for(int i = 0; i < numberOfPlayers ; i++) {
				if(numberOfPlayers != 2)
					playerFormMapping.put(players[i], (i == 0 ? new Mark() : new Polygon(2+i)));
				else
					playerFormMapping.put(players[i], (i == 0 ? new Mark() : new Circle()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	public abstract boolean play();
	
	public GameField getGameField() {
		return this.gameField;
	}
	
	public EnumMap<PlayerEnumeration, EV3PrintableForm> getPlayersWithFormMapping() {
		return this.playerFormMapping;
	}
	
	public String getLastPlayer() {
		return this.lastPlayer;
	}
	
	public void setLastPlayer(String lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
}
