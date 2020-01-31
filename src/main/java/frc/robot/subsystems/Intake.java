
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

    // Intake Motors (Shared with Color Wheel)
    public TalonSRX intakeTalon;

    // Intake Solenoids
    private Solenoid intakeDeploySol;

    public Intake() {
        // Intake Motors (Shared with Color Wheel)
        intakeTalon = new TalonSRX(5);
        intakeTalon.setInverted(false);
        intakeTalon.setNeutralMode(NeutralMode.Coast);

        // Intake Solenoids
        intakeDeploySol = new Solenoid(1);
    }

    @Override
    public void initDefaultCommand() {
        // No Default Command - Intake Shares a Motor with Color Wheel
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopIntake() {
        intakeTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setIntakeSpeed(double speed) {
        intakeTalon.set(ControlMode.PercentOutput, speed);
    }

    public void deployIntake(boolean toggle) {
        intakeDeploySol.set(toggle);
    }
}