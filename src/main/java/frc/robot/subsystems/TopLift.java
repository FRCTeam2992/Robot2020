
package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TopLift extends Subsystem {

    // Top Lift Motors (Shared with Color Wheel)
    public TalonSRX topLiftMotor;

    public TopLift() {
        // Top Lift Motors (Shared with Color Wheel)
        topLiftMotor = new TalonSRX(5);
        topLiftMotor.setInverted(false);
        topLiftMotor.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopTopLiftAndWheel());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopTopLift() {
        topLiftMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setTopLiftSpeed(double speed) {
        topLiftMotor.set(ControlMode.PercentOutput, speed);
    }
}