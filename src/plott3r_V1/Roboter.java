package plott3r_V1;

import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import positions.Position2D;
import positions.Position3D;

public class Roboter {
//	public static void main(String args[]) {
//		try {
//			Roboter roboter = new Roboter();
//			Sound.beep();
//			roboter.moveToHomePosition();
//			roboter.bereitePapierVor();
//
//			Delay.msDelay(1000);
//			roboter.entfernePapier();
//			roboter.moveToHomePosition();
//			Sound.twoBeeps();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private Position3D currentPosition;

	private MultiPositionAchse xAchse = new MultiPositionAchse(new TouchSensor(SensorPort.S1), MotorPort.A,
			Einbaurichtung.UMGEKEHRT, new Reifen(40.0),
			new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_MITTEL), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_MITTEL)));
	private MultiPositionAchse yAchse = new MultiPositionAchse(new LichtSensor(SensorPort.S3), MotorPort.B,
			Einbaurichtung.UMGEKEHRT, new Reifen(43.2),
			new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private DualPositionAchse zAchse = new DualPositionAchse(null, MotorPort.C, Einbaurichtung.REGULAER, null, null);

	public Roboter() {}

	public void bereitePapierVor() throws InterruptedException {
		yAchse.setSpeed(100);
		yAchse.forward(800);
		this.currentPosition = new Position3D(0, 0, false);
	}

	public void entfernePapier() throws InterruptedException {
		zAchse.deaktiviere();
		yAchse.setSpeed(Integer.MAX_VALUE);
		yAchse.backward(2500);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.exit(0);
	}

	public Position3D getCurrentPosition() {
		return this.currentPosition;
	}

	public MultiPositionAchse getXAchse() {
		return this.xAchse;
	}

	public MultiPositionAchse getYAchse() {
		return this.yAchse;
	}
	
	public DualPositionAchse getZAchse() {
		return this.zAchse;
	}


	public void moveToHomePosition() throws InterruptedException {
		zAchse.deaktiviere();
		xAchse.setSpeed(100);
		while (!xAchse.isSensorAktiv()) {
			xAchse.backward();
		}
		xAchse.stop();
		xAchse.forward();
		Delay.msDelay(200);
		xAchse.stop();
		this.currentPosition = new Position3D(0, 0, false);
		this.resetTachoCounts();
	}

	public void moveToPosition(Position2D position2D, int mmSec) throws InterruptedException {
		this.moveToPosition(new Position3D(position2D, this.zAchse.isAktiv()), mmSec);
	}

	public void moveToPosition(Position3D position, int mmSec) throws InterruptedException {
		if (position.isZ())
			this.zAchse.aktiviere();
		else
			this.zAchse.deaktiviere();

		double deltaX = currentPosition.getX() - position.getX();
		double deltaY = currentPosition.getY() - position.getY();
		double hypo = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		double time = hypo / mmSec;

 		xAchse.getMotor().synchronizeWith(yAchse.getMotor());

		xAchse.setSpeed(deltaX / time);
		yAchse.setSpeed(deltaY / time);

		xAchse.getMotor().startSynchronization();

		xAchse.rotateMm(deltaX);
		yAchse.rotateMm(deltaY);

		xAchse.getMotor().endSynchronization();

		xAchse.getMotor().waitComplete();
		yAchse.getMotor().waitComplete();

		this.currentPosition = new Position3D(xAchse.getPositionFromTachoCount(), yAchse.getPositionFromTachoCount(),
				zAchse.isAktiv());
	}

	private void resetTachoCounts() {
		this.xAchse.resetTachoCount();
		this.yAchse.resetTachoCount();
		if (xAchse.getTachoCount() != 0 || yAchse.getTachoCount() != 0)
			throw new RuntimeException("Konnte Tachocount nicht zurücksetzen");
	}

	public void stop() {
		xAchse.stop();
		yAchse.stop();
		zAchse.stop();
	}

}
