
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.StopSorter;

public class Sorter extends Subsystem {

    // Sorter Motors
    private VictorSPX sorterVictor1;
    private VictorSPX sorterVictor2;

    public Sorter() {
        // Sorter Motors
        sorterVictor1 = new VictorSPX(6);
        sorterVictor1.setInverted(false);
        sorterVictor1.setNeutralMode(NeutralMode.Coast);

        sorterVictor2 = new VictorSPX(7);
        sorterVictor2.setInverted(false);
        sorterVictor2.setNeutralMode(NeutralMode.Coast);
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

    public void setSorterSpeed(double left, double right) {
        sorterVictor1.set(ControlMode.PercentOutput, left);
        sorterVictor2.set(ControlMode.PercentOutput, right);
    }

}
