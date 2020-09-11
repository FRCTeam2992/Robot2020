
package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class Constants {
    // Drive Constants
    public static final int driveEncoderPulses = 2048;
    public static final double driveWheelDiamterMeters = 0.1524;
    public static final double driveTrackWidthMeters = 0.590804; // 23.26"

    public static final double straightDriveThreshold = 0.2;
    public static final double straightDriveP = 0.0;
    public static final double driveTurnDamp = 0.2;

    // Drive PID Constants
    public static final double driveP = 0.005; //2.42
    public static final double driveI = 0.0;
    public static final double driveD = 0.0;
    public static final double driveFeedForward = 0.0;

    public static final double driveStaticGain = 0.147;
    public static final double driveVelocityGain = 1.22;
    public static final double driveAccelerationGain = 0.0538;

    // Vision Constants
    public static final double limelightShooterAngle = 20.0;
    public static final double limelightLoadStationAngle = 175.0;
    public static final double cameraAngle = -4.5;
    public static final double cameraHeight = 42.0;
    public static final double goalHeight = 81.0;
    public static final double visionSteerP = 0.025;
    public static final int limelightShooterPipelineClose = 0;
    public static final int limelightShooterPipelineFar = 1;
    public static final int limelightLoadStationPipeline = 2;

    // Color Wheel Constants
    public static final int colorWheelEncoderPulses = 1024;
    public static final double colorWheelSpinRatio = 8.0;
    public static final double colorSensorMinAngle = 0.0;
    public static final double colorSensorMaxAngle = 180.0;
    public static final double colorWheelSpinRotations = 4.0;

    // Color Wheel Target Colors
    public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    // Shooter Constants
    public static final int deafaultShooterSpeed = 5200;
    public static final int shooterEncoderPulses = 2048;

    // Turret Constants
    public static final double turretP = 0.05;
    public static final double turretI = 0.0;
    public static final double turretD = 0.003;
    public static final double turretTolerance = 1.0;
    public static final int turretOffset = 2074;
    public static final int turretMinEnd = 40;
    public static final int turretMaxEnd = 320;

    // Climb Constants
    public static final double climbMoveDelay = 0.25;

    // Sorter Constants
    public static final double autoSorterSpeedChangeDelay = 3.0;
}