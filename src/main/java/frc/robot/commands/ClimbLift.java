
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class ClimbLift extends Command {

    private double m_climbLiftSpeed = 0;

    private Timer CJTimer;

    public ClimbLift(double climbLiftSpeed) {
        requires(Robot.climb);

        m_climbLiftSpeed = climbLiftSpeed;

        CJTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.climb.lockClimb(false);

        CJTimer.reset();
        CJTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.oi.climbOverride.get()) {
            if (CJTimer.get() >= Constants.climbMoveDelay) {
                Robot.climb.setClimbLiftSpeed(m_climbLiftSpeed);
            }
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
        Robot.climb.stopClimb();

        Robot.climb.lockClimb(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.climb.stopClimb();

        Robot.climb.lockClimb(true);
    }
}