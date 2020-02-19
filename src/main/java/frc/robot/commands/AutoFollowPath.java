
package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoFollowPath extends Command {

  private boolean isDone = false;

  private String mTrajectoryName = "";
  private boolean mIsLoadMode = false;

  private Timer elpasedTimer;
  private double previousTime = 0;

  private Pose2d currentRobotPose;

  private RamseteController pathController;
  private Trajectory trajectory;

  private DifferentialDriveKinematics driveKinematics;
  private DifferentialDriveWheelSpeeds previousWheelSpeeds;

  private SimpleMotorFeedforward driveFeedforward;

  public AutoFollowPath(String trajectoryName, boolean isLoadMode) {
    requires(Robot.driveTrain);

    mTrajectoryName = trajectoryName;
    mIsLoadMode = isLoadMode;

    pathController = new RamseteController(2.0, 0.7);

    driveKinematics = new DifferentialDriveKinematics(Constants.driveTrackWidthMeters);

    driveFeedforward = new SimpleMotorFeedforward(Constants.driveStaticGain, Constants.driveVelocityGain,
        Constants.driveAccelerationGain);
  }

  public AutoFollowPath(String trajectoryName) {
    this(trajectoryName, false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setInterruptible(true);

    try {
      // Get Trajectory Path from Deploy Directory
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath()
          .resolve("paths/" + mTrajectoryName + ".wpilib.json");
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

      // Update Current Robot Pose
      updateCurrentRobotPose();

      // Set Tarjectory Start Position to Current Robot Position
      Transform2d transform = currentRobotPose.minus(trajectory.getInitialPose());
      trajectory = trajectory.transformBy(transform);

      // Set Trajectory Starting Speeds
      Trajectory.State initialState = trajectory.sample(0);
      previousWheelSpeeds = driveKinematics.toWheelSpeeds(new ChassisSpeeds(initialState.velocityMetersPerSecond, 0,
          initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory " + mTrajectoryName, ex.getStackTrace());
      isDone = true;
    }

    Robot.driveTrain.setDriveGear(false);
    Robot.driveTrain.setBrakeMode(true);

    elpasedTimer.reset();
    elpasedTimer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (trajectory != null) {
      double currentTime = elpasedTimer.get();
      double deltaTime = currentTime - previousTime;

      updateCurrentRobotPose();

      Trajectory.State trajectoryState = trajectory.sample(currentTime);
      ChassisSpeeds adjustedSpeeds = pathController.calculate(Robot.driveTrain.getCurrentPoseMeters(), trajectoryState);
      DifferentialDriveWheelSpeeds targetWheelSpeeds = driveKinematics.toWheelSpeeds(adjustedSpeeds);

      double leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
      double rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

      double leftFeedForward = driveFeedforward.calculate(leftSpeedSetpoint,
          (leftSpeedSetpoint - previousWheelSpeeds.leftMetersPerSecond) / deltaTime);
      double rightFeedForward = driveFeedforward.calculate(rightSpeedSetpoint,
          (rightSpeedSetpoint - previousWheelSpeeds.rightMetersPerSecond) / deltaTime);

      if (mIsLoadMode) {
        Robot.driveTrain.velocityDrive(-rightSpeedSetpoint, rightFeedForward, -leftSpeedSetpoint, leftFeedForward);
      } else {
        Robot.driveTrain.velocityDrive(leftSpeedSetpoint, leftFeedForward, rightSpeedSetpoint, rightFeedForward);
      }

      previousWheelSpeeds = targetWheelSpeeds;
      previousTime = currentTime;
    } else {
      isDone = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone || elpasedTimer.get() >= trajectory.getTotalTimeSeconds();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stopDriveTrain();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.driveTrain.stopDriveTrain();
  }

  public Pose2d invertPoseHeading(Pose2d pose) {
    return new Pose2d(pose.getTranslation(), pose.getRotation().unaryMinus());
  }

  public void updateCurrentRobotPose() {
    if (mIsLoadMode) {
      currentRobotPose = invertPoseHeading(Robot.driveTrain.getCurrentPoseMeters());
    } else {
      currentRobotPose = Robot.driveTrain.getCurrentPoseMeters();
    }
  }
}
