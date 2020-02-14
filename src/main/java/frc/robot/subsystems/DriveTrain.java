
package frc.robot.subsystems;

import frc.robot.commands.*;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    // Drive Motors
    private CANSparkMax leftSparkDrive1;
    private CANSparkMax leftSparkDrive2;
    private CANSparkMax rightSparkDrive1;
    private CANSparkMax rightSparkDrive2;

    // Drive Solenoids
    private Solenoid driveGearShift;

    // Robot Gyro
    public final AHRS navx;

    public DriveTrain() {
        // Drive Motors
        leftSparkDrive1 = new CANSparkMax(1, MotorType.kBrushless);
        leftSparkDrive1.setInverted(true);
        leftSparkDrive1.setIdleMode(IdleMode.kCoast);

        leftSparkDrive2 = new CANSparkMax(2, MotorType.kBrushless);
        leftSparkDrive2.follow(leftSparkDrive1);

        rightSparkDrive1 = new CANSparkMax(3, MotorType.kBrushless);
        rightSparkDrive1.setInverted(false);
        rightSparkDrive1.setIdleMode(IdleMode.kCoast);

        rightSparkDrive2 = new CANSparkMax(4, MotorType.kBrushless);
        rightSparkDrive2.follow(rightSparkDrive1);

        // Drive Shift Solenoid
        driveGearShift = new Solenoid(0, 0);

        // Robot Gyro
        navx = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveSticks());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopDriveTrain() {
        leftSparkDrive1.set(0);
        rightSparkDrive1.set(0);
    }

    public void tankDrive(double left, double right) {
        left = Math.max(-1, Math.min(1, left));
        right = Math.max(-1, Math.min(1, right));

        leftSparkDrive1.set(left);
        rightSparkDrive1.set(right);
    }

    public void arcadeDrive(double moveValue, double rotateValue) {
        double leftMotorSpeed = 0;
        double rightMotorSpeed = 0;

        if (moveValue < 0) {
            leftMotorSpeed = moveValue - rotateValue;
            rightMotorSpeed = moveValue + rotateValue;
        } else {
            leftMotorSpeed = moveValue + rotateValue;
            rightMotorSpeed = moveValue - rotateValue;
        }

        double max = Math.max(Math.abs(leftMotorSpeed), Math.abs(rightMotorSpeed));

        if (max > 1.0) {
            leftMotorSpeed /= max;
            rightMotorSpeed /= max;
        }

        tankDrive(leftMotorSpeed, rightMotorSpeed);
    }

    public void setDriveGear(boolean toggle) {
        driveGearShift.set(toggle);
    }

    public void setIdleMode(IdleMode mode) {
        leftSparkDrive1.setIdleMode(mode);
        rightSparkDrive1.setIdleMode(mode);
    }

    public double calcGyroError(double heading) {
        return navx.getYaw() - heading;
    }

    public double scaleAngle(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }
}