
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class ColorSensorDeploy extends Command {

  private boolean m_deployToggle;

  public ColorSensorDeploy(boolean deployToggle) {
    m_deployToggle = deployToggle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (m_deployToggle) {
      Robot.colorWheel.setSensorServoAngle(Constants.colorSensorMaxAngle);
    } else {
      Robot.colorWheel.setSensorServoAngle(Constants.colorSensorMinAngle);
    }
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