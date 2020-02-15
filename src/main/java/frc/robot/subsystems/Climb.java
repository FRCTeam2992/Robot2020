
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;

public class Climb extends Subsystem {

    // Climb Motors
    private TalonSRX climbLiftMtr;
    private VictorSPX climbSlideMtr;

    // Climb Solenoids
    private Solenoid climbLockSol;

    public Climb() {
        // Climb Motors
        climbLiftMtr = new TalonSRX(13);
        climbSlideMtr = new VictorSPX(14);

        // Climb Solenoids
        climbLockSol = new Solenoid(2);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopClimb());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopClimb() {
        climbLiftMtr.set(ControlMode.PercentOutput, 0);
        climbSlideMtr.set(ControlMode.PercentOutput, 0);
    }

    public void setClimbLiftSpeed(double speed) {
        climbLiftMtr.set(ControlMode.PercentOutput, speed);
    }

    public void setClimbSlideSpeed(double speed) {
        climbSlideMtr.set(ControlMode.PercentOutput, speed);
    }

    public void lockClimb(boolean toggle) {
        climbLockSol.set(!toggle);
    }
}