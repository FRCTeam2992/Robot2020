
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

    private TalonSRX turretTalon;

    private PIDController turretRotate;

    public Turret() {
        turretTalon = new TalonSRX(10);
        turretTalon.setNeutralMode(NeutralMode.Brake);

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
        SmartDashboard.putNumber("Turret Angle", getTurretAngle());
        SmartDashboard.putNumber("Turret Position", getTurretPostion());
        SmartDashboard.putNumber("Raw Turret Position", turretTalon.getSelectedSensorPosition());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopTurret() {
        turretTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setTurretSpeed(double speed) {
        double setSpeed = speed;

        if (setSpeed < 0 && getTurretAngle() <= Constants.turretMinEnd) {
            setSpeed = 0;
        } else if (setSpeed > 0 && getTurretAngle() > Constants.turretMaxEnd) {
            setSpeed = 0;
        }

        turretTalon.set(ControlMode.PercentOutput, setSpeed);
    }

    public void goToAngle(double angle) {
        setTurretSpeed(turretRotate.calculate(getTurretAngle(), angle));
    }

    public int getTurretPostion() {
        // return (turretTalon.getSelectedSensorPosition() - Constants.turretOffset) %
        // 4096;

        int position = turretTalon.getSelectedSensorPosition() - Constants.turretOffset;

        if (position < 0) {
            return 4096 + position;
        } else {
            return position;
        }
    }

    public double getTurretAngle() {
        return getTurretPostion() * (360.0 / 4096.0);
    }
}