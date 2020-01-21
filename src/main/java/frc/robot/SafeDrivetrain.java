
package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafeDrivetrain extends DifferentialDrive {
	private double maxJerk = 10;
	private Timer time = new Timer();
	private double lastPower = 0;
	private double lastLastPower = 0; // joystick values are in finite timestamps...
	private double lastTime = 0;
	private double lastLastTime = 0;
	private double timeWindow = 1;
	private double maxAcceleration = 0.5;
	private double thisAccel;
	private double dt = 0;

	public SafeDrivetrain(SpeedController left, SpeedController right){
		super(left, right);
		time.start();
		time.reset();
	}


	@Override
	public void arcadeDrive(double xSpeed, double zRotation) {
		
		dt = (time.get() - lastTime);
		SmartDashboard.putNumber("dt", dt);
		double limitPower = lastPower; //fix
		SmartDashboard.putNumber("last Power", lastPower);

		if (xSpeed != lastPower) {
			
			thisAccel = (xSpeed - lastPower) / dt;
			SmartDashboard.putNumber("thisAccel", thisAccel);

			if (thisAccel > maxAcceleration) {
				limitPower = lastPower + (maxAcceleration * dt);
			}
			if (thisAccel < -maxAcceleration) {
				limitPower = lastPower - (maxAcceleration * dt);
			}

			thisAccel = (limitPower - lastPower) / dt;
			SmartDashboard.putNumber("true accel?", thisAccel);

			lastPower = limitPower;
			lastTime = time.get();
		}

		SmartDashboard.putNumber("limit Power", limitPower);

		super.arcadeDrive(limitPower, zRotation);
	}


	public void aarcadeDrive(double xPower, double zRotation) {

		double thisdt = (time.get() - lastTime);
		if (thisdt > 0.5) {
			thisdt = 0.5;
		}
		SmartDashboard.putNumber("dt", dt);

		SmartDashboard.putNumber("last Power", lastPower);
		double limitPower = 0;

		thisAccel = (lastPower - lastLastPower) / dt;

		if (thisAccel > maxAcceleration) {
			SmartDashboard.putBoolean("if", true);
			limitPower = lastPower + (maxAcceleration * thisdt);
		}
		if (thisAccel < -maxAcceleration) {
			SmartDashboard.putBoolean("if", false);
			limitPower = lastPower - (maxAcceleration * thisdt);
		}
		SmartDashboard.putNumber("(maxAcceleration * thisdt)", (maxAcceleration * thisdt));

		SmartDashboard.putNumber("thisAccel", thisAccel);
		thisAccel = (limitPower - lastPower) / thisdt;
		SmartDashboard.putNumber("true accel?", thisAccel);


		// // if(xPower > 1/2*maxJerk*time^2-1/2*maxJerk((time-timeWindow)^2)){
		// //     xPower = 1/2*maxJerk*time^2-1/2*maxJerk((time-timeWindow)^2);
		// //     // time.
		// // }

		SmartDashboard.putNumber("limit Power", limitPower);
		super.arcadeDrive(limitPower, 0);

		if (xPower != lastPower) {

			lastLastPower = lastPower;
			lastLastTime = lastTime;

			lastPower = limitPower;
			lastTime = time.get();
			dt = lastTime - lastLastTime;
		}

	}

	public void setMaxJerk(double maxJerk) {
			this.maxJerk = maxJerk;
		}

	public double getMaxJerk() {
		return maxJerk;
	}

	public void setMaxAcceleration(double maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setTimeWindow(double timeWindow) {
		this.timeWindow = timeWindow;
	}
	
	public double getTimeWindow() {
		return timeWindow;
	}


	private double limit(double in, double low, double high) {

		if (in > high) {
			return high;
		}
		if (in < low) {
			return low;
		}

		return in;
	}


}
