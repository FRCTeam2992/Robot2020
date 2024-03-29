
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.groups.*;

/**
 * This command checks to see what drive mode the robot is in and starts the
 * coresponding vision process. It can stop the currently running vision command
 * group.
 */
public class VisionProcessing extends Command {

  private static CommandGroup visionGroup;

  private boolean mStopRunning = false;
  private boolean mIsFar = false;

  public VisionProcessing(boolean stopRunning, boolean isFar) {
    mStopRunning = stopRunning;
    mIsFar = isFar;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (mStopRunning) {
      if (visionGroup != null) {
        visionGroup.cancel();
      }
    } else {
      if (Robot.isLoadMode) {
        // visionGroup = new AutoVisionLoadStation();
        visionGroup = new AutoVisionShooter(mIsFar);
      } else {
        visionGroup = new AutoVisionShooter(mIsFar);
      }

      visionGroup.start();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
