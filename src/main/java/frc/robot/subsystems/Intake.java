
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;

public class Intake extends Subsystem {

    // Intake Motors
    private VictorSPX intakeMotor;

    // Intake Solenoids
    private Solenoid intakeDeploySol;

    public Intake() {
        // Intake Motors
        intakeMotor = new VictorSPX(9);
        intakeMotor.setInverted(true);
        intakeMotor.setNeutralMode(NeutralMode.Coast);

        // Intake Solenoids
        intakeDeploySol = new Solenoid(1);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopIntake() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setIntakeSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void deployIntake(boolean toggle) {
        intakeDeploySol.set(toggle);
    }
}