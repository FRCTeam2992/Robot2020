
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShooterAtSetpoint extends Command {

  private double mTimeout = 0;

  private Timer timeoutTimer;

  public ShooterAtSetpoint(double timeout) {
    mTimeout = timeout;

    timeoutTimer = new Timer();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeoutTimer.reset();
    timeoutTimer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.shooter.shooterSetSpeed - Robot.shooter.getShooterRPM()) <= 50
        || timeoutTimer.get() >= mTimeout;
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