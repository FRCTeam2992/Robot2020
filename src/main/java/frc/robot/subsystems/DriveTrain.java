
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.*;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;

public class DriveTrain extends Subsystem {

    // Drive Motors
    private CANSparkMax leftSparkDrive1;
    private CANSparkMax leftSparkDrive2;
    private CANSparkMax rightSparkDrive1;
    private CANSparkMax rightSparkDrive2;

    // Drive Encoders
    private CANEncoder leftDriveEncoder;
    private CANEncoder rightDriveEncoder;

    // Drive PID Controllers
    private CANPIDController leftDriveController;
    private CANPIDController rightDriveController;

    // Drive Solenoids
    private Solenoid driveGearShiftSol;

    // Robot Gyro
    public final AHRS navx;

    // Drive Odometery
    private DifferentialDriveOdometry driveOdometry;

    public DriveTrain() {
        // Drive Motors
        leftSparkDrive1 = new CANSparkMax(1, MotorType.kBrushless);
        leftSparkDrive1.setInverted(true);
        leftSparkDrive1.setIdleMode(IdleMode.kCoast);
        leftSparkDrive1.setSmartCurrentLimit(40);

        leftSparkDrive2 = new CANSparkMax(2, MotorType.kBrushless);
        leftSparkDrive2.follow(leftSparkDrive1);

        rightSparkDrive1 = new CANSparkMax(3, MotorType.kBrushless);
        rightSparkDrive1.setInverted(false);
        rightSparkDrive1.setIdleMode(IdleMode.kCoast);
        rightSparkDrive1.setSmartCurrentLimit(40);

        rightSparkDrive2 = new CANSparkMax(4, MotorType.kBrushless);
        rightSparkDrive2.follow(rightSparkDrive1);

        // Drive Encoders
        leftDriveEncoder = new CANEncoder(leftSparkDrive1, AlternateEncoderType.kQuadrature,
                Constants.driveEncoderPulses);

        rightDriveEncoder = new CANEncoder(rightSparkDrive1, AlternateEncoderType.kQuadrature,
                Constants.driveEncoderPulses);

        // Convert Encoder Positions to Meters
        leftDriveEncoder.setPositionConversionFactor(
                (Constants.driveWheelDiamterMeters * Math.PI) / Constants.driveEncoderPulses * 4);
        rightDriveEncoder.setPositionConversionFactor(
                (Constants.driveWheelDiamterMeters * Math.PI) / Constants.driveEncoderPulses * 4);

        // Convert Encoder Velocities to Meters Per Second
        leftDriveEncoder.setVelocityConversionFactor((Constants.driveWheelDiamterMeters * Math.PI) / 60);
        rightDriveEncoder.setVelocityConversionFactor((Constants.driveWheelDiamterMeters * Math.PI) / 60);

        // Drive PID Controllers
        leftDriveController = leftSparkDrive1.getPIDController();
        leftDriveController.setFeedbackDevice(leftDriveEncoder);
        leftDriveController.setP(Constants.driveP);
        leftDriveController.setI(Constants.driveI);
        leftDriveController.setD(Constants.driveD);
        leftDriveController.setFF(Constants.driveFeedForward);

        rightDriveController = rightSparkDrive1.getPIDController();
        rightDriveController.setFeedbackDevice(rightDriveEncoder);
        rightDriveController.setP(Constants.driveP);
        rightDriveController.setI(Constants.driveI);
        rightDriveController.setD(Constants.driveD);
        rightDriveController.setFF(Constants.driveFeedForward);

        // Drive Shift Solenoid
        driveGearShiftSol = new Solenoid(0, 0);

        // Robot Gyro
        navx = new AHRS(SPI.Port.kMXP);

        // Drive Odometry (Need to Reset Encoders First)
        resetEndoders();
        driveOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(navx.getYaw()));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveSticks());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        // Update Drive Odometry
        driveOdometry.update(Rotation2d.fromDegrees(navx.getYaw()), leftDriveEncoder.getPosition(),
                rightDriveEncoder.getPosition());
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

    /**
     * @param leftVelocity              the desired velocity of the left side in
     *                                  meters per second.
     * @param leftArbitraryFeedForward  the desired arbitrary feed forward of the
     *                                  left side in volts.
     * @param rightVelocity             the desired velocity of the right side in
     *                                  meters per second.
     * @param rightArbitraryFeedForward the desired arbitrary feed forward of the
     *                                  right side in volts.
     */
    public void velocityDrive(double leftVelocity, double leftArbitraryFeedForward, double rightVelocity,
            double rightArbitraryFeedForward) {
        leftDriveController.setReference(leftVelocity, ControlType.kVelocity, 0, leftArbitraryFeedForward,
                ArbFFUnits.kVoltage);
        rightDriveController.setReference(rightVelocity, ControlType.kVelocity, 0, rightArbitraryFeedForward,
                ArbFFUnits.kVoltage);
    }

    public void setDriveGear(boolean toggle) {
        driveGearShiftSol.set(toggle);
    }

    public void setBrakeMode(boolean toggleMode) {
        if (toggleMode) {
            leftSparkDrive1.setIdleMode(IdleMode.kBrake);
            rightSparkDrive1.setIdleMode(IdleMode.kBrake);
        } else {
            leftSparkDrive1.setIdleMode(IdleMode.kCoast);
            rightSparkDrive1.setIdleMode(IdleMode.kCoast);
        }
    }

    public void resetEndoders() {
        leftDriveEncoder.setPosition(0);
        rightDriveEncoder.setPosition(0);
    }

    public void resetOdometry() {
        resetEndoders();
        driveOdometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(navx.getYaw()));
    }

    public Pose2d getCurrentPoseMeters() {
        return driveOdometry.getPoseMeters();
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