
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopClimbUp extends Command {

    private double mHoldSpeed = 0;
    private double mHoldTime = 0;

    private Timer holdTimer;

    public StopClimbUp(double holdSpeed, double holdTime) {
        requires(Robot.climb);

        mHoldSpeed = holdSpeed;
        mHoldTime = holdTime;

        holdTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        holdTimer.reset();
        holdTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (holdTimer.get() < mHoldTime && Robot.oi.climbOverride.get()) {
            Robot.climb.setClimbLiftSpeed(mHoldSpeed);
        } else {
            Robot.climb.stopClimb();
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
    }
}