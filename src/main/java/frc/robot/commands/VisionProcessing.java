
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.groups.AutoShooterShoot;

/**
 * This Command makes sure the commandGroups using Limelight get cancelled and
 * don't conflict Fix: This command is called in OI by a single button make
 * better
 */
public class VisionProcessing extends Command {

  private static CommandGroup visionGroup;

  private boolean m_toggle;

  public VisionProcessing(boolean toggle) {
    m_toggle = toggle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (m_toggle) {
      if (Robot.isLoadMode) {

      } else {
        visionGroup = new AutoShooterShoot();
      }

      visionGroup.start();
    } else {
      if (visionGroup != null) {
        visionGroup.cancel();
      }
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
