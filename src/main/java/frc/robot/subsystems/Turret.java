
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.encoder.AS5600EncoderPwm;
import frc.robot.Constants;
import frc.robot.commands.*;

public class Turret extends Subsystem {

    private TalonSRX turretTalon;

    private AS5600EncoderPwm turretEncoder;

    private PIDController turretRotate;

    public Turret() {
        turretTalon = new TalonSRX(10);
        turretTalon.setNeutralMode(NeutralMode.Brake);

        turretEncoder = new AS5600EncoderPwm(turretTalon.getSensorCollection());

        turretRotate = new PIDController(Constants.turretP, Constants.turretI, Constants.turretD);
        turretRotate.setTolerance(Constants.turretTolerance);
        turretRotate.disableContinuousInput();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new RotateTurretStop());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Encoder Position", turretEncoder.getAngle());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopTurret() {
        turretTalon.set(ControlMode.PercentOutput, 0);
    }

    public double getTurretAngle() {
        return turretEncoder.getAngle();
    }

    public void setTurretSpeed(double speed) {
        turretTalon.set(ControlMode.PercentOutput, speed);
    }

    public void goToAngle(double angle) {
        setTurretSpeed(turretRotate.calculate(turretEncoder.getAngle(), angle));
    }

    public boolean atTarget() {
        return turretRotate.atSetpoint();
    }
}