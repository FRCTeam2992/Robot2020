
package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.RamseteController;
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

  private Timer elpasedTimer;

  private double mTimeout;

  private RamseteController pathController;
  private Trajectory trajectory;

  private DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(
      Constants.driveTrackWidthMeters);

  public AutoFollowPath(String trajectoryName, double timeout) {
    requires(Robot.driveTrain);

    mTrajectoryName = trajectoryName;

    mTimeout = timeout;

    pathController = new RamseteController(2.0, 0.7);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath()
          .resolve("paths/" + mTrajectoryName + ".wpilib.json");
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
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
      Trajectory.State trajectoryState = trajectory.sample(elpasedTimer.get());
      ChassisSpeeds adjustedSpeeds = pathController.calculate(Robot.driveTrain.getCurrentPoseMeters(), trajectoryState);
      DifferentialDriveWheelSpeeds wheelSpeeds = driveKinematics.toWheelSpeeds(adjustedSpeeds);

      Robot.driveTrain.velocityDrive(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
    } else {
      isDone = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone || pathController.atReference() || elpasedTimer.get() >= mTimeout;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stopDriveTrain();
    Robot.driveTrain.setBrakeMode(false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.driveTrain.stopDriveTrain();
    Robot.driveTrain.setBrakeMode(false);
  }
}
