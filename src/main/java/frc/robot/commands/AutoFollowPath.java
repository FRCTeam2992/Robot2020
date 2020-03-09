
package frc.robot.commands;

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
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoFollowPath extends Command {

  private boolean isDone = false;

  private Trajectory mTrajectory;
  private boolean mIsLoadMode = false;

  private Timer elpasedTimer;
  private double previousTime = 0;

  private Pose2d currentRobotPose;

  private RamseteController pathController;

  private DifferentialDriveKinematics driveKinematics;
  private DifferentialDriveWheelSpeeds previousWheelSpeeds;

  private SimpleMotorFeedforward driveFeedforward;

  public AutoFollowPath(Trajectory trajectory, boolean isLoadMode) {
    requires(Robot.driveTrain);

    mTrajectory = trajectory;
    mIsLoadMode = isLoadMode;

    pathController = new RamseteController(2.0, 0.7);

    driveKinematics = new DifferentialDriveKinematics(Constants.driveTrackWidthMeters);

    driveFeedforward = new SimpleMotorFeedforward(Constants.driveStaticGain, Constants.driveVelocityGain,
        Constants.driveAccelerationGain);

    elpasedTimer = new Timer();
  }

  public AutoFollowPath(Trajectory trajectory) {
    this(trajectory, false);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.setInterruptible(true);

    if (mTrajectory != null) {
      // Update Current Robot Pose
      updateCurrentRobotPose();

      // Set Tarjectory Start Position to Current Robot Position
      Transform2d transform = currentRobotPose.minus(mTrajectory.getInitialPose());

      mTrajectory = mTrajectory.transformBy(transform);

      // Set Trajectory Starting Speeds
      Trajectory.State initialState = mTrajectory.sample(0);
      previousWheelSpeeds = driveKinematics.toWheelSpeeds(new ChassisSpeeds(initialState.velocityMetersPerSecond, 0,
          initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));

      Robot.driveTrain.setBrakeMode(true);

      elpasedTimer.reset();
      elpasedTimer.start();
    } else {
      isDone = true;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (mTrajectory != null) {
      double currentTime = elpasedTimer.get();
      double deltaTime = currentTime - previousTime;

      updateCurrentRobotPose();

      Trajectory.State trajectoryState = mTrajectory.sample(currentTime);
      ChassisSpeeds adjustedSpeeds = pathController.calculate(currentRobotPose, trajectoryState);
      DifferentialDriveWheelSpeeds targetWheelSpeeds = driveKinematics.toWheelSpeeds(adjustedSpeeds);

      double leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
      double rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

      double leftFeedForward = driveFeedforward.calculate(leftSpeedSetpoint,
          (leftSpeedSetpoint - previousWheelSpeeds.leftMetersPerSecond) / deltaTime);
      double rightFeedForward = driveFeedforward.calculate(rightSpeedSetpoint,
          (rightSpeedSetpoint - previousWheelSpeeds.rightMetersPerSecond) / deltaTime);


      if (mIsLoadMode) {
        Robot.driveTrain.velocityDrive(-rightSpeedSetpoint, -rightFeedForward, -leftSpeedSetpoint, -leftFeedForward);
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
    return isDone || elpasedTimer.get() >= mTrajectory.getTotalTimeSeconds();
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

  public Pose2d invertPose(Pose2d pose) {
    return new Pose2d(pose.getTranslation().unaryMinus(), pose.getRotation());
  }

  public void updateCurrentRobotPose() {
    if (mIsLoadMode) {
      currentRobotPose = invertPose(Robot.driveTrain.getCurrentPoseMeters());
    } else {
      currentRobotPose = Robot.driveTrain.getCurrentPoseMeters();
    }
  }
}