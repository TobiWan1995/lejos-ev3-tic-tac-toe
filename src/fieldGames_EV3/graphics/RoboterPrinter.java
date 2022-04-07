package fieldGames_EV3.graphics;

import fieldGames_EV3.game.GameField;
import plott3r_V1.Roboter;
import positions.Position2D;
import positions.Position3D;

public class RoboterPrinter extends Printer {
	
	private Roboter robo;

	private double skalarX;
	private double skalarY;
	
	public RoboterPrinter(double skalar, Roboter robo){
		this.skalarY = skalar; 
		// X moves only 1/3 of Y
		this.skalarX = 3*skalar;
		this.robo = robo;
	}
	
	@Override
	public void printGameField(GameField gameField) {
		try {		
			// draw field y
			this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX() , this.robo.getCurrentPosition().getY() + gameField.getY() * -skalarY, true), 75);
			for(int i = 0; i < gameField.getY(); i++) {
				this.skalarY = -this.skalarY; // to switch direction for Y
				this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX() - skalarX , this.robo.getCurrentPosition().getY(), true), 50);
				this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX(), this.robo.getCurrentPosition().getY() + gameField.getY() * -skalarY, true), 75);
			}
			
			// draw field x
			this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX() + gameField.getX() * skalarX, this.robo.getCurrentPosition().getY(), true), 75);
			for(int j = 0; j < gameField.getX(); j++) {
				this.skalarX = -this.skalarX; // to switch direction for X
				this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX(), this.robo.getCurrentPosition().getY() + skalarY, true), 50);
				this.robo.moveToPosition(new Position3D(this.robo.getCurrentPosition().getX() + gameField.getX() * skalarX, this.robo.getCurrentPosition().getY(), true), 75);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void drawFormIntoField(int index, EV3PrintableForm form, GameField gamefield) {
		// leave space to draw into field
		double radius = skalarY/3;
		// lift up pencil
		this.robo.getZAchse().deaktiviere();
		// move to accordingly position on field
		double positionX = (this.robo.getCurrentPosition().getX() - (index%gamefield.getY())*this.skalarX);
		double positionY = (this.robo.getCurrentPosition().getY() - (index/gamefield.getY())*this.skalarY);
		try {
			this.robo.moveToPosition(new Position2D(positionX + this.robo.getCurrentPosition().getX(), positionY + this.robo.getCurrentPosition().getY()), 75);
			// move into field to start drawing
			this.robo.moveToPosition(new Position2D(this.robo.getCurrentPosition().getX() - (skalarX/2), this.robo.getCurrentPosition().getY() - (skalarY/6)), 75);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// leave 10mm space in each field for form
		form.draw(radius, this.robo);
	}
	
	public void removePaper() {
		try {
			this.robo.entfernePapier();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeStartPosition() {
		// initialize position and printer
		try {
			this.robo.bereitePapierVor();
			this.robo.moveToHomePosition();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
