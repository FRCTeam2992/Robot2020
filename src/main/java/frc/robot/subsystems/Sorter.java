
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.StopSorter;

public class Sorter extends Subsystem {

    // Sorter Motors
    private VictorSPX sorterVictor1;
    private VictorSPX sorterVictor2;

    // Sorter Sensor
    public DigitalInput sorterBallSensor;
    public DigitalInput sorterBallSensor2;

    public Sorter() {
        // Sorter Motors
        sorterVictor1 = new VictorSPX(6);
        sorterVictor1.setInverted(false);
        sorterVictor1.setNeutralMode(NeutralMode.Coast);

        sorterVictor2 = new VictorSPX(7);
        sorterVictor2.setInverted(false);
        sorterVictor2.setNeutralMode(NeutralMode.Coast);

        // Sorter Sensor (Shared with Bottom Lift)
        sorterBallSensor = Robot.bottomLift.liftBallSensor;
        sorterBallSensor2 = Robot.bottomLift.liftBallSensor2;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopSorter());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopSorter() {
        sorterVictor1.set(ControlMode.PercentOutput, 0);
        sorterVictor2.set(ControlMode.PercentOutput, 0);
    }

    public void setSorterSpeed(double left, double right) {
        sorterVictor1.set(ControlMode.PercentOutput, left);
        sorterVictor2.set(ControlMode.PercentOutput, right);
    }
}