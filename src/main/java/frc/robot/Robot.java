package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    
    CANSparkMax front_left = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax rear_left = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax front_right = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax rear_right = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

   SafeDrivetrain drivetrain = new SafeDrivetrain(
                                     new SpeedControllerGroup(front_left, rear_left),
						                       	 new SpeedControllerGroup(front_right, rear_right));


  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Left", front_left.get());
    SmartDashboard.putNumber("Right", front_right.get());
    SmartDashboard.getNumber("Max Jerk", 0);
    SmartDashboard.getNumber("Time Window", 0);

  }

  @Override
  public void autonomousInit() {
   
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
