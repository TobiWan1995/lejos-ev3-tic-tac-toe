package fieldGames_EV3.graphics;

import plott3r_V1.Roboter;
import positions.Position2D;
import positions.Position3D;

public class Cross implements EV3PrintableForm {

	@Override
	public void draw(double umfang, Roboter robo) {
		this.adjustPositionXForMark(10, robo);
		this.drawVerticalLine(20, true, robo);
		this.adjustPositionXForMark(20, robo);
		this.drawVerticalLine(20, false, robo);
	}
	
	public void adjustPositionXForMark(int amount, Roboter robo) {
		// move to accordingly position in field 
		double positionX = robo.getCurrentPosition().getX() + (3*amount);
		try {
			robo.moveToPosition(new Position3D(positionX, robo.getCurrentPosition().getY(), false), 75);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawVerticalLine(int amount, boolean invertY, Roboter robo) {
		// move to accordingly position in field 
				double positionX = robo.getCurrentPosition().getX() - (3*amount);
				double positionY = robo.getCurrentPosition().getY() - (invertY ? amount : - amount);
				try {
					robo.moveToPosition(new Position3D(positionX, positionY, true), 75);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
