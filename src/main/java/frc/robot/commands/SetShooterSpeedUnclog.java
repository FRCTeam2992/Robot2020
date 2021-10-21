
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Constants;
import frc.robot.Robot;

public class SetShooterSpeedUnclog extends Command {

    private double mShooterSpeed;

    private int oldShooterSpeed;
    private boolean wasRunning;

    public SetShooterSpeedUnclog(int shooterSpeed) {
        mShooterSpeed = shooterSpeed;
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
        oldShooterSpeed = Robot.shooter.shooterSetSpeed;
        wasRunning = Robot.shooter.isRunning();
        
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (wasRunning) {
            Robot.shooter.setShooterVelocity(oldShooterSpeed);
        } else if (Robot.shooter.getShooterRPM() < 400) {
            mShooterSpeed = -400;
            mShooterSpeed = (mShooterSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);
            Robot.shooter.setShooterVelocity(mShooterSpeed);
        }
        
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.shooter.shooterSetSpeed = oldShooterSpeed;
        if (!wasRunning) {
           // Scheduler.getInstance().add(new StartShooter());
        }
    }
}