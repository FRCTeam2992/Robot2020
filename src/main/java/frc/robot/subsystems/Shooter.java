
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

    // Shooter Motors
    private TalonSRX shooterTalon;
    private VictorSPX shooterVictor;

    // Shooter Set Speed
    private int shooterSetSpeed = Constants.deafaultShooterSpeed;

    public Shooter() {
        // Shooter Motors
        shooterTalon = new TalonSRX(11);
        shooterTalon.setInverted(false);
        shooterTalon.setSensorPhase(false);
        shooterTalon.setNeutralMode(NeutralMode.Coast);

        shooterVictor = new VictorSPX(12);
        shooterVictor.setInverted(false);
        shooterVictor.follow(shooterTalon);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopShooter());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopShooter() {
        shooterTalon.set(ControlMode.PercentOutput, 0);
    }

    public int getShooterSetSpeed() {
        return shooterSetSpeed;
    }

    public void setShooterSetSpeed(int speed) {
        shooterSetSpeed = speed;
    }

    public void setShooterVelocity(double speed) {
        shooterTalon.set(ControlMode.Velocity, speed);
    }

    public int getShooterRPM() {
        return (shooterTalon.getSelectedSensorVelocity() * 600) / (Constants.shooterEncoderPulses * 4);
    }
}