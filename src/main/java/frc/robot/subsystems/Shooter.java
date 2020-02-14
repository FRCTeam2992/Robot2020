
package frc.robot.subsystems;

import frc.lib.util.ShooterSpeeds;
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
    public int shooterSetSpeed = Constants.deafaultShooterSpeed;

    // Shooter Speed List
    public ShooterSpeeds shooterSpeedList = new ShooterSpeeds();

    public Shooter() {
        // Shooter Motors
        shooterTalon = new TalonSRX(11);
        shooterTalon.setInverted(false);
        shooterTalon.setSensorPhase(false);
        shooterTalon.setNeutralMode(NeutralMode.Coast);

        shooterVictor = new VictorSPX(12);
        shooterVictor.setInverted(false);
        shooterVictor.follow(shooterTalon);

        // Shooter Speed List
        shooterSpeedList.addSetpoint(60, 4000);
        shooterSpeedList.addSetpoint(120, 4500);
        shooterSpeedList.addSetpoint(250, 5000);
        shooterSpeedList.addSetpoint(400, 5500);
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

    /**
     * @param velocity speed in position change per 100ms
     */
    public void setShooterVelocity(double velocity) {
        shooterTalon.set(ControlMode.Velocity, velocity);
    }

    public int getShooterRPM() {
        return (shooterTalon.getSelectedSensorVelocity() * 600) / (Constants.shooterEncoderPulses * 4);
    }
}