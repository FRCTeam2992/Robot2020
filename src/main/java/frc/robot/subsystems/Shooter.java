
package frc.robot.subsystems;

import frc.lib.util.ShooterSpeeds;
import frc.robot.Constants;
import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        shooterVictor.setInverted(true);
        shooterVictor.follow(shooterTalon);

        // Shooter Speed List
        shooterSpeedList.addSetpoint(65, 5100);
        shooterSpeedList.addSetpoint(100, 5300);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopShooter());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        // Update Dashboard
        SmartDashboard.putNumber("Shooter Set Speed", shooterSetSpeed);
        SmartDashboard.putNumber("Shooter Velocity RPM", getShooterRPM());
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


    public boolean isRunning() {
        return (shooterTalon.getControlMode() == ControlMode.Velocity); 
    }

    public double getShooterRPM() {
        return (shooterTalon.getSelectedSensorVelocity() * 600.0) / (Constants.shooterEncoderPulses * 4.0);
    }
}