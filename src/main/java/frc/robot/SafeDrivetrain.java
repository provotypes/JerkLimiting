
package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafeDrivetrain extends DifferentialDrive {
private double maxJerk;
private Timer time;
private double lastPower;
private double timeWindow = 1;
private double maxAcceleration;

    public SafeDrivetrain(SpeedController left, SpeedController right){
        super(left, right);
        time.start();
        time.reset();
    }


@Override
public void arcadeDrive(double xSpeed, double zRotation) {

    // if(xSpeed > 1/2*maxJerk*time^2-1/2*maxJerk((time-timeWindow)^2)){
    //     xSpeed = 1/2*maxJerk*time^2-1/2*maxJerk((time-timeWindow)^2);
    //     // time.
    // }


    super.arcadeDrive(xSpeed, zRotation);
}
    public void setMaxJerk(double maxJerk) {
            this.maxJerk = maxJerk;
        }

    public double getMaxJerk() {
        return maxJerk;
    }

    public void setTimeWindow(){
        this.timeWindow = timeWindow;
    }
    
    public double getTimeWindow(){
        return timeWindow;
    }





}
