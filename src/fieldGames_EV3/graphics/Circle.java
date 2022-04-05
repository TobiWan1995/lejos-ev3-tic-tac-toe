package fieldGames_EV3.graphics;

import plott3r_V1.Roboter;
import positions.Position3D;

public class Circle implements EV3PrintableForm {

	@Override
	public void draw(double radius, Roboter robo) {
		double winkelSchritt = 2*Math.PI/100;
		double positionX, positionY, pX1, pY1;
		try {
			for (int i = 0; i < 100; i++) {
				pX1 = 3*((radius * Math.sin(i * winkelSchritt)) - (radius * Math.sin((i+1) * winkelSchritt)));
				pY1 = (radius * Math.cos(i * winkelSchritt)) - (radius * Math.cos((i+1) * winkelSchritt));
				
				positionX = robo.getCurrentPosition().getX() - pX1;
				positionY = robo.getCurrentPosition().getY() - pY1;
				
				robo.moveToPosition(new Position3D(positionX , positionY, i != 0), 25);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
