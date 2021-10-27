
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.*;

import java.io.IOException;
import java.nio.file.Path;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

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

    // Motion Trajectories
    public Trajectory RightTrench;
    public Trajectory CenterTrench;

    public DriveTrain() {
        // Drive Motors
        leftSparkDrive1 = new CANSparkMax(1, MotorType.kBrushless);
        leftSparkDrive1.setInverted(false);
        leftSparkDrive1.setIdleMode(IdleMode.kCoast);
        leftSparkDrive1.setSmartCurrentLimit(45);
        leftSparkDrive1.setOpenLoopRampRate(0.6);

        leftSparkDrive2 = new CANSparkMax(2, MotorType.kBrushless);
        leftSparkDrive2.follow(leftSparkDrive1);

        rightSparkDrive1 = new CANSparkMax(3, MotorType.kBrushless);
        rightSparkDrive1.setInverted(true);
        rightSparkDrive1.setIdleMode(IdleMode.kCoast);
        rightSparkDrive1.setSmartCurrentLimit(45);
        rightSparkDrive1.setOpenLoopRampRate(0.6);

        rightSparkDrive2 = new CANSparkMax(4, MotorType.kBrushless);
        rightSparkDrive2.follow(rightSparkDrive1);

        // Drive Encoders
        leftDriveEncoder = new CANEncoder(leftSparkDrive1, AlternateEncoderType.kQuadrature,
                Constants.driveEncoderPulses * 4);
        leftDriveEncoder.setVelocityConversionFactor(1);
        leftDriveEncoder.setPositionConversionFactor(1);
        leftDriveEncoder.setInverted(false);

        rightDriveEncoder = new CANEncoder(rightSparkDrive1, AlternateEncoderType.kQuadrature,
                Constants.driveEncoderPulses * 4);
        rightDriveEncoder.setVelocityConversionFactor(1);
        rightDriveEncoder.setPositionConversionFactor(1);
        rightDriveEncoder.setInverted(true);

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
        driveOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(-navx.getYaw()));

        // Motion Trajectories
        getMotionPaths();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveSticks());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        // Update Drive Odometry
        driveOdometry.update(Rotation2d.fromDegrees(-navx.getYaw()), rotationsToMeters(leftDriveEncoder.getPosition()),
                rotationsToMeters(rightDriveEncoder.getPosition()));

        // Update Dashboard
        SmartDashboard.putNumber("Left Drive Encoder", leftDriveEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Encoder", rightDriveEncoder.getPosition());
        SmartDashboard.putNumber("Gyro Yaw", navx.getYaw());
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


    public void autoTurnArcade(double moveValue, double rotateValue, double POVValue) {
        // Calculate how far we are from the right heading
        double headingError = calcGyroError(POVValue);

        if (Math.abs(headingError) < 2.0) {
            // We are close enough so just pass joysticks in raw
            arcadeDrive(moveValue, rotateValue);
        }
        else {
            // Normalize headingError
            while (headingError < -180.0) {
                headingError += 360.0;
            }
            while (headingError > 180.0) {
                headingError -= 360.0;
            }

            // Calculate a compute rotateValue based on headingError
            final double autoTurnP = 0.05;     // Anything over 20 degree error is full turn speed
            rotateValue = autoTurnP * headingError;
            rotateValue = Math.max(-1.0, Math.min(1.0, rotateValue));
            arcadeDrive(moveValue, rotateValue);        // Drive with the computed turn correction
        }

    }

    public void arcadeDrive(double moveValue, double rotateValue) {
        double leftMotorSpeed = moveValue - rotateValue;
        double rightMotorSpeed = moveValue + rotateValue;

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
        leftVelocity = metersToRotations(leftVelocity * 60);
        leftDriveController.setReference(leftVelocity, ControlType.kVelocity, 0, leftArbitraryFeedForward,
                ArbFFUnits.kVoltage);

        rightVelocity = metersToRotations(rightVelocity * 60);
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

    public double rotationsToMeters(double rotations) {
        return rotations * (Math.PI * Constants.driveWheelDiamterMeters);
    }

    public double metersToRotations(double meters) {
        return meters / (Math.PI * Constants.driveWheelDiamterMeters);
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

    public void getMotionPaths() {
        // Right Trench
        Path RightTrenchPath = Filesystem.getDeployDirectory().toPath()
                .resolve("paths/output/output/RightTrench.wpilib.json");

        // Center Trench
        Path CenterTrenchPath = Filesystem.getDeployDirectory().toPath()
                .resolve("paths/output/output/CenterTrench.wpilib.json");

        try {
            RightTrench = TrajectoryUtil.fromPathweaverJson(RightTrenchPath);
            CenterTrench = TrajectoryUtil.fromPathweaverJson(CenterTrenchPath);
        } catch (IOException e) {
            DriverStation.reportError("Unable to open trajectory", e.getStackTrace());
            e.printStackTrace();
        }
    }
}