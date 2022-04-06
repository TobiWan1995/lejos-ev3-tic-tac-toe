package fieldGames_EV3.game;

public class GameField {

	private String[] fields; 
	private int x, y;
	private int lastField = 0;
	
	public GameField(int x, int y) {
		this.fields = new String[x * y];
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < this.fields.length ; i++)
			this.fields[i] = "FIELD" + i; 
	}
	
	public boolean setzeSpielstein(int index, String player) {
		if(!this.fields[index].isBlank() || index < 0) return false;
		this.fields[index] = player;
		this.lastField = index;
		return true;
	}
	
	public String[] getFields() {
		return this.fields;
	}
	
	public int getLastFieldIndex() {
		return this.lastField;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
