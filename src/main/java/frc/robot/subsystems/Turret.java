
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.*;

public class Turret extends Subsystem {

    // Turret Motors
    private TalonSRX turretTalon;

    // Turret PID Controller
    public PIDController turretRotate;

    public Turret() {
        // Turret Motors
        turretTalon = new TalonSRX(10);
        turretTalon.setNeutralMode(NeutralMode.Brake);

        // Turret PID Controller
        turretRotate = new PIDController(Constants.turretP, Constants.turretI, Constants.turretD);
        turretRotate.setTolerance(Constants.turretTolerance);
        turretRotate.disableContinuousInput();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopTurret());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        // Update Dashboard
        SmartDashboard.putNumber("Turret Angle", getTurretAngle());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopTurret() {
        turretTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setTurretSpeed(double speed) {
        double setSpeed = speed;

        if ((setSpeed < 0 && getTurretAngle() <= Constants.turretMinEnd)
                || (setSpeed > 0 && getTurretAngle() > Constants.turretMaxEnd)) {
            setSpeed = 0;
        }

        turretTalon.set(ControlMode.PercentOutput, setSpeed);
    }

    public void goToAngle(double angle) {
        setTurretSpeed(turretRotate.calculate(getTurretAngle(), angle));
    }

    public double getTurretPostion() {
        double position = turretTalon.getSelectedSensorPosition() - Constants.turretOffset;

        if (position < 0) {
            position += 4096;
        }

        return position;
    }

    public double getTurretAngle() {
        return getTurretPostion() * (360.0 / 4096.0);
    }
}