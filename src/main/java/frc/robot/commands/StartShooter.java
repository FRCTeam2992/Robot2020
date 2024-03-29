
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class StartShooter extends Command {

    private double mShooterSpeed = 0;

    public StartShooter() {
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Convert RPM to Ticks per 100ms
        mShooterSpeed = Robot.shooter.shooterSetSpeed;

        mShooterSpeed = (mShooterSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);

        Robot.shooter.setShooterVelocity(mShooterSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.shooter.stopShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.shooter.stopShooter();
    }
}