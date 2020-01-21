package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

	Joystick j = new Joystick(0);

	CANSparkMax frontLeft = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax rearLeft = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax frontRight = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
	CANSparkMax rearRight = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

	

	SafeDrivetrain drivetrain = new SafeDrivetrain(
								new SpeedControllerGroup(frontLeft, rearLeft),
											new SpeedControllerGroup(frontRight, rearRight));


	@Override
	public void robotInit() {

		frontLeft.setIdleMode(IdleMode.kBrake);
		rearLeft.setIdleMode(IdleMode.kBrake);
		frontRight.setIdleMode(IdleMode.kBrake);
		rearRight.setIdleMode(IdleMode.kBrake);

		frontLeft.setOpenLoopRampRate(0);
		rearLeft.setOpenLoopRampRate(0);
		frontRight.setOpenLoopRampRate(0);
		rearRight.setOpenLoopRampRate(0);

		SmartDashboard.putNumber("Max Jerk", 10);
		SmartDashboard.putNumber("Time Window", 0);
		SmartDashboard.putNumber("Max Accel", 0.5);

	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Left", -frontLeft.get());
		SmartDashboard.putNumber("Right", frontRight.get());

		// SmartDashboard.putNumber("stick", j.getRawAxis(1));

		drivetrain.setMaxJerk(SmartDashboard.getNumber("Max Jerk", 0));
		drivetrain.setTimeWindow(SmartDashboard.getNumber("Time Window", 0));
		drivetrain.setMaxAcceleration(SmartDashboard.getNumber("Max Accel", 10));

	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void teleopPeriodic() {
		drivetrain.arcadeDrive(j.getRawAxis(1), 0);
	}

	@Override
	public void testPeriodic() {
	}
}
