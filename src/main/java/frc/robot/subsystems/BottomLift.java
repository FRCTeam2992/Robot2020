
package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BottomLift extends Subsystem {

    // Bottom Lift Motors
    public VictorSPX bottomLiftMotor;

    // Bottom Lift Sensors
    public DigitalInput liftBallSensor;

    public BottomLift() {
        // Bottom Lift Motors
        bottomLiftMotor = new VictorSPX(8);
        bottomLiftMotor.setInverted(false);
        bottomLiftMotor.setNeutralMode(NeutralMode.Coast);

        // Bottom Lift Sensors
        liftBallSensor = new DigitalInput(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopBottomLift());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopBottomLift() {
        bottomLiftMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setBottomLiftSpeed(double speed) {
        bottomLiftMotor.set(ControlMode.PercentOutput, speed);
    }
}