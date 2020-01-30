
package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafeDrivetrain extends DifferentialDrive {
	private double maxJerk = 10;
	private Timer time = new Timer();
	private double lastPower = 0;
	private double lastTime = 0;
	private double timeWindow = 1;
	private double maxAcceleration = 0.5;
	private double thisAccel = 0;
	private double lastAccel = 0;
	private double thisJerk = 0;
	private double dt = 0;

	public SafeDrivetrain(SpeedController left, SpeedController right) {
		super(left, right);
		time.start();
		time.reset();
	}


	@Override
	public void arcadeDrive(double xSpeed, double zRotation) {

		dt = (time.get() - lastTime);
		if (dt > 0.5) {
			dt = 0.5;
			lastPower = 0;
			lastAccel = 0;
		}


		double limitPower = lastPower;
		SmartDashboard.putNumber("dt", dt);

		if (xSpeed != lastPower) {

			double useAcceleration = 0;

			// calculate jerk //
			thisJerk = (thisAccel - lastAccel) / dt;
			SmartDashboard.putNumber("thisJerk", thisJerk);
			// Jerk limit //
			if (thisJerk > maxJerk) {
				useAcceleration = lastAccel + (maxJerk * dt);
			}
			if (thisAccel < -maxJerk) {
				useAcceleration = lastAccel - (maxJerk * dt); 
			}
			
			// calculate acceleration //
			thisAccel = (xSpeed - lastPower) / dt;
			SmartDashboard.putNumber("thisAccel", thisAccel);

			useAcceleration = limit(useAcceleration, -maxAcceleration, maxAcceleration);

			// Acceleration limit //
			if (thisAccel > useAcceleration) {
				limitPower = lastPower + (useAcceleration * dt);
			}
			if (thisAccel < -useAcceleration) {
				limitPower = lastPower - (useAcceleration * dt);
			}


			// store data //
			thisAccel = (limitPower - lastPower) / dt;
			SmartDashboard.putNumber("true accel", thisAccel);

			thisJerk = (useAcceleration - lastAccel) / dt;
			SmartDashboard.putNumber("true jerk", thisJerk);

			lastPower = limitPower;
			lastAccel = thisAccel;
			lastTime = time.get();
		}

		SmartDashboard.putNumber("limit Power", limitPower);

		super.arcadeDrive(limitPower, zRotation, false);
		
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
